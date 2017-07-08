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
    <title>用户列表</title>
    <base href="<%=basePath%>">

    <%@ include file="../../../comm/default_header.jsp" %>
    <link rel="stylesheet" href="<%=basePath%>/plugins/select2/css/select2.min.css"/>
    <script type="text/javascript" src="<%=basePath%>/static/js/pinyin.js"></script>
    <script type="text/javascript" src="<%=basePath%>/plugins/select2/js/select2.full.js"></script>
    <script type="text/javascript" src="<%=basePath%>/plugins/select2/js/i18n/zh-CN.js"></script>

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
                            <div class="col-sm-9">
                                <div>
                                    <table id="user_dept_list" class="table table-striped table-bordered table-hover"
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
                                            <th class="center">用户编号</th>
                                            <th class="center">用户名</th>
                                            <th class="center">登陆账户</th>
                                            <th class="center">
                                                <i class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>
                                                最近登录
                                            </th>
                                            <th class="center">状态</th>
                                        </tr>
                                        </thead>

                                        <c:choose>
                                            <c:when test="${not empty userList}">
                                                <c:forEach items="${userList}" var="user" varStatus="vs">
                                                    <tr>
                                                        <td class="center" style="width:50px;">
                                                            <label class="pos-rel">
                                                                <input type="checkbox" name="ids"
                                                                       value="${user.USER_ID}"
                                                                       id="${user.USER_ID}" alt="${user.ACCOUNT}"
                                                                       class="ace"/>
                                                                <span class="lbl"></span>
                                                            </label>
                                                        </td>
                                                        <td class="center" style="width: 60px;">${vs.index+1}</td>
                                                        <td class="center">${user.USER_ID}</td>
                                                        <td class="center">${user.USERNAME}</td>
                                                        <td class="center">${user.ACCOUNT}</td>
                                                        <td class="center">${user.LAST_LOGIN}</td>
                                                        <td style="width:60px;" class="center">
                                                            <c:if test="${user.LOCKED == 0}">
                                                                <span class="label label-success arrowed">正常</span>
                                                            </c:if>
                                                            <c:if test="${user.LOCKED == 1}">
                                                                <span class="label label-important arrowed-in">冻结</span>
                                                            </c:if>
                                                        </td>
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
                                    </div>
                                    <div class="clearfix">
                                        <div class="pull-right tableTools-container"></div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-3">

                                <div class="widget-box widget-color-blue2">
                                    <div class="widget-header">
                                        <h4 class="widget-title lighter smaller">部门树</h4>
                                    </div>

                                    <div class="widget-body scroll-content" style="overflow:auto;">
                                        <div class="widget-main padding-8">
                                            <ul id="departmentTree" class="ztree"></ul>
                                        </div>
                                    </div>
                                </div>
                                <button type="button" id="saveChange" class="btn btn-primary" onclick="saveChange()">
                                    提交
                                </button>
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

<script type="text/javascript">
    jQuery(function ($) {
        //initiate dataTables plugin
        var user_list_table = $('#user_dept_list').DataTable({
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

        new $.fn.dataTable.Buttons(user_list_table, {
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

        user_list_table.buttons().container().appendTo($('.tableTools-container'));

        // style the message box
        var defaultCopyAction = user_list_table.button(1).action();
        user_list_table.button(1).action(function (e, dt, button, config) {
            defaultCopyAction(e, dt, button, config);
            $('.dt-button-info').addClass('gritter-item-wrapper gritter-info gritter-center white');
        });

        var defaultColvisAction = user_list_table.button(0).action();
        user_list_table.button(0).action(function (e, dt, button, config) {

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

        user_list_table.on('select', function (e, dt, type, index) {
            if (type === 'row') {
                $(user_list_table.row(index).node()).find('input:checkbox').prop('checked', true);
            }
        });
        user_list_table.on('deselect', function (e, dt, type, index) {
            if (type === 'row') {
                $(user_list_table.row(index).node()).find('input:checkbox').prop('checked', false);
            }
        });

        //table checkboxes
        $('th input[type=checkbox], td input[type=checkbox]').prop('checked', false);

        //select/deselect all rows according to table header checkbox
        $('#user_dept_list > thead > tr > th input[type=checkbox], #user_list_wrapper input[type=checkbox]').eq(0).on('click', function () {
            var th_checked = this.checked;//checkbox inside "TH" table header

            $('#user_dept_list').find('tbody > tr').each(function () {
                var row = this;
                if (th_checked) user_list_table.row(row).select();
                else  user_list_table.row(row).deselect();
            });
        });

        //select/deselect a row when the checkbox is checked/unchecked
        $('#user_dept_list').on('click', 'td input[type=checkbox]', function () {
            var row = $(this).closest('tr').get(0);
            if (this.checked) user_list_table.row(row).deselect();
            else user_list_table.row(row).select();
        });

        $(document).on('click', '#user_dept_list .dropdown-toggle', function (e) {
            e.stopImmediatePropagation();
            e.stopPropagation();
            e.preventDefault();
        })
    });
</script>

<script type="text/javascript">
    var zTree;

    var setting = {
        check: {
            enable: true,
            chkStyle: "checkbox",
            chkboxType :{ "Y" : "s", "N" : "s" }
        },
        view: {

            dblClickExpand: true,
            showLine: true,
            selectedMulti: false
        },
        data: {
            simpleData: {
                enable: true,
                idKey: "code",
                pIdKey: "pId",
                rootPId: "-1"
            }
        },
        callback: {
            beforeClick: function (treeId, treeNode) {
                var zTree = $.fn.zTree.getZTreeObj("departmentTree");
                if (treeNode.isParent) {
                    zTree.expandNode(treeNode);
                    return false;
                } else {
                    return true;
                }
            }
        }
    };

    $(document).ready(function () {
        ajaxFetchData();  // 异步获取部门数据
    });

    // 异步获取部门数据信息，并展示
    function ajaxFetchData() {
        var t = $("#departmentTree");

        $.ajax({
            type: "GET",
            url: "<%=basePath%>b/userDept/getDepTree.do",
            dataType: "json",
            async: true,
            data: {
                deptInfo: $("#deptInfo").val()
            },
            success: function (data) {
                //var result = eval("(" + data + ")");  // 这个data不能直接使用，需要转化一下
                t = $.fn.zTree.init(t, setting, data);
                var zTree = $.fn.zTree.getZTreeObj("departmentTree");
                zTree.expandAll(true);
            }
        });
    }


    $("input[name='ids']").click(function () {
        var treeObj = $.fn.zTree.getZTreeObj("departmentTree");
        treeObj.checkAllNodes(false);
        var userId = $(this).attr('value');
        if ($("#" + userId).is(":checked")) {

        } else {
            $.ajax({
                type: "POST",
                url: "<%=basePath%>b/userDept/getUserDept.do",
                datatype: "json",
                data: {
                    userId: userId
                },
                success: function (userDept) {
                    var dept = userDept.deptArr;
                    if (null == dept || dept === "") {
                        return false;
                    }
                    var treeObj = $.fn.zTree.getZTreeObj("departmentTree");
                    for (var i = 0; i < dept.length; i++) {
                        treeObj.checkNode(treeObj.getNodeByParam("id", dept[i], null), true, true);
                    }

                }
            })
        }

    });

    function saveChange() {

        var userId = $('input:checkbox[name=ids]:checked').val();
        if (userId == '') {
            layer.msg("请勾选一个用户！");
            return false;
        }
        var treeObj = $.fn.zTree.getZTreeObj("departmentTree");
        var nodes = treeObj.getCheckedNodes(true);
        var deptId = "";
        for (var i = 0; i < nodes.length; i++) {
            if (i == nodes.length - 1) {
                deptId += nodes[i].id
            } else {
                deptId += nodes[i].id + "/"
            }

        }
        $.ajax({
            type: "POST",
            url: "<%=basePath%>b/userDept/saveUserDept.do",
            datatype: "json",
            data: {
                userId: userId,
                deptId: deptId
            },
            success: function (rc) {
                if (rc.code == "0") {
                    layer.msg(rc.message);
                }
            }
        })


    }
</script>

</body>
</html>
