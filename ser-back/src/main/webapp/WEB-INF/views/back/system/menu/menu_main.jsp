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

    <link rel="stylesheet" href="<%=basePath%>/plugins/select2/css/select2.min.css"/>
    <link rel="stylesheet" href="<%=basePath%>/plugins/iconpicker/css/fontawesome-iconpicker.min.css"/>

    <script type="text/javascript" src="<%=basePath%>/static/js/pinyin.js"></script>
    <script type="text/javascript" src="<%=basePath%>/plugins/select2/js/select2.full.js"></script>
    <script type="text/javascript" src="<%=basePath%>/plugins/select2/js/i18n/zh-CN.js"></script>
    <script type="text/javascript" src="<%=basePath%>/plugins/iconpicker/js/fontawesome-iconpicker.min.js"></script>

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
                <form id="menuForm" class="form-horizontal" action="#" method="post" name="menuForm">
                    <div class="form-group">
                        <div class="col-sm-11">
                            <div class="form-inline">
                                <label class="control-label on-padding-left" for="menuInfo"><!-- no-padding-right -->
                                    菜单信息：
                                </label>
                                <input type="text" id="menuInfo" name="menuInfo" class="form-control" value="">
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
                            <!-- 菜单树 -->
                            <div class="col-sm-4">
                                <!-- 工具按钮 新增/编辑/删除
                                <div>
                                    <p>
                                        <button class="btn btn-sm btn-primary" onclick="">新增</button>
                                        <button class="btn btn-sm btn-primary" onclick="">编辑</button>
                                        <button class="btn btn-sm btn-primary" onclick="">删除</button>
                                    </p>
                                </div>
                                -->

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
                            <div class="col-sm-8">

                                <div class="clearfix form-actions">
                                    <!-- 新增/编辑 -->
                                    <div class="row">
                                        <div class="col-xs-12">

                                            <form class="form-horizontal" role="form" id="menuFrom">

                                                <input id="menuId" name="menuId" type="hidden" value="${menu.menuId}"/>
                                                <input id="tag" name="tag" type="hidden" value="ADD"/>

                                                <div class="form-group">
                                                    <label class="control-label col-sm-2 col-xs-12 no-padding-right"
                                                           for="supId">上级菜单:</label>
                                                    <div class="col-sm-10 col-xs-12">
                                                        <select id="supId" name="supId" class="select2" multiple=""
                                                                style="max-height:150px; overflow-y:auto; overflow-x: hidden">
                                                            <option value="0">请选择</option>
                                                            <c:forEach items="${menuList}" var="menu" varStatus="index">
                                                                <option value="${menu.menuId}">${menu.menuName}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                </div>

                                                <div class="form-group">
                                                    <label class="col-sm-2 control-label no-padding-right"
                                                           for="menuName">菜单名称:</label>
                                                    <div class="col-sm-10">
                                                        <input type="text" id="menuName" name="menuName"
                                                               placeholder="菜单名称" class="col-xs-10 col-sm-5"
                                                               value="${menu.menuName}"/>
                                                    </div>
                                                </div>

                                                <div class="form-group">
                                                    <label class="col-sm-2 control-label no-padding-right"
                                                           for="menuUrl">菜单链接:</label>
                                                    <div class="col-sm-10">
                                                        <input type="text" id="menuUrl" name="menuUrl"
                                                               placeholder="菜单链接" class="col-xs-10 col-sm-5"
                                                               value="${menu.menuUrl}"/>
                                                    </div>
                                                </div>

                                                <div class="form-group">
                                                    <label class="col-sm-2 control-label no-padding-right"
                                                           for="sequence">序号:</label>
                                                    <div class="col-sm-10">
                                                        <input type="text" id="sequence" name="sequence"
                                                               placeholder="序号" class="col-xs-10 col-sm-5"
                                                               value="${menu.sequence}"/>
                                                    </div>
                                                </div>

                                                <div class="form-group">
                                                    <label class="col-sm-2 control-label no-padding-right" for="icon">图标:</label>
                                                    <div class="col-sm-10">
                                                        <input type="text" id="icon_" name="icon_"
                                                               class="col-xs-10 col-sm-5"
                                                               value="${menu.icon}"/>
                                                        <button id="icon" type="buton"
                                                                class="btn btn-primary iconpicker-component">
                                                        </button>
                                                    </div>
                                                </div>

                                                <div class="form-group">
                                                    <label class="col-sm-2 control-label no-padding-right"
                                                           for="isLeaf">
                                                        是否为子节点:
                                                    </label>
                                                    <div class="col-sm-10 checkbox">
                                                        <label>
                                                            <input id="isLeaf" name="isLeaf" value="1"
                                                                   class="ace ace-checkbox-2" type="checkbox"/>
                                                            <span class="lbl">  注: 勾选即子节点 </span>
                                                        </label>
                                                    </div>
                                                </div>

                                                <div class="form-group">
                                                    <label class="col-sm-2 control-label no-padding-right"
                                                           for="remark">备注:</label>
                                                    <div class="col-sm-10">
                                                        <textarea id="remark" name="remark" class="form-control"
                                                                  placeholder="备注信息">${menu.remark}</textarea>
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

    jQuery(function ($) {
        $('#icon_').iconpicker({
            selected: false,
            defaultValue: false,theme: 'fip-bootstrap',
            source: 'fa_icons',
            searchSource: 'fa_icons',
            templates: {
                popover: '<div class="iconpicker-popover popover"><div class="arrow"></div>' +
                '<div class="popover-title"></div><div class="popover-content"></div></div>',
                footer: '<div class="popover-footer"></div>',
                buttons: '<button class="iconpicker-btn iconpicker-btn-cancel btn btn-default btn-sm">Cancel</button>' +
                ' <button class="iconpicker-btn iconpicker-btn-accept btn btn-primary btn-sm">Accept</button>',
                search: '<input type="search" class="form-control iconpicker-search" placeholder="Type to filter" />',
                iconpicker: '<div class="iconpicker"><div class="iconpicker-items"></div></div>',
                iconpickerItem: '<a role="button" href="#" class="iconpicker-item"><i></i></a>',
            }
        });
    })

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
                return editMenu(treeNode.id, 'ADD'); // , treeNode.id -> 1 / treeNode.tId -->
            });

        // 添加编辑按钮点击操作
        var editBtn = $("#editBtn_" + treeNode.tId);
        if (editBtn)
            editBtn.bind("click", function () {
                $("#tag").val("EDIT");
                return editMenu(treeNode.id, 'EDIT');
            });

        // 添加删除按钮点击操作
        var removeBtn = $("#removeBtn_" + treeNode.tId);
        if (removeBtn)
            removeBtn.bind("click", function () {
                return removeMenu(treeNode.id);
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
                var zTree = $.fn.zTree.getZTreeObj("menuTree");
                if (treeNode.isParent) {
                    zTree.expandNode(treeNode);
                    return false;
                } else {
                    return true;
                }
            }
        }/*,
         async: {
         enabled: true,
         autoParam: ['code'],
         dataType: "json"
         }*/
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
        var t = $("#menuTree");

        $.ajax({
            type: "POST",
            url: "<%=basePath%>b/menu/getMenuTree.do",
            dataType: "json",
            async: true,
            data: {
                menuInfo: $("#menuInfo").val()
            },
            success: function (data) {
                //var result = eval("(" + data + ")");  // 这个data不能直接使用，需要转化一下

                t = $.fn.zTree.init(t, setting, data);
            }
        });
    }

    // 定义

    // 新增/修改 -- 提交
    function saveChange() {

        var tag = $("#tag").val();
        var menuId = $("#menuId").val();
        var supId = $("#supId").val();
        var menuName = $("#menuName").val();
        var menuUrl = $("#menuUrl").val();
        var icon_ = $("#icon_").val();
        var sequence = $("#sequence").val();
        var isLeaf = 0;
        if ($("#isLeaf").val() !== undefined) isLeaf = $("#isLeaf").val();
        var remark = $("#remark").val();

        if (menuName == null || menuName == '') {
            layer.msg('请正确菜单名称!');
            return false;
        }

        if (remark == undefined || remark == null) remark = "";

        $.ajax({
            type: "POST",
            url: "<%=basePath%>b/menu/editMenu.do",
            dataType: "json",
            async: true,
            data: {
                tag: $("#tag").val(),
                menuId: menuId,
                supId: supId,
                menuName: menuName,
                menuUrl: menuUrl,
                icon: icon_,
                sequence: sequence,
                isLeaf: isLeaf,
                remark: remark
            },
            success: function (rc) {
                if (rc.code == '0') {
                    var zTree = $.fn.zTree.getZTreeObj("menuTree");
                    if (tag == "ADD" || "" == tag) {
                        if (rc.data != null && rc.data.supId != null) {
                            var supNode = null;
                            if (rc.data != null && rc.data.parentMenu != null) {
                                supNode = zTree.getNodeByParam("id", rc.data.parentMenu.menuId, null);
                            }
                            if (supNode != undefined && supNode != null) {
                                var treeNode_N = [{
                                    id: rc.data.menuId,
                                    tId: rc.data.menuId,
                                    pId: rc.data.supId,
                                    name: rc.data.menuName,
                                    isParent: false
                                }];
                                layer.msg('新增字典数据成功');
                                zTree.addNodes(supNode, treeNode_N);
                                var n = zTree.getNodeByParam("id", rc.data.menuId, null);  // 根据新的id找到新添加的节点
                                zTree.selectNode(n);  // 让新添加的节点处于选中状态
                            } else {
                                var treeNode_N = [{
                                    id: rc.data.menuId,
                                    tId: rc.data.menuId,
                                    name: rc.data.menuName,
                                    isParent: false
                                }];
                                zTree.addNodes(null, treeNode_N);
                                layer.msg('新增字典数据成功');
                                var n = zTree.getNodeByParam("id", rc.data.menuId, null);  // 根据新的id找到新添加的节点
                                zTree.selectNode(n);  // 让新添加的节点处于选中状态

                            }

                        }

                        $("#menuFrom")[0].reset();
                        $("select option[value='0']").attr("select", "selected");

                    }

                    if (tag == "EDIT") {
                        var node = zTree.getNodeByParam("id", rc.data, null);
                        node.name = menuName;
                        zTree.updateNode(node);
                    }
                } else {
                    layer.msg(rc.message);
                }
            }
        });
    }

    // 重置表单
    function resetForm() {
        $("#menuName").empty();
        $("#menuUrl").empty();
        $("#sequence").empty();
        $("#isLeaf").empty();
        $("#remark").empty();
        $("#icon_").empty();
    }

    // 新增Form/编辑Form
    function editMenu(menuId, tag) {

        $.ajax({
            type: "GET",
            url: "b/menu/editMenu.do",
            dataType: "json",
            async: true,
            data: {
                menuId: menuId,
                tag: tag
            },
            success: function (menu) {

                alert(tag);
                if (tag == "ADD") {
                    $("#supId").val(menu.menuId).trigger('change.select2');// 动态改变值以后必须触发改变事件。否则将不会生效(联动)

                    $("#menuId").val("");
                    $("#menuName").val("");
                    $("#menuUrl").val("");
                    $("#sequence").val("");
                    $("#icon_").val("");
                    $("#isLeaf").removeAttr("checked");
                    $("#remark").val("");
                }

                if (tag == 'EDIT') {
                    if (menu.parentMenu !== undefined && menu.parentMenu !== null) {
                        $("#supId").val(menu.parentMenu.menuId).trigger('change');// 动态改变值以后必须触发改变事件。否则将不会生效(联动)
                    } else {
                        $("#supId").val('-1').trigger('change');// 动态改变值以后必须触发改变事件。否则将不会生效(联动)
                    }

                    $("#menuId").val(menu.menuId);
                    $("#menuName").val(menu.menuName);
                    $("#menuUrl").val(menu.menuUrl);
                    $("#sequence").val(menu.sequence);
                    $("#icon_").val(menu.icon);

                    if (menu.isLeaf == true) {
                        $("#isLeaf").attr("checked", 'true');
                    } else {
                        $("#isLeaf").attr("checked", 'false');
                    }
                    $("#remark").val(menu.remark);
                }
            }
        });

        return false;
    }

    function removeMenu(menuId) {
        alert(menuId);
        layer.confirm('您确定要删除该菜单数据？', {
            btn: ['确定', '暂时不要']  // 按钮
        }, function () {
            layer.msg('您点击了确定', {icon: 1});

            // 执行异步删除动作
            $.ajax({
                type: "POST",
                url: "<%=basePath%>b/menu/removeMenu.do",
                dataType: "json",
                async: true,
                data: {
                    menuId: menuId
                },
                success: function (rc) {
                    if (rc.code == '0') {
                        var zTree = $.fn.zTree.getZTreeObj("menuTree");
                        var node = zTree.getNodeByParam("id", rc.data, null);
                        zTree.removeNode(node);

                        layer.msg('已为您删除该菜单数据');
                    } else
                        layer.msg('删除操作出错!');
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
