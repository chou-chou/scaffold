<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>科教研管理系统 [404 资源不存在]</title>

    <!-- Bootstrap -->
    <link href="<%=basePath%>/static/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="<%=basePath%>/static/fonts/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">

    <!-- Custom Theme Style -->
    <link href="<%=basePath%>/static/css/custom.min.css" rel="stylesheet">
</head>

<body class="nav-md">
<div class="container body">
    <div class="main_container">
        <!-- page content -->
        <div class="col-md-12">
            <div class="col-middle">
                <div class="text-center">
                    <h1 class="error-number">404</h1>
                    <h2>抱歉，没有找到您想要访问的页面! </h2>
                    <p>您想要访问的页面不存在! <a href="#"> 反馈? </a>
                    </p>
                    <div class="mid_center">
                        <h3 class="small">可能以下原因导致404，您可以检查一下：</h3>
                        <div class="mid_center">
                            <ul style="text-align:left;">
                                <li>检查请求链接是否有误</li>
                                <li>检查处理类代码是否有误</li>
                                <li>检查环境配置是否有误</li>
                                <li>检查处理类视图映射路径</li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- /page content -->
    </div>
</div>

<!-- jQuery -->
<script src="<%=basePath%>/static/js/jquery-1.11.3.min.js"></script>
<!-- Bootstrap -->
<script src="<%=basePath%>/static/js/bootstrap.min.js"></script>
<!-- Custom Theme Scripts -->
<script src="<%=basePath%>/static/js/custom.min.js"></script>
</body>
</html>
