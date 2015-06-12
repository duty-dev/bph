<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>

<script src="<%=basePath%>JS/jquery-1.9.1.js" type='text/javascript'></script>
<script src="<%=basePath%>JS/rabbit/sockjs-0.3.4.js"
	type='text/javascript'></script>
<script src="<%=basePath%>JS/rabbit/stomp.js" type='text/javascript'></script>
<style>
.box {
	width: 440px;
	float: left;
	margin: 0 20px 0 20px;
}

.box div,.box input {
	border: 1px solid;
	-moz-border-radius: 4px;
	border-radius: 4px;
	width: 100%;
	padding: 5px;
	margin: 3px 0 10px 0;
}

.box div {
	border-color: grey;
	height: 300px;
	overflow: auto;
}

div code {
	display: block;
}

#first div code {
	-moz-border-radius: 2px;
	border-radius: 2px;
	border: 1px solid #eee;
	margin-bottom: 5px;
}

#second div {
	font-size: 0.8em;
}
</style>
<title>RabbitMQ Web STOMP Examples : Echo Server</title>
<link href="main.css" rel="stylesheet" type="text/css" />
</head>
<body lang="en">
	<h1>
		<a href="index.html">RabbitMQ Web STOMP Examples</a> > Echo Server
	</h1>

	<div id="first" class="box">
		<h2>Received</h2>
		<div></div>
		<form>
			<input autocomplete="off" value="Type here..."></input>
		</form>
	</div>

	<div id="second" class="box">
		<h2>Logs</h2>
		<div></div>
	</div>
	<script>
		var ws = new SockJS('http://' + window.location.hostname
				+ ':15674/stomp');
		var client = Stomp.over(ws);
		// SockJS does not support heart-beat: disable heart-beats
		client.heartbeat.incoming = 0;
		client.heartbeat.outgoing = 0;

		client.debug = function(e) {
			$('#second div').append($("<code>").text(e));
		};

		// default receive callback to get message from temporary queues
		client.onreceive = function(m) {
			alert(m.body);
		}

		var on_connect = function(x) {
			id = client.subscribe("/exchange/gps/51000.51001", function(m) {
			});
		};
		var on_error = function() {
			console.log('error');
		};
		client.connect('guest', 'guest', on_connect, on_error, '/');

		$('#first form').submit(function() {
			var text = $('#first form input').val();
			if (text) {
				client.send('/queue/test', {
					'reply-to' : '/temp-queue/foo'
				}, text);
				$('#first form input').val("");
			}
			return false;
		});
	</script>
</body>
</html>
