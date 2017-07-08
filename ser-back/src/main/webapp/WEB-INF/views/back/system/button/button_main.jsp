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
    <title>按钮面板</title>
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

                <!-- 分割线 -->
                <div class="hr hr-4"></div>
                <%--<div class="hr hr-dotted"></div>--%>

                <div class="row">
                    <div class="col-xs-12">

                        <div class="row">
                            <!-- 字典树 -->
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

                            <!-- 按钮信息列表 -->
                            <div class="col-sm-9">

                                <table id="button_list" class="table table-striped table-bordered table-hover">
                                    <thead>
                                    <tr>
                                        <th class="center">
                                            <label>
                                                <input type="checkbox" name="ids" class="ace"/>
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
                                        <%--<th>按钮修饰</th>--%>
                                        <th>是否可见</th>
                                        <th>是否可用</th>
                                    </tr>
                                    </thead>

                                    <tbody>
                                    <c:forEach items="${allBtnList}" var="button" varStatus="vs">
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
                                        <td>${button.menuId}</td>
                                        <td>${button.btnId}</td>
                                        <td class="hidden-480">${button.btnTag}</td>
                                        <td>${button.btnText}</td>
                                        <td>${button.btnType}</td>
                                        <%--<td>${button.btnClass}</td>--%>

                                        <td style="width:60px;" class="center">
                                            <c:if test="${button.visible eq true}">
                                                <span class="label label-success arrowed">可见</span>
                                            </c:if>
                                            <c:if test="${button.visible eq false}">
                                                <span class="label label-important arrowed-in">隐藏</span>
                                            </c:if>
                                        </td>

                                        <td style="width:60px;" class="center">
                                            <c:if test="${button.enabled eq false}">
                                                <span class="label label-success arrowed">可用</span>
                                            </c:if>
                                            <c:if test="${button.enabled eq true}">
                                                <span class="label label-important arrowed-in">禁用</span>
                                            </c:if>
                                        </td>
                                    </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                                <div class="page-header position-relative">
                                    <table style="width:100%">
                                        <tr>
                                            <td style="vertical-align: top;">
                                                <shiro:hasAnyPermission name="sys:button:refresh">
                                                    <a id="buttonEditBtn" class="btn btn-mini btn-success"
                                                       onclick="refreshList();">刷新</a>
                                                </shiro:hasAnyPermission>
                                                <shiro:hasAnyPermission name="sys:button:edit">
                                                    <a id="buttonEditBtn" class="btn btn-mini btn-success" onclick="btnEdit();">编辑</a>
                                                </shiro:hasAnyPermission>
                                            </td>
                                        </tr>
                                    </table>
                                    <div class="clearfix">
                                        <div class="pull-right tableTools-container"></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- 返回顶部 -->
    <a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
        <i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
    </a>
</div>

<!-- 新增/编辑 模态框（Modal） -->
<div class="modal fade" id="btnEditModal" tabindex="-1" role="dialog" aria-labelledby="btnEditModalLabel" aria-hidden="true"
     aria-describedby="编辑界面">
    <div class="modal-dialog" role="document" aria-hidden="true">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="关闭">×</button>
                <h4 class="modal-title" id="btnEditModalLabel">编辑</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-xs-12">
                        <form class="form-horizontal" role="form">
                            <input type="hidden" id="autoId" name="autoId"/>
                            <input type="hidden" id="menuId" name="menuId"/>
                            <input type="hidden" id="rowSelector" name="rowSelector" />
                            <div class="form-group">
                                <label for="menuName" class="col-sm-3 control-label no-padding-right">对应菜单:</label>
                                <div class="col-sm-9">
                                    <input type="text" id="menuName" name="menuName" value="" class="col-xs-10 col-sm-5"
                                           disabled="true"　readOnly="true"></i>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="btnTag" class="col-sm-3 control-label no-padding-right"><span
                                        class="required-item">*</span>&nbsp;按钮标识:</label>
                                <div class="col-sm-9">
                                    <input type="text" id="btnTag" name="btnTag" value="" class="col-xs-10 col-sm-5"
                                           placeholder="请键入..."></i>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="btnId" class="col-sm-3 control-label no-padding-right" >
                                    按钮ID:
                                </label>
                                <div class="col-sm-9">
                                    <input type="text" id="btnId" name="btnId" value="" class="col-xs-10 col-sm-5"
                                           disabled="true"　readOnly="true"></i>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-3 control-label no-padding-right" for="btnTitle">
                                    按钮标题:
                                </label>
                                <div class="col-sm-9">
                                    <input type="text" id="btnTitle" name="btnTitle" value="" class="col-xs-10 col-sm-5"></i>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-sm-3 col-xs-12 no-padding-right"
                                       for="btnType">按钮类型:</label>
                                <div class="col-sm-9 col-xs-12">
                                    <input type="text" id="btnType" name="btnType" value="" class="col-xs-10 col-sm-5"
                                           disabled="true"　readOnly="true"></i>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-sm-3 col-xs-12 no-padding-right"
                                       for="btnClass">按钮修饰:</label>
                                <div class="col-sm-9 col-xs-12">
                                    <input type="text" id="btnClass" name="btnClass" value="" class="col-xs-10 col-sm-5"
                                           disabled="true" readOnly="true"></i>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-3 control-label no-padding-right" for="enabled">
                                    是否可用:
                                </label>
                                <div class="col-sm-9 checkbox no-padding-left">
                                    <label>
                                        <input id="enabled" name="enabled" value="1"
                                               class="ace ace-checkbox-2" type="checkbox"
                                               <c:if test="${button.enabled == true}">checked</c:if>/>
                                        <span class="lbl" style="color:#2B6EA1;">  注: 勾选即为可用 </span>
                                    </label>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-3 control-label no-padding-right" for="visible">
                                    是否可用:
                                </label>
                                <div class="col-sm-9 checkbox no-padding-left">
                                    <label>
                                        <input id="visible" name="visible" value="1"
                                               class="ace ace-checkbox-2" type="checkbox"
                                               <c:if test="${button.visible == true}">checked</c:if>/>
                                        <span class="lbl" style="color:#2B6EA1;">  注: 勾选即为可见 </span>
                                    </label>
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

    // 按钮列表页面
    jQuery(function ($) {
        button_list_table = $('#button_list').DataTable({
            "columnDefs": [
                {
                    "targets": 1,
                    "visible": false
                },
                {"name": "btnTag", "targets": 3},  // 设置列标记
                {"name": "btnTitle", "targets": 4},
                {"name": "btnType", "targets": 5}
            ],
            //"retrieve": true, // button_list_table共用一个实例
            bAutoWidth: false,
            "aoColumns": [
                {"bSortable": false},
                {"bSortable": false},  // menuId列不可见
                null, null, null, null,
                {"bSortable": false},
                {"bSortable": false},
                {"bSortable": false}
            ],
            "aaSorting": [],
            //"info": false,

            select: {
                style: 'multi'
            },

            language: {
                "url": "<%=basePath%>/plugins/dataTables/i18n/dataTables_zh-CN.json"
            }

            //bStateSave: true // 异步执行完成之后才调用页面刷新
        });

        button_list_table.columns(1).visible(false);

        //$.fn.dataTable.Buttons.defaults.dom.container.className = 'dt-buttons btn-overlap btn-group btn-overlap';

        button_list_table.on('select', function (e, dt, type, index) {
            if (type === 'row') {
                $(button_list_table.row(index).node()).find('input:checkbox').prop('checked', true);
                $('#rowSelector').attr("value", index);
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
        $('#button_list > thead > tr > th input[type=checkbox], #button_list_wrapper input[type=checkbox]').eq(0).on('click', function () {
            var th_checked = this.checked;//checkbox inside "TH" table header

            $('#button_list').find('tbody > tr').each(function () {
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

        $(document).on('click', '#button_list .dropdown-toggle', function (e) {
            e.stopImmediatePropagation();
            e.stopPropagation();
            e.preventDefault();
        })

    });

    $(document).one('ajaxloadstart.page', function (e) {
        //$('[class*=select2]').remove();
        //$('.multiselect').multiselect('destroy');
    });

    //////////////////////////// 按钮编辑页面功能 //////////////////////////////////

    var btnEditModal = $('#btnEditModal');

    function refreshList() {
        var data = button_list_table.cell(1, 3).data();
        alert(data + " _ ");
        button_list_table.draw(false);  // 刷新表格数据，分页信息不会重置
    }

    function btnEdit() {
        var id = $('input[name="ids"]:checked').val();

        var rowSelector = $('#rowSelector').val();
        $('#rowSelector').attr("value", rowSelector);

        if (id == '' || null == id || typeof(id) == "undefined" || id == "undefined") {
            layer.msg("勾选要编辑的按钮！");
            return false;
        } else {
            btnEditModal.modal();

            $.ajax({
                type:"POST",
                url:"b/button/getButton.do",
                dataType:"json",
                data:{
                    id : id
                },
                success: function (button) {
                    $('#autoId').attr("value", button.id);
                    $('#menuId').attr('value', button.menu.id);
                    $('#menuName').attr('value', button.menu.menuName);

                    $('#btnTag').attr('value', button.btnTag);
                    $('#btnId').attr('value', button.btnId);
                    $('#btnType').attr('value', button.btnType);
                    $('#btnTitle').attr('value', button.btnTitle);
                    $('#btnClass').attr('value', button.btnClass);

                    if (button.enabled === '1') {
                        $("#enabled").attr("checked", 'true');
                    } else {
                        $("#enabled").attr("checked", 'false');
                    }

                    if (button.visible === '1') {
                        $("#visible").attr("checked", 'true');
                    } else {
                        $("#visible").attr("checked", 'false');
                    }

                    $('#btnText').attr('value', button.btnText);
                    $('#remark').attr('value', button.remark);
                },
                error: function() {
                    layer.msg("没有获取到该按钮信息...");
                }
            });
        }
    }

    function saveChange() {
        var id = $('#autoId').val();
        var btnTag = $('#btnTag').val();
        var btnTitle = $('#btnTitle').val();

        var rowSelector = $('#rowSelector').val();

        $.ajax({
            type:"POST",
            url:"b/button/editButton.do",
            dataType:"json",
            data:{
                id : id,
                btnTag: btnTag,
                btnTitle: btnTitle
            },
            success: function(result) {
                if (result.code == "success") {
                    btnEditModal.modal('hide');
                    // 刷新datatables的row数据

                    layer.msg("按钮信息更新成功!");
                    button_list_table.cell(rowSelector, 3).data(btnTag).draw();
                    button_list_table.cell(rowSelector, 4).data(btnTitle).draw();
                    button_list_table.draw(false);
                }
            },
            error: function() {
                layer.msg("更新失败..");
            }
        });
    }

</script>
</body>
</html>