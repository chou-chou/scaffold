<!-- 当jsp include动态文件时（jsp文件）可以在被include的jsp文件头部加上一下代码，防止乱码 -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!-- 底部区域 -->
<div class="footer">
    <div class="footer-inner">
        <div class="footer-content">
            <span class="bigger-120">
                <span class="blue bolder">武汉同步远方科技有限公司</span>
                Application &copy; 2016-2017
            </span>

            &nbsp; &nbsp;
            <span class="action-buttons">
                <%--<a href="#">
                    <i class="ace-icon fa fa-twitter-square light-blue bigger-150"></i>
                </a>

                <a href="#">
                    <i class="ace-icon fa fa-facebook-square text-primary bigger-150"></i>
                </a>--%>

                <a href="#">
                    <i class="ace-icon fa fa-rss-square orange bigger-150"></i>
                </a>
            </span>
        </div>
    </div>
</div>
<!-- /.footer -->