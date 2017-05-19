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
    <title>菜单列表</title>
    <base href="<%=basePath%>">

    <%@ include file="../../../comm/default_header.jsp" %>

    <%--<link rel="stylesheet" href="<%=basePath%>/static/css/bootstrap-multiselect.min.css"/>
    <script type="text/javascript" src="<%=basePath%>/static/js/bootstrap-multiselect.min.js"></script>--%>

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
                <form id="departmentFrm" class="form-horizontal" action="#" method="post" name="departmentFrm">
                    <div class="form-group">
                        <div class="col-sm-11">
                            <div class="form-inline">
                                <label class="control-label on-padding-left" for="deptInfo"><!-- no-padding-right -->
                                    部门信息：
                                </label>
                                <input type="text" id="deptInfo" name="deptInfo" class="form-control" value="">
                            </div>
                        </div>
                        <div class="col-sm-1">
                            <button type="button" class="btn btn-purple btn-sm" onclick="ajaxFetchData();">
                                <span class="ace-icon fa fa-search icon-on-right bigger-110"></span>
                                搜索
                            </button>
                        </div>
                    </div>
                </form>

                <!-- 分割线 -->
                <div class="hr hr-4"></div>

                <div class="row">
                    <div class="col-xs-12">

                        <div class="row">
                            <!-- 字典树 -->
                            <div class="col-sm-4">
                                <!-- 工具按钮 新增/编辑/删除 -->
                                <div>
                                    <p>
                                        <button id="departmentAddBtn" class="btn btn-sm btn-primary" onclick="">新增</button>
                                        <button id="departmentModifyBtn" class="btn btn-sm btn-primary" onclick="">编辑</button>
                                        <button id="departmentDeleteBtn" class="btn btn-sm btn-primary" onclick="">删除</button>
                                    </p>
                                </div>

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
                            </div>

                            <!-- 菜单新增/编辑 -->
                            <div class="col-sm-8">

                                <%--<div class="hr hr-dotted"></div>--%>

                                <div  class="clearfix form-actions">
                                    <!-- 新增/编辑 -->
                                    <div class="row">
                                        <div class="col-xs-12">

                                            <form class="form-horizontal" role="form">
                                                <input id="deptId" name="deptId" type="hidden" value="${dic.deptId}"/>
                                                <input id="tag" name="tag" type="hidden"/>

                                                <div class="form-group">
                                                    <label class="control-label col-sm-2 col-xs-12 no-padding-right"
                                                           for="supCode">上级部门:</label>
                                                    <div class="col-sm-10 col-xs-12">
                                                        <select id="supCode" name="supCode" class="select2" multiple=""
                                                                style="max-height:150px; overflow-y:auto; overflow-x: hidden">
                                                            <option value="-1"> - 请选择 -</option>
                                                            <c:forEach items="${deptList}" var="dept" varStatus="index">
                                                                <option value="${dept.deptCode}">${dept.deptName}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                </div>

                                                <div class="form-group">
                                                    <label class="col-sm-2 control-label no-padding-right" for="entryCode">部门编码:</label>
                                                    <div class="col-sm-10">
                                                        <input type="text" id="entryCode" name="entryCode"
                                                               placeholder="部门编码" class="col-xs-10 col-sm-5"
                                                               value="${dic.deptCode}"/>
                                                    </div>
                                                </div>

                                                <div class="form-group">
                                                    <label class="col-sm-2 control-label no-padding-right" for="entryValue">部门名称:</label>
                                                    <div class="col-sm-10">
                                                        <input type="text" id="entryValue" name="entryValue"
                                                               placeholder="部门名称" class="col-xs-10 col-sm-5"
                                                               value="${dic.deptName}"/>
                                                    </div>
                                                </div>

                                                <div class="form-group">
                                                    <label class="col-sm-2 control-label no-padding-right" for="described">功能描述:</label>
                                                    <div class="col-sm-10">
                                                        <input type="text" id="described" name="described"
                                                               placeholder="功能描述" class="col-xs-10 col-sm-5"
                                                               value="${dic.described}"/>
                                                    </div>
                                                </div>


                                                <div class="form-group">
                                                    <label class="col-sm-2 control-label no-padding-right"
                                                           for="address">地址:</label>
                                                    <div class="col-sm-10">
                                                        <textarea id="address" name="address" class="form-control"
                                                                  placeholder="地址">${dic.address}</textarea>
                                                    </div>
                                                </div>

                                                <div class="form-group">
                                                    <label class="col-sm-2 control-label no-padding-right"
                                                           for="remark">备注:</label>
                                                    <div class="col-sm-10">
                                                        <textarea id="remark" name="remark" class="form-control"
                                                                  placeholder="备注信息">${dic.remark}</textarea>
                                                    </div>
                                                </div>

                                                <div class="col-md-offset-3 col-md-9">
                                                    <button class="btn btn-info" type="button"
                                                            onclick="return saveChange();">
                                                        <i class="ace-icon fa fa-check bigger-110"></i>
                                                        提交
                                                    </button>

                                                    &nbsp; &nbsp; &nbsp;
                                                    <button class="btn" type="reset" onclick="resetForm();">
                                                        <i class="ace-icon fa fa-undo bigger-110"></i>
                                                        重置
                                                    </button>
                                                </div>

                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    var zTree;
    var demoIframe;

    function addHoverDom(treeId, treeNode) {
        var sObj = $("#" + treeNode.tId + "_span");
        if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId).length > 0) return;
        var addStr = "<span class='button add' id='addBtn_" + treeNode.tId + "' title='新增' onfocus='this.blur();'></span>";

        addStr += "<span class='button edit' id='editBtn_" + treeNode.tId + "' title='编辑' ></span>";
        addStr += "<span class='button remove' id='removeBtn_" + treeNode.tId + "' title='删除' onfocus='this.blur();'></span>";

        sObj.after(addStr);
        var addBtn = $("#addBtn_" + treeNode.tId);

        // 添加新增按钮点击操作
        if (addBtn)
            addBtn.bind("click", function () {
                $("#tag").val("ADD");
                return editDepartment(treeNode.id, 'ADD'); // , treeNode.id -> 1 / treeNode.tId --> departmentTree_1
            });

        // 添加编辑按钮点击操作
        var editBtn = $("#editBtn_" + treeNode.tId);
        if (editBtn)
            editBtn.bind("click", function () {
                $("#tag").val("EDIT");
                return editDepartment(treeNode.id, 'EDIT');
            });

        // 添加删除按钮点击操作
        var removeBtn = $("#removeBtn_" + treeNode.tId);
        if (removeBtn)
            removeBtn.bind("click", function () {
                alert(treeNode.name)
                return removeDepartment(treeNode.id, treeNode.name);
            });

    };

    function removeHoverDom(treeId, treeNode) {
        $("#addBtn_" + treeNode.tId).unbind().remove();
        $("#removeBtn_" + treeNode.tId).unbind().remove();
        $("#editBtn_" + treeNode.tId).unbind().remove();
    };

    var setting = {
        check: {
            enable: true,
            chkStyle: "checkbox",
            chkboxType: {"Y": "", "N": ""}
        },
        view: {
            addHoverDom: addHoverDom,  // 当鼠标移动到节点上时，显示用户自定义控件
            removeHoverDom: removeHoverDom,  // 当鼠标移出节点时，隐藏用户自定义控件
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
        ajaxFetchData();  // 异步获取字典数据
    });

    $('.select2').select2({
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

    /*$('.multiselect').multiselect({
     enableFiltering: true,
     filterPlaceholder: '键入关键词查询',
     enableHTML: true,
     buttonClass: 'btn btn-white btn-primary',
     templates: {
     button: '<button type="button" class="multiselect dropdown-toggle" data-toggle="dropdown"><span class="multiselect-selected-text"></span> &nbsp;<b class="fa fa-caret-down"></b></button>',
     ul: '<ul class="multiselect-container dropdown-menu"></ul>',
     filter: '<li class="multiselect-item filter"><div class="input-group"><span class="input-group-addon"><i class="fa fa-search"></i></span><input class="form-control multiselect-search" type="text"></div></li>',
     filterClearBtn: '<span class="input-group-btn"><button class="btn btn-default btn-white btn-grey multiselect-clear-filter" type="button"><i class="fa fa-times-circle red2"></i></button></span>',
     li: '<li><a tabindex="0"><label></label></a></li>',
     divider: '<li class="multiselect-item divider"></li>',
     liGroup: '<li class="multiselect-item multiselect-group"><label></label></li>'
     }
     });*/

    $(document).one('ajaxloadstart.page', function (e) {
        $('[class*=select2]').remove();
        //$('.multiselect').multiselect('destroy');
    });

    // 业务功能
    // 异步获取字典数据信息，并展示
    function ajaxFetchData() {
        var t = $("#departmentTree");

        $.ajax({
            type: "POST",
            url: "/b/department/getDepTree.do",
            dataType: "json",
            async: true,
            data: {
                deptInfo: $("#deptInfo").val()
            },
            success: function (data) {
                //var result = eval("(" + data + ")");  // 这个data不能直接使用，需要转化一下

                t = $.fn.zTree.init(t, setting, data);
            }
        });
    }

    // 新增/修改 -- 提交
    function saveChange() {
        var deptId = $("#deptId").val();
        var supId = $("#supCode").val();
        var deptCode = $("#entryCode").val();
        var deptName = $("#entryValue").val();
        var described = $("#described").val();
        var address = $("#address").val();
        var remark = $("#remark").val();
        var tag = $("#tag").val();

        if (supId == '') {
            layer.msg("请选择一个合适的上级部门!");
            return false;
        }

        $.ajax({
            type: "POST",
            url: "<%=basePath%>/b/department/editDepartment.do",
            dataType: "json",
            async: true,
            data: {
                deptId: deptId,
                supId: supId,
                deptCode: deptCode,
                deptName: deptName,
                described: described,
                address: address,
                remark: remark,
                tag: tag
            },
            success: function (rc) {
                if (rc.code == '0') {
                    var zTree = $.fn.zTree.getZTreeObj("departmentTree");
                    alert(rc.message);

                    if (tag == "ADD") {
                        var supId = null;
                        if (rc.data != null && rc.data.supId != null) {
                            supId = zTree.getNodeByTId("departmentTree_" + rc.data.supId.deptId);  // 父级
                            //supCode = zTree.getNodeByParam("id", rc.data.supDic.dicId, null); // 父级
                        }

                        if (supId != undefined && supId != null) {
                            zTree.addNodes(supId, -1, {
                                id: rc.data.deptId,
                                pId: rc.data.deptCode,
                                code: rc.data.deptCode,
                                name: rc.data.deptName
                            });
                            var n = zTree.getNodeByParam("id", rc.data.deptId, null);  // 根据新的id找到新添加的节点
                            zTree.selectNode(n);  // 让新添加的节点处于选中状态
                        }
                    }

                    if (tag == "EDIT") {
                        var node = zTree.getNodeByParam("id", rc.data, null);
                        node.name = deptName;
                        zTree.updateNode(node);
                    }
                }
            }
        });
    }

    // 重置表单
    function resetForm() {
        $("#entryCode").empty();
        $("#entryValue").empty();
        $("#described").empty();
        $("#address").empty();
        $("#remark").empty();
    }

    // 新增Form/编辑Form
    function editDepartment(deptId, tag) {

        alert(deptId + " " + tag);

        $.ajax({
            type: "GET",
            url: "/b/department/editDepartment.do",
            dataType: "json",
            async: true,
            data: {
                deptId: deptId,
                tag: tag
            },
            success: function (dept) {

                if (tag == 'ADD') {
                    $("#supCode").val(dept.entryCode).trigger('change.select2');// 动态改变值以后必须触发改变事件。否则将不会生效(联动)

                    $("#deptId").val("");
                    $("#described").val("");
                    $("#entryCode").val("");
                    $("#entryValue").val("");
                    $("#address").val("");
                    $("#remark").val("");
                }

                if (tag == 'EDIT') {
                    if (dept.supDepartment !== undefined && dept.supDepartment !== null) {
                        $("#supCode").val(dept.supDepartment.entryCode).trigger('change');// 动态改变值以后必须触发改变事件。否则将不会生效(联动)
                    } else {
                        $("#supCode").val('-1').trigger('change');// 动态改变值以后必须触发改变事件。否则将不会生效(联动)
                    }

                    $("#supCode").attr("value", dept.supId);
                    $("#deptId").val(dept.deptId);
                    $("#described").val(dept.described);
                    $("#entryCode").val(dept.deptCode);
                    $("#entryValue").val(dept.deptName);
                    $("#address").val(dept.address);
                    $("#remark").val(dept.remark);
                }
            }
        });

        return true;
    }

    function removeDepartment(deptId, name) {
        layer.confirm('您确定要删除该字典数据？', {
            btn: ['确定', '暂时不要']  // 按钮
        }, function () {
            layer.msg('您点击了确定', {icon: 1});

            // 执行异步删除动作
            $.ajax({
                type: "POST",
                url: "/b/department/removeDepartment.do",
                dataType: "json",
                async: true,
                data: {
                    deptId: deptId,
                    deptName: name
                },
                success: function (rc) {
                    if (rc.code == '0') {
                        var zTree = $.fn.zTree.getZTreeObj("departmentTree");
                        var node = zTree.getNodeByParam("id", rc.data, null);
                        zTree.removeNode(node);

                        layer.msg('已为您删除该字典数据[' + deptId + ']', {icon: 1});
                    } else
                        layer.msg('删除操作出错![' + rc.message + ']');
                }
            });

            return true;
        }, function () {

            return true;
        });

        return false;
    }

</script>

</body>
</html>
