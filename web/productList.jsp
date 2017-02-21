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

    <script type="text/javascript">
        <%--var str = ${pageContext.request.contextPath};--%>
//        声明了以上变量后函数就不能响应了
        var currentPage = ${pager.currentPage};
        var totalPage = ${pager.totalPage};

        function firstPage() {
            window.location.href = "${pageContext.request.contextPath}/servlet/ProductListServlet?pageNum=1";
        }

        function previousPage() {
            if (currentPage === 1) {
                alert("已经是第一页了！");
            } else {
                window.location.href = "${pageContext.request.contextPath}/servlet/ProductListServlet?pageNum=" + (currentPage - 1);
            }
        }

        function nextPage() {
            if (currentPage === totalPage) {
                alert("已经是最后一页了！");
            } else {
                window.location.href = "${pageContext.request.contextPath}/servlet/ProductListServlet?pageNum=" + (currentPage + 1);
            }
        }

        function lastPage() {
            window.location.href = "${pageContext.request.contextPath}/servlet/ProductListServlet?pageNum=" + totalPage;
        }
    </script>

</head>
<body>
<h1>商品列表</h1>
<table width="100%" style="text-align: center">
    <a href="${pageContext.request.contextPath}/index.jsp">回到首页</a>
    <div style="display: inline; float: right;">
        <a href="${pageContext.request.contextPath}/shoppingCart.jsp">查看购物车</a>
    </div>
    <hr>
    <c:forEach items="${pager.dataList}" var="product" varStatus="status">
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
<div align="center">
    共${pager.totalRecord}条记录，共${pager.totalPage}页，当前第${pager.currentPage}页 &nbsp;&nbsp;&nbsp;
    <a href="javascript:void(0)" onclick="firstPage()">首页</a>&nbsp;&nbsp;
    <a href="javascript:void(0)" onclick="previousPage()">上一页</a>&nbsp;&nbsp;
    <a href="javascript:void(0)" onclick="nextPage()">下一页</a>&nbsp;&nbsp;
    <a href="javascript:void(0)" onclick="lastPage()">尾页</a>&nbsp;&nbsp;
</div>
</body>
</html>
