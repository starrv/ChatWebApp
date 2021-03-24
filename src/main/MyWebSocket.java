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
    	session.getUserProperties().put("username", "user-"+session.getOpenSessions().size());
        System.out.println("User "+session.getUserProperties().get("username")+" with session id "+session.getId()+" is connected");  
    }
    @OnClose
    public void onClose(Session session) 
    {
        System.out.println("User "+session.getUserProperties().get("username")+" with session id "+session.getId()+" is disconnected");
    }
    
    @OnMessage
    public void onMessage(String message, Session session) 
    {
        Set<Session> sessions=session.getOpenSessions();
        Iterator<Session> iterator=sessions.iterator();
        Session nextSession=null;
        while(iterator.hasNext())
        {
        	nextSession=iterator.next();
        	try 
        	{
        		System.out.println("Message from "+session.getUserProperties().get("username")+": "+message);
        		nextSession.getBasicRemote().sendText("Message from "+session.getUserProperties().get("username")+": "+message);
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
