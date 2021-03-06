<%--
  Created by IntelliJ IDEA.
  User: Aki
  Date: 2017/2/10
  Time: 10:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
  <head>
    <title>EStore</title>
  </head>
  <body>
  <div align="center">
      <h1>主页</h1>
      <c:if test="${sessionScope.user == null}">
      欢迎游客，
      <a href="${pageContext.request.contextPath}/login.jsp">登录</a>
      <a href="${pageContext.request.contextPath}/register.jsp">注册</a>
      </c:if>
      <c:if test="${sessionScope.user != null}">
          <c:if test="${sessionScope.user.role == 'user'}">
              欢迎${sessionScope.user.nickname}<br>
              <a href="${pageContext.request.contextPath}/servlet/ProductListServlet">查看商品</a>,
              <a href="${pageContext.request.contextPath}/shoppingCart.jsp">查看购物车</a>,
              <a href="${pageContext.request.contextPath}/servlet/OrderListServlet">查看订单</a>,
              <a href="${pageContext.request.contextPath}/servlet/LogoutServlet">退出登录</a>
          </c:if>
          <c:if test="${sessionScope.user.role == 'admin'}">
              欢迎管理员 ${sessionScope.user.nickname}<br>
              <a href="${pageContext.request.contextPath}/addProduct.jsp">添加商品</a>,
              <a href="${pageContext.request.contextPath}/servlet/ProductListServlet">查看商品</a>,
              <a href="${pageContext.request.contextPath}/servlet/SaleListServlet">下载销售榜单</a>
              <a href="${pageContext.request.contextPath}/servlet/LogoutServlet">退出登录</a>
          </c:if>
      </c:if>
  </div>
  </body>
</html>
