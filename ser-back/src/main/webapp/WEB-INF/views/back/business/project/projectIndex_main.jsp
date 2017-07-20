<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
    <title>项目评审指标列表</title>
    <base href="<%=basePath%>">

    <%@ include file="../../../comm/default_header.jsp" %>

    <!-- datatables相关css/js -->
    <script type="text/javascript">
        if ('ontouchstart' in document.documentElement) document.write("<script src='<%=basePath%>/plugins/jquery/jquery.mobile.custom.min.js'>" + "<" + "/script>")
    </script>

    <script src="<%=basePath%>/plugins/dataTables/jquery.dataTables.js"></script>
    <script src="<%=basePath%>/plugins/dataTables/jquery.dataTables.bootstrap.min.js"></script>
    <script src="<%=basePath%>/plugins/dataTables/extends/button/dataTables.buttons.js"></script>
    <script src="<%=basePath%>/plugins/dataTables/extends/button/buttons.flash.js"></script>
    <script src="<%=basePath%>/plugins/dataTables/extends/button/buttons.html5.js"></script>
    <script src="<%=basePath%>/plugins/dataTables/extends/button/buttons.print.js"></script>
    <script src="<%=basePath%>/plugins/dataTables/extends/button/buttons.colVis.js"></script>
    <script src="<%=basePath%>/plugins/dataTables/extends/select/dataTables.select.js"></script>

    <script src="<%=basePath%>/static/js/moment.min.js"></script>
    <script src="<%=basePath%>/plugins/bootstrap/extends/bootstrap-datepicker/bootstrap-datepicker.min.js"></script>
    <script src="<%=basePath%>/plugins/bootstrap/extends/bootstrap-datepicker/i18n/bootstrap-datepicker.zh-CN.js"></script>
    <link href="<%=basePath%>/plugins/bootstrap/extends/bootstrap-datepicker/bootstrap-datepicker3.min.css"
          rel="stylesheet"/>

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
                                <table id="index_list" class="table table-striped table-bordered table-hover"
                                       style="margin-top:5px;"><%-- cellspacing="0"  table table-striped table-bordered table-hover--%>
                                    <thead>
                                    <tr>
                                        <th class="center" style="width:50px;">
                                            <label class="pos-rel">
                                                <input id="zcheckbox" type="checkbox" class="ace"/>
                                                <span class="lbl"></span>
                                            </label>
                                        </th>
                                        <th class="center" style="width:60px;">序号</th>
                                        <th class="center">指标编号</th>
                                        <th class="center">指标名称</th>
                                        <th class="center">指标类型</th>
                                        <th class="center">说明</th>
                                        <th class="center">总分</th>
                                    </tr>
                                    </thead>


                                    <c:choose>
                                        <c:when test="${not empty indexList}">
                                            <c:forEach items="${indexList}" var="index" varStatus="vs">
                                                <tr>
                                                    <td class="center" style="width:50px;">
                                                        <label class="pos-rel">
                                                            <input type="checkbox" name="ids" value="${index.id}"
                                                                   id="${index.id}" alt="${index.indexCode}"
                                                                   class="ace"/>
                                                            <span class="lbl"></span>
                                                        </label>
                                                    </td>
                                                    <td class="center" style="width: 60px;">${vs.index+1}</td>
                                                    <td class="center">${index.indexCode}</td>
                                                    <td class="center">${index.indexName}</td>
                                                    <td class="center">${index.indexType}</td>
                                                    <td class="center">${index.remark}</td>
                                                    <td class="center">${index.indexScore}</td>
                                                </tr>
                                            </c:forEach>
                                        </c:when>
                                        <c:otherwise>
                                            <th class="main_info">
                                            <td colspan="100" class="center">没有相关数据</td>
                                            </th>
                                        </c:otherwise>
                                    </c:choose>
                                    </tbody>
                                </table>
                                <div class="page-header position-relative">
                                    <table style="width:100%">
                                        <tr>
                                            <td style="vertical-align: top;">
                                                <shiro:hasAnyPermission name="sys:projectIndex:add">
                                                    <a inCtrl class="btn btn-mini btn-success" data-toggle="modal"
                                                       data-target="#indexModal" onclick="addTag();">新增</a>
                                                </shiro:hasAnyPermission>
                                                <shiro:hasAnyPermission name="sys:projectIndex:edit">
                                                    <a inCtrl class="btn btn-mini btn-warn"
                                                       onclick="editProjectIndex();">编辑</a>
                                                </shiro:hasAnyPermission>
                                                <shiro:hasAnyPermission name="sys:projectIndex:delete">
                                                    <a inCtrl title="批量删除" class="btn btn-mini btn-danger"
                                                       onclick="deleteProjectIndex();">删除 </a>
                                                </shiro:hasAnyPermission>
                                            </td>
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

<!-- 模态框（Modal） -->
<div class="modal fade" id="indexModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"
     aria-describedby="新增/编辑界面">
    <div class="modal-dialog" role="document" aria-hidden="true">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="关闭">×</button>
                <h4 class="modal-title" id="myModalLabel">编辑</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-xs-12">
                        <form class="form-horizontal" role="form">
                            <input type="hidden" id="tag" name="code" value="ADD"/>
                            <input type="hidden" id="indexId" name="indexId" value=""/>
                            <div class="form-group">
                                <label for="indexCode" class="col-sm-3 control-label no-padding-right">指标编码:</label>
                                <div class="col-sm-9">
                                    <input type="text" id="indexCode" name="indexCode" value=""
                                           class="col-xs-10 col-sm-5"
                                           placeholder="请输入指标编码"><i class="ace-icon fa fa-asterisk red"></i>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="indexName" class="col-sm-3 control-label no-padding-right">指标名称:</label>
                                <div class="col-sm-9">
                                    <input type="text" name="indexName" value="" id="indexName"
                                           class="col-xs-10 col-sm-5"
                                           placeholder="指标名称"><i class="ace-icon fa fa-asterisk red bottom"></i>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="indexType" class="col-sm-3 control-label no-padding-right">指标类型:</label>
                                <div class="col-sm-9">
                                    <input type="text" name="indexType" value="" id="indexType"
                                           class="col-xs-10 col-sm-5"
                                           placeholder="指标类型"><i class="ace-icon fa fa-asterisk red bottom"></i>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="remark" class="col-sm-3 control-label no-padding-right">指标说明:</label>
                                <div class="col-sm-9">
                                    <input type="text" name="remark" value="" id="remark" class="col-xs-10 col-sm-5"
                                           placeholder="指标说明"><i class="ace-icon fa fa-asterisk red bottom"></i>
                                </div>
                            </div>


                            <div class="form-group">
                                <label for="indexScore" class="col-sm-3 control-label no-padding-right">指标总分:</label>
                                <div class="col-sm-9">
                                    <input type="text" name="indexScore" value="" id="indexScore"
                                           class="col-xs-10 col-sm-5"
                                           placeholder="指标总分"><i class="ace-icon fa fa-asterisk red bottom"></i>
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
        $('#indexModal').modal({
            keyboard: true,
            show: false
        });
    });

    <!-- 添加add标签 重置form -->
    function addTag() {
        $('h4').html("新增");
        $("#tag").val("ADD");
        $("#indexCode").val("");
        $("#indexName").val("");
        $("#indexScore").val("");
        $("#indexType").val("");
        $("#remark").val("");
    }

    <!--编辑项目-->
    function editProjectIndex() {
        var indexId = $('input[name="ids"]:checked').val();
        $('h4').html("编辑");
        $("#tag").val("EDIT");
        if (indexId == '' || null == indexId || typeof(indexId) == "undefined" || indexId == "undefined") {
            layer.msg("勾选要编辑的团队指标！");
            return false;
        } else {
            $('#indexModal').modal();
        }
        $.ajax({
            type: "GET",
            url: "b/projectIndex/editProjectIndex.do",
            dataType: "json",
            async: true,
            data: {
                indexId: indexId
            },
            success: function (index) {
                $("#indexId").val(index.id);
                $("#indexCode").val(index.indexCode);
                $("#indexName").val(index.indexName);
                $("#indexType").val(index.indexType);
                $("#indexScore").val(index.indexScore);
                $("#remark").val(index.remark);
            }
        });


    }


    //批量删除
    function deleteProjectIndex() {
        layer.confirm('您确定要删除该字典数据？', {
            btn: ['确定', '暂时不要']  // 按钮
        }, function () {
            layer.msg('您点击了确定', {icon: 1});
            var indexIds = "";
            $('input:checkbox[name=ids]:checked').each(function (i) {
                if (0 == i) {
                    indexIds = $(this).val();
                } else {
                    I
                    indexIds += ("," + $(this).val());
                }
            });
            if (indexIds == '') {
                layer.msg("请选择要删除的信息!");
                return false;
            }


            // 执行异步删除动作
            $.ajax({
                type: "POST",
                url: "b/projectIndex/deleteProjectIndexs.do",
                dataType: "json",
                async: true,
                data: {
                    indexIds: indexIds
                },
                success: function (rc) {
                    if (rc.code == '0') {
                        layer.msg('已为您删除该字典数据', {icon: 1});
                        setTimeout(function () {
                            location.reload();
                        }, 500);

                    } else
                        layer.msg('删除操作出错![' + rc.message + ']');
                }
            });

            return true;
        }, function () {

            return true;
        });

        return false;


    };

    <!-- 提交form -->
    function saveChange() {
        var tag = $("#tag").val();
        var indexCode = $("#indexCode").val();
        var indexName = $("#indexName").val();

        if (tag == "EDIT") {
            var indexId = $("#indexId").val();
        } else {
            var indexId = "";
        }
        var indexType = $("#indexType").val();
        var indexScore = $("#indexScore").val();
        var remark = $("#remark").val();
        if (indexCode == null || indexCode == '') {
            layer.msg('请正确填写字典编码!');
            return false;
        }

        $.ajax({
            type: "POST",
            url: "<%=basePath%>/b/projectIndex/editProjectIndex.do",
            dataType: "json",
            async: true,
            data: {
                tag: tag,
                id: indexId,
                indexCode: indexCode,
                indexName: indexName,
                indexType: indexType,
                indexScore: indexScore,
                remark: remark
            },
            success: function (rc) {
                if (rc.code == '0') {
                    alert(rc.message);
                    location.reload();
                }
            }
        });
    }

</script>
<script type="text/javascript">
    jQuery(function ($) {
        //initiate dataTables plugin
        var index_list_table = $('#index_list').DataTable({
            /*"dom": 'Bfrtip',*/
            bAutoWidth: false,
            "aoColumns": [
                null, null,
                {"bSortable": true},
                {"bSortable": true},
                {"bSortable": true},
                {"bSortable": true},
                null
            ],
            "aaSorting": [],

            select: {
                style: 'multi'
            },

            language: {
                "url": "<%=basePath%>/plugins/dataTables/i18n/dataTables_zh-CN.json"
            }
        });

        $.fn.dataTable.Buttons.defaults.dom.container.className = 'dt-buttons btn-overlap btn-group btn-overlap';

        new $.fn.dataTable.Buttons(index_list_table, {
            buttons: [
                {
                    extend: "colvis",
                    text: "<i class='fa fa-search bigger-110 blue'></i><span class='hidden'>显示/隐藏 列</span>",
                    className: "btn btn-white btn-primary btn-bold"/*,
                 columns: ":not(:first):not(:last)"*/
                },
                {
                    extend: "copy",
                    text: "<i class='fa fa-copy bigger-110 pink'></i><span class='hidden'>复制</span>",
                    className: "btn btn-white btn-primary btn-bold"
                },
                {
                    extend: "csv",
                    text: "<i class='fa fa-database bigger-110 orange'></i> <span class='hidden'>导出CSV</span>",
                    className: "btn btn-white btn-primary btn-bold"
                },
                {
                    extend: "excel",
                    text: "<i class='fa fa-file-excel-o bigger-110 green'></i> <span class='hidden'>导出Excel</span>",
                    className: "btn btn-white btn-primary btn-bold"
                },
                {
                    extend: "pdf",
                    text: "<i class='fa fa-file-pdf-o bigger-110 red'></i> <span class='hidden'>导出PDF</span>",
                    className: "btn btn-white btn-primary btn-bold",
                    download: 'open'
                },
                {
                    extend: "print",
                    text: "<i class='fa fa-print bigger-110 grey'></i> <span class='hidden'>打印</span>",
                    className: "btn btn-white btn-primary btn-bold",
                    autoPrint: false,
                    message: 'This print was produced using the Print button for DataTables'
                }
            ]
        });

        index_list_table.buttons().container().appendTo($('.tableTools-container'));

        // style the message box
        var defaultCopyAction = index_list_table.button(1).action();
        index_list_table.button(1).action(function (e, dt, button, config) {
            defaultCopyAction(e, dt, button, config);
            $('.dt-button-info').addClass('gritter-item-wrapper gritter-info gritter-center white');
        });

        var defaultColvisAction = index_list_table.button(0).action();
        index_list_table.button(0).action(function (e, dt, button, config) {

            defaultColvisAction(e, dt, button, config);

            if ($('.dt-button-collection > .dropdown-menu').length == 0) {
                $('.dt-button-collection')
                    .wrapInner('<ul class="dropdown-menu dropdown-light dropdown-caret dropdown-caret" />')
                    .find('a').attr('href', '#').wrap("<li />")
            }
            $('.dt-button-collection').appendTo('.tableTools-container .dt-buttons')
        });

        setTimeout(function () {
            $($('.tableTools-container')).find('a.dt-button').each(function () {
                var div = $(this).find(' > div').first();
                if (div.length == 1) div.tooltip({container: 'body', title: div.parent().text()});
                else $(this).tooltip({container: 'body', title: $(this).text()});
            });
        }, 500);

        index_list_table.on('select', function (e, dt, type, index) {
            if (type === 'row') {
                $(index_list_table.row(index).node()).find('input:checkbox').prop('checked', true);
            }
        });
        index_list_table.on('deselect', function (e, dt, type, index) {
            if (type === 'row') {
                $(index_list_table.row(index).node()).find('input:checkbox').prop('checked', false);
            }
        });

        //table checkboxes
        $('th input[type=checkbox], td input[type=checkbox]').prop('checked', false);

        //select/deselect all rows according to table header checkbox
        $('#index_list > thead > tr > th input[type=checkbox], #index_list_wrapper input[type=checkbox]').eq(0).on('click', function () {
            var th_checked = this.checked;//checkbox inside "TH" table header

            $('#index_list').find('tbody > tr').each(function () {
                var row = this;
                if (th_checked) index_list_table.row(row).select();
                else  index_list_table.row(row).deselect();
            });
        });

        //select/deselect a row when the checkbox is checked/unchecked
        $('#index_list').on('click', 'td input[type=checkbox]', function () {
            var row = $(this).closest('tr').get(0);
            if (this.checked) index_list_table.row(row).deselect();
            else index_list_table.row(row).select();
        });

        $(document).on('click', '#index_list .dropdown-toggle', function (e) {
            e.stopImmediatePropagation();
            e.stopPropagation();
            e.preventDefault();
        })
    });
</script>

</body>
</html>