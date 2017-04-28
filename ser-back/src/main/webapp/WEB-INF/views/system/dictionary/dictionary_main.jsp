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

    <%--<link rel="stylesheet" href="<%=basePath%>/static/css/bootstrap-multiselect.min.css"/>
    <script type="text/javascript" src="<%=basePath%>/static/js/bootstrap-multiselect.min.js"></script>--%>

    <link rel="stylesheet" href="<%=basePath%>/plugins/select2/css/select2.min.css"/>
    <script type="text/javascript" src="<%=basePath%>/plugins/select2/js/select2.min.js"></script>
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
                <form id="menuFrm" class="form-horizontal" action="#" method="post" name="menuFrm">
                    <div class="form-group">
                        <div class="col-sm-11">
                            <div class="form-inline">
                                <label class="control-label on-padding-left" for="dicInfo"><!-- no-padding-right -->
                                    字典信息：
                                </label>
                                <input type="text" id="dicInfo" name="dicInfo" class="form-control" value="">
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
                <div class="hr hr-5"></div>

                <div class="row">
                    <div class="col-xs-12">

                        <div class="row">
                            <!-- 字典树 -->
                            <div class="col-sm-4">
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

                                <!-- 新增/编辑 -->
                                <div class="row">
                                    <div class="col-xs-12">

                                        <form class="form-horizontal" role="form">
                                            <input id="dicId" name="dicId" type="hidden" value="${dic.dicId}" />
                                            <input id="tag" name="tag" type="hidden" />

                                            <div class="form-group">
                                                <label class="control-label col-sm-2 col-xs-12 no-padding-right" for="supCode">上级字典:</label>
                                                <div class="col-sm-10 col-xs-12">
                                                    <select id="supCode" name="supCode" class="select2" multiple=""
                                                            style="max-height:150px; overflow-y:auto; overflow-x: hidden" >
                                                        <option value="-1"> - 请选择 - </option>
                                                        <c:forEach items="${dicList}" var="dic" varStatus="index">
                                                            <option value="${dic.entryCode}">${dic.entryValue}</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>

                                            <%--<div class="form-group">
                                                <label class="control-label col-sm-2 col-xs-12 no-padding-right" for="supCode">上级字典:</label>
                                                <div class="col-sm-10 col-xs-12">
                                                    <select id="supCode" name="supCode" class="multiselect dropdown-menu" style="max-height:150px; overflow-y:auto; overflow-x: hidden" multiple="">
                                                        <c:forEach items="${dicList}" var="dic" varStatus="index">
                                                            <option value="${dic.entryCode}">${dic.entryValue}</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>--%>

                                            <div class="form-group">
                                                <label class="col-sm-2 control-label no-padding-right" for="entryCode">字典编码:</label>
                                                <div class="col-sm-10">
                                                    <input type="text" id="entryCode" name="entryCode" placeholder="字典编码" class="col-xs-10 col-sm-5"
                                                        value="${dic.entryCode}"/>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <label class="col-sm-2 control-label no-padding-right" for="entryValue">字典名称:</label>
                                                <div class="col-sm-10">
                                                    <input type="text" id="entryValue" name="entryValue" placeholder="字典名称" class="col-xs-10 col-sm-5"
                                                           value="${dic.entryValue}"/>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <label class="col-sm-2 control-label no-padding-right" for="sequence">序号:</label>
                                                <div class="col-sm-10">
                                                    <input type="text" id="sequence" name="sequence" placeholder="序号" class="col-xs-10 col-sm-5"
                                                           value="${dic.sequence}"/>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <label class="col-sm-2 control-label no-padding-right" for="enabled">
                                                    是否可用:
                                                </label>
                                                <div class="col-sm-10 checkbox">
                                                    <label>
                                                        <input id="enabled" name="enabled" class="ace ace-checkbox-2" type="checkbox" <c:if test="${dic.enabled == true}">checked</c:if>/>
                                                        <span class="lbl">  注: 勾选即可用 </span>
                                                    </label>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <label class="col-sm-2 control-label no-padding-right" for="remark">备注:</label>
                                                <div class="col-sm-10">
                                                    <textarea id="remark" name="remark" class="form-control"placeholder="备注信息">${dic.sequence}</textarea>
                                                </div>
                                            </div>

                                            <div class="clearfix form-actions">
                                                <div class="col-md-offset-3 col-md-9">
                                                    <button class="btn btn-info" type="button" onclick="return saveChange();">
                                                        <i class="ace-icon fa fa-check bigger-110"></i>
                                                        提交
                                                    </button>

                                                    &nbsp; &nbsp; &nbsp;
                                                    <button class="btn" type="reset" onclick="resetForm();">
                                                        <i class="ace-icon fa fa-undo bigger-110"></i>
                                                        重置
                                                    </button>
                                                </div>
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
                return editDictionary(treeNode.id, 'ADD'); // , treeNode.id -> 1 / treeNode.tId --> dictionaryTree_1
            });

        // 添加编辑按钮点击操作
        var editBtn = $("#editBtn_" + treeNode.tId);
        if (editBtn)
            editBtn.bind("click", function () {
                $("#tag").val("EDIT");
                return editDictionary(treeNode.id, 'EDIT');
            });

        // 添加删除按钮点击操作
        var removeBtn = $("#removeBtn_" + treeNode.tId);
        if (removeBtn)
            removeBtn.bind("click", function () {
                return removeDictionary(treeNode.id);
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
            chkboxType: {"Y":"", "N":""}
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
                rootPId: ""
            }
        },
        callback: {
            beforeClick: function (treeId, treeNode) {
                var zTree = $.fn.zTree.getZTreeObj("dictionaryTree");
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

    $('.select2').css('width', '200px').select2({
        allowClear:true,
        language: "zh-CN",
        placeholder:'键入查询',
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

    $(document).one('ajaxloadstart.page', function(e){
        $('[class*=select2]').remove();
        //$('.multiselect').multiselect('destroy');
    })

    // 业务功能
    // 异步获取字典数据信息，并展示
    function ajaxFetchData() {
        var t = $("#dictionaryTree");

        $.ajax({
            type: "POST",
            url: "/dictionary/getDicTree.do",
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

    // 新增/修改 -- 提交
    function saveChange() {

        var supCode = $("#supCode").val();
        var entryCode = $("#entryCode").val();
        var entryValue = $("#entryValue").val();
        var sequence = $("#sequence").val();
        var enabled = $("#enabled").val();
        var remark = $("#remark").val;

        if (supCode == '') {
            layer.msg("请选择一个合适的上级字典!");
            return false;
        }

        $.ajax({
            type: "POST",
            url: "/dictionary/editDictionary.do",
            dataType: "json",
            async: true,
            data: {
                supCode: supCode,
                entryCode: entryCode,
                entryValue: entryValue,
                sequence: sequence,
                enabled: enabled,
                remark: remark
            },
            success: function (rc) {
                if (rc.code == '0' || rc.code == "0") {
                    alert(rc.message);
                }
            }
        });
    }

    // 重置表单
    function resetForm() {
        $("#entryCode").empty();
        $("#entryValue").empty();
        $("#sequence").empty();
        $("#enabled").empty();
        $("#remark").empty();
    }

    // 新增Form/编辑Form
    function editDictionary(dicId, tag) {

        alert(dicId + " " + tag);

        $.ajax({
            type: "GET",
            url: "/dictionary/editDictionary.do",
            dataType: "json",
            async: true,
            data: {
                dicId: dicId,
                tag: tag
            },
            success: function (dic) {
                // 设置
                $("#supCode").val(dic.entryCode);
                $("#supCode option[text='" + dic.entryValue + "']").attr("selected", true);
                //$("#supCode").select2().select2('val', dic.entryCode);
                //$("#supCode").select2('text', dic.entryValue);

                $("#supCode").attr("value", dic.entryCode);
                $("#supCode").trigger('change');  // 动态改变值以后必须触发改变时间。否则将不会生效

                if (tag == 'EDIT') {
                    $("#dicId").val(dic.dicId);
                    $("#sequence").val(dic.sequence);
                    $("#entryCode").val(dic.entryCode);
                    $("#entryValue").val(dic.entryValue);

                    if (dic.enabled == true) {
                        $("#enabled").attr("checked", 'true');
                    } else {
                        $("#enabled").attr("checked", 'false');
                    }
                    $("#remark").val(dic.remark);
                }
            }
        });

        return true;
    }

    function removeDictionary(dicId) {

        return false;
    }

</script>

</body>
</html>
