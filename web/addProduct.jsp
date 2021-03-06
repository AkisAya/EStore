<%--
  Created by IntelliJ IDEA.
  User: Aki
  Date: 2017/2/11
  Time: 16:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>添加商品</title>
    <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.js"></script>
    <script type="text/javascript">
        function checkData(){
            var price = document.getElementsByName("price")[0].value;
            if(isNaN(price)){
                alert("单价必须是数字!");
                document.getElementsByName("price")[0].value = "";
                return false;
            }else if(price<=0){
                alert("单价必须大于0!");
                document.getElementsByName("price")[0].value = "";
                return false;
            }else{
                return true;
            }
        }


        function upload() {
            $("#bar1").css("display", "block");
            // every 0.1s toggle showProgress()
            window.setInterval(showProgress, 100);
        }

        function showProgress() {
            $.post("${pageContext.request.contextPath}/servlet/uploadProgress",
            function (data) {
                if (data != null) {
                    var jsonObj = eval("(" + data + ")");
                    $("#bar2").css("width", jsonObj.per + "%");
                }
            });
        }
    </script>
</head>
<body>
    <div align="center">
        <h1>添加商品</h1>
        <form action="${pageContext.request.contextPath}/servlet/AddProductServlet" method="post"
              enctype="multipart/form-data" onsubmit="return checkData()">
            <table>
                <tr>
                    <td>商品名称</td>
                    <td><input type="text" name="name"></td>
                </tr>
                <tr>
                    <td>商品价格</td>
                    <td><input type="text" name="price"></td>
                </tr>
                <tr>
                    <td>商品类别</td>
                    <td>
                        <select name="category">
                            <option value="电子数码">电子数码</option>
                            <option value="图书杂志">图书杂志</option>
                            <option value="日用百货">日用百货</option>
                            <option value="大型家电">大型家电</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>商品库存</td>
                    <td><input type="text" name="pnum"></td>
                </tr>
                <tr>
                    <td>商品图片</td>
                    <td>
                        <input type="file" name="imgFile">
                        <div id="bar1" style = "width: 173px; height: 5px; border: 1px solid green; display:none;">
                            <div id="bar2" style="width: 0% ;height: 5px;background-color: green"/>
                        </div>
                        <div id="msg_div"></div>
                    </td>

                </tr>
                <tr>
                    <td>商品简介</td>
                    <td><textarea name="description" cols="22" rows="5"></textarea> </td>
                </tr>
                <tr>
                    <td colspan="2" align="center">
                        <input type="submit" value="提交" style="width: 100px" onclick="upload()">
                    </td>
                </tr>
            </table>
        </form>
    </div>
</body>
</html>
