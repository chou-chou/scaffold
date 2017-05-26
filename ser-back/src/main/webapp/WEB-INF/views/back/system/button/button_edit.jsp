<%--
  Created by IntelliJ IDEA.
  User: david
  Date: 2017-05-25
  Time: 11:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<div class="clearfix form-actions">
    <!-- 新增/编辑 -->
    <div class="row">
        <div class="col-xs-12">

            <form class="form-horizontal" role="form">

                <input id="dicId" name="dicId" type="hidden" value="${dic.dicId}"/>
                <input id="tag" name="tag" type="hidden" value="ADD"/>

                <div class="form-group">
                    <label class="control-label col-sm-2 col-xs-12 no-padding-right"
                           for="supCode">上级字典:</label>
                    <div class="col-sm-10 col-xs-12">
                        <select id="supCode" name="supCode" class="select2"
                                style="max-height:35px; overflow-y:auto; overflow-x: hidden">
                            <option value="-1">请选择</option>
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
                    <label class="col-sm-2 control-label no-padding-right"
                           for="entryCode">字典编码:</label>
                    <div class="col-sm-10">
                        <input type="text" id="entryCode" name="entryCode"
                               placeholder="字典编码" class="col-xs-10 col-sm-5"
                               value="${dic.entryCode}"/>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-2 control-label no-padding-right"
                           for="entryValue">字典名称:</label>
                    <div class="col-sm-10">
                        <input type="text" id="entryValue" name="entryValue"
                               placeholder="字典名称" class="col-xs-10 col-sm-5"
                               value="${dic.entryValue}"/>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-2 control-label no-padding-right"
                           for="sequence">序号:</label>
                    <div class="col-sm-10">
                        <input type="text" id="sequence" name="sequence"
                               placeholder="序号" class="col-xs-10 col-sm-5"
                               value="${dic.sequence}"/>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-2 control-label no-padding-right"
                           for="enabled">
                        是否可用:
                    </label>
                    <div class="col-sm-10 checkbox">
                        <label>
                            <input id="enabled" name="enabled" value="1"
                                   class="ace ace-checkbox-2" type="checkbox"
                                   <c:if test="${dic.enabled == true}">checked</c:if>/>
                            <span class="lbl">  注: 勾选即可用 </span>
                        </label>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-2 control-label no-padding-right" for="remark">备注:</label>
                    <div class="col-sm-10">
                                                        <textarea id="remark" name="remark" class="form-control"
                                                                  placeholder="备注信息">${dic.sequence}</textarea>
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

</body>
</html>
