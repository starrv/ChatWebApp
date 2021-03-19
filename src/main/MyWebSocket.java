package main;

import java.io.IOException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/endpoint")
public class MyWebSocket 
{
	
    @OnOpen
    public void onOpen(Session session) 
    {
        System.out.println("onOpen::" + session.getId());   
        System.out.println(session.getOpenSessions().size()+" clients connected to chat");
    }
    @OnClose
    public void onClose(Session session) 
    {
        System.out.println("onClose::" +  session.getId());
        System.out.println(session.getOpenSessions().size()+" clients connected to chat");
    }
    
    @OnMessage
    public void onMessage(String message, Session session) 
    {
        System.out.println("onMessage::From=" + session.getId() + " Message=" + message);
        try {
            session.getBasicRemote().sendText("Hello Client " + session.getId() + "!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @OnError
    public void onError(Throwable t) {
        System.out.println("onError::" + t.getMessage());
    }

}
