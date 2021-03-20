package main;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

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
    }
    @OnClose
    public void onClose(Session session) 
    {
        System.out.println("onClose::" +  session.getId());
    }
    
    @OnMessage
    public void onMessage(String message, Session session) 
    {
        System.out.println("onMessage::From=" + session.getId() + " Message=" + message);
        Set<Session> sessions=session.getOpenSessions();
        Iterator<Session> iterator=sessions.iterator();
        Session nextSession=null;
        while(iterator.hasNext())
        {
        	nextSession=iterator.next();
        	try 
        	{
        		nextSession.getBasicRemote().sendText(message);
        	}
        	catch(IOException e)
        	{
        		e.printStackTrace();
        	}
        }
    }
    
    @OnError
    public void onError(Throwable t) {
        System.out.println("onError::" + t.getMessage());
    }

}
