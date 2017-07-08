<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isErrorPage="true" %>
<%@ page import="com.hrp.utils.Exceptions" %>
<%@ page import="com.hrp.utils.Servlets" %>
<%@ page import="com.hrp.utils.lang.StringUtil" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";

    response.setStatus(500);

    // 获取异常类
    Throwable ex = Exceptions.getThrowable(request);

    // 编译错误信息
    StringBuilder sb = new StringBuilder("错误信息：\n");
    if (ex != null) {
        sb.append(Exceptions.getStackTraceAsString(ex));
    } else {
        sb.append("未知错误.\n\n");
    }

    // 如果是异步请求，则直接返回信息
    if (Servlets.isAjaxRequest(request)) {
        out.print(sb);
    }

    // 输出异常信息页面
    else {
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <base href="<%=basePath%>"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>科教研管理系统 [500 服务器异常]</title>

    <!-- Bootstrap -->
    <link href="<%=basePath%>/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">
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
                    <h1 class="error-number">500</h1>
                    <h2>服务器内部错误!</h2>

                    <div class="mid_center">
                        <div>错误信息：<%=ex == null ? "未知错误." : StringUtil.toHtml(ex.getMessage())%> <br/> <br/></div>
                        <%--<h5>错误定位：</h5>
                        <div class="mid_center">
                            <ul class="text-align:left">
                                <li>类名： ${clazz}</li>
                                <li>方法:  ${methodName}</li>
                                <li>行数： ${line}</li>
                            </ul>
                        </div>--%>
                        <p>
                            <button class="btn btn-default" type="button" onclick="showErr();"> 查看详情 </button>
                            <button class="btn btn-default" type="button" onclick="javascript:history.go(-1);">  返 回  </button>
                        </p>
                    </div>
                </div>
            </div>

        </div>

        <div class="col-md-12">
            <div id="err" style="display:none;text-align: left; margin-left: 30px; margin-right: 30px;">
                <%=StringUtil.toHtml(sb.toString())%><br/>
            </div>
        </div>
        <!-- /page content -->
    </div>
</div>

<script type="text/javascript">
    function showErr() {
        $("#err").style.display = "block";
    }
</script>

<!-- jQuery -->
<script src="<%=basePath%>/plugins/jquery/jquery-1.11.3.min.js"></script>
<!-- Bootstrap -->
<script src="<%=basePath%>/plugins/bootstrap/js/bootstrap.min.js"></script>
<!-- Custom Theme Scripts -->
<script src="<%=basePath%>/static/js/custom.min.js"></script>
</body>
</html>

<%
    }
    out = pageContext.pushBody();
%>