<%--
  Created by IntelliJ IDEA.
  User: david
  Date: 2017-05-25
  Time: 10:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
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
    <title>按钮面板</title>
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

                <!-- 分割线 -->
                <div class="hr hr-4"></div>
                <%--<div class="hr hr-dotted"></div>--%>

                <div class="row">
                    <div class="col-xs-12">

                        <div class="row">
                            <!-- 字典树 -->
                            <div class="col-sm-4">
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

                            <!-- 按钮信息列表 -->
                            <div class="col-sm-8">

                                <table id="button_list" class="table table-striped table-bordered table-hover">
                                    <thead>
                                    <tr>
                                        <th class="center">
                                            <label>
                                                <input type="checkbox" class="ace"/>
                                                <span class="lbl"></span>
                                            </label>
                                        </th>
                                        <th>按钮ID</th>
                                        <th>按钮TEXT</th>
                                        <th class="hidden-480">按钮TAG</th>

                                        <th>按钮TYPE</th>
                                        <th>
                                            <i class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>
                                            按钮Class
                                        </th>
                                        <th class="hidden-480">是否可见</th>
                                        <th class="hidden-480">是否可用</th>
                                        <th></th>
                                    </tr>
                                    </thead>

                                    <tbody>
                                    <c:forEach items="${buttonList}" var="button" varStatus="vs">
                                    <tr>
                                        <td class="center">
                                            <label class="pos-rel">
                                                <input type="checkbox" name="ids"
                                                       value="${button.id}"
                                                       id="${button.id}" alt="${button.btnId}"
                                                       class="ace"/>
                                                <span class="lbl"></span>
                                            </label>
                                        </td>
                                        <td>${button.btnId}</td>
                                        <td>${button.btnText}</td>
                                        <td class="hidden-480">${button.btnTag}</td>
                                        <td>${button.btnType}</td>
                                        <td>${button.btnClass}</td>

                                        <td style="width:60px;" class="center">
                                            <c:if test="${button.visible == 0}">
                                                <span class="label label-success arrowed">可见</span>
                                            </c:if>
                                            <c:if test="${button.visible == 1}">
                                                <span class="label label-important arrowed-in">隐藏</span>
                                            </c:if>
                                        </td>

                                        <td style="width:60px;" class="center">
                                            <c:if test="${button.disabled == 0}">
                                                <span class="label label-success arrowed">可用</span>
                                            </c:if>
                                            <c:if test="${button.disabled == 1}">
                                                <span class="label label-important arrowed-in">禁用</span>
                                            </c:if>
                                        </td>

                                        <td>
                                            <a class="btn btn-xs btn-info" title='编辑' onclick="editButton('${button.id }');">
                                                <i class="ace-icon fa fa-envelope-o bigger-120" title="编辑"></i>
                                            </a>
                                        </td>
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
</div>

<script type="text/javascript">

    jQuery(function ($) {
        var button_list_table = $('#button_list').DataTable({
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

        new $.fn.dataTable.Buttons(button_list_table, {
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

        button_list_table.buttons().container().appendTo($('.tableTools-container'));

        // style the message box
        var defaultCopyAction = button_list_table.button(1).action();
        button_list_table.button(1).action(function (e, dt, button, config) {
            defaultCopyAction(e, dt, button, config);
            $('.dt-button-info').addClass('gritter-item-wrapper gritter-info gritter-center white');
        });

        var defaultColvisAction = button_list_table.button(0).action();
        button_list_table.button(0).action(function (e, dt, button, config) {

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

        button_list_table.on('select', function (e, dt, type, index) {
            if (type === 'row') {
                $(button_list_table.row(index).node()).find('input:checkbox').prop('checked', true);
            }
        });
        button_list_table.on('deselect', function (e, dt, type, index) {
            if (type === 'row') {
                $(button_list_table.row(index).node()).find('input:checkbox').prop('checked', false);
            }
        });

        //table checkboxes
        $('th input[type=checkbox], td input[type=checkbox]').prop('checked', false);

        //select/deselect all rows according to table header checkbox
        $('#menu_list > thead > tr > th input[type=checkbox], #menu_list_wrapper input[type=checkbox]').eq(0).on('click', function () {
            var th_checked = this.checked;//checkbox inside "TH" table header

            $('#menu_list').find('tbody > tr').each(function () {
                var row = this;
                if (th_checked) button_list_table.row(row).select();
                else  button_list_table.row(row).deselect();
            });
        });

        //select/deselect a row when the checkbox is checked/unchecked
        $('#button_list').on('click', 'td input[type=checkbox]', function () {
            var row = $(this).closest('tr').get(0);
            if (this.checked) button_list_table.row(row).deselect();
            else button_list_table.row(row).select();
        });

        $(document).on('click', '#menu_list .dropdown-toggle', function (e) {
            e.stopImmediatePropagation();
            e.stopPropagation();
            e.preventDefault();
        })

    });

    var zTree;

    var setting = {
        check: {
            enable: true,
            chkStyle: "checkbox",
            chkboxType: {"Y": "", "N": ""}
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
                var zTree = $.fn.zTree.getZTreeObj("buttonTree");
                if (treeNode.isParent) {
                    zTree.expandNode(treeNode);
                    return false;
                } else {
                    return true;
                }
            }
        },
        async: {
            enabled: true,
            url: "/menu/getDicTree.do",
            autoParam: ['code'],
            dataType: "json"
         }
    };

    $(document).ready(function () {
        ajaxFetchData();  // 异步获取字典数据
    });

    /*$('#supCode').select2({
        width: '235px',
        placeholder: {
            id: '-1',
            text: '键入查询'
        },
        allowClear: true,
        language: "zh-CN",
        multiple: false
    });*/

    $(document).one('ajaxloadstart.page', function (e) {
        $('[class*=select2]').remove();
        //$('.multiselect').multiselect('destroy');
    });

    // 业务功能
    // 异步获取字典数据信息，并展示
    function ajaxFetchData() {
        var t = $("#buttonTree");

        $.ajax({
            type: "POST",
            url: "<%=basePath%>b/dictionary/getDicTree.do",
            dataType: "json",
            async: true,
            data: {
                dicInfo: $("#dicInfo").val()
            },
            success: function (data) {
                //var result = eval("(" + data + ")");  // 这个data不能直接使用，需要转化一下

                t = $.fn.zTree.init(t, setting, data);
            }
        });
    }

</script>

</body>
</html>