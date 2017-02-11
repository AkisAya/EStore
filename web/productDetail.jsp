<%--
  Created by IntelliJ IDEA.
  User: Aki
  Date: 2017/2/11
  Time: 21:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>商品详情页</title>
</head>
<body>
<h1>${product.name }</h1><hr>
<table width="100%" style="text-align: center">
    <tr>
        <td><img src="${pageContext.request.contextPath}/servlet/ProductImgServlet?imgurl=${product.imgurls}"/></td>
        <td>
            商品名称:${product.name }<br>
            商品种类:${product.category }<br>
            商品库存:${product.pnum }<br>
            商品价格:${product.price }<br>
            商品描述:${product.description }<br>
            <a href="#"><img src="${pageContext.request.contextPath}/img/buy.bmp"/></a><br>
        </td>
    </tr>
</table>
</body>
</html>
