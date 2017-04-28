<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>

<html lang="en">
<head>
    <base href="<%=basePath%>">
    <meta charset="utf-8"/>
    <title>科教研管理系统</title>
    <meta name="keywords" content="科教研管理系统" />
    <meta name="description" content="科教研管理系统" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta http-equiv="Cache-Control" content="max-age=7200" />

    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0"/>
    <link rel="shortcut icon" href="<%=basePath%>/static/img/favicon.ico" type="image/x-ico">

    <%@ include file="../views/comm/default_header.jsp" %>
</head>

<body class="skin-1" style="overflow:auto;">
    <!-- Preloader -->
    <%--<div id="preloader">
        <div id="status">
            <i class="fa fa-spinner fa-spin"></i>
        </div>
    </div>--%>

    <!-- 头部区域 -->
    <%@include file="../views/comm/main_header.jsp" %>

    <div class="main-container ace-save-state" id="main-container">
        <script type="text/javascript">
            try {
                ace.settings.loadState('main-container')
            } catch (e) {}
        </script>

        <!-- 左侧面板 -->
        <%@include file="../views/comm/left_panel.jsp" %>

        <!-- 内容面板 -->
        <%@include file="../views/comm/content_panel.jsp" %>

        <!-- 底部LOG -->
        <%@include file="../views/comm/footer_bar.jsp" %>

        <!-- 回到TOP -->
        <a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse display">
            <i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
        </a>

    </div>

    <!-- inline scripts related to this page -->
    <script type="text/javascript">
        $(document).on('webkitfullscreenchange mozfullscreenchange msfullscreenchange fullscreenchange', function(){
            if (!document.fullscreenElement &&    // alternative standard method
                    !document.mozFullScreenElement &&
                    !document.webkitFullscreenElement &&
                    !document.msFullscreenElement ) {
                // 在这里处理 bug
                changeThePadding();
            }
        });

        function changeThePadding() {
            // 处理全屏bug  1、window失去焦点  2、部分浏览器在页面失去焦点
        }

        jQuery(function ($) {
            $.serindex.load();

            $.ajax({
                type: "GET",
                url: "anon/fetchMenus.do",
                success: function(data) {
                    $.serindex.loadMenu(data);
                    $.sertab.init();
                }
            })

        });
    </script>

</body>
</html>

<!-- 当jsp include动态文件时（jsp文件）可以在被include的jsp文件头部加上一下代码，防止乱码 -->
<%--<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>--%>

<%--当jsp include静态文件时（html文件）可以在被include的html文件的<head></head>标签内加上代码：--%>
<%--<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />--%>
