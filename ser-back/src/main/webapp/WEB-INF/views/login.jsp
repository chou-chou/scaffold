<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>

<html>

<head>
    <base href="<%=basePath%>">
    <title>登录页面</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">

    <link rel="stylesheet" href="<%=basePath%>/static/css/login.css"/>
    <link rel="stylesheet" href="<%=basePath%>/static/css/ace.min.css" class="ace-main-stylesheet" id="main-ace-style"/>
    <link rel="stylesheet" href="<%=basePath%>/static/fonts/font-awesome/4.5.0/css/font-awesome.min.css"/>

</head>

<body class="beg-login-bg">
<div class="beg-login-box">
    <header>
        <h1>科教研管理系统</h1>
    </header>
    <div class="beg-login-main">
        <form action="login.do" class="login-form" method="POST">
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
                    <label>
                        <span>记住我?  </span>
                    </label>
                    <label>
                        <input name="rememberMe" class="ace ace-switch ace-switch-3" type="checkbox" value="rememberMe"
                               title="记住账号"/>
                        <span class="lbl" style="vertical-align: middle;"></span>
                    </label>
                </div>
                <div class="beg-pull-right">
                    <button class="login-btn login-btn-primary">
                        <i class="login-icon">&#xe650;</i> 登录
                    </button>
                </div>
                <div class="beg-clear">
                    <span style="color: darkblue;">
                        ${message.message}
                    </span>
                </div>
            </div>
        </form>
    </div>
    <footer>
        <p>武汉同步远方科技有限公司</p>
    </footer>
</div>

<script src="<%=basePath%>/static/js/jquery-1.11.3.min.js"></script>
<script src="<%=basePath%>/plugins/layer/layer.js"></script>

<script type="text/javascript">
    $(document).ready(function () {
        // 打印信息
    });

    function kickout() {
        var _href = window.location.href;
        if (_href && _href.indexOf("kickout") != -1) {
            layer.msg("您的账号在另一台设备上登录，您被挤下线，若不是您本人操作，请立即修改密码！");
        }
    }
    window.onload = kickout();

</script>
</body>

</html>