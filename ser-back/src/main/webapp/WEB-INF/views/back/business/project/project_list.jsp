<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>项目申报列表</title>
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
    <link href="<%=basePath%>/plugins/bootstrap/extends/bootstrap-datepicker/bootstrap-datepicker3.min.css" rel="stylesheet" />

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
                        <%--<div class="row">
                            <div class="col-xs-12">
                                <form id="userSearchFrm" class="form-inline" action="user/list.do" method="get">
                                    <a class="btn btn-info" href="#" value="">新增</a>
                                    <label class="inline">用户</label>
                                    <input type="text" id="userInfo" name="userInfo" value="" class="form-control">
                                    <button type="submit" class="btn btn-purple btn-sm">
                                        <span class="ace-icon fa fa-search icon-on-right bigger-110"></span>
                                        搜索
                                    </button>
                                </form>
                            </div>
                        </div>--%>

                        <div class="space-4"></div>

                        <div class="row">
                            <div class="col-xs-12">
                                <table id="menu_list" class="table table-striped table-bordered table-hover" style="margin-top:5px;"><%-- cellspacing="0"  table table-striped table-bordered table-hover--%>
                                    <thead>
                                    <tr>
                                        <th class="center" style="width:50px;">
                                            <label class="pos-rel">
                                                <input id="zcheckbox" type="checkbox" class="ace"/>
                                                <span class="lbl"></span>
                                            </label>
                                        </th>
                                        <th class="center" style="width:60px;">序号</th>
                                        <th class="center">项目编号</th>
                                        <th class="center">项目名称</th>
                                        <th class="center">申报人</th>
                                        <th class="center">申报时间</th>
                                        <th class="center">项目类型</th>
                                        <th class="center">申报经费</th>
                                        <th class="center">操作</th>
                                    </tr>
                                    </thead>


                                    <c:choose>
                                        <c:when test="${not empty proList}">
                                            <c:if test="${QX.cha == 1}">
                                            <c:forEach items="${proList}" var="pro" varStatus="vs">
                                                <tr>
                                                    <td class="center" style="width:50px;">
                                                        <label class="pos-rel">
                                                            <input type="checkbox" name="ids" value="${pro.id}"
                                                                id="${pro.id}" alt="${pro.proCode}" class="ace"/>
                                                            <span class="lbl"></span>
                                                        </label>
                                                    </td>
                                                    <td class="center" style="width: 60px;">${vs.index+1}</td>
                                                    <td class="center">${pro.proCode}</td>
                                                    <td class="center">${pro.proName}</td>
                                                    <td class="center">${pro.reportor}</td>
                                                    <td class="center"><fmt:formatDate value="${pro.reportTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>:
                                                    <td class="center">${pro.proType}</td>
                                                    <td class="center">${pro.declareFunds}</td>
                                                    <td class="center">
                                                        <c:if test="${QX.edit != 1 && QX.del != 1 }">
                                                            <span class="label label-large label-grey arrowed-in-right arrowed-in">
                                                                <i class="ace-icon fa fa-lock" title="无权限"></i>
                                                            </span>
                                                        </c:if>
                                                        <div class="hidden-sm hidden-xs btn-group">

                                                            <c:if test="${QX.edit == 1 }">
                                                                <a class="btn btn-xs btn-success" data-toggle="modal" data-target="#myModal" title="编辑" onclick="editProject('${pro.id}');">
                                                                    <i class="ace-icon fa fa-pencil-square-o bigger-120" title="编辑"></i>
                                                                </a>
                                                            </c:if>
                                                            <c:if test="${QX.del == 1 }">
                                                                <a class="btn btn-xs btn-danger" onclick="delProject('${pro.id }');">
                                                                    <i class="ace-icon fa fa-trash-o bigger-120" title="删除"></i>
                                                                </a>
                                                            </c:if>
                                                        </div>
                                                        <div class="hidden-md hidden-lg">
                                                            <div class="inline pos-rel">
                                                                <button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown" data-position="auto">
                                                                    <i class="ace-icon fa fa-cog icon-only bigger-110"></i>
                                                                </button>
                                                                <ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">


                                                                    <c:if test="${QX.edit == 1 }">
                                                                        <li>
                                                                            <a style="cursor:pointer;" onclick="editProject('${pro.id}');" class="tooltip-success" data-rel="tooltip" title="修改">
																	<span class="green">
																		<i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
																	</span>
                                                                            </a>
                                                                        </li>
                                                                    </c:if>
                                                                    <c:if test="${QX.del == 1 }">
                                                                        <li>
                                                                            <a style="cursor:pointer;" onclick="delProject('${pro.id }');" class="tooltip-error" data-rel="tooltip" title="删除">
																	<span class="red">
																		<i class="ace-icon fa fa-trash-o bigger-120"></i>
																	</span>
                                                                            </a>
                                                                        </li>
                                                                    </c:if>
                                                                </ul>
                                                            </div>
                                                        </div>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                            </c:if>
                                            <c:if test="${QX.cha == 0}">
                                                <tr>
                                                    <td colspan="100" class="center">您无权查看</td>
                                                </tr>
                                            </c:if>
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
                                                <c:if test="${QX.add == 1}">
                                                    <a class="btn btn-mini btn-success" data-toggle="modal" data-target="#myModal" onclick="addTag();">新增</a>
                                                </c:if>
                                                <c:if test="${QX.del == 1 }">
                                                    <a title="批量删除" class="btn btn-mini btn-danger" onclick="makeAll();" >删除
                                                        <%--<i class='ace-icon fa fa-trash-o bigger-120'></i>--%>
                                                    </a>
                                                </c:if>
                                            </td>
                                            <td style="vertical-align: top;">
                                                <div class="pagination" style="float: right;padding-top:0px; margin-top:0px;">
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
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" aria-describedby="新增/编辑界面">
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
                            <input type="hidden"  id="tag" name="code" value="ADD"/>
                            <input type="hidden"  id="proId" name="proId" value=""/>
                            <div class="form-group">
                                <label for="proCode" class="col-sm-3 control-label no-padding-right">项目编码:</label>
                                <div class="col-sm-9">
                                    <input type="text"  id="proCode" name="proCode" value="" class="col-xs-10 col-sm-5"
                                           placeholder="请输入项目编码"><i class="ace-icon fa fa-asterisk red"></i>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="proName" class="col-sm-3 control-label no-padding-right">项目名称:</label>
                                <div class="col-sm-9">
                                    <input type="text" name="proName" value="" id="proName" class="col-xs-10 col-sm-5"
                                           placeholder="项目名称"><i class="ace-icon fa fa-asterisk red bottom"></i>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="reportor" class="col-sm-3 control-label no-padding-right">申报人:</label>
                                <div class="col-sm-9">
                                    <input type="text" name="reportor" value="" id="reportor" class="col-xs-10 col-sm-5"
                                           placeholder="申报人"><i class="ace-icon fa fa-asterisk red bottom"></i>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="proType" class="col-sm-3 control-label no-padding-right">项目类型:</label>
                                <div class="col-sm-9">
                                    <input type="text" name="proType" value="" id="proType" class="col-xs-10 col-sm-5"
                                           placeholder="项目类型"><i class="ace-icon fa fa-asterisk red bottom"></i>
                                </div>
                            </div>


                            <div class="form-group">
                                <label for="declareFunds" class="col-sm-3 control-label no-padding-right">申报经费:</label>
                                <div class="col-sm-9">
                                    <input type="text" name="declareFunds" value="" id="declareFunds" class="col-xs-10 col-sm-5"
                                           placeholder="申报经费"><i class="ace-icon fa fa-asterisk red bottom"></i>
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
    $(function() {
        $('#myModal').modal({
            keyboard: true,
            show:false
        });
    });

    $(function () {
        $('#id-date-picker-1').datepicker({
            autoclose: true,
            language: "zh-CN",
            format: "yyyy-mm-dd",
            todayHighlight: true
        }).next().on(ace.click_event, function() {
            $(this).previous().focus();
        })
    });
    <!-- 添加add标签 重置form -->
    function addTag(){
        $('h4').html("新增");
        $("#tag").val("ADD");
        var  tag = $("#tag").val();
        $("#proCode").val("");
        $("#proName").val("");
        $("#reportor").val("");
        $("#proType").val("");
        $("#declareFunds").val("");
    }

    <!--编辑项目-->
    function editProject(id){
        $('h4').html("编辑");
        $("#tag").val("EDIT");
        var tag=  $("#tag").val();
        if (id == '') {
            layer.msg("编辑操作出错！");
            return false;
        }
        $.ajax({
            type: "GET",
            url: "b/project/editProject.do",
            dataType: "json",
            async: true,
            data: {
               proIds: id
            },
            success: function (pro) {
                $("#proId").val(pro.id);
                $("#proCode").val(pro.proCode);
                $("#proName").val(pro.proName);
                $("#reportor").val(pro.reportor);
                $("#proType").val(pro.proType);
                $("#declareFunds").val(pro.declareFunds);
            }
        });

    }

    //单个删除
    function delProject(id){
        layer.confirm('您确定要删除该字典数据？', {
            btn: ['确定', '暂时不要']  // 按钮
        }, function(){
            layer.msg('您点击了确定', {icon:1});

            if (id == '') {
                layer.msg("删除操作出错！");
                return false;
            }


            // 执行异步删除动作
            $.ajax({
                type: "POST",
                url: "b/project/deleteProjects.do",
                dataType: "json",
                async: true,
                data: {
                    proIds: id
                },
                success: function (rc) {
                    if (rc.code == '0') {
                        layer.msg('已为您删除该字典数据', {icon:1});
                        setTimeout(function () {
                            location.reload();
                        },500);
                    } else
                        layer.msg('删除操作出错![' + rc.message + ']');
                }
            });

            return true;
        }, function() {

            return true;
        });

        return false;

    };

    //批量删除
    function makeAll(){
        layer.confirm('您确定要删除该字典数据？', {
            btn: ['确定', '暂时不要']  // 按钮
        }, function(){
            layer.msg('您点击了确定', {icon:1});
            var proIds = "";
            $('input:checkbox[name=ids]:checked').each(function(i){
                if(0==i){
                    proIds = $(this).val();
                }else{I
                    proIds += (","+$(this).val());
                }
            });
            if (proIds == '') {
                layer.msg("请选择要删除的信息!");
                return false;
            }


            // 执行异步删除动作
            $.ajax({
                type: "POST",
                url: "b/project/deleteProjects.do",
                dataType: "json",
                async: true,
                data: {
                    proIds: proIds
                },
                success: function (rc) {
                    if (rc.code == '0') {
                        layer.msg('已为您删除该字典数据', {icon:1});
                        setTimeout(function () {
                            location.reload();
                        }, 500);

                    } else
                        layer.msg('删除操作出错![' + rc.message + ']');
                }
            });

            return true;
        }, function() {

            return true;
        });

        return false;


    };

    <!-- 提交form -->
    function saveChange() {
        var  tag=$("#tag").val();
        var proCode = $("#proCode").val();
        var proName = $("#proName").val();

        if(tag=="EDIT"){
            var proId = $("#proId").val();
        }else {
            var proId = "";
        }
        var proType = $("#proType").val();
        var reportor = $("#reportor").val();
        var declareFunds = $("#declareFunds").val();
        if (proCode == null || proCode == '') {
            layer.msg('请正确填写字典编码!');
            return false;
        }

        $.ajax({
            type: "POST",
            url:"<%=basePath%>/b/project/editProject.do",
            dataType: "json",
            async: true,
            data: {
                tag:tag,
                id:proId,
                proCode: proCode,
                proName: proName,
                proType: proType,
                reportor: reportor,
                declareFunds: declareFunds
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
        var menu_list_table = $('#menu_list').DataTable({
            /*"dom": 'Bfrtip',*/
            bAutoWidth: false,
            "aocolumns": [
                {"bSortable": false},
                null, null, null, null, null,
                {"bSortable": false}
            ],
            "aaSorting": [],

            select: {
                style: 'multi'
            },

            language: {
                "url": "<%=basePath%>/plugins/DataTables/i18n/zh-CN.json"
            }
        });

        $.fn.dataTable.Buttons.defaults.dom.container.className = 'dt-buttons btn-overlap btn-group btn-overlap';

        new $.fn.dataTable.Buttons(menu_list_table, {
            buttons: [
                {
                    extend: "colvis",
                    text: "<i class='fa fa-search bigger-110 blue'></i><span class='hidden'>显示/隐藏 列</span>",
                    clasName: "btn btn-white btn-primary btn-bold",
                    columns: ":not(:first):not(:last)"
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

        menu_list_table.buttons().container().appendTo($('.tableTools-container'));

        // style the message box
        var defaultCopyAction = menu_list_table.button(1).action();
        menu_list_table.button(1).action(function (e, dt, button, config) {
            defaultCopyAction(e, dt, button, config);
            $('.dt-button-info').addClass('gritter-item-wrapper gritter-info gritter-center white');
        });

        var defaultColvisAction = menu_list_table.button(0).action();
        menu_list_table.button(0).action(function (e, dt, button, config) {

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

        menu_list_table.on('select', function (e, dt, type, index) {
            if (type === 'row') {
                $(menu_list_table.row(index).node()).find('input:checkbox').prop('checked', true);
            }
        });
        menu_list_table.on('deselect', function (e, dt, type, index) {
            if (type === 'row') {
                $(menu_list_table.row(index).node()).find('input:checkbox').prop('checked', false);
            }
        });

        //table checkboxes
        $('th input[type=checkbox], td input[type=checkbox]').prop('checked', false);

        //select/deselect all rows according to table header checkbox
        $('#menu_list > thead > tr > th input[type=checkbox], #menu_list_wrapper input[type=checkbox]').eq(0).on('click', function () {
            var th_checked = this.checked;//checkbox inside "TH" table header

            $('#menu_list').find('tbody > tr').each(function () {
                var row = this;
                if (th_checked) menu_list_table.row(row).select();
                else  menu_list_table.row(row).deselect();
            });
        });

        //select/deselect a row when the checkbox is checked/unchecked
        $('#menu_list').on('click', 'td input[type=checkbox]', function () {
            var row = $(this).closest('tr').get(0);
            if (this.checked) menu_list_table.row(row).deselect();
            else menu_list_table.row(row).select();
        });

        $(document).on('click', '#menu_list .dropdown-toggle', function(e) {
            e.stopImmediatePropagation();
            e.stopPropagation();
            e.preventDefault();
        })

    });
</script>

</body>
</html>
