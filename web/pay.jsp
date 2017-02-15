<%--
  Created by IntelliJ IDEA.
  User: Aki
  Date: 2017/2/15
  Time: 11:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>支付界面</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/servlet/PayServlet" method="post">
    <table width="60%">
        <tr>
            <td bgcolor="#F7FEFF" colspan="4">
                订单号：${param.orderId} <input type="hidden" value="${param.orderId}" name="orderId"/><br>
                支付金额：${param.money}元
            </td>
        </tr>
        <tr><td><br/></td></tr>
        <tr>
            <td>请您选择在线支付银行</td>
        </tr>
        <tr>
            <td><INPUT TYPE="radio" NAME="pd_FrpId" value="CMBCHINA-NET-B2C">招商银行 </td>
            <td><INPUT TYPE="radio" NAME="pd_FrpId" value="ICBC-NET-B2C">工商银行</td>
            <td><INPUT TYPE="radio" NAME="pd_FrpId" value="ABC-NET-B2C">农业银行</td>
            <td><INPUT TYPE="radio" NAME="pd_FrpId" value="CCB-NET-B2C">建设银行 </td>
        </tr>
        <tr>
            <td><INPUT TYPE="radio" NAME="pd_FrpId" value="CMBC-NET-B2C">中国民生银行总行</td>
            <td><INPUT TYPE="radio" NAME="pd_FrpId" value="CEB-NET-B2C" >光大银行 </td>
            <td><INPUT TYPE="radio" NAME="pd_FrpId" value="BOCO-NET-B2C">交通银行</td>
            <td><INPUT TYPE="radio" NAME="pd_FrpId" value="SDB-NET-B2C">深圳发展银行</td>
        </tr>
        <tr>
            <td><INPUT TYPE="radio" NAME="pd_FrpId" value="BCCB-NET-B2C">北京银行</td>
            <td><INPUT TYPE="radio" NAME="pd_FrpId" value="CIB-NET-B2C">兴业银行 </td>
            <td><INPUT TYPE="radio" NAME="pd_FrpId" value="SPDB-NET-B2C">上海浦东发展银行 </td>
            <td><INPUT TYPE="radio" NAME="pd_FrpId" value="ECITIC-NET-B2C">中信银行</td>
        </tr>
        <tr><td><br/></td></tr>
        <tr>
            <td><INPUT TYPE="submit" value="确定支付"></td>
        </tr>
    </table>
</form>
</body>
</html>
