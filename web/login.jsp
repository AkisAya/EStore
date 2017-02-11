<%--
  Created by IntelliJ IDEA.
  User: Aki
  Date: 2017/2/10
  Time: 21:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div align="center">
    <h1>Estore登录</h1><hr>
    <label style="color: red;">${error_msg}</label>
    <form action="${pageContext.request.contextPath}/servlet/LoginServlet" method="POST">
        <table>
            <tr>
                <td align="right">用户名:</td>
                <td><input type="text" name="username" value="${cookie.username.value}" /></td>
            </tr>
            <tr>
                <td align="right">密码:</td>
                <td><input type="password" name="password" /></td>
            </tr>
            <tr>
                <td align="right">
                    <input type="checkbox" name="rememberName"
                           value="checked" <c:if test="${cookie.username != null}">checked</c:if>>记住用户名</td>
                <td><input type="checkbox" name="autoLogin" value="checked">自动登录</td>
            </tr>
            <tr>
                <td colspan="2" align="center"><input type="submit" value="登录" style="width: 90px"/></td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>
