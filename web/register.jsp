<%--
  Created by IntelliJ IDEA.
  User: Aki
  Date: 2017/2/10
  Time: 14:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>用户注册</title>
    <script>
        function changeImg(img) {
            img.src = img.src+"?time="+new Date().getTime();
        }

        // 前端校验
        function checkForm(){
            var canSub = true;
            //1.非空校验
            canSub = checkNull("username","用户名不能为空!") && canSub;
            canSub = checkNull("password","密码不能为空!") && canSub;
            canSub = checkNull("password2","确认密码不能为空!") && canSub;
            canSub = checkNull("nickname","昵称不能为空!") && canSub;
            canSub = checkNull("email","邮箱不能为空!") && canSub;
            canSub = checkNull("validStr","验证码不能为空!") && canSub;

            //2.两次密码一致的校验
            var psw1 = document.getElementsByName("password")[0].value;
            var psw2 = document.getElementsByName("password2")[0].value;
            if(psw1 != psw2){
                document.getElementById("password2_msg").innerHTML = "<label style='color: red;'>两次密码不一致!</label>";
                canSub = false;
            }

            //3.邮箱格式校验:sssss@xxx.xxx.xxx.xxx
            var email = document.getElementsByName("email")[0].value;
            if( email!= null && email != "" && !/^\w+@\w+(\.\w+)+$/.test(email)){
                document.getElementById("email_msg").innerHTML = "<label style='color: red;'>邮箱格式不正确!</label>";
                canSub = false;
            }
            return canSub;
        }

        function checkNull(name, msg){
            document.getElementById(name+"_msg").innerHTML = "";
            var objValue = document.getElementsByName(name)[0].value;
            if(objValue == null || objValue == ""){
                document.getElementById(name+"_msg").innerHTML = "<label style='color: red;'>"+msg+"</label>";
                return false;
            }
            return true;
        }
    </script>
    
    
</head>
<body>
<div align="center">
    <h1>EStore用户注册</h1>
    <form action="${pageContext.request.contextPath}/servlet/RegisterServlet" method="post" onsubmit="return checkForm()">
        <table>
            <tr>
                <td align="right">用户名：</td>
                <td align="left"><input type="text" name="username" value="${param.username}"></td>
                <td id="username_msg"></td>
            </tr>
            <tr>
                <td align="right">密码：</td>
                <td align="left"><input type="password" name="password"></td>
                <td id="password_msg"></td>
            </tr>
            <tr>
                <td align="right">重复密码：</td>
                <td align="left"><input type="password" name="password2"></td>
                <td id="password2_msg"></td>
            </tr>
            <tr>
                <td align="right">邮箱：</td>
                <td align="left"><input type="text" name="email" value="${param.email}"></td>
                <td id="email_msg"></td>
            </tr>
            <tr>
                <td align="right">昵称：</td>
                <td align="left"><input type="text" name="nickname" value="${param.nickname}"></td>
                <td id="nickname_msg"></td>
            </tr>
            <tr>
                <td align="right">验证码：</td>
                <td align="left"><input type="text" name="validStr"></td>
                <td id="validStr_msg">${check_msg}</td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td><img src="${pageContext.request.contextPath}/servlet/ImgValidServlet" alt="验证码图片"
                         onclick="changeImg(this)" style="cursor: pointer"></td>
            </tr>
            <tr>
                <td colspan="2" align="center"><input type="submit" value="注册" style="width: 90px"></td>
            </tr>

        </table>
    </form>
</div>

</body>
</html>
