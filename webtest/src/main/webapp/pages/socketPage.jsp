<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<% String path=request.getContextPath();%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title></title>
    <script src="<%=path %>/js/jquery-2.1.1.min.js"></script>
    <script type="text/javascript">
        $(function () {
            var websocket;
            var target = "ws://localhost:8080/chat.do";
            if ('WebSocket' in window) {
                websocket = new WebSocket(target);
            } else if ('MozWebSocket' in window) {
                websocket = new MozWebSocket(target);
            } else {
                alert('WebSocket is not supported by this browser.');
                return;
            }
            websocket.onopen = function (evnt) {
                $("#tou").html("链接服务器成功!")
            };
            websocket.onmessage = function (evnt) {
                $("#msg").html($("#msg").html() + "<br/>" + evnt.data);
            };
            websocket.onerror = function (evnt) {
            };
            websocket.onclose = function (evnt) {
                $("#tou").html("与服务器断开了链接!")
            }
            $('#send').bind('click', function () {
                send();
            });
            function send() {
                if (websocket != null) {
                    var message = document.getElementById('message').value;
                    websocket.send(message);
                } else {
                    alert('未与服务器链接.');
                }
            }
        });
    </script>
</head>
<body>
<div id="tou"> webSocket及时聊天Demo程序</div>
<div id="msg"></div>
<input type="text" id="message">
<button type="button" id="send"> 发送</button>
</body>
</html>