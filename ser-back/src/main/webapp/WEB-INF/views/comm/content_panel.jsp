<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!--中间内容-->
<div class="main-content">
    <div class="main-content-inner">

        <div id="content-wrapper" class="content-wrapper">
            <div class="content-tabs">
                <button class="roll-nav roll-left tabLeft">
                    <i class="fa fa-backward"></i>
                </button>
                <nav class="page-tabs menuTabs">
                    <div class="page-tabs-content" style="margin-left: 0px;">
                        <a href="javascript:void(0);" class="menuTab active" data-id="#">欢迎首页</a>
                        <%--<a href="javascript:void(0);" class="menuTab" data-id="#" style="padding-right: 15px;">平台介绍<i
                                class="fa fa-remove"></i></a>--%>
                    </div>
                </nav>

                <button class="roll-nav roll-right tabRight">
                    <i class="fa fa-forward" style="margin-left: 3px;"></i>
                </button>
                <div class="btn-group roll-nav roll-right">
                    <button class="dropdown tabClose" data-toggle="dropdown">
                        页签操作<i class="fa fa-caret-down" style="padding-left: 3px;"></i>
                    </button>
                    <ul class="dropdown-menu dropdown-menu-right">
                        <li><a class="tabReload" href="javascript:void(0);">刷新当前</a></li>
                        <li><a class="tabCloseCurrent" href="javascript:void(0);">关闭当前</a></li>
                        <li><a class="tabCloseAll" href="javascript:void(0);">全部关闭</a></li>
                        <li><a class="tabCloseOther" href="javascript:void(0);">除此之外全部关闭</a></li>
                    </ul>
                </div>
                <button class="roll-nav roll-right fullscreen"><i class="fa fa-arrows-alt"></i></button>
            </div><!-- /.content-tabs -->

            <!-- 系统设置面板 -->
            <%--<%@include file="setting_panel.jsp" %>--%>

            <div class="content-iframe" style="overflow: visible;">
                <div class="mainContent" id="content-main" style="margin: 10px; margin-bottom: 0px; padding: 0;">
                    <iframe class="LRADMS_iframe" width="100%" height="100%" src="<%=basePath%>c/system/mapping.do"
                            frameborder="0" data-id=""></iframe><!-- default.html -->
                </div>
            </div><!-- /.content-iframe -->
        </div><!-- /.content-wrapper -->
    </div><!-- /.main-content-inner -->
</div>
<!-- /.main-content -->