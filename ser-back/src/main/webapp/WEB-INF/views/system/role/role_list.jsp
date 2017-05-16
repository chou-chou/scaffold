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
    <title>角色列表</title>
    <base href="<%=basePath%>">

    <%@ include file="../../comm/default_header.jsp" %>

    <!-- datatables相关css/js -->
    <script type="text/javascript">
        if('ontouchstart' in document.documentElement) document.write("<script src='<%=basePath%>/static/js/jquery.mobile.custom.min.js'>"+"<" + "/script>")
    </script>

    <script src="<%=basePath%>/static/js/jquery.dataTables.min.js"></script>
    <script src="<%=basePath%>/static/js/jquery.dataTables.bootstrap.min.js"></script>
    <script src="<%=basePath%>/static/js/dataTables.buttons.min.js"></script>
    <script src="<%=basePath%>/static/js/buttons.flash.min.js"></script>
    <script src="<%=basePath%>/static/js/buttons.html5.min.js"></script>
    <script src="<%=basePath%>/static/js/buttons.print.min.js"></script>
    <script src="<%=basePath%>/static/js/buttons.colVis.min.js"></script>
    <script src="<%=basePath%>/static/js/dataTables.select.min.js"></script>

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
                                        <th class="center">角色编号</th>
                                        <th class="center">角色名称</th>
                                        <th class="center">是否为管理员</th>
                                        <th class="center">备注说明</th>
                                        <th class="center">上级编码</th>
                                        <th class="center">操作</th>
                                    </tr>
                                    </thead>


                                    <c:choose>
                                        <c:when test="${not empty roleList}">
                                            <c:if test="${QX.cha == 1}">
                                            <c:forEach items="${roleList}" var="role" varStatus="vs">
                                                <tr>
                                                    <td class="center" style="width:50px;">
                                                        <label class="pos-rel">
                                                            <input type="checkbox" name="ids" value="${role.roleId}"
                                                                id="${role.roleId}" alt="${role.code}" class="ace"/>
                                                            <span class="lbl"></span>
                                                        </label>
                                                    </td>
                                                    <td class="center" style="width: 60px;">${vs.index+1}</td>
                                                    <td class="center">${role.code}</td>
                                                    <td class="center">${role.roleName}</td>
                                                    <td style="width:120px;" class="center">
                                                        <c:if test="${role.isSys == 0}">
                                                            <span class="label label-success arrowed">是</span>
                                                        </c:if>
                                                        <c:if test="${role.isSys == 1}">
                                                            <span class="label label-important arrowed-in">否</span>
                                                        </c:if>
                                                    </td>
                                                    <td class="center">${role.remark}</td>
                                                    <td class="center">${role.supId}</td>
                                                    <td class="center">
                                                        <c:if test="${QX.edit != 1 && QX.del != 1 }">
                                                            <span class="label label-large label-grey arrowed-in-right arrowed-in">
                                                                <i class="ace-icon fa fa-lock" title="无权限"></i>
                                                            </span>
                                                        </c:if>
                                                        <div class="hidden-sm hidden-xs btn-group">

                                                            <c:if test="${QX.edit == 1 }">
                                                                <a class="btn btn-xs btn-success" data-toggle="modal" data-target="#myModal" title="编辑" onclick="editRole('${role.roleId}');">
                                                                    <i class="ace-icon fa fa-pencil-square-o bigger-120" title="编辑"></i>
                                                                </a>
                                                            </c:if>
                                                            <c:if test="${QX.del == 1 }">
                                                                <a class="btn btn-xs btn-danger" onclick="delRole('${role.roleId }');">
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
                                                                            <a style="cursor:pointer;" onclick="editRole('${role.roleId}');" class="tooltip-success" data-rel="tooltip" title="修改">
																	<span class="green">
																		<i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
																	</span>
                                                                            </a>
                                                                        </li>
                                                                    </c:if>
                                                                    <c:if test="${QX.del == 1 }">
                                                                        <li>
                                                                            <a style="cursor:pointer;" onclick="delRole('${role.roleId }');" class="tooltip-error" data-rel="tooltip" title="删除">
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
                            <input type="hidden"  id="roleId" name="roleId" value=""/>
                            <div class="form-group">
                                <label for="code" class="col-sm-3 control-label no-padding-right">角色编码:</label>
                                <div class="col-sm-9">
                                    <input type="text"  id="code" name="code" value="" class="col-xs-10 col-sm-5"
                                           placeholder="请输入角色编码"><i class="ace-icon fa fa-asterisk red"></i>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="roleName" class="col-sm-3 control-label no-padding-right">角色名称:</label>
                                <div class="col-sm-9">
                                    <input type="text" name="roleName" value="" id="roleName" class="col-xs-10 col-sm-5"
                                           placeholder="角色名称"><i class="ace-icon fa fa-asterisk red bottom"></i>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label no-padding-right" for="isSys">
                                                   是否管理员:
                                </label>
                                <div class="col-sm-9 checkbox no-padding-left">
                                    <label>
                                        <input id="isSys" name="enabled" value="1"
                                               class="ace ace-checkbox-2" type="checkbox"
                                               <c:if test="${role.isSys == true}">checked</c:if>/>
                                        <span class="lbl" style="color:#2B6EA1;">  注: 勾选即为管理员 </span>
                                    </label>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-sm-3 col-xs-12 no-padding-right"
                                       for="supId">上级角色:</label>
                                <div class="col-sm-9 col-xs-12">
                                    <select id="supId" name="supId" class="select2 col-xs-10 col-sm-5"
                                            style="max-height:135px; overflow-y:auto; overflow-x: hidden">
                                        <option value="-1">请选择</option>
                                        <c:forEach items="${roleList}" var="role" varStatus="index">
                                            <option value="${role.code}">${role.roleName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-3 control-label no-padding-right"
                                       for="orders">序号:</label>
                                <div class="col-sm-9">
                                    <input type="text" id="orders" name="orders"
                                           placeholder="序号" class="col-xs-10 col-sm-5"
                                           value=""/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-3 control-label no-padding-right" for="remark">备注:</label>
                                <div class="col-sm-9">
                                    <textarea id="remark" name="remark" class="form-control"
                                              placeholder="备注信息"></textarea>
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

        $('#supId').select2({
            width: '235px',
            placeholder: {
                id: '-1',
                text: '键入查询'
            },
            allowClear: true,
            language: "zh-CN",
            //minimumInputLength: 1,
            multiple: false
        });
    });
    function addTag(){
        $("#tag").val("ADD");
        var tag=  $("#tag").val();
    }

    function editRole(id){
        $("#tag").val("EDIT");
        var tag=  $("#tag").val();
        if (id == '') {
            layer.msg("编辑操作出错！");
            return false;
        }
        $.ajax({
            type: "GET",
            url: "role/editRole.do",
            dataType: "json",
            async: true,
            data: {
                roleIds: id
            },
            success: function (role) {
                if (role.supId !== undefined && role.supId !== null) {
                    $("#supId").val(role.supId).trigger('change');// 动态改变值以后必须触发改变事件。否则将不会生效(联动)
                } else {
                    $("#supId").val('-1').trigger('change');// 动态改变值以后必须触发改变事件。否则将不会生效(联动)
                }
                $("#roleId").val(role.roleId);
                $("#code").val(role.code);
                $("#roleName").val(role.roleName);
                if (role.isSys == '0') {
                    $("#isSys").attr("checked", 'true');
                } else {
                    $("#isSys").attr("checked", 'false');
                }
                $("#remark").val(role.remark);
                $("#orders").val(role.orders);

            }
        });

    }

    //单个删除
    function delRole(id){
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
                url: "role/deleteRoles.do",
                dataType: "json",
                async: true,
                data: {
                    roleIds: id
                },
                success: function (rc) {
                    if (rc.code == '0') {
                        layer.msg('已为您删除该字典数据', {icon:1});
                        setTimeout(function () {
                            location.reload();
                        },1000);
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
            var roleIds = "";
            $('input:checkbox[name=ids]:checked').each(function(i){
                if(0==i){
                    roleIds = $(this).val();
                }else{
                    roleIds += (","+$(this).val());
                }
            });
            if (roleIds == '') {
                layer.msg("请选择要删除的信息!");
                return false;
            }


            // 执行异步删除动作
            $.ajax({
                type: "POST",
                url: "role/deleteRoles.do",
                dataType: "json",
                async: true,
                data: {
                    roleIds: roleIds
                },
                success: function (rc) {
                    if (rc.code == '0') {
                        layer.msg('已为您删除该字典数据', {icon:1});
                        setTimeout(function () {
                            location.reload();
                        }, 1000);

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

    function saveChange() {

          var  tag=$("#tag").val();


        var code = $("#code").val();
        var roleName = $("#roleName").val();

       if($("input[type='checkbox']").is(':checked')){
           var isSys ="0"
       } else {
           var isSys ="1"
       }
        if(tag=="EDIT"){
            var roleId = $("#roleId").val();
        }else {
            var roleId = "";
        }
        var remark = $("#remark").val();
        var supId = $("#supId").val();
        var orders = $("#orders").val();

        if (code == null || code == '') {
            layer.msg('请正确填写字典编码!');
            return false;
        }

        if (remark == undefined || remark == null)  remark = "";

        $.ajax({
            type: "POST",
            url: "/role/editRole.do",
            dataType: "json",
            async: true,
            data: {
                tag:tag,
                roleId:roleId,
                code: code,
                roleName: roleName,
                isSys: isSys,
                supId: supId,
                orders: orders,
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
        var menu_list_table = $('#menu_list').DataTable({
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
                "url": "<%=basePath%>/plugins/DataTables/i18n/dataTables_zh-CN.json"
            }
        });

        $.fn.dataTable.Buttons.defaults.dom.container.className = 'dt-buttons btn-overlap btn-group btn-overlap';

        new $.fn.dataTable.Buttons(menu_list_table, {
            buttons: [
                {
                    extends: "colvis",
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
                    extend: "csvHtml5",
                    text: "<i class='fa fa-database bigger-110 orange'></i> <span class='hidden'>导出CSV</span>",
                    className: "btn btn-white btn-primary btn-bold"
                },
                {
                    extend: "excelHtml5",
                    text: "<i class='fa fa-file-excel-o bigger-110 green'></i> <span class='hidden'>导出Excel</span>",
                    className: "btn btn-white btn-primary btn-bold"
                },
                {
                    extend: "pdfHtml5",
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

        /*setTimeout(function () {
            $($('.tableTools-container')).find('a.dt-button').each(function () {
                var div = $(this).find(' > div').first();
                if (div.length == 1) div.tooltip({container: 'body', title: div.parent().text()});
                else $(this).tooltip({container: 'body', title: $(this).text()});
            });
        }, 500);*/

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
        $('#menu_list > thead > tr > th input[type=checkbox], #dynamic-table_wrapper input[type=checkbox]').eq(0).on('click', function () {
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

    });
</script>

</body>
</html>
