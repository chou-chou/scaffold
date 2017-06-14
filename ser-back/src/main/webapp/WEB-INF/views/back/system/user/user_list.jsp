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

    <link rel="stylesheet" href="<%=basePath%>/plugins/validation/jquery.validate.css"/>

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

    <script type="text/javascript">
        /*$.validator.setDefaults({
         submitHandler: function() {
         alert("提交事件!");
         }
         })*/

        //手机验证规则
        /*jQuery.validator.addMethod("mobile", function (value, element) {
         var mobile = /^1[3|4|5|7|8]\d{9}$/;
         return this.optional(element) || (mobile.test(value));
         }, "手机格式不对");*/

        $().ready(function () {
            //让当前表单调用validate方法，实现表单验证功能
            $("#userForm").validate({
//            var validation = new Validation({
                //debug:true, //调试模式，即使验证成功也不会跳转到目标页面
                //onkeyup:null, //当丢失焦点时才触发验证请求
                rules: { //配置验证规则，key就是被验证的dom对象，value就是调用验证的方法(也是json格式)
                    account: {
                        required: true, //必填。如果验证方法不需要参数，则配置为true
                        remote: {  // 返回值只能是true或false
                            url: "<%=basePath%>b/user/checkoutAccount.do",
                            type: "GET",   //数据发送方式
                            dataType: "json",  //接受数据格式
                            data: {   //要传递的数据，默认已传递应用此规则的表单项
                                account: function () {
                                    $("#account").val()
                                }
                            }
                        }
                    },
                    email: {
                        email: true
                    },
                    telephone: {
                        mobile: true
                    }
                },
                messages: {
                    account: {
                        required: "请输入用户名",
                        remote: "该用户名已存在！"
                    },

                    email: {
                        email: "邮箱格式不正确"
                    },
                    telephone: {
                        mobile: "手机格式不对"
                    }
                }
            });
        });
    </script>
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
                                    <table id="user_list" class="table table-striped table-bordered table-hover"
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
                                                                        <a class="btn btn-xs btn-success"  data-toggle="modal" data-target="#userModal"  title="编辑"
                                                                           onclick="editUser('${user.USER_ID}');">
                                                                            <i class="ace-icon fa fa-pencil-square-o bigger-120"
                                                                               title="编辑"></i>
                                                                        </a>
                                                                    </c:if>
                                                                    <c:if test="${QX.del == 1 }">
                                                                        <a class="btn btn-xs btn-danger"
                                                                           onclick="delUser(${user.USER_ID });">
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
                                                                                       class="tooltip-success" data-toggle="modal" data-target="#userModal"  title="修改">
																	<span class="green">
																		<i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
																	</span>
                                                                                    </a>
                                                                                </li>
                                                                            </c:if>
                                                                            <c:if test="${QX.del == 1 }">
                                                                                <li>
                                                                                    <a style="cursor:pointer;"
                                                                                       onclick="delUser(${user.USER_ID });"
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
                                                        <a class="btn btn-mini btn-success"  data-toggle="modal" data-target="#userModal" onclick="add();">新增</a>
                                                    </c:if>
                                                    <c:if test="${QX.email == 1 }">
                                                        <a id="userEmailBtn" title="批量发送电子邮件" class="btn btn-mini btn-info"
                                                           onclick="makeAll('确定要给选中的用户发送邮件吗?');">
                                                            <i class="ace-icon fa fa-envelope bigger-120"></i>
                                                        </a>
                                                    </c:if>
                                                    <c:if test="${QX.sms == 1 }">
                                                        <a id="userSmsBtn" title="批量发送短信" class="btn btn-mini btn-warning"
                                                           onclick="makeAll('确定要给选中的用户发送短信吗?');">
                                                            <i class="ace-icon fa fa-envelope-o bigger-120"></i>
                                                        </a>
                                                    </c:if>
                                                    <c:if test="${QX.del == 1 }">
                                                        <a id="userDeleteBtn" title="批量删除" class="btn btn-mini btn-danger"
                                                           onclick="batchDeleteUser('确定要删除选中的数据吗?');">
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

<div class="modal fade" id="userModal" tabindex="-1" role="dialog" aria-labelledby="userModalLabel" aria-hidden="true" aria-describedby="新增/编辑界面">
    <div class="modal-dialog" role="document" aria-hidden="true">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="关闭">×</button>
                <h4 class="modal-title" id="userModalLabel">编辑</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-xs-12">
                        <form class="form-horizontal" role="form" id="userForm">
                            <input type="hidden"  id="tag" name="code" value="ADD"/>
                            <input type="hidden"  id="userId" name="userId" value=""/>
                            <div class="form-group">
                                <label for="userName" class="col-sm-3 control-label no-padding-right"><span
                                        class="required-item">*</span>&nbsp;用户名:</label>
                                <div class="col-sm-9">
                                    <input type="text" id="userName" name="userName" value="" class="col-xs-10 col-sm-5"
                                           placeholder="请输入用户名">
                                </div>
                            </div>

                            <div class="form-group" <%--tabindex="1"--%>>
                                <label for="account" class="col-sm-3 control-label no-padding-right"><span
                                        class="required-item">*</span>&nbsp;登录账户:</label>
                                <div class="col-sm-9">
                                    <input type="text" id="account" name="account" value="" class="col-xs-10 col-sm-5"
                                           placeholder="请输入登录账户">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-3 control-label no-padding-right" for="isEnabled">
                                    是否激活:
                                </label>
                                <div class="col-sm-9 checkbox no-padding-left">
                                    <label>
                                        <input id="isEnabled" name="isEnabled" value="1"
                                               class="ace ace-checkbox-2" type="checkbox"
                                               <c:if test="${user.enabled == 1}">checked</c:if>/>
                                        <span class="lbl" style="color:#2B6EA1;">  注: 勾选即激活 </span>
                                    </label>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-3 control-label no-padding-right"
                                       for="telephone">电话号码:</label>
                                <div class="col-sm-9">
                                    <input type="text" id="telephone" name="telephone"
                                           placeholder="电话号码" class="col-xs-10 col-sm-5"
                                           value=""/>
                                    <label id="teleHint" name="hint" style="color:red;visibility:hidden;"></label>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-3 control-label no-padding-right"
                                       for="email">电子邮箱:</label>
                                <div class="col-sm-9">
                                    <input type="email" id="email" name="email"
                                           placeholder="电子邮箱" class="col-xs-10 col-sm-5"
                                           value="" required data-rule-email="true" data-msg-required="请输入email地址" data-msg-email="请输入正确的email地址"/>
                                    <label id="emailHint" name="hint" style="color:red; visibility:hidden;"></label>
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
                <button type="button" class="btn btn-default" data-dismiss="modal" onclick="off()">关闭</button>
                <button type="button"  id="saveChange" class="btn btn-primary" onclick="saveChange()">提交</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>
<!-- /.modal -->
<script type="text/javascript">
    jQuery(function ($) {
        //initiate dataTables plugin
        var user_list_table = $('#user_list').DataTable({
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
        $('#user_list > thead > tr > th input[type=checkbox], #user_list_wrapper input[type=checkbox]').eq(0).on('click', function () {
            var th_checked = this.checked;//checkbox inside "TH" table header

            $('#user_list').find('tbody > tr').each(function () {
                var row = this;
                if (th_checked) user_list_table.row(row).select();
                else  user_list_table.row(row).deselect();
            });
        });

        //select/deselect a row when the checkbox is checked/unchecked
        $('#user_list').on('click', 'td input[type=checkbox]', function () {
            var row = $(this).closest('tr').get(0);
            if (this.checked) user_list_table.row(row).deselect();
            else user_list_table.row(row).select();
        });

        $(document).on('click', '#user_list .dropdown-toggle', function (e) {
            e.stopImmediatePropagation();
            e.stopPropagation();
            e.preventDefault();
        })
    });
</script>

<script type="text/javascript">

    function off(){
        $("#accHint").css( "visibility","hidden");
        $("#teleHint").css( "visibility","hidden");
        $("#emailHint").css( "visibility","hidden");

    }
    function add(){
        $('h4').html("新增");1
        $("#tag").val("ADD");
        var  tag=$("#tag").val();
        document.getElementById("userForm").reset();
        $("#account").val("");
        $("[name=isEnabled]:checkbox").prop("checked", false);
        off();
    }

    //验证登录账户是否已存在
    /*$("#account").blur(function () {
        var account = $("#account").val();
        alert(" --- " + account);
        if (account == '') {
            $("#accHint").html("登录账户名不为空");
            $("#accHint").css( "visibility","visible");
            $("#saveChange").attr("disabled", true);
            checkoutAll();
            return false;
        }
        $.ajax({
            type: "GET",
     url: "%=basePath%>b/user/checkoutAccount.do",
            dataType: "json",
            async: true,
            data: {
                account: account
            },
            success: function (rc) {
                if (rc.code == '0') {
                    $("#accHint").css( "visibility","visible");
                    $("#accHint").html("登录账户已存在");
                } else {
                    $("#accHint").css( "visibility","hidden");

                }
                checkoutAll();
            }

        })

     });*/
    //验证手机格式
    /*$("#telephone").blur(function () {
        var telephone=$("#telephone").val();
        if(""!=telephone){
            var myreg=/^(13+\d{9})|(159+\d{8})|(153+\d{8})$/;
            if(!myreg.test(telephone)){
                $("#teleHint").css( "visibility","visible");
                $("#teleHint").html("手机格式不正确");
            }else{
                $("#teleHint").css( "visibility","hidden");
            }
        }else{
            $("#teleHint").css( "visibility","hidden");
        }
        checkoutAll();
     });*/

    //验证邮箱格式
    /*$("#email").blur(function () {
        var email=$("#email").val();
        if(""!=email){
            var myreg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
            if(!myreg.test(email)){
                $("#emailHint").css( "visibility","visible");
                $("#emailHint").html("邮箱格式不正确");
            }else{
                $("#emailHint").css( "visibility","hidden");
            }
        }else{
            $("#emailHint").css( "visibility","hidden");
        }
        checkoutAll();

     });*/

    function checkoutAll(){
        $("label[name='hint']").each(function(){
            if($(this).is(":hidden")){
                $("#saveChange").attr("disabled", false);
            }else{
                $("#saveChange").attr("disabled", true);
                return false;
            }

        })
    }

    // 编辑角色
    function editUser(userId){
        $('h4').html("编辑");
        $("#tag").val("EDIT");
        var tag=  $("#tag").val();
        if (userId == '') {
            layer.msg("编辑操作出错！");
            return false;
        }
        $.ajax({
            type: "GET",
            url: "b/user/editUser.do",
            dataType: "json",
            async: true,
            data: {
                userId: userId
            },
            success: function (user) {

                $("#userName").val(user.username);
                $("#account").val(user.account);
                if (user.enabled == '1') {
                    $("#isEnabled").attr("checked", 'true');
                } else {
                    $("#isEnabled").attr("checked", 'false');
                }
                $("#remark").val(user.remark);
                $("#email").val(user.email);
                $("#telephone").val(user.telephone);
                $("#userId").val(user.userId);


            }
        });

    }

    //单个删除用户
    function delUser(userId){
        layer.confirm('您确定要删除该字典数据？', {
            btn: ['确定', '暂时不要']  // 按钮
        }, function(){
            layer.msg('您点击了确定', {icon:1});

            if (userId == '') {
                layer.msg("删除操作出错！");
                return false;
            }

            // 执行异步删除动作
            $.ajax({
                type: "POST",
                url:"<%=basePath%>/b/user/deleteUsers.do",
                dataType: "json",
                async: true,
                data: {
                    userIds: userId
                },
                success: function (rc) {
                    if (rc.code == '0') {
                        layer.msg('已为您删除该字典数据', {icon:1});
                        setTimeout(function () {
                            location.reload();
                        },1000);
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

    //批量删除用户
    function batchDeleteUser(hint){
        layer.confirm(hint, {
            btn: ['确定', '暂时不要']  // 按钮
        }, function(){
            layer.msg('您点击了确定', {icon:1});
            var userIds = "";
            $('input:checkbox[name=ids]:checked').each(function(i){
                if(0==i){
                    userIds = $(this).val();
                }else{
                    userIds += (","+$(this).val());
                }
            });
            if (userIds == '') {
                layer.msg("请选择要删除的信息!");
                return false;
            }

            // 执行异步删除动作
            $.ajax({
                type: "POST",
                url: "<%=basePath%>/b/user/deleteUsers.do",
                dataType: "json",
                async: true,
                data: {
                    userIds: userIds
                },
                success: function (rc) {
                    if (rc.code == '0') {
                        layer.msg('已为您删除该字典数据', {icon:1});
                        setTimeout(function () {
                            location.reload();
                        }, 1000);

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
        var userName = $("#userName").val();
        var account = $("#account").val();
        if($("input[type='checkbox']").is(':checked')){
            var isEnabled ="1"
        } else {
            var isEnabled ="0"
        }
        if(tag=="EDIT"){
            var userId = $("#userId").val();
        }else {
            var userId = "";
        }
        var remark = $("#remark").val();
        var telephone = $("#telephone").val();
        var email = $("#email").val();
        if (remark == undefined || remark == null)  remark = "";
        $.ajax({
            type: "POST",
            url: "<%=basePath%>/b/user/editUser.do",
            dataType: "json",
            async: true,
            data: {
                tag:tag,
                userName:userName,
                account: account,
                isEnabled: isEnabled,
                telephone: telephone,
                email: email,
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

</body>
</html>
