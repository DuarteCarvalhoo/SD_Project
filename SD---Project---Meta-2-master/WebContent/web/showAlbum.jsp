<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Dropmusic - Music View</title>
    <script type="text/javascript">

        var websocket = null;

        window.onload = function() { // URI = ws://10.16.0.165:8080/WebSocket/ws
            connect('ws://' + window.location.host + '/ws');
        }

        function connect(host) { // connect to the host websocket
            if ('WebSocket' in window)
                websocket = new WebSocket(host);
            else if ('MozWebSocket' in window)
                websocket = new MozWebSocket(host);

            websocket.onopen    = onOpen; // set the 4 event listeners below
            websocket.onclose   = onClose;
            websocket.onmessage = onMessage;
            websocket.onerror   = onError;
        }

        function onOpen(event) {
            var username = "<%=session.getAttribute("username")%>";
            websocket.send(username);
        }

        function onClose(event) {
        }

        function onMessage(message) {
            alert(message.data);
        }

        function onError(event) {
        }

        function doSend() {
            var message = document.getElementById('chat').value;
            if (message != '')
                websocket.send(message); // send the message to the server
            document.getElementById('history').value = '';
        }

        function writeToHistory(text) {
            var history = document.getElementById('history');
            var line = document.createElement('p');
            line.style.wordWrap = 'break-word';
            line.innerHTML = text;
            history.appendChild("eheheheh");
            history.scrollTop = history.scrollHeight;
        }

    </script>
</head>

<h2>${name}'s information</h2>
<body>
<a>Title: ${name}</a><br>
<a>Artist: ${artist}</a><br>
<a>Publisher: ${publisher}</a><br>
<a>Score: ${score}</a><br>
<a>Genre: ${genre}</a><br>
<a>Description: ${description}</a><br>
<a>Length: ${length}</a><br>
<a>Critics:</a><br>
<c:forEach items="${critics}" varStatus="i">
    <a>      ● </a><a>${critics[i.index]}</a><br>
</c:forEach>
<a>Musics:</a><br>
<c:forEach items="${musics}" varStatus="i">
    <a>      ● </a><a>${musics[i.index]}</a><br>
</c:forEach>

<p><a href="<s:url action="menuPrincipal" />">Menu Principal</a></p>
</body>
</html>
