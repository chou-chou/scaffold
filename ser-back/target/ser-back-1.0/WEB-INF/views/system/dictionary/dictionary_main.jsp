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

    <%@ include file="../../comm/default_header.jsp" %>

    <script type="text/javascript" src="<%=basePath%>/static/js/tree.min.js"></script>

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
                <form id="menuFrm" class="form-horizontal" action="menu/list.do" method="post" name="menuFrm">
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
                            <button type="submit" class="btn btn-purple btn-sm">
                                <span class="ace-icon fa fa-search icon-on-right bigger-110"></span>
                                搜索
                            </button>
                        </div>
                    </div>
                </form>

                <!-- 分割线 -->
                <div class="hr hr-5"></div>

                <div class="row">
                    <div class="col-xs-12">

                        <div class="row">
                            <!-- 字典树 -->
                            <div class="col-sm-4">
                                <%--<div class="well well-sm">
                                    <h4 class="blue smaller">
                                        <i class="fa fa-tags">
                                            字典树
                                        </i>
                                    </h4>
                                    <div class="tree-container ace-scroll" style="position: relative;">
                                        <div class="scroll-track scroll-active" style="display:block;height:250px;">
                                            <div class="scroll-bar" style="height:155px; top:0px;"></div>
                                        </div>
                                        <div class="scroll-content" style="max-length: 480px;">
                                            <ul id="dictionaryTree" class="ztree"></ul>
                                        </div>
                                    </div>
                                </div>--%>
                                <div class="widget-box widget-color-blue2">
                                    <div class="widget-header">
                                        <h4 class="widget-title lighter smaller">字典树</h4>
                                    </div>

                                    <div class="widget-body scroll-content" style="overflow:auto;">
                                        <div class="widget-main padding-8">
                                            <ul id="dictionaryTree" class="ztree"></ul>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!-- 菜单新增/编辑 -->
                            <div class="col-sm-8">
                                <!-- 工具按钮 新增/编辑/删除 -->
                                <div>
                                    <p>
                                        <button class="btn btn-sm btn-primary" onclick="">新增</button>
                                        <button class="btn btn-sm btn-primary" onclick="">编辑</button>
                                        <button class="btn btn-sm btn-primary" onclick="">删除</button>
                                    </p>
                                </div>

                                <div class="hr hr-dotted"></div>

                                <table id="menu_list" class="table table-striped table-bordered table-hover">
                                    <thead>
                                    <tr>
                                        <th class="center">
                                            <label>
                                                <input type="checkbox" class="ace"/>
                                                <span class="lbl"></span>
                                            </label>
                                        </th>
                                        <th>Domain</th>
                                        <th>Price</th>
                                        <th class="hidden-480">Clicks</th>

                                        <th>
                                            <i class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>
                                            Update
                                        </th>
                                        <th class="hidden-480">Status</th>
                                        <th></th>
                                    </tr>
                                    </thead>

                                    <tbody>
                                    <tr>
                                        <td class="center">
                                            <label class="pos-rel">
                                                <input type="checkbox" class="ace"/>
                                                <span class="lbl"></span>
                                            </label>
                                        </td>
                                        <td>
                                            <a href="#">app.com</a>
                                        </td>
                                        <td>$45</td>
                                        <td class="hidden-480">3,330</td>
                                        <td>Feb 12</td>

                                        <td class="hidden-480">
                                            <span class="label label-sm label-warning">Expiring</span>
                                        </td>

                                        <td>
                                            <div class="hidden-sm hidden-xs action-buttons">
                                                <a class="blue" href="#">
                                                    <i class="ace-icon fa fa-search-plus bigger-130"></i>
                                                </a>

                                                <a class="green" href="#">
                                                    <i class="ace-icon fa fa-pencil bigger-130"></i>
                                                </a>

                                                <a class="red" href="#">
                                                    <i class="ace-icon fa fa-trash-o bigger-130"></i>
                                                </a>
                                            </div>

                                            <div class="hidden-md hidden-lg">
                                                <div class="inline pos-rel">
                                                    <button class="btn btn-minier btn-yellow dropdown-toggle"
                                                            data-toggle="dropdown" data-position="auto">
                                                        <i class="ace-icon fa fa-caret-down icon-only bigger-120"></i>
                                                    </button>

                                                    <ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
                                                        <li>
                                                            <a href="#" class="tooltip-info" data-rel="tooltip"
                                                               title="View">
                                                                            <span class="blue">
                                                                                <i class="ace-icon fa fa-search-plus bigger-120"></i>
                                                                            </span>
                                                            </a>
                                                        </li>

                                                        <li>
                                                            <a href="#" class="tooltip-success" data-rel="tooltip"
                                                               title="Edit">
                                                                            <span class="green">
                                                                                <i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
                                                                            </span>
                                                            </a>
                                                        </li>

                                                        <li>
                                                            <a href="#" class="tooltip-error" data-rel="tooltip"
                                                               title="Delete">
                                                                            <span class="red">
                                                                                <i class="ace-icon fa fa-trash-o bigger-120"></i>
                                                                            </span>
                                                            </a>
                                                        </li>
                                                    </ul>
                                                </div>
                                            </div>
                                        </td>
                                    </tr>
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
    var zTree;
    var demoIframe;

    function addHoverDom(treeId, treeNode) {
        var sObj = $("#" + treeNode.tId + "_span");
        if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId).length > 0) return;
        var addStr = "<span class='button remove' id='removeBtn_" + treeNode.tId
                + "' title='add node' onfocus='this.blur();'></span>";

        addStr += "<span class='button add' id='addBtn_" + treeNode.tId + "'></span>";
        addStr += "<span class='button edit' id='editBtn_" + treeNode.tId + "'></span>";
        sObj.after(addStr);
        var btn = $("#addBtn_" + treeNode.tId);
        if (btn) btn.bind("click", function () {
            var zTree = $.fn.zTree.getZTreeObj("treeDemo");
            zTree.addNodes(treeNode, {id: (1000 + newCount), pId: treeNode.id, name: "new node" + (newCount++)});
            return false;
        });
    };

    function removeHoverDom(treeId, treeNode) {
        $("#addBtn_" + treeNode.tId).unbind().remove();
        $("#removeBtn_" + treeNode.tId).unbind().remove();
        $("#editBtn_" + treeNode.tId).unbind().remove();
    };

    var setting = {
        check: {
            enable: true
        },
        view: {
            addHoverDom: addHoverDom,
            removeHoverDom: removeHoverDom,
            dblClickExpand: false,
            showLine: true,
            selectedMulti: false
        },
        data: {
            simpleData: {
                enable: true,
                idKey: "id",
                pIdKey: "pId",
                rootPId: ""
            }
        },
        callback: {
            beforeClick: function (treeId, treeNode) {
                var zTree = $.fn.zTree.getZTreeObj("tree");
                if (treeNode.isParent) {
                    zTree.expandNode(treeNode);
                    return false;
                } else {
                    demoIframe.attr("src", treeNode.file + ".html");
                    return true;
                }
            }
        }
    };

    var zNodes = [
        {id: 1, pId: 0, name: "[core] 基本功能 演示", open: true},
        {id: 101, pId: 1, name: "最简单的树 --  标准 JSON 数据", file: "core/standardData"},
        {id: 102, pId: 1, name: "最简单的树 --  简单 JSON 数据", file: "core/simpleData"},
        {id: 103, pId: 1, name: "不显示 连接线", file: "core/noline"},
        {id: 104, pId: 1, name: "不显示 节点 图标", file: "core/noicon"},
        {id: 105, pId: 1, name: "自定义图标 --  icon 属性", file: "core/custom_icon"},
        {id: 106, pId: 1, name: "自定义图标 --  iconSkin 属性", file: "core/custom_iconSkin"},
        {id: 107, pId: 1, name: "自定义字体", file: "core/custom_font"},
        {id: 115, pId: 1, name: "超链接演示", file: "core/url"},
        {id: 108, pId: 1, name: "异步加载 节点数据", file: "core/async"},
        {id: 109, pId: 1, name: "用 zTree 方法 异步加载 节点数据", file: "core/async_fun"},
        {id: 110, pId: 1, name: "用 zTree 方法 更新 节点数据", file: "core/update_fun"},
        {id: 111, pId: 1, name: "单击 节点 控制", file: "core/click"},
        {id: 112, pId: 1, name: "展开 / 折叠 父节点 控制", file: "core/expand"},
        {id: 113, pId: 1, name: "根据 参数 查找 节点", file: "core/searchNodes"},
        {id: 114, pId: 1, name: "其他 鼠标 事件监听", file: "core/otherMouse"},

        {id: 2, pId: 0, name: "[excheck] 复/单选框功能 演示", open: false},
        {id: 201, pId: 2, name: "Checkbox 勾选操作", file: "excheck/checkbox"},
        {id: 206, pId: 2, name: "Checkbox nocheck 演示", file: "excheck/checkbox_nocheck"},
        {id: 207, pId: 2, name: "Checkbox chkDisabled 演示", file: "excheck/checkbox_chkDisabled"},
        {id: 208, pId: 2, name: "Checkbox halfCheck 演示", file: "excheck/checkbox_halfCheck"},
        {id: 202, pId: 2, name: "Checkbox 勾选统计", file: "excheck/checkbox_count"},
        {id: 203, pId: 2, name: "用 zTree 方法 勾选 Checkbox", file: "excheck/checkbox_fun"},
        {id: 204, pId: 2, name: "Radio 勾选操作", file: "excheck/radio"},
        {id: 209, pId: 2, name: "Radio nocheck 演示", file: "excheck/radio_nocheck"},
        {id: 210, pId: 2, name: "Radio chkDisabled 演示", file: "excheck/radio_chkDisabled"},
        {id: 211, pId: 2, name: "Radio halfCheck 演示", file: "excheck/radio_halfCheck"},
        {id: 205, pId: 2, name: "用 zTree 方法 勾选 Radio", file: "excheck/radio_fun"},

        {id: 3, pId: 0, name: "[exedit] 编辑功能 演示", open: false},
        {id: 301, pId: 3, name: "拖拽 节点 基本控制", file: "exedit/drag"},
        {id: 302, pId: 3, name: "拖拽 节点 高级控制", file: "exedit/drag_super"},
        {id: 303, pId: 3, name: "用 zTree 方法 移动 / 复制 节点", file: "exedit/drag_fun"},
        {id: 304, pId: 3, name: "基本 增 / 删 / 改 节点", file: "exedit/edit"},
        {id: 305, pId: 3, name: "高级 增 / 删 / 改 节点", file: "exedit/edit_super"},
        {id: 306, pId: 3, name: "用 zTree 方法 增 / 删 / 改 节点", file: "exedit/edit_fun"},
        {id: 307, pId: 3, name: "异步加载 & 编辑功能 共存", file: "exedit/async_edit"},
        {id: 308, pId: 3, name: "多棵树之间 的 数据交互", file: "exedit/multiTree"},

        {id: 4, pId: 0, name: "大数据量 演示", open: false},
        {id: 401, pId: 4, name: "一次性加载大数据量", file: "bigdata/common"},
        {id: 402, pId: 4, name: "分批异步加载大数据量", file: "bigdata/diy_async"},
        {id: 403, pId: 4, name: "分批异步加载大数据量", file: "bigdata/page"},

        {id: 5, pId: 0, name: "组合功能 演示", open: false},
        {id: 501, pId: 5, name: "冻结根节点", file: "super/oneroot"},
        {id: 502, pId: 5, name: "单击展开/折叠节点", file: "super/oneclick"},
        {id: 503, pId: 5, name: "保持展开单一路径", file: "super/singlepath"},
        {id: 504, pId: 5, name: "添加 自定义控件", file: "super/diydom"},
        {id: 505, pId: 5, name: "checkbox / radio 共存", file: "super/checkbox_radio"},
        {id: 506, pId: 5, name: "左侧菜单", file: "super/left_menu"},
        {id: 513, pId: 5, name: "OutLook 样式的左侧菜单", file: "super/left_menuForOutLook"},
        {id: 507, pId: 5, name: "下拉菜单", file: "super/select_menu"},
        {id: 509, pId: 5, name: "带 checkbox 的多选下拉菜单", file: "super/select_menu_checkbox"},
        {id: 510, pId: 5, name: "带 radio 的单选下拉菜单", file: "super/select_menu_radio"},
        {id: 508, pId: 5, name: "右键菜单 的 实现", file: "super/rightClickMenu"},
        {id: 511, pId: 5, name: "与其他 DOM 拖拽互动", file: "super/dragWithOther"},
        {id: 512, pId: 5, name: "异步加载模式下全部展开", file: "super/asyncForAll"},

        {id: 6, pId: 0, name: "其他扩展功能 演示", open: false},
        {id: 601, pId: 6, name: "隐藏普通节点", file: "exhide/common"},
        {id: 602, pId: 6, name: "配合 checkbox 的隐藏", file: "exhide/checkbox"},
        {id: 603, pId: 6, name: "配合 radio 的隐藏", file: "exhide/radio"}
    ];

    $(document).ready(function () {
        var t = $("#dictionaryTree");
        t = $.fn.zTree.init(t, setting, zNodes);
        demoIframe = $("#testIframe");
        demoIframe.bind("load", loadReady);
        var zTree = $.fn.zTree.getZTreeObj("tree");
        zTree.selectNode(zTree.getNodeByParam("id", 101));
    });

    function loadReady() {
        var bodyH = demoIframe.contents().find("body").get(0).scrollHeight,
                htmlH = demoIframe.contents().find("html").get(0).scrollHeight,
                maxH = Math.max(bodyH, htmlH), minH = Math.min(bodyH, htmlH),
                h = demoIframe.height() >= maxH ? minH : maxH;
        if (h < 530) h = 530;
        demoIframe.height(h);
    }

</script>

</body>
</html>
