<%--
  Created by IntelliJ IDEA.
  User: david
  Date: 2017-03-26
  Time: 12:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>

<html>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>登录</title>
    <link rel="stylesheet" href="${basePath}/static/css/login.css" />
</head>

<body class="beg-login-bg">
<div class="beg-login-box">
    <header>
        <h1>科教研管理系统</h1>
    </header>
    <div class="beg-login-main">
        <form action="login.do" class="login-form" method="post">
            <input name="__RequestVerificationToken" type="hidden" value="fkfh8D89BFqTdrE2iiSdG_L781RSRtdWOH411poVUWhxzA5MzI8es07g6KPYQh9Log-xf84pIR2RIAEkOokZL3Ee3UKmX0Jc8bW8jOdhqo81" />
            <div class="login-form-item">
                <label class="beg-login-icon">
                    <i class="login-icon">&#xe612;</i>
                </label>
                <input type="text" name="username" autocomplete="off" placeholder="这里输入登录名" class="login-input">
            </div>
            <div class="login-form-item">
                <label class="beg-login-icon">
                    <i class="login-icon">&#xe642;</i>
                </label>
                <input type="password" name="password" autocomplete="off" placeholder="这里输入密码" class="login-input">
            </div>
            <div class="login-form-item">
                <div class="beg-pull-left beg-login-remember">
                    <label>记住帐号？</label>
                    <input type="checkbox" name="rememberMe" value="true" checked title="记住帐号">
                </div>
                <div class="beg-pull-right">
                    <button class="login-btn login-btn-primary" >
                        <i class="login-icon">&#xe650;</i> 登录
                    </button>
                </div>
                <div class="beg-clear"></div>
            </div>
        </form>
    </div>
    <footer>
        <p>武汉同步远方科技有限公司</p>
    </footer>
</div>

<script type="text/javascript">
    function kickout(){
        var href=location.href;
        if(href.indexOf("kickout")>0){
            alert("您的账号在另一台设备上登录，您被挤下线，若不是您本人操作，请立即修改密码！");
        }
    }
    window.onload=kickout();
</script>
</body>

</html>