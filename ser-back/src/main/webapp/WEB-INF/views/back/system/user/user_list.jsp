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
                                <div>
                                    <table id="menu_list" class="table table-striped table-bordered table-hover"
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
                                            <th class="center">用户编号</th>
                                            <th class="center">用户名</th>
                                            <th class="center">登陆账户</th>
                                            <th class="center">
                                                <i class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>
                                                最近登录
                                            </th>
                                            <th class="center">状态</th>
                                            <th class="center">操作</th>
                                        </tr>
                                        </thead>


                                        <c:choose>
                                            <c:when test="${not empty userList}">
                                                <c:if test="${QX.cha == 1}">
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
                                                            <td class="center">
                                                                <c:if test="${QX.edit != 1 && QX.del != 1 }">
                                                            <span class="label label-large label-grey arrowed-in-right arrowed-in">
                                                                <i class="ace-icon fa fa-lock" title="无权限"></i>
                                                            </span>
                                                                </c:if>
                                                                <div class="hidden-sm hidden-xs btn-group">
                                                                    <c:if test="${QX.email == 1 }">
                                                                        <a class="btn btn-xs btn-info" title='发送电子邮件'
                                                                           onclick="sendEmail('${user.EMAIL }');">
                                                                            <i class="ace-icon fa fa-envelope-o bigger-120"
                                                                               title="发送电子邮件"></i>
                                                                        </a>
                                                                    </c:if>
                                                                    <c:if test="${QX.sms == 1 }">
                                                                        <a class="btn btn-xs btn-warning" title='发送短信'
                                                                           onclick="sendSms('${user.PHONE }');">
                                                                            <i class="ace-icon fa fa-envelope-o bigger-120"
                                                                               title="发送短信"></i>
                                                                        </a>
                                                                    </c:if>
                                                                    <c:if test="${QX.edit == 1 }">
                                                                        <a class="btn btn-xs btn-success" title="编辑"
                                                                           onclick="editUser('${user.USER_ID}');">
                                                                            <i class="ace-icon fa fa-pencil-square-o bigger-120"
                                                                               title="编辑"></i>
                                                                        </a>
                                                                    </c:if>
                                                                    <c:if test="${QX.del == 1 }">
                                                                        <a class="btn btn-xs btn-danger"
                                                                           onclick="delUser('${user.USER_ID }','${user.USERNAME }');">
                                                                            <i class="ace-icon fa fa-trash-o bigger-120"
                                                                               title="删除"></i>
                                                                        </a>
                                                                    </c:if>
                                                                </div>
                                                                <div class="hidden-md hidden-lg">
                                                                    <div class="inline pos-rel">
                                                                        <button class="btn btn-minier btn-primary dropdown-toggle"
                                                                                data-toggle="dropdown"
                                                                                data-position="auto">
                                                                            <i class="ace-icon fa fa-cog icon-only bigger-110"></i>
                                                                        </button>
                                                                        <ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
                                                                            <c:if test="${QX.email == 1 }">
                                                                                <li>
                                                                                    <a style="cursor:pointer;"
                                                                                       onclick="sendEmail('${user.EMAIL }');"
                                                                                       class="tooltip-info"
                                                                                       data-rel="tooltip"
                                                                                       title="发送电子邮件">
																	<span class="blue">
																		<i class="ace-icon fa fa-envelope bigger-120"></i>
																	</span>
                                                                                    </a>
                                                                                </li>
                                                                            </c:if>
                                                                            <c:if test="${QX.sms == 1 }">
                                                                                <li>
                                                                                    <a style="cursor:pointer;"
                                                                                       onclick="sendSms('${user.PHONE }');"
                                                                                       class="tooltip-success"
                                                                                       data-rel="tooltip" title="发送短信">
																	<span class="blue">
																		<i class="ace-icon fa fa-envelope-o bigger-120"></i>
																	</span>
                                                                                    </a>
                                                                                </li>
                                                                            </c:if>
                                                                            <c:if test="${QX.edit == 1 }">
                                                                                <li>
                                                                                    <a style="cursor:pointer;"
                                                                                       onclick="editUser('${user.USER_ID}');"
                                                                                       class="tooltip-success"
                                                                                       data-rel="tooltip" title="修改">
																	<span class="green">
																		<i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
																	</span>
                                                                                    </a>
                                                                                </li>
                                                                            </c:if>
                                                                            <c:if test="${QX.del == 1 }">
                                                                                <li>
                                                                                    <a style="cursor:pointer;"
                                                                                       onclick="delUser('${user.USER_ID }','${user.USERNAME }');"
                                                                                       class="tooltip-error"
                                                                                       data-rel="tooltip" title="删除">
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
                                                        <a class="btn btn-mini btn-success" onclick="add();">新增</a>
                                                    </c:if>
                                                    <c:if test="${QX.email == 1 }">
                                                        <a title="批量发送电子邮件" class="btn btn-mini btn-info"
                                                           onclick="makeAll('确定要给选中的用户发送邮件吗?');">
                                                            <i class="ace-icon fa fa-envelope bigger-120"></i>
                                                        </a>
                                                    </c:if>
                                                    <c:if test="${QX.sms == 1 }">
                                                        <a title="批量发送短信" class="btn btn-mini btn-warning"
                                                           onclick="makeAll('确定要给选中的用户发送短信吗?');">
                                                            <i class="ace-icon fa fa-envelope-o bigger-120"></i>
                                                        </a>
                                                    </c:if>
                                                    <c:if test="${QX.del == 1 }">
                                                        <a title="批量删除" class="btn btn-mini btn-danger"
                                                           onclick="makeAll('确定要删除选中的数据吗?');">
                                                            <i class='ace-icon fa fa-trash-o bigger-120'></i>
                                                        </a>
                                                    </c:if>
                                                </td>
                                                <td style="vertical-align: top;">
                                                    <div class="pagination"
                                                         style="float: right;padding-top:0px; margin-top:0px;">
                                                        ${page.pageStr}
                                                    </div>
                                                </td>
                                            </tr>
                                        </table>
                                    </div>
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

<script type="text/javascript">
    jQuery(function ($) {
        //initiate dataTables plugin
        var menu_list_table = $('#menu_list').DataTable({
            /*"dom": 'Bfrtip',*/
            bAutoWidth: false,
            "aoColumns": [
                {"bSortable": false},
                null, null, null, null, null,
                {"bSortable": false}
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

        new $.fn.dataTable.Buttons(menu_list_table, {
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

        $(document).on('click', '#menu_list .dropdown-toggle', function (e) {
            e.stopImmediatePropagation();
            e.stopPropagation();
            e.preventDefault();
        })

    });
</script>

</body>
</html>
