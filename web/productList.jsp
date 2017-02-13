<%--
  Created by IntelliJ IDEA.
  User: Aki
  Date: 2017/2/11
  Time: 20:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>商品列表</title>
</head>
<body>
<h1>商品列表</h1>
<table width="100%" style="text-align: center">
    <div align="right">
        <a href="${pageContext.request.contextPath}/shoppingCart.jsp">查看购物车</a>
    </div>
    <c:forEach items="${productList}" var="product" varStatus="status">
        <tr>
            <td width="20%">
                <a href="${pageContext.request.contextPath}/servlet/ProductDetailServlet?id=${product.id }">
                    <img src="${pageContext.request.contextPath}/servlet/ProductImgServlet?imgurl=${product.imgurls}"
                         style="cursor: pointer;"/></a>
            </td>
            <td width="40%">
                    ${product.name}<br>
                    ${product.price}<br>
                    ${product.category}<br>
            </td>
            <td width="40%">
                <c:if test="${product.pnum>0}">
                    <font color="blue">有货</font>
                </c:if>
                <c:if test="${product.pnum<=0}">
                    <font color="red">缺货</font>
                </c:if>
            </td>
        </tr>
        <tr>
            <td colspan="3"><hr></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
