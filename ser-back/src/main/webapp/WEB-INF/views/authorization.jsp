<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <base href="<%=basePath%>">
    <title>系统认证失败</title>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">

    <link href="<%=basePath%>/static/css/bootstrap.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="<%=basePath%>/static/css/font-awesome.min.css"/>

    <link rel="stylesheet" href="<%=basePath%>/static/css/ace.min.css"/>
    <link rel="stylesheet" href="<%=basePath%>/static/css/ace-skins.min.css"/>

    <link rel="stylesheet" href="<%=basePath%>/static/css/custom.min.css"/>
</head>
<body class="nav-md">
<div class="container body">
    <div class="main_container">
        <!-- page content -->
        <div class="col-md-12">
            <div class="col-middle">
                <div class="text-center text-center">
                    <h1 class="error-number">403</h1>
                    <h3>系统有效性认证</h3>
                    <p>
                        <span style="color: red;">无系统使用权限/系统过期！</span>
                        你需要从厂商处获取授权，才能使用本款软件!
                        <a href="javascript: void(0);">反馈?</a>
                    </p>
                    <%--<div class="mid_center">
                        <h3>Search</h3>
                        <form>
                            <div class="col-xs-12 form-group pull-right top_search">
                                <div class="input-group">
                                    <input type="text" class="form-control" placeholder="Search for...">
                                    <span class="input-group-btn">
                                        <button class="btn btn-default" type="button">Go!</button>
                                     </span>
                                </div>
                            </div>
                        </form>
                    </div>--%>
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

<script src="<%=basePath%>/static/js/custom.min.js"></script>
</body>
</html>