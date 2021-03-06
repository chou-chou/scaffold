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
    <title>角色列表</title>
    <base href="<%=basePath%>">

    <%@ include file="../../../comm/default_header.jsp" %>

    <link rel="stylesheet" href="<%=basePath%>/plugins/jqGrid/ui.jqgrid-bootstrap.css"/>
    <script src="<%=basePath%>/plugins/jqGrid/jquery.jqGrid.js"></script>
    <script src="<%=basePath%>/plugins/jqGrid/grid.locale-cn.js"></script>

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
                                <table id="role_list" class="table table-striped table-bordered table-hover"
                                       style="margin-top:5px;">
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
                                    </tr>
                                    </thead>

                                    <tbody>
                                    <c:choose>
                                        <c:when test="${not empty roleList}">
                                            <c:if test="${QX.cha == 1}">
                                                <c:forEach items="${roleList}" var="role" varStatus="vs">
                                                    <tr>
                                                        <td class="center" style="width:50px;">
                                                            <label class="pos-rel">
                                                                <input type="checkbox" name="ids" value="${role.roleId}"
                                                                       id="${role.roleId}" alt="${role.code}"
                                                                       class="ace"/>
                                                                <span class="lbl"></span>
                                                            </label>
                                                        </td>
                                                        <td class="center" style="width: 60px;">${vs.index+1}</td>
                                                        <td class="center">${role.code}</td>
                                                        <td class="center">${role.roleName}</td>
                                                        <td style="width:120px;" class="center">
                                                            <c:if test="${role.isSys == 1}">
                                                                <span class="label label-success arrowed">是</span>
                                                            </c:if>
                                                            <c:if test="${role.isSys == 0}">
                                                                <span class="label label-important arrowed-in">否</span>
                                                            </c:if>
                                                        </td>
                                                        <td class="center">${role.remark}</td>
                                                        <td class="center">${role.supId}</td>
                                                    </tr>
                                                </c:forEach>
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
                                                <shiro:hasAnyPermission name="sys:role:add">
                                                    <a inCtrl id="roleAddBtn" class="btn btn-mini btn-success"
                                                       data-toggle="modal"
                                                       data-target="#myModal" onclick="addTag();">新增</a>
                                                </shiro:hasAnyPermission>
                                                <shiro:hasAnyPermission name="sys:role:edit">
                                                    <a inCtrl id="roleEditBtn" class="btn btn-mini btn-warn"
                                                       data-toggle="modal"
                                                       data-target="#myModal" onclick="editTag();">编辑</a>
                                                </shiro:hasAnyPermission>
                                                <shiro:hasAnyPermission name="sys:role:delete">
                                                    <a inCtrl id="roleDeleteBtn" title="批量删除" class="btn btn-mini btn-danger"
                                                       onclick="makeAll();">删除</a>
                                                </shiro:hasAnyPermission>
                                                <shiro:hasAnyPermission name="sys:role:privilege">
                                                    <a inCtrl id="rolePrivilegeBtn" class="btn btn-mini btn-danger"
                                                       onclick="editRight();">权限设置</a>
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

<!-- 新增/编辑 模态框（Modal） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"
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
                            <input type="hidden" id="roleId" name="roleId" value=""/>
                            <div class="form-group">
                                <label for="code" class="col-sm-3 control-label no-padding-right">角色编码:</label>
                                <div class="col-sm-9">
                                    <input type="text" id="code" name="code" value="" class="col-xs-10 col-sm-5"
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
                                        <input id="isSys" name="isSys" value="1"
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

<!-- 权限设置 模态框（Modal） -->
<div class="modal fade bs-example-modal-lg" id="rightModal" tabindex="-1" role="dialog" aria-labelledby="rightModalLabel" aria-hidden="true"
     aria-describedby="权限设置界面" >
    <div class="modal-dialog modal-lg" role="document" aria-hidden="true">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="关闭">×</button>
                <h4 class="modal-title" id="rightModalLabel">权限设置</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-xs-12">
                        <div class="row">

                            <!-- 菜单树 -->
                            <div class="col-sm-3">

                                <div class="widget-box widget-color-blue2">
                                    <div class="widget-header">
                                        <h4 class="widget-title lighter smaller">菜单树</h4>
                                    </div>

                                    <div class="widget-body scroll-content" style="overflow:auto;">
                                        <div class="widget-main padding-8">
                                            <ul id="menuTree" class="ztree"></ul>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!-- 菜单新增/编辑 -->
                            <div class="col-sm-9">

                                <input type="hidden" id="roleId4Right" name="roleId4Right" />

                                <%--<div class="page-header">
                                    <h1>
                                        按钮
                                        <small>
                                            <i class="ace-icon fa fa-angle-double-right"></i>
                                            对应菜单页面
                                        </small>
                                    </h1>
                                </div>--%>

                                <div class="row">
                                    <div class="col-xs-12">
                                        <!-- 按钮信息列表 -->
                                        <table id="button_list" class="table table-striped table-bordered table-hover">
                                            <thead>
                                            <tr>
                                                <th class="center">
                                                    <label>
                                                        <input type="checkbox" id="bids" name="bids" class="ace"/>
                                                        <span class="lbl"></span>
                                                    </label>
                                                </th>
                                                <th>菜单编号</th>
                                                <th>按钮编码</th>
                                                <th>
                                                    <i class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>按钮标识
                                                </th>
                                                <th>按钮标题</th>
                                                <th>按钮类型</th>
                                            </tr>
                                            </thead>

                                            <tbody>
                                            <c:forEach items="${allBtnList}" var="button" varStatus="vs">
                                                <tr id="${button.id}">
                                                    <td class="center">
                                                        <label class="pos-rel">
                                                            <input type="checkbox" name="bids"
                                                                   value="${button.id}"
                                                                   id="${button.id}" alt="${button.btnId}"
                                                                   class="ace"/>
                                                            <span class="lbl"></span>
                                                        </label>
                                                    </td>
                                                    <td>${button.menuId}</td>
                                                    <td>${button.btnId}</td>
                                                    <td class="hidden-480"><font color="#00008b">${button.btnTag}</font></td>
                                                    <td>${button.btnText}</td>
                                                    <td>${button.btnType}</td>
                                                </tr>
                                            </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" <%--data-dismiss="modal"--%> onclick="test();">关闭</button>
                <shiro:hasAnyPermission name="sys:role:add">
                    <button inCtrl type="button" class="btn btn-primary" onclick="saveRight();">提交</button>
                </shiro:hasAnyPermission>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<script>
    $(function () {
        $('#rightModal').modal({
            keyboard: true,
            show: false,
            width: "auto",
            'margin-left': function() {
                return -($(this).width() / 2);
            }
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
    <!-- 添加add标签 重置form -->
    function addTag() {
        $('h4').html("新增");
        $("#tag").val("ADD");
        var tag = $("#tag").val();
        $("#code").val("");
        $("#roleName").val("");
        $("[name=isSys]:checkbox").prop("checked", false);
        $("#roleId").val("");
        $("#remark").val("");
        $("#orders").val("");
        var code = $("#code").val();
        alert(tag + code);
    }

    <!--编辑角色 -->
    function editRole(id) {
        $('h4').html("编辑");
        $("#tag").val("EDIT");
        var tag = $("#tag").val();
        if (id == '') {
            layer.msg("编辑操作出错！");
            return false;
        }
        $.ajax({
            type: "GET",
            url: "b/role/editRole.do",
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
                if (role.isSys == '1') {
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
    function delRole(id) {
        layer.confirm('您确定要删除该角色数据？', {
            btn: ['确定', '暂时不要']  // 按钮
        }, function () {
            layer.msg('您点击了确定', {icon: 1});

            if (id == '') {
                layer.msg("删除操作出错！");
                return false;
            }

            // 执行异步删除动作
            $.ajax({
                type: "POST",
                url: "<%=basePath%>/b/role/deleteRoles.do",
                dataType: "json",
                async: true,
                data: {
                    roleIds: id
                },
                success: function (rc) {
                    if (rc.code == '0') {
                        layer.msg('已为您删除该角色数据', {icon: 1});
                        setTimeout(function () {
                            location.reload();
                        }, 1000);
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

    //批量删除
    function makeAll() {
        layer.confirm('您确定要删除该角色数据？', {
            btn: ['确定', '暂时不要']  // 按钮
        }, function () {
            layer.msg('您点击了确定', {icon: 1});
            var roleIds = "";
            $('input:checkbox[name=ids]:checked').each(function (i) {
                if (0 == i) {
                    roleIds = $(this).val();
                } else {
                    roleIds += ("," + $(this).val());
                }
            });
            if (roleIds == '') {
                layer.msg("请选择要删除的信息!");
                return false;
            }

            // 执行异步删除动作
            $.ajax({
                type: "POST",
                url: "<%=basePath%>/b/role/deleteRoles.do",
                dataType: "json",
                async: true,
                data: {
                    roleIds: roleIds
                },
                success: function (rc) {
                    if (rc.code == '0') {
                        layer.msg('已为您删除该角色数据', {icon: 1});
                        setTimeout(function () {
                            location.reload();
                        }, 1000);

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
        var code = $("#code").val();
        var roleName = $("#roleName").val();
        if ($("input[type='checkbox']").is(':checked')) {
            var isSys = "1"
        } else {
            var isSys = "0"
        }
        if (tag == "EDIT") {
            var roleId = $("#roleId").val();
        } else {
            var roleId = "";
        }
        var remark = $("#remark").val();
        var supId = $("#supId").val();
        var orders = $("#orders").val();
        if (code == null || code == '') {
            layer.msg('请正确填写角色编码!');
            return false;
        }
        if (remark == undefined || remark == null) remark = "";
        $.ajax({
            type: "POST",
            url: "<%=basePath%>/b/role/editRole.do",
            dataType: "json",
            async: true,
            data: {
                tag: tag,
                roleId: roleId,
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
        var role_list_table = $('#role_list').DataTable({
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
                style: 'single'
            },

            language: {
                "url": "<%=basePath%>/plugins/dataTables/i18n/dataTables_zh-CN.json"
            }
        });

        $.fn.dataTable.Buttons.defaults.dom.container.className = 'dt-buttons btn-overlap btn-group btn-overlap';

        addButton2DataTables(role_list_table);  // 为DataTables添加button

        setTimeout(function () {
            $($('.tableTools-container')).find('a.dt-button').each(function () {
                var div = $(this).find(' > div').first();
                if (div.length == 1) div.tooltip({container: 'body', title: div.parent().text()});
                else $(this).tooltip({container: 'body', title: $(this).text()});
            });
        }, 500);

        role_list_table.on('select', function (e, dt, type, index) {
            if (type === 'row') {
                $(role_list_table.row(index).node()).find('input:checkbox').prop('checked', true);
            }
        });
        role_list_table.on('deselect', function (e, dt, type, index) {
            if (type === 'row') {
                $(role_list_table.row(index).node()).find('input:checkbox').prop('checked', false);
            }
        });

        //table checkboxes
        $('th input[type=checkbox], td input[type=checkbox]').prop('checked', false);

        //select/deselect all rows according to table header checkbox
        $('#role_list > thead > tr > th input[type=checkbox], #role_list_wrapper input[type=checkbox]').eq(0).on('click', function () {
            var th_checked = this.checked;//checkbox inside "TH" table header

            $('#role_list').find('tbody > tr').each(function () {
                var row = this;
                if (th_checked) role_list_table.row(row).select();
                else  role_list_table.row(row).deselect();
            });
        });

        //select/deselect a row when the checkbox is checked/unchecked
        $('#role_list').on('click', 'td input[type=checkbox]', function () {
            var row = $(this).closest('tr').get(0);
            if (this.checked) role_list_table.row(row).deselect();
            else role_list_table.row(row).select();
        });

        $(document).on('click', '#role_list .dropdown-toggle', function (e) {
            e.stopImmediatePropagation();
            e.stopPropagation();
            e.preventDefault();
        })
    });
</script>

<script type="text/javascript">
    var zTree;
    var button_list_table;

    var setting = {

        check: {
            enable: false
        },
        view: {
            dblClickExpand: true,
            showLine: true,
            selectedMulti: false
        },
        data: {
            simpleData: {
                enable: true,
                idKey: "id",
                pIdKey: "pId",
                rootPId: "-1"
            }
        },
        callback: {
            beforeClick: beforeClickTreeNode,
            onClick: clickTreeNode,
            onRightClick: rightClickTreeNode
        }
    };

    /**
     * 上级菜单点击展开
     */
    function beforeClickTreeNode(treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("menuTree");
        if (treeNode.isParent) {
            zTree.expandNode(treeNode);
            return false;
        } else {
            return true;
        }
    }

    /**
     * 末级菜单选中时触发（按钮列表是在页面初始化时全部加载的，这里只需要过滤menuId项即可）
     * @param e
     * @param treeId
     * @param treeNode
     */
    function clickTreeNode(e, treeId, treeNode) {
        button_list_table.columns(1).search(treeNode.id).draw();
    }

    /**
     * 树节点右键 取消选中状态
     * @param e
     * @param treeId
     * @param treeNode
     */
    function rightClickTreeNode(e, treeId, treeNode) {
        zTree.cancelSelectedNode(treeNode);  // 取消节点选中状态
        button_list_table.draw();
    }

    // 业务功能
    $(document).ready(function () {
        zTree = $("#menuTree");

        var json = ${menuJson};
        zTree = $.fn.zTree.init(zTree, setting, json);
    });

    var btnSelected = new Array();  // 用来记录分页被选中的button id

    // 按钮列表页面
    jQuery(function ($) {
        // 全选/反选
        $('#bids').toggle(function() {
            $('input[name="bids"]').attr("checked", 'true');
        }, function() {
            $('input[name="bids"]').removeAttr("checked");
        });

        button_list_table = $('#button_list').DataTable({
            //"dom": 'ftlp',
            "columnDefs": [
                {
                    "name": "menuId",
                    "targets": 1,
                    "visible": false
                },
                {"name": "btnId", "targets": 2},
                {"name": "btnTag", "targets": 3},  // 设置列标记
                {"name": "btnTitle", "targets": 4},
                {"name": "btnType", "targets": 5}
            ],
            "retrieve": true, // button_list_table共用一个实例
            "aoColumns": [
                {"bSortable": false},
                {"bSortable": false},  // menuId列不可见
                null, null,
                {"bSortable": false}, {"bSortable": false}
            ],
            bAutoWidth: false,
            "info": false,

            select: {
                style: 'multi'
            },

            language: {
                "url": "<%=basePath%>/plugins/dataTables/i18n/dataTables_zh-CN.json"
            },

            "createdRow": function(row, data, index) {
                var id = $('td', row).eq(0).find('input:checkbox').attr('id');
                if ($.inArray(id, btnSelected) !== -1) {
                    $('td', row).eq(0).find('input:checkbox').prop('checked', true);
                    $(row).addClass('selected');
                    //$('td', row).addClass('');
                }
            }//,

            // 保存状态操作
            //stateSave: true,

            /*"stateSaveParams": function (settings, data) {
                console.log("stateSaveParams");
                // 这里操作保存的数据，写自己特定的逻辑
            },

            "stateSaveCallback": function (settings, data) {
                console.log("stateSaveCallback");

                //DT默认保存的key值为DataTables_+表格id+页面名称

                localStorage.setItem('DataTables_' + settings.sInstance, JSON.stringify(data));
            },

            //读取状态操作

            "stateLoadParams": function (settings, data) {
                console.log("stateSaveParams");

                //在读取数据的时候可以改变数据，根据自己逻辑来处理

                //data.search.search = "";

                //或者你可以直接禁用从缓存里读取数据，只要直接返回false即可

                //return false;
            },

            "stateLoadCallback": function (settings) {
                console.log("stateLoadCallback");
                return JSON.parse(localStorage.getItem('DataTables_' + settings.sInstance));

                //同样你还可以从服务器取数，采用同步的方式获取到保存在服务器里的数据
            },

            //状态加载完后执行的回调函数

            "stateLoaded": function (settings, data) {
                console.log("stateLoaded");

                //在这里你可以打印出保存的缓存数据

                //alert( 'Saved filter was: '+data.search.search );
            }*/

        });

        button_list_table.columns(1).visible(false);  // 隐藏menuId列

        //table checkboxes
        $('th input[type=checkbox], td input[type=checkbox]').prop('checked', false);

        //select/deselect a row when the checkbox is checked/unchecked
        $('#button_list').on('click', 'td input[type=checkbox]', function () {
            var id = this.id;
            var index = $.inArray(id, btnSelected);

            if (index === -1) {
                btnSelected.push(id);
            } else {
                btnSelected.splice(index, 1);
            }

            if ($(this).hasClass('selected')) {
                $(this).removeClass('selected');
            }
            else {
                $(this).addClass('selected');
            }

            //var row = $(this).closest('tr').get(0);
            //alert(row + " _ " + id + " _ " + index);
        });

        $(document).on('click', '#button_list .dropdown-toggle', function (e) {
            e.stopImmediatePropagation();
            e.stopPropagation();
            e.preventDefault();
        });

    });

    //////////////////////////// 权限编辑页面功能 //////////////////////////////////

    $("#button_list").on('page.dt', function() {

        $.each(btnSelected, function(index, btnId) {
            $("td input[name='bids'][id='" + btnId + "']").prop('checked', true);
        });
        //button_list_table.state.save();
        //alert("page...  " + btnSelected);
    });

    /**
     * 跳转到按钮权限编辑页面
     */
    function editRight() {
        var rightModal = $('#rightModal');

        var roleId = $('input[name="ids"]:checked').val();

        if (roleId == '' || null == roleId || typeof(roleId) == "undefined" || roleId == "undefined") {
            layer.msg("勾选要编辑的按钮！");
            return false;
        } else {
            $('#roleId4Right').attr('value', roleId[0]);
            rightModal.modal();

            $.ajax({
                type: "GET",
                url: "<%=basePath%>/b/role/editRoleButton.do",
                dataType: "json",
                data: {
                    roleId: roleId
                },
                success: function (btnList) {
                    var btnId;

//                    button_list_table.$('tr.selected').removeClass('selected');
                    button_list_table.$('tr.selected').attr("checked", 'true');
                    $.each(btnList, function(index, button) {
                        btnId = button.id;

                        var c = 0;
                        $.each(btnSelected, function(index, ele) {
                            if (ele == btnId)   {
                                c++;    return true; // 类似continue
                            }
                        });
                        if (c==0)   btnSelected.push(btnId);

                        //$("#button_list > tbody > tr > td input[type=checkbox][id='" + btnId + "']").prop("checked", true);
//                        $("input[name='bids'][type=checkbox]").addClass('selected');
                        //$("input[name='bids'][type=checkbox]").attr("checked", 'true');
                        $("td input[name='bids'][id='" + btnId + "']").prop('checked', true);
                    });

                    //$("input[name='bids']").prop('checked', true);
                    //button_list_table.draw();
                    //alert(btnSelected);
                }
            });

            return true;
        }
    }

    var btnIds = "";

    /**
     * 保存角色和按钮的对应关系
     */
    function saveRight() {
        var rightModal = $('#rightModal');
        var roleId = $('#roleId4Right').val();

        alert(roleId + " _ " + btnSelected);

        $.ajax({
            type: "POST",
            url: "<%=basePath%>/b/role/editRoleButton.do",
            dataType: "json",
            async: true,
            traditional: true,
            data: {
                roleId: roleId,
                btnId: btnSelected
            },
            success: function (rc) {
                if (rc.code == 'success') {
                    alert(rc.message);
                    rightModal.modal("hide");
                }
            }
        });

    }

    function test() {
//        $("input[name='bids']").attr("checked", 'checked');
        $("td input[name='bids'][id='17']").prop('checked', true);
//        $("input[name='bids']").addClass('selected');
    }

</script>

</body>
</html>