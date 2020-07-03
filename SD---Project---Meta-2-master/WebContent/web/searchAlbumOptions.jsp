<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Dropmusic - Search Album</title>
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
<body>
<h2>You wanna search by album name or artist?</h2><br><br>

<p><a href="<s:url action="searchAlbumByNameInput" />">Search By Name</a></p>
<p><a href="<s:url action="searchAlbumByArtistInput" />">Search By Artist</a></p>

<p><a href="<s:url action="searchMenu"/>">Back</a></p>
</body>
</html>
