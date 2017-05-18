<!-- 当jsp include动态文件时（jsp文件）可以在被include的jsp文件头部加上一下代码，防止乱码 -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!--左边导航-->
<div id="sidebar" class="sidebar responsive ace-save-state">
    <script type="text/javascript">
        try{ace.settings.loadState('sidebar')}catch(e){}
    </script>

    <div class="user-panel">
        <div class="pull-left image">
            <img src="${basePath}/static/img/avatars/user2-160x160.jpg" class="img-circle" alt="User Image">
        </div>
        <div class="pull-left info">
            <p>${sessionScope.currentUserName}</p>
            <a><i class="fa fa-circle text-success"></i>在线</a>
        </div>
    </div>

    <ul class="nav nav-list" id="sidebar-menu"></ul>

    <div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
        <i id="sidebar-toggle-icon" class="ace-icon fa fa-angle-double-left ace-save-state" data-icon1="ace-icon fa fa-angle-double-left" data-icon2="ace-icon fa fa-angle-double-right"></i>
    </div>

</div>
<!-- leftpanel -->
