<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="shortcut icon" href="<%=basePath%>/static/img/favicon.ico" type="image/x-ico">

    <title>锁屏</title>

    <link rel="stylesheet" href="<%=basePath%>/static/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="<%=basePath%>/static/font-awesome/4.5.0/css/font-awesome.min.css"/>

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
        <script src="<%=basePath%>/static/js/html5shiv.min.js"></script>
        <script src="<%=basePath%>/static/js/respond.min.js"></script>
    <![endif]-->

    <style>
        .notfound {
            background: #e4e7ea;
        }

        .notfound h4 {
            font-size: 12px;
            line-height: 18px;
        }

        .lockedpanel {
            width: 250px;
            margin: 10% auto 0 auto;
            text-align: center;
        }

        .lockedpanel .loginuser {
            text-align: center;
        }

        .lockedpanel .loginuser img {
            -moz-border-radius: 100px;
            -webkit-border-radius: 100px;
            border-radius: 100px;
            background: rgba(255, 255, 255, 0.4);
            padding: 5px;
        }

        .lockedpanel .locked {
            font-size: 42px;
            margin-bottom: 20px;
        }

        .lockedpanel .logged {
            margin-top: 20px;
        }

        .lockedpanel .logged h4 {
            margin: 0;
            font-size: 21px;
            color: #333;
        }

        .lockedpanel form {
            margin-top: 20px;
        }

        .lockedpanel form .btn {
            display: block;
            margin-top: 10px;
        }
    </style>
</head>

<body class="notfound">
<section>
    <div class="lockedpanel">
        <div class="locked">
            <i class="ace-icon fa fa-lock"></i>
        </div>
        <div class="loginuser">
            <img src="<%=basePath%>/static/img/avatars/user1-128x128.jpg" alt=""/>
        </div>
        <div class="logged">
            <h4>John Doe</h4>
            <small class="text-muted">username@domain.com</small>
        </div>
        <form method="post" action="unlock.do">
            <input type="password" class="form-control" placeholder="请输入密码"/>
            <button class="btn btn-success btn-block"> 解 锁 </button>
        </form>
    </div><!-- lockedpanel -->

</section>

<script src="<%=basePath%>/static/js/jquery-1.11.3.min.js"></script>

</body>
</html>
