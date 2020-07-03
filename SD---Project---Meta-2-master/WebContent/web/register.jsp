<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Dropmusic - Sign Up</title>
</head>
<body>

    <h1>Register</h1><br><br>

    <s:form action="register" method="post">
        <s:text name="Username:" />
        <s:textfield name="username"/><br><br>
        <s:text name="Password:" />
        <s:password name="password"/><br>
        <s:submit/>
    </s:form>
    <p><a href="<s:url action="index" />">Back</a></p>
</body>
</html>