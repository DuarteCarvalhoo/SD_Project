<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Dropmusic</title>
  </head>
  <body>
  Welcome to Dropmusic!
  <p><a href="<s:url action="signup" />">Sign up</a></p>
  <p><a href="<s:url action="login" />">Login</a></p>
  <p><a href="<s:url action="dropboxauth" />">Login with Dropbox</a></p>
  </body>
</html>