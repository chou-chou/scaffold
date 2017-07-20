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
    <title>评审指标表</title>
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
                                <table id="indexTable_list" class="table table-striped table-bordered table-hover" style="margin-top:5px;"><%-- cellspacing="0"  table table-striped table-bordered table-hover--%>
                                    <thead>
                                    <tr>
                                        <th class="center" style="width:50px;">
                                            <label class="pos-rel">
                                                <input id="zcheckbox" type="checkbox" class="ace"/>
                                                <span class="lbl"></span>
                                            </label>
                                        </th>
                                        <th class="center" style="width:60px;">序号</th>
                                        <th class="center">表编号</th>
                                        <th class="center">表名称</th>
                                        <th class="center">制定人</th>
                                        <th class="center">备注</th>
                                    </tr>
                                    </thead>


                                    <c:choose>
                                        <c:when test="${not empty indexTableList}">
                                            <c:forEach items="${indexTableList}" var="indexTable" varStatus="vs">
                                                <tr>
                                                    <td class="center" style="width:50px;">
                                                        <label class="pos-rel">
                                                            <input type="checkbox" name="ids" value="${indexTable.id}"
                                                                id="${indexTable.id}" alt="${team.indexTableCode}" class="ace"/>
                                                            <span class="lbl"></span>
                                                        </label>
                                                    </td>
                                                    <td class="center" style="width: 60px;">${vs.index+1}</td>
                                                    <td class="center">${indexTable.indexTableCode}</td>
                                                    <td class="center">${indexTable.indexTableName}</td>
                                                    <td class="center">${indexTable.creator}</td>
                                                    <td class="center">${indexTable.remark}</td>
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
                                    <table style="width:100%">
                                        <tr>
                                            <td style="vertical-align: top;">
                                                <shiro:hasAnyPermission name="sys:team:add">
                                                    <a inCtrl id="indexTableSaveBtn" class="btn btn-mini btn-success" data-toggle="modal" data-target="#indexTableModal" onclick="addTag();">新增</a>
                                                </shiro:hasAnyPermission>
                                                <shiro:hasAnyPermission name="sys:team:edit">
                                                    <a inCtrl id="indexTableEditBtn" class="btn btn-mini btn-warn" data-toggle="modal"
                                                    onclick="editIndexTable();">编辑</a><%--data-target="#indexTableModal"--%>
                                                </shiro:hasAnyPermission>
                                                <shiro:hasAnyPermission name="sys:team:delete">
                                                    <a inCtrl id="indexTableDeleteBtn" title="批量删除" class="btn btn-mini btn-danger" onclick="deleteTeam();" >删除
                                                    </a>
                                                </shiro:hasAnyPermission>
                                                <shiro:hasAnyPermission name="sys:team:memberManage">
                                                    <a inCtrl id="indexTableManageBtn" title="指标管理" class="btn btn-mini btn-danger" onclick="indexManage();" >指标管理</a>
                                                </shiro:hasAnyPermission>
                                            </td>
                                            <td style="vertical-align: top;">
                                                <div class="pagination" style="float: right;padding-top:0px; margin-top:0px;">
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

<!-- 模态框（Modal） -->
<div class="modal fade" id="indexTableModal" tabindex="-1" role="dialog" aria-labelledby="indexTableModalLabel" aria-hidden="true" aria-describedby="新增/编辑界面">
    <div class="modal-dialog" role="document" aria-hidden="true">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="关闭">×</button>
                <h4 class="modal-title" id="indexTableModalLabel">编辑</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-xs-12">
                        <form class="form-horizontal" role="form">
                            <input type="hidden"  id="tag" name="code" value="ADD"/>
                            <input type="hidden"  id="indexTableId" name="indexTableId" value=""/>
                            <div class="form-group">
                                <label for="indexTableCode" class="col-sm-3 control-label no-padding-right">指标表编码:</label>
                                <div class="col-sm-9">
                                    <input type="text"  id="indexTableCode" name="indexTableCode" value="" class="col-xs-10 col-sm-5"
                                           placeholder="请输入表编码"><i class="ace-icon fa fa-asterisk red"></i>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="indexTableName" class="col-sm-3 control-label no-padding-right">指标表名称:</label>
                                <div class="col-sm-9">
                                    <input type="text" name="indexTableName" value="" id="indexTableName" class="col-xs-10 col-sm-5"
                                           placeholder="指标表名称"><i class="ace-icon fa fa-asterisk red bottom"></i>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="creator" class="col-sm-3 control-label no-padding-right">制定人:</label>
                                <div class="col-sm-9">
                                    <input type="text" name="creator" value="" id="creator" class="col-xs-10 col-sm-5"
                                           placeholder="制定人">
                                </div>
                            </div>


                            <div class="form-group">
                                <label for="remark" class="col-sm-3 control-label no-padding-right">备注:</label>
                                <div class="col-sm-9">
                                    <input type="text" name="remark" value="" id="remark" class="col-xs-10 col-sm-5"
                                           placeholder="备注">
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

<!-- 表指标管理模态框（Modal） -->
<div class="modal fade" id="indexManageModal" tabindex="-1" role="dialog" aria-labelledby="indexManageModalLabel" aria-hidden="true" aria-describedby="成员管理界面">
    <div class="modal-dialog" role="document" aria-hidden="true">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="关闭">×</button>
                <h5 class="modal-title" id="indexManageModalLabel">指标管理</h5>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <label class="col-sm-3 control-label no-padding-top" for="indexBox"> 指标列表 </label>

                    <div class="col-sm-8">
                        <select multiple="multiple" size="10" name="indexTable[]" id="indexBox">
                            <c:forEach items="${indexList}" var="indexs" varStatus="index">
                                <option id="${indexs.id}" value="${indexs.id}">${indexs.indexName}</option>
                            </c:forEach>
                        </select>
                        <br />
                        <input id="showValue" type="button" value="show selected data" />
                        <div class="hr hr-16 hr-dotted"></div>
                    </div>
                </div>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="saveIndex()">提交</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<script>
    $(function() {
        $('#indexTableModal').modal({
            keyboard: true,
            show:false
        });
    });


    <!-- 添加add标签 重置form -->
    function addTag(){
        $('h4').html("新增");
        $("#tag").val("ADD");
        $("#indexTableCode").val("");
        $("#indexTableName").val("");
        $("#creator").val("");
        $("#remark").val("");
    }

    <!--编辑团队-->
    function editIndexTable(){
        var indexTableId=$('input[name="ids"]:checked').val();
        $('h4').html("编辑");
        $("#tag").val("EDIT");
        if (indexTableId == '' || null==indexTableId || typeof(indexTableId) == "undefined" || indexTableId == "undefined") {
            //$(".btn btn-default").click()
            $('#indexTableModal').attr("show", false);
            layer.msg("勾选要编辑的表！");
            return false;
        } else {
            $('#indexTableModal').modal();
        }
        $.ajax({
            type: "GET",
            url: "b/indexTable/editIndexTable.do",
            dataType: "json",
            async: true,
            data: {
                indexTableId: indexTableId
            },
            success: function (indexTable) {
                $("#indexTableId").val(indexTable.id);
                alert(indexTable.id);
                $("#indexTableCode").val(indexTable.indexTableCode);
                $("#indexTableName").val(indexTable.indexTableName);
                $("#creator").val(indexTable.creator);
                $("#remark").val(indexTable.remark);
            }
        });

    }

    //批量删除
    function deleteTeam(){
        layer.confirm('您确定要删除该表数据？', {
            btn: ['确定', '暂时不要']  // 按钮
        }, function(){
            layer.msg('您点击了确定', {icon:1});
            var idnexTableIds = "";
            $('input:checkbox[name=ids]:checked').each(function(i){
                if(0==i){
                    idnexTableIds = $(this).val();
                }else{
                    idnexTableIds += (","+$(this).val());
                }
            });
            if (idnexTableIds == '') {
                layer.msg("请选择要删除的信息!");
                return false;
            }
            // 执行异步删除动作
            $.ajax({
                type: "POST",
                url: "b/indexTable/deleteIndexTable.do",
                dataType: "json",
                async: true,
                data: {
                    idnexTableIds: idnexTableIds
                },
                success: function (rc) {
                    if (rc.code == '0') {
                        layer.msg('已为您删除该数据', {icon:1});
                        setTimeout(function () {
                            location.reload();
                        }, 500);

                    } else
                        layer.msg('删除操作出错![' + rc.message + ']');
                }
            });

            return true;
        }, function() {

            return true;
        });

        return false;


    };

    <!-- 提交form -->
    function saveChange() {
        var  tag=$("#tag").val();
        var indexTableCode = $("#indexTableCode").val();
        var indexTableName = $("#indexTableName").val();

        if(tag=="EDIT"){
            var indexTableId = $("#indexTableId").val();
        }else {
            var indexTableId = "";
        }
        var creator = $("#creator").val();
        var remark = $("#remark").val();

        if (indexTableCode == null || indexTableCode == '') {
            layer.msg('请正确填写表编码!');
            return false;
        }

        $.ajax({
            type: "POST",
            url:"<%=basePath%>/b/indexTable/editindexTable.do",
            dataType: "json",
            async: true,
            data: {
                tag:tag,
                id:indexTableId,
                indexTableCode: indexTableCode,
                indexTableName: indexTableName,
                creator: creator,
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
        //initiate dataTables plugin
        var indexTable_list_table = $('#indexTable_list').DataTable({
            /*"dom": 'Bfrtip',*/
            bAutoWidth: false,
            "aoColumns": [
                null, null,
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

        addButton2DataTables(indexTable_list_table);

        setTimeout(function () {
            $($('.tableTools-container')).find('a.dt-button').each(function () {
                var div = $(this).find(' > div').first();
                if (div.length == 1) div.tooltip({container: 'body', title: div.parent().text()});
                else $(this).tooltip({container: 'body', title: $(this).text()});
            });
        }, 500);

        indexTable_list_table.on('select', function (e, dt, type, index) {
            if (type === 'row') {
                $(indexTable_list_table.row(index).node()).find('input:checkbox').prop('checked', true);
            }
        });
        indexTable_list_table.on('deselect', function (e, dt, type, index) {
            if (type === 'row') {
                $(indexTable_list_table.row(index).node()).find('input:checkbox').prop('checked', false);
            }
        });

        //table checkboxes
        $('th input[type=checkbox], td input[type=checkbox]').prop('checked', false);

        //select/deselect all rows according to table header checkbox
        $('#indexTable_list > thead > tr > th input[type=checkbox], #indexTable_list_wrapper input[type=checkbox]').eq(0).on('click', function () {
            var th_checked = this.checked;//checkbox inside "TH" table header

            $('#indexTable_list').find('tbody > tr').each(function () {
                var row = this;
                if (th_checked) indexTable_list_table.row(row).select();
                else  indexTable_list_table.row(row).deselect();
            });
        });

        //select/deselect a row when the checkbox is checked/unchecked
        $('#indexTable_list').on('click', 'td input[type=checkbox]', function () {
            var row = $(this).closest('tr').get(0);
            if (this.checked) indexTable_list_table.row(row).deselect();
            else indexTable_list_table.row(row).select();
        });

        $(document).on('click', '#indexTable_list .dropdown-toggle', function (e) {
            e.stopImmediatePropagation();
            e.stopPropagation();
            e.preventDefault();
        })
    });
</script>

<script type="text/javascript">
    var dualListBox = $('#indexBox').bootstrapDualListbox({
        nonSelectedListLabel: 'Non-selected',
        selectedListLabel: 'Selected',
        preserveSelectionOnMove: 'moved',
        moveOnSelect: true
    });
    var container1 = dualListBox.bootstrapDualListbox('getContainer');
    container1.find('.btn').addClass('btn-white btn-info btn-bold');

    var callback = function () {
        if (dualListBox != null) {
            console.info("刷新。。。");
            dualListBox.bootstrapDualListbox('refresh', true);
        }
    };

    function indexManage() {
        var IndexTableId = $('input[name="ids"]:checked').val();
        $("#tag").val("EDIT");
        if (IndexTableId == '' || null==IndexTableId || typeof(IndexTableId) == "undefined" || IndexTableId == "undefined") {
            layer.msg("勾选要编辑的团队！");
            return false;
        } else {
            $("#indexBox").removeClass("selected");
            $("#indexBox").each(function(){  //遍历所有option
                $(this).removeClass("selected");
            });
            $("#indexManageModal").modal();

            console.info("我出来了。。。。");
        }

        /**
         * 初始化DualListBox
         */
        initDualListBox(
            "b/indexTable/editIndexId.do",   // 获取选中项
            IndexTableId,                     // url对应的参数 -> "param" : xxx
            'id',
            'indexBox',              // 页面dualListBox对应的id
            callback);                  // 对应的回调函数
    }

    $("#showValue").click(function () {
        alert($('[name="indexTable[]"]').val());
    });

    /*$('#indexManageModal').on('hidden.bs.modal', function() {
        console.info("我隐藏起来了。。。。");
        $("#indexBox").removeClass("selected");
        $("#indexBox").each(function(){  //遍历所有option
            $(this).removeClass("selected");
        });

        if (dualListBox != null)
        dualListBox.bootstrapDualListbox('refresh', true);
    });*/

    function saveIndex() {
        var memberArr =$('[name="indexTable[]"]').val();
        var indexTableId=$('input[name="ids"]:checked').val();
        $.ajax({
            type:"POST",
            url:"b/indexTable/saveIndexId.do",
            dataType:"json",
            async: true,
            data:{
                memberArr:JSON.stringify(memberArr),
                id:indexTableId
            },
            success:function (result) {
                if (result.code == '0') {
                    alert(result.message);
                    location.reload();
                }
            }

        });
    }
</script>

</body>
</html>
