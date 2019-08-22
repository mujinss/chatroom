package edu.udacity.java.nano.chat;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocket Server
 *
 * @see ServerEndpoint WebSocket Client
 * @see Session   WebSocket Session
 */

@Component
@ServerEndpoint("/chat")
public class WebSocketChatServer {

    /**
     * All chat sessions.
     */
    private static Map<String, Session> onlineSessions = new ConcurrentHashMap<>();
    private static Map<String, String> onlineUsers = new ConcurrentHashMap<>();

    private static void sendMessageToAll(String msg) {
        System.out.println(msg);
        onlineSessions.forEach((key, session) -> {
            try {
                session.getBasicRemote().sendText(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //TODO: add send message method.
    }

    /**
     * Open connection, 1) add session, 2) add user.
     */
    @OnOpen
    public void onOpen(Session session) {
        //String numOfSession = Integer.toString(onlineSessions.size());
        onlineSessions.put(session.getId(), session);
        onlineUsers.put(session.getId(), session.getQueryString());
        Message msg = new Message();
        msg.setUsername(session.getQueryString().split("=")[1]);
        msg.setMessage("logged in");
        msg.setOnlineCount(Integer.toString(onlineUsers.size()));
        msg.setType("SPEAK");
        sendMessageToAll(JSON.toJSONString(msg));

        //TODO: add on open connection.
    }

    /**
     * Send message, 1) get username and session, 2) send message to all.
     */
    @OnMessage
    public void onMessage(Session session, String jsonStr) throws IOException {
        Message newMsg = JSON.parseObject(jsonStr, Message.class);
        newMsg.setType("SPEAK");
        newMsg.setOnlineCount(Integer.toString(onlineUsers.size()));
        //String sessionId = onlineSessions.forEach();
        sendMessageToAll(JSON.toJSONString(newMsg));
        //TODO: add send message.
    }

    /**
     * Close connection, 1) remove session, 2) update user.
     */
    @OnClose
    public void onClose(Session session) {
        Iterator<Map.Entry<String, Session>> iterator = onlineSessions.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = iterator.next();
            if (session.equals(entry.getValue())) {
                iterator.remove();
                String username = onlineUsers.get(session.getId());
                onlineUsers.remove(session.getId());
                Message newMsg = new Message();
                newMsg.setType("SPEAK");
                newMsg.setOnlineCount(Integer.toString(onlineUsers.size()));
                newMsg.setUsername(username);
                newMsg.setMessage("has left the room");
                sendMessageToAll(JSON.toJSONString(newMsg));
            }
        }


        //TODO: add close connection.
    }

    /**
     * Print exception.
     */
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

}
