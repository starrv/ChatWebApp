package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/endpoint")
public class WebSocketServer 
{
	
    @OnOpen
    public void onOpen(Session session) 
    {
    	session.getUserProperties().put("username", "user-"+session.getOpenSessions().size());
        System.out.println("User "+session.getUserProperties().get("username")+" with session id "+session.getId()+" is connected");
        ArrayList<String> usernames=new ArrayList<String>();
        String username="";
        Set<Session> sessions=session.getOpenSessions();
        Iterator<Session> iterator=sessions.iterator();
        Session nextSession=null;
        while(iterator.hasNext())
        {
        	nextSession=iterator.next();
        	try 
        	{
        		username=(String)session.getUserProperties().get("username");
        		usernames.add(username);
        		nextSession.getBasicRemote().sendText("User "+username+" is connected");
        	}
        	catch(IOException e)
        	{
        		e.printStackTrace();
        	}
        }
        String message="~The members of the group chat are:<br>";
        for(int index=0; index<usernames.size(); index++)
        {
        	message+=usernames.get(index)+"<br>";
        }
        try
        {
        	session.getBasicRemote().sendText(message);
        }
        catch(IOException e)
        {
        	e.printStackTrace();
        }
    }
    
    @OnClose
    public void onClose(Session session) 
    {
        System.out.println("User "+session.getUserProperties().get("username")+" with session id "+session.getId()+" is disconnected");
        Set<Session> sessions=session.getOpenSessions();
        Iterator<Session> iterator=sessions.iterator();
        Session nextSession=null;
        while(iterator.hasNext())
        {
        	nextSession=iterator.next();
        	try 
        	{
        		nextSession.getBasicRemote().sendText("User "+session.getUserProperties().get("username")+" is disconnected");
        	}
        	catch(IOException e)
        	{
        		e.printStackTrace();
        	}
        }
    }
    
    @OnMessage
    public void onMessage(String message, Session session) 
    {
    	System.out.println("Message from "+session.getUserProperties().get("username")+" with session id "+session.getId()+": "+message);
        Set<Session> sessions=session.getOpenSessions();
        Iterator<Session> iterator=sessions.iterator();
        Session nextSession=null;
        while(iterator.hasNext())
        {
        	nextSession=iterator.next();
        	try 
        	{
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
