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
    <title>科研团队列表</title>
    <base href="<%=basePath%>">

    <%@ include file="../../../comm/default_header.jsp" %>

    <!-- datatables相关css/js -->
    <script type="text/javascript">
        if ('ontouchstart' in document.documentElement) document.write("<script src='<%=basePath%>/plugins/jquery/jquery.mobile.custom.min.js'>" + "<" + "/script>")
    </script>

    <script src="<%=basePath%>/static/js/moment.min.js"></script>
    <script src="<%=basePath%>/plugins/bootstrap/extends/bootstrap-datepicker/bootstrap-datepicker.min.js"></script>
    <script src="<%=basePath%>/plugins/bootstrap/extends/bootstrap-datepicker/i18n/bootstrap-datepicker.zh-CN.js"></script>
    <link href="<%=basePath%>/plugins/bootstrap/extends/bootstrap-datepicker/bootstrap-datepicker3.min.css" rel="stylesheet" />

    <script src="<%=basePath%>/plugins/bootstrap/extends/bootstrap-duallistbox/jquery.bootstrap-duallistbox.min.js"></script>
    <link href="<%=basePath%>/plugins/bootstrap/extends/bootstrap-duallistbox/bootstrap-duallistbox.min.css" rel="stylesheet" />

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
                                <table id="team_list" class="table table-striped table-bordered table-hover" style="margin-top:5px;"><%-- cellspacing="0"  table table-striped table-bordered table-hover--%>
                                    <thead>
                                    <tr>
                                        <th class="center" style="width:50px;">
                                            <label class="pos-rel">
                                                <input id="zcheckbox" type="checkbox" class="ace"/>
                                                <span class="lbl"></span>
                                            </label>
                                        </th>
                                        <th class="center" style="width:60px;">序号</th>
                                        <th class="center">团队编号</th>
                                        <th class="center">团队名称</th>
                                        <th class="center">团队类型</th>
                                        <th class="center">成立时间</th>
                                        <th class="center">备注</th>
                                    </tr>
                                    </thead>


                                    <c:choose>
                                        <c:when test="${not empty teamList}">
                                            <c:if test="${QX.cha == 1}">
                                            <c:forEach items="${teamList}" var="team" varStatus="vs">
                                                <tr>
                                                    <td class="center" style="width:50px;">
                                                        <label class="pos-rel">
                                                            <input type="checkbox" name="ids" value="${team.teamId}"
                                                                id="${team.teamId}" alt="${team.teamCode}" class="ace"/>
                                                            <span class="lbl"></span>
                                                        </label>
                                                    </td>
                                                    <td class="center" style="width: 60px;">${vs.index+1}</td>
                                                    <td class="center">${team.teamCode}</td>
                                                    <td class="center">${team.teamName}</td>
                                                    <td class="center">${team.teamType}</td>
                                                    <td class="center"><fmt:formatDate value="${team.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>:
                                                    <td class="center">${team.remark}</td>
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
                                                    <a class="btn btn-mini btn-success" data-toggle="modal" data-target="#teamModal" onclick="addTag();">新增</a>
                                                </c:if>
                                                <a class="btn btn-mini btn-warn" data-toggle="modal"
                                                    onclick="editTeam();">编辑</a><%--data-target="#teamModal"--%>
                                                <c:if test="${QX.del == 1 }">
                                                    <a title="批量删除" class="btn btn-mini btn-danger" onclick="deleteTeam();" >删除
                                                    </a>
                                                </c:if>
                                                <a title="成员管理" class="btn btn-mini btn-danger" onclick="memberManage();" >成员管理</a>
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
<div class="modal fade" id="teamModal" tabindex="-1" role="dialog" aria-labelledby="teamModalLabel" aria-hidden="true" aria-describedby="新增/编辑界面">
    <div class="modal-dialog" role="document" aria-hidden="true">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="关闭">×</button>
                <h4 class="modal-title" id="teamModalLabel">编辑</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-xs-12">
                        <form class="form-horizontal" role="form">
                            <input type="hidden"  id="tag" name="code" value="ADD"/>
                            <input type="hidden"  id="teamId" name="teamId" value=""/>
                            <div class="form-group">
                                <label for="teamCode" class="col-sm-3 control-label no-padding-right">团队编码:</label>
                                <div class="col-sm-9">
                                    <input type="text"  id="teamCode" name="teamCode" value="" class="col-xs-10 col-sm-5"
                                           placeholder="请输入团队编码"><i class="ace-icon fa fa-asterisk red"></i>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="teamName" class="col-sm-3 control-label no-padding-right">团队名称:</label>
                                <div class="col-sm-9">
                                    <input type="text" name="teamName" value="" id="teamName" class="col-xs-10 col-sm-5"
                                           placeholder="请输入团队名称"><i class="ace-icon fa fa-asterisk red bottom"></i>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="teamType" class="col-sm-3 control-label no-padding-right">团队类型:</label>
                                <div class="col-sm-9">
                                    <input type="text" name="teamType" value="" id="teamType" class="col-xs-10 col-sm-5"
                                           placeholder="团队类型"><i class="ace-icon fa fa-asterisk red bottom"></i>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="remake" class="col-sm-3 control-label no-padding-right">备注:</label>
                                <div class="col-sm-9">
                                    <input type="text" name="remake" value="" id="remake" class="col-xs-10 col-sm-5"
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

<!-- 成员管理模态框（Modal） -->
<div class="modal fade" id="memberManageModal" tabindex="-1" role="dialog" aria-labelledby="memberManageModalLabel" aria-hidden="true" aria-describedby="成员管理界面">
    <div class="modal-dialog" role="document" aria-hidden="true">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="关闭">×</button>
                <h5 class="modal-title" id="memberManageModalLabel">成员管理</h5>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <label class="col-sm-3 control-label no-padding-top" for="teamUserBox"> 成员列表 </label>

                    <div class="col-sm-8">
                        <select multiple="multiple" size="10" name="teamUser[]" id="teamUserBox">
                            <c:forEach items="${userList}" var="user" varStatus="index">
                                <option id="${user.USER_ID}" value="${user.USER_ID}">${user.USERNAME}</option>
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
                <button type="button" class="btn btn-primary" onclick="saveMember()">提交</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<script>
    $(function() {
        $('#teamModal').modal({
            keyboard: true,
            show:false
        });
    });

    $(function () {
        $('#id-date-picker-1').datepicker({
            autoclose: true,
            language: "zh-CN",
            format: "yyyy-mm-dd",
            todayHighlight: true
        }).next().on(ace.click_event, function() {
            $(this).previous().focus();
        })
    });
    <!-- 添加add标签 重置form -->
    function addTag(){
        $('h4').html("新增");
        $("#tag").val("ADD");
        var  tag = $("#tag").val();
        $("#teamCode").val("");
        $("#teamName").val("");
        $("#teamType").val("");
        $("#remark").val("");
    }

    <!--编辑团队-->
    function editTeam(){
        var teamId=$('input[name="ids"]:checked').val();
        $('h4').html("编辑");
        $("#tag").val("EDIT");
        if (teamId == '' || null==teamId || typeof(teamId) == "undefined" || teamId == "undefined") {
            //$(".btn btn-default").click()
            $('#teamModal').attr("show", false);
            layer.msg("勾选要编辑的团队！");
            return false;
        } else {
            $('#teamModal').modal();
        }
        $.ajax({
            type: "GET",
            url: "b/team/editTeam.do",
            dataType: "json",
            async: true,
            data: {
                teamId: teamId
            },
            success: function (team) {
                $("#teamId").val(team.teamId);
                $("#teamCode").val(team.teamCode);
                $("#teamName").val(team.teamName);
                $("#teamType").val(team.teamType);
                $("#remark").val(team.remark);
            }
        });

    }

    //批量删除
    function deleteTeam(){
        layer.confirm('您确定要删除该字典数据？', {
            btn: ['确定', '暂时不要']  // 按钮
        }, function(){
            layer.msg('您点击了确定', {icon:1});
            var teamIds = "";
            $('input:checkbox[name=ids]:checked').each(function(i){
                if(0==i){
                    teamIds = $(this).val();
                }else{
                    teamIds += (","+$(this).val());
                }
            });
            if (teamIds == '') {
                layer.msg("请选择要删除的信息!");
                return false;
            }
            // 执行异步删除动作
            $.ajax({
                type: "POST",
                url: "b/team/deleteTeam.do",
                dataType: "json",
                async: true,
                data: {
                    teamIds: teamIds
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
        var teamCode = $("#teamCode").val();
        var teamName = $("#teamName").val();

        if(tag=="EDIT"){
            var teamId = $("#teamId").val();
        }else {
            var teamId = "";
        }
        var teamType = $("#teamType").val();
        var remark = $("#remark").val();

        if (teamCode == null || teamCode == '') {
            layer.msg('请正确填写团队编码!');
            return false;
        }

        $.ajax({
            type: "POST",
            url:"<%=basePath%>/b/team/editTeam.do",
            dataType: "json",
            async: true,
            data: {
                tag:tag,
                teamId:teamId,
                teamCode: teamCode,
                teamName: teamName,
                teamType: teamType,
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
        var user_list_table = $('#team_list').DataTable({
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
        $('#team_list > thead > tr > th input[type=checkbox], #user_list_wrapper input[type=checkbox]').eq(0).on('click', function () {
            var th_checked = this.checked;//checkbox inside "TH" table header

            $('#team_list').find('tbody > tr').each(function () {
                var row = this;
                if (th_checked) user_list_table.row(row).select();
                else  user_list_table.row(row).deselect();
            });
        });

        //select/deselect a row when the checkbox is checked/unchecked
        $('#team_list').on('click', 'td input[type=checkbox]', function () {
            var row = $(this).closest('tr').get(0);
            if (this.checked) user_list_table.row(row).deselect();
            else user_list_table.row(row).select();
        });

        $(document).on('click', '#team_list .dropdown-toggle', function (e) {
            e.stopImmediatePropagation();
            e.stopPropagation();
            e.preventDefault();
        })
    });
</script>

<script type="text/javascript">
    var dualListBox;

    function memberManage() {
        var teamId = $('input[name="ids"]:checked').val();
        $("#tag").val("EDIT");
        if (teamId == '' || null==teamId || typeof(teamId) == "undefined" || teamId == "undefined") {
            layer.msg("勾选要编辑的团队！");
            return false;
        } else {
            $("#memberManageModal").modal();
        }

        var callback = function () {
            // 渲染dualListBox
            dualListBox = $('#teamUserBox').bootstrapDualListbox({
                nonSelectedListLabel: 'Non-selected',
                selectedListLabel: 'Selected',
                preserveSelectionOnMove: 'moved',
                moveOnSelect: true//,
                //    infoTextFiltered: '<span class="label label-purple label-lg">Filtered</span>'
            });
            var container1 = dualListBox.bootstrapDualListbox('getContainer');
            container1.find('.btn').addClass('btn-white btn-info btn-bold');
        };

        /**
         * 初始化DualListBox
         */
        initDualListBox(
            "b/team/editTeamUser.do",   // 获取选中项
            teamId,                     // url对应的参数 -> "param" : xxx
            'userId',
            'teamUserBox',              // 页面dualListBox对应的id
            callback);                  // 对应的回调函数
        dualListBox.bootstrapDualListbox('refresh', true);

        /*$.ajax({
            type: "GET",
            url: "b/team/editTeamUser.do",
            dataType: "json",
            async: true,
            data: {
                teamId: teamId
            },
            success: function (userTeamsList) {
                $.each(userTeamsList, function (index, userTeam) {
         $("#teamUser option[value='" + userTeam.userId + "']").attr("selected", true);

                });
         teamUserBox.bootstrapDualListbox('refresh', true);
            }
         });*/

        $("#showValue").click(function () {
            alert($('[name="teamUser[]"]').val());
        });

    }
    function saveMember() {
        var memberArr =$('[name="teamUser[]"]').val();
        var teamId=$('input[name="ids"]:checked').val();
        $.ajax({
            type:"POST",
            url:"b/team/saveMember.do",
            dataType:"json",
            async: true,
            data:{
                memberArr:JSON.stringify(memberArr),
                teamId:teamId
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
