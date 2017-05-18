<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>

<html lang="en">
<head>
    <base href="<%=basePath%>">
    <meta charset="utf-8"/>
    <title>科教研管理系统</title>
    <meta name="keywords" content="科教研管理系统"/>
    <meta name="description" content="科教研管理系统"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta http-equiv="Cache-Control" content="max-age=7200"/>

    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0"/>
    <link rel="shortcut icon" href="<%=basePath%>/static/img/favicon.ico" type="image/x-ico">

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

<body class="skin-1" style="overflow:auto;">

<div class="main-container ace-save-state" id="main-container">
    <script type="text/javascript">
        try {
            ace.settings.loadState('main-container')
        } catch (e) {
        }
    </script>

    <div class="main-content">
        <div class="main-content-inner">
            <div class="page-content">

                <div class="row">
                    <div class="col-xs-12">
                        <h3 class="header smaller lighter">SpringMVC映射列表</h3>

                        <div class="clearfix">
                            <div class="pull-right tableTools-container"></div>
                        </div>

                        <div>
                            <table id="dynamic-table" class="table table-striped table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th class="center">序号</th>
                                    <th>controllerName</th>
                                    <th>requestMethod</th>
                                    <th class="hidden-480">requestUrl</th>
                                    <th>requestType</th>
                                    <th>responseType</th>
                                </tr>
                                </thead>

                                <tbody>
                                <c:forEach items="${actionList}" var="action" varStatus="status">
                                    <tr id="${status.index}">
                                        <td align="center">${status.index + 1}</td>
                                        <td>${action.controllerName}</td>
                                        <td>${action.requestMethod}</td>
                                        <td class="hidden-480">
                                            <c:choose>
                                                <c:when test="${!fn:contains(action.requestUrl,'}') and (action.requestType=='GET' or action.requestType=='')}">
                                                    ${action.requestUrl}<br>
                                                    <a href="http://localhost:8080/SER/${action.requestUrl}" target="_blank">跳转查看</a>
                                                </c:when>
                                                <c:otherwise>${action.requestUrl}</c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>${action.requestType}</td>
                                        <td>${action.responseType}</td>
                                        <%--<td>
                                            <c:if test="${action.parameters.size() > 0 }">
                                                <table class="table table-striped table-hover table-bordered table-condensed">
                                                    <thead>
                                                        <tr>
                                                            <th>parameterName</th>
                                                            <th>parameterType</th>
                                                            <th>parameterAnnotations</th>
                                                        </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach items="${action.parameters}" var="p">
                                                        <tr>
                                                            <td>${p.name}</td>
                                                            <td>${p.type}</td>
                                                            <td>${p.annotation}</td>
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                            </c:if>
                                         </td>--%>
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

<script type="text/javascript">
    jQuery(function ($) {
        var myTable = $('#dynamic-table').DataTable({
            bAutoWidth: false,
            "aoColumns": [
                {"bSortable": false},
                null, null, null, null
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

        new $.fn.dataTable.Buttons(myTable, {
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
        myTable.buttons().container().appendTo($('.tableTools-container'));

        var defaultCopyAction = myTable.button(1).action();
        myTable.button(1).action(function (e, dt, button, config) {
            defaultCopyAction(e, dt, button, config);
            $('.dt-button-info').addClass('gritter-item-wrapper gritter-info gritter-center white');
        });

        var defaultColvisAction = myTable.button(0).action();
        myTable.button(0).action(function (e, dt, button, config) {

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

        myTable.on('select', function (e, dt, type, index) {
            if (type === 'row') {
                $(myTable.row(index).node()).find('input:checkbox').prop('checked', true);
            }
        });
        myTable.on('deselect', function (e, dt, type, index) {
            if (type === 'row') {
                $(myTable.row(index).node()).find('input:checkbox').prop('checked', false);
            }
        });

        $('th input[type=checkbox], td input[type=checkbox]').prop('checked', false);

        //select/deselect all rows according to table header checkbox
        $('#dynamic-table > thead > tr > th input[type=checkbox], #dynamic-table_wrapper input[type=checkbox]').eq(0).on('click', function () {
            var th_checked = this.checked;//checkbox inside "TH" table header

            $('#dynamic-table').find('tbody > tr').each(function () {
                var row = this;
                if (th_checked) myTable.row(row).select();
                else  myTable.row(row).deselect();
            });
        });

        //select/deselect a row when the checkbox is checked/unchecked
        $('#dynamic-table').on('click', 'td input[type=checkbox]', function () {
            var row = $(this).closest('tr').get(0);
            if (this.checked) myTable.row(row).deselect();
            else myTable.row(row).select();
        });

        $(document).on('click', '#dynamic-table .dropdown-toggle', function (e) {
            e.stopImmediatePropagation();
            e.stopPropagation();
            e.preventDefault();
        });
    });

</script>
</body>
</html>

