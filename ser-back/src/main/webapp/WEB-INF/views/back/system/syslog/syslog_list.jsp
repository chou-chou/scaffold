<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="shiro" uri="/shiro-tag-extend" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>日志列表</title>
    <base href="<%=basePath%>">

    <%@ include file="../../../comm/default_header.jsp" %>

</head>

<body>
<div class="main-container">

    <script type="text/javascript">
        try {
            ace.settings.check('main-container', 'fixed')
        } catch (e) {
        }
    </script>

    <div class="main-content">
        <div class="main-content-inner">
            <div class="page-content">
                <div class="row">
                    <div class="col-xs-12">

                        <div class="space-4"></div>

                        <div class="row">
                            <div class="col-xs-12">
                                <table id="log_list" class="table table-striped table-bordered table-hover"
                                       style="margin-top:5px;">
                                    <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th class="center" style="width:135px;">时间</th>
                                        <th class="center" style="width:90px;">用户</th>
                                        <th class="center">地址</th>
                                        <th class="center">操作</th>
                                        <th class="center">方法</th>
                                        <th class="center">URL</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody></tbody>
                                </table>
                                <div class="page-header position-relative">
                                    <table style="width:100%">
                                        <tr>
                                            <td style="vertical-align: top;">
                                                <div class="pagination"
                                                     style="float: right;padding-top:0px; margin-top:0px;">
                                                    ${page.pageStr}
                                                </div>
                                            </td>
                                        </tr>
                                    </table>
                                    <div class="clearfix">
                                        <div class="pull-right tableTools-container"></div>
                                    </div>
                                </div>
                            </div>
                        </div><!-- /.row -->
                    </div>
                </div>
            </div><!-- /.page-content -->
        </div>
    </div><!-- /.main-content -->

    <!-- 返回顶部 -->
    <a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
        <i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
    </a>
</div><!-- /.main-container -->

<!-- 明细 模态框（Modal） -->
<div class="modal fade" id="detailModal" tabindex="-1" role="dialog" aria-labelledby="detailModalLabel" aria-hidden="true"
     aria-describedby="明细界面">
    <div class="modal-dialog" role="document" aria-hidden="true">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="关闭">×</button>
                <h4 class="modal-title" id="detailModalLabel">明细</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-xs-12">
                        <form class="form-horizontal" role="form">
                            <div class="form-group">
                                <label for="username_" class="col-sm-3 control-label no-padding-right">用户:</label>
                                <div class="col-sm-9">
                                    <input type="text" id="username_" name="username_" value="" class="col-xs-10 col-sm-5">
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="createDate_" class="col-sm-3 control-label no-padding-right">时间:</label>
                                <div class="col-sm-9">
                                    <input type="text" name="createDate_" value="" id="createDate_" class="col-xs-10 col-sm-5">
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="ip_" class="col-sm-3 control-label no-padding-right">地址:</label>
                                <div class="col-sm-9">
                                    <input type="text" name="ip_" value="" id="ip_" class="col-xs-10 col-sm-5">
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="handle_" class="col-sm-3 control-label no-padding-right">操作:</label>
                                <div class="col-sm-9">
                                    <input type="text" name="handle_" value="" id="handle_" class="col-xs-10 col-sm-5">
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="method_" class="col-sm-3 control-label no-padding-right">方法:</label>
                                <div class="col-sm-9">
                                    <input type="text" name="method_" value="" id="method_" class="col-xs-10 col-sm-5">
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="url_" class="col-sm-3 control-label no-padding-right">URL:</label>
                                <div class="col-sm-9">
                                    <input type="text" name="url_" value="" id="url_" class="col-xs-10 col-sm-5">
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="parameter_" class="col-sm-3 control-label no-padding-right">参数:</label>
                                <div class="col-sm-9">
                                    <input type="text" name="parameter_" value="" id="parameter_" class="col-xs-10 col-sm-5">
                                </div>
                            </div>

                        </form>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="saveChange()">提交</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<script>

    $(function () {
        $('#detailModal').modal({
            keyboard: true,
            show: false,
            width: "auto",
            'margin-left': function() {
                return -($(this).width() / 2);
            }
        });

        var log_list_table = $('#log_list').DataTable({
            bAutoWidth: false,

            language: {
                "url": "<%=basePath%>/plugins/dataTables/i18n/dataTables_zh-CN.json"
            },
            "sPaginationType" : "full_numbers",  //详细分页组，可以支持直接跳转到某页

            "processing": true,  // 设置为true，就会有表格加载时的提示
            // 开启服务器模式
            serverSide: true,
            columnDefs: [
                {
                    "targets": -1,  // 最后一列添加明细按键
                    "data": null,
                    "defaultContent": "<a class='btn btn-xs btn-info' title='明细'> <i class='ace-icon fa fa-flag bigger-120' title='明细'></i> </a>"
                },
                {
                    "targets": 0,  // 第一列隐藏
                    "data": "logId",
                    "visible": false,
                    "searchable": false
                }
            ],
            // 绑定数据
            "aoColumns": [
                {"mDataProp": "logId"},
                {"mDataProp": "createDate",
                    render: function(data, type, full, meta) {
                        //return toIsoDateTime(data);
                        var crtTime = new Date(data);
                        return dateFmt("yyyy-MM-dd hh:mm:ss", crtTime);  //  直接调用公共JS里面的时间类处理的办法
                    }
                },
                {"mDataProp": "username"},
                {"mDataProp": "ip"},
                {"mDataProp": "handle"},
                {"mDataProp": "method"},
                {"mDataProp": "url"},
                {}
            ],
            // 数据来源（包括处理分页，排序，过滤）
            ajax: {
                url: "<%=basePath%>/b/syslog/fetchData.do",
                "dataSrc": "data"
            },

            searchDelay: 1000
        });

        $.fn.dataTable.Buttons.defaults.dom.container.className = 'dt-buttons btn-overlap btn-group btn-overlap';

        log_list_table.on('select', function (e, dt, type, index) {
            if (type === 'row') {
                $(log_list_table.row(index).node()).find('input:checkbox').prop('checked', true);
            }
        });
        log_list_table.on('deselect', function (e, dt, type, index) {
            if (type === 'row') {
                $(log_list_table.row(index).node()).find('input:checkbox').prop('checked', false);
            }
        });

        //select/deselect all rows according to table header checkbox
        $('#log_list > thead > tr > th input[type=checkbox], #log_list_wrapper input[type=checkbox]').eq(0).on('click', function () {
            var th_checked = this.checked;//checkbox inside "TH" table header

            $('#log_list').find('tbody > tr').each(function () {
                var row = this;
                if (th_checked) log_list_table.row(row).select();
                else  log_list_table.row(row).deselect();
            });
        });

        //select/deselect a row when the checkbox is checked/unchecked
        $('#log_list').on('click', 'td input[type=checkbox]', function () {
            var row = $(this).closest('tr').get(0);
            if (this.checked) log_list_table.row(row).deselect();
            else log_list_table.row(row).select();
        });

        // 清除弹窗原数据
        $("#detailModal").on("hidden.bs.modal", function() {
            $(this).removeData("bs.modal");
        });

        $('#log_list tbody').on('click', 'a', function() {
            var data = log_list_table.row($(this).parents('tr')).data();  // 所选择的行的数据
            alert(data.logId);
            $("#detailModal").modal('show');

            $.ajax({
                url: "<%=basePath%>/b/syslog/logDetail.do",
                data: {
                    logId : data.logId
                },
                cache: false,
                success: function(sysLog) {
                    console.log(sysLog);
                    $("#username_").attr('val', sysLog.username);
                    $('#createDate_').attr('val', function() {
                        var crtTime = new Date(sysLog.createDate);
                        return dateFmt("yyyy-MM-dd hh:mm:ss", crtTime);  //  直接调用公共JS里面的时间类处理的办法
                    });
                    $('#ip_').attr('val', sysLog.ip);
                    $('#handle_').attr('val', sysLog.handle);
                    $('#method_').attr('val', sysLog.method);
                    $('#url_').attr('val', sysLog.url);
                    $('#parameter_').attr('val', sysLog.parameter);
                }
            });
        });

        $(document).on('click', '#log_list .dropdown-toggle', function (e) {
            e.stopImmediatePropagation();
            e.stopPropagation();
            e.preventDefault();
        })
    });
</script>

</body>
</html>