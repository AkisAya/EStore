<%--
  Created by IntelliJ IDEA.
  User: Aki
  Date: 2017/2/13
  Time: 13:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>添加订单</title>
</head>
<body>
    <h1>生成订单</h1>
    <form action="${pageContext.request.contextPath}/servlet/AddOrderServlet" method="post">
        收货地址:<textarea rows="4" cols="30" name="receiverinfo"></textarea><br>
        支付方式:<input type="radio" checked/>在线支付<br>
        <input type="submit" value="生成订单"/>
    </form>

    <table width="100%" border="1" style="text-align: center">
        <tr>
            <th>商品名称</th>
            <th>商品种类</th>
            <th>商品单价</th>
            <th>购买数量</th>
            <th>库存状态</th>
            <th>总价</th>
        </tr>
        <c:set var="money" value="0" />
        <c:forEach items="${sessionScope.shoppingCart}" var="entry">
            <tr>
                <td>${entry.key.name}</td>
                <td>${entry.key.category}</td>
                <td>${entry.key.price}元</td>
                <td>${entry.value}件</td>
                <td>
                    <c:if test="${entry.value<=entry.key.pnum}">
                        <font color="blue">有货</font>
                    </c:if>
                    <c:if test="${entry.value>entry.key.pnum}">
                        <font color="red">缺货</font>
                    </c:if>
                </td>
                <td>${entry.key.price * entry.value }元
                    <c:set var="money" value="${money + entry.key.price * entry.value }"/>
                </td>
            </tr>
        </c:forEach>
    </table>
    <div align="right">
        <font color="red" size=6">总价:${money }元</font>
    </div>
</body>
</html>
