$(document).ready(function()
{
	var sendType="send to all";
	var read=null;
	var isConnected=false;
	var readInterval=1000;
	
	const socketURL='ws://127.0.0.1:8080/ChatWebApp/endpoint';
	
	// Create WebSocket connection
	let socket = null;
	let getState=null;
	
	$("#connect").click(function( event )
	{
		$("#disconnect").attr("disabled",false);
		$("#connect").attr("disabled",true);
		$("#send").attr("disabled",false);
		
		// Create WebSocket connection
		socket = new WebSocket(socketURL);
		
		getState=setInterval(function(){
			console.log("Ready state: "+socket.readyState);
		},1000);
		
		// Connection opened
		socket.addEventListener('open', function (event) {
		    socket.send('Hello Server!');
		});

		// Listen for messages
		socket.addEventListener('message', function (event) {
			if(event.data[0]=="~")
			{
				$("#users-display").html("<p class='border border-dark rounded p-2 m-2 bg-light' style='box-shadow:5px 5px 5px gray;'>"+event.data.substring(0)+"</p>");
			}
			else
			{
				$("#chat-info").html($("#chat-info").html()+"<p class='border border-dark rounded p-2 m-2 bg-light' style='box-shadow:5px 5px 5px gray;'>Message from server: "+event.data+"</p>");
			}
		});
		
		// Listen for possible errors
		socket.addEventListener('error', function (event) {
		  console.log('WebSocket error: ', event);
		});
		
		
		//Close WebSocket connection
		socket.onclose = function(event) {
			console.log("WebSocket is closed now.");
		};
		
	});

	$("#disconnect").click(function( event )
	{
		$("#connect").attr("disabled",false);
		$("#disconnect").attr("disabled",true);
		$("#send").attr("disabled",true);
		socket.close();	
		clearInterval(getState);				
	});
	
	$(window).on("unload",function(){
		$("#connect").attr("disabled",false);
		$("#disconnect").attr("disabled",true);
		$("#send").attr("disabled",true);
		socket.close();
		clearInterval(getState);
	});
	
	$("#send-to-all").click(function(){
		sendType=getSendType();
		console.log(sendType);
	});
	
	$("#send-private").click(function(){
		sendType=getSendType();
		console.log(sendType);
	});
	
	$("#send-private-encrypted").click(function(){
		sendType=getSendType();
		console.log(sendType);
	});
	
	document.querySelector('#chat-form').addEventListener('submit', (event) => {
		event.preventDefault();
		var message=$("#user-input").val();
		if(message!="")
		{
			socket.send(message);
		}
	});
});


function getSendType()
{
	var sendPrivate=$("#send-private");
	var sendPrivateEncrypted=$("#send-private-encrypted");
	if(sendPrivate.is(":checked")==true)
	{
		return "send private";
	}
	else if(sendPrivateEncrypted.is(":checked")==true)
	{
		return "send private encrypted";
	}
	else
	{
		return "send to all";
	}
}
