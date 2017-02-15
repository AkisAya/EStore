<%--
  Created by IntelliJ IDEA.
  User: Aki
  Date: 2017/2/14
  Time: 17:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>订单展示</title>
</head>
<body>
    <h1>我的订单</h1>
    <a href="${pageContext.request.contextPath}/index.jsp">回到首页</a><br>
    <c:forEach items="${requestScope.orderExtList}" var="orderExt">
        <strong>
            <fmt:formatDate value="${orderExt.ordertime}"></fmt:formatDate>
        </strong>&nbsp;&nbsp;&nbsp;
        订单号: ${orderExt.id}&nbsp;&nbsp;&nbsp;
        <div style="display: inline; float: right;"><a href="#">删除订单记录</a></div>&nbsp;&nbsp;
        <table border="1" width="100%" style="text-align: center">
            <tr>
                <td>商品名称</td>
                <td>单价</td>
                <td>数量</td>
                <td>金额/元</td>
                <td>交易状态</td>
            </tr>
            <c:forEach items="${orderExt.productMap}" var="entry" varStatus="status">
                <tr>
                    <td>${entry.key.name}</td>
                    <td>${entry.key.price}</td>
                    <td>${entry.value}</td>
                    <!--下面两栏只在第一次循环填入数据，其余为空数据-->
                    <c:if test="${status.index == 0}">
                        <td rowspan="<c:out value="${fn:length('${orderExt.productMap}')}"/>" width="20%">${orderExt.money}</td>
                        <td rowspan="<c:out value="${fn:length('${orderExt.productMap}')}"/>" width="20%">
                            <c:if test="${orderExt.paystate == 0}">
                                待支付，<a href="${pageContext.request.contextPath}/pay.jsp?orderId=${orderExt.id}&money=${orderExt.money}">前往支付</a><br>
                                <a href="${pageContext.request.contextPath}/servlet/CancelOrderServlet?id=${orderExt.id}">取消订单</a>
                            </c:if>
                            <c:if test="${orderExt.paystate == 1}">已支付</c:if>
                        </td>
                    </c:if>

                </tr>
            </c:forEach>
        </table>
        <hr>
    </c:forEach>
</body>
</html>
