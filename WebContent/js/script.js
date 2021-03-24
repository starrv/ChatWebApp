$(document).ready(function()
{
	var sendType="send to all";
	var read=null;
	var isConnected=false;
	var readInterval=1000;
	
	// Create WebSocket connection
	let socket = null;
	
	$("#connect").click(function( event )
	{
		$("#disconnect").attr("disabled",false);
		$("#connect").attr("disabled",true);
		$("#send").attr("disabled",false);
		
		// Create WebSocket connection
		socket = new WebSocket('ws://127.0.0.1:8080/ChatWebApp/endpoint');
		
		var getState=setInterval(function(){
			console.log("Ready state: "+socket.readyState);
		},1000);
		
		// Connection opened
		socket.addEventListener('open', function (event) {
		    socket.send('Hello Server!');
		});

		// Listen for messages
		socket.addEventListener('message', function (event) {
			$("#chat-info").html($("#chat-info").html()+"<p class='border border-dark rounded p-2 m-2 bg-light' style='box-shadow:5px 5px 5px gray;'>Message from server: "+event.data+"</p>");
		});
		
		// Listen for possible errors
		socket.addEventListener('error', function (event) {
		  console.log('WebSocket error: ', event);
		});
		
		
		//Close WebSocket connection
		socket.onclose = function(event) {
			console.log("WebSocket is closed now.");
		};
		
//		$.ajax(
//				{
//				  method: "POST",
//				  url: "http://localhost:8080/ChatWebApp/Chat",
//				  data: {"clickedButton":"connect"}
//				})
//				.done(function( data )
//				{
//					console.log(data);					
//					read=setInterval(function()
//					{
//						$.ajax(
//								{
//								  method: "POST",
//								  url: "http://localhost:8080/ChatWebApp/Chat",
//								  data: {"read":"read"}
//								})
//								.done(function( data )
//								{
//									if(data!="")
//									{
//										console.log(data);
//									}
//								})
//								.fail(function(error)
//								{
//									if(error!="")
//										{
//											console.error(error);
//										}
//									});
//						},readInterval);						
//				})
//				.fail(function(error)
//				{
//					console.error(error);
//				});
	
		
		
		
	});

	$("#disconnect").click(function( event )
	{
		$("#connect").attr("disabled",false);
		$("#disconnect").attr("disabled",true);
		$("#send").attr("disabled",true);
		socket.close();	
		clearInterval(getState);
				
//				$.ajax(
//						{
//						  method: "POST",
//						  url: "http://localhost:8080/ChatWebApp/Chat",
//						  data: {"clickedButton":"disconnect"}
//						})
//						.done(function( data )
//						{
//							console.log(data);
//						})
//						.fail(function(error)
//						{
//							console.error(error);
//						});
				//clearInterval(read);
	});
	
	$(window).on("unload",function(){
		$("#connect").attr("disabled",false);
		$("#disconnect").attr("disabled",true);
		$("#send").attr("disabled",true);
		socket.close();
		clearInterval(getState);
//		$.ajax(
//				{
//				  method: "POST",
//				  url: "http://localhost:8080/ChatWebApp/Chat",
//				  data: {"clickedButton":"disconnect"}
//				})
//				.done(function( data )
//				{
//					console.log(data);
//				})
//				.fail(function(error)
//				{
//					console.error(error);
//				})
		//clearInterval(read);
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
		socket.send(message);
//		$.ajax(
//				{
//				  method: "POST",
//				  url: "http://localhost:8080/ChatWebApp/Chat",
//				  data: {"clickedButton":"send","sendType":sendType, "message":message}
//				})
//				.done(function( data )
//				{
//					console.log(data);
//				})
//				.fail(function(error)
//				{
//					console.error(error);
//				});
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
