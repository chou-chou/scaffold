<!-- 插件区js -->
<!--[if !IE]> -->
    <script type="text/javascript" src="<%=basePath%>/plugins/jquery/jquery-2.1.4.min.js"></script>
<!-- <![endif]-->

<!--[if IE]>
    <script type="text/javascript" src="<%=basePath%>/plugins/jquery/jquery-1.11.3.min.js"></script>
<![endif]-->

<script type="text/javascript">
    if ('ontouchstart' in document.documentElement)
        document.write("<script type='text/javascript' src='<%=basePath%>/plugins/jquery/jquery.mobile.custom.min.js'>" + "<" + "/script>");
</script>

<!--[if lte IE 8]>
    <script type="text/javascript" src="<%=basePath%>/static/js/excanvas.min.js"></script>
<![endif]-->

<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
    <script type="text/javascript" src="<%=basePath%>/static/js/html5shiv.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/static/js/respond.min.js"></script>
<![endif]-->

<script type="text/javascript" src="<%=basePath%>/plugins/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/static/js/jquery.ui.touch-punch.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/static/js/jquery.sparkline.index.min.js"></script>

<!-- ace scripts -->
<script type="text/javascript" src="<%=basePath%>/plugins/ace/js/ace-elements.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/plugins/ace/js/ace.min.js"></script>

<!-- ace settings handler -->
<script type="text/javascript" src="<%=basePath%>/plugins/ace/js/ace-extra.min.js"></script>

<script type="text/javascript" src="<%=basePath%>/static/js/index.js"></script>

<script type="text/javascript" src="<%=basePath%>/plugins/zTree/3.5/jquery.ztree.all.js"></script>

<script type="text/javascript" src="<%=basePath%>/plugins/layer/layer.js" ></script>

<script type="text/javascript" src="<%=basePath%>/plugins/validation/jquery.validate.js"></script>
<script type="text/javascript" src="<%=basePath%>/plugins/validation/additional-methods.js"></script>
<script type="text/javascript" src="<%=basePath%>/plugins/validation/jquery.validate.messages_zh.js"></script>

<!-- datatables相关css/js -->
<script type="text/javascript">
    if ('ontouchstart' in document.documentElement) document.write("<script src='<%=basePath%>/plugins/jquery/jquery.mobile.custom.min.js'>" + "<" + "/script>")
</script>

<script type="text/javascript" src="<%=basePath%>/plugins/dataTables/jquery.dataTables.js"></script>
<script type="text/javascript" src="<%=basePath%>/plugins/dataTables/jquery.dataTables.bootstrap.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/plugins/dataTables/extends/button/dataTables.buttons.js"></script>
<script type="text/javascript" src="<%=basePath%>/plugins/dataTables/extends/button/buttons.flash.js"></script>
<script type="text/javascript" src="<%=basePath%>/plugins/dataTables/extends/button/buttons.html5.js"></script>
<script type="text/javascript" src="<%=basePath%>/plugins/dataTables/extends/button/buttons.print.js"></script>
<script type="text/javascript" src="<%=basePath%>/plugins/dataTables/extends/button/buttons.colVis.js"></script>
<script type="text/javascript" src="<%=basePath%>/plugins/dataTables/extends/select/dataTables.select.js"></script>

<script type="text/javascript" src="<%=basePath%>/plugins/select2/js/select2.full.js"></script>
<script type="text/javascript" src="<%=basePath%>/plugins/select2/js/i18n/zh-CN.js"></script>

<script type="text/javascript" src="<%=basePath%>/static/js/pinyin.js"></script>

<%-- 一些抽离具体页面的通用js方法 --%>
<script type="text/javascript">
    // Select2 --> Tree
    (function($) {
        $.fn.select2tree = function (options) {
            var defaults = {
                language: "zh-CN",
                theme: "bootstrap"
            };
            var opts = $.extend(defaults, options);
            opts.templateResult = function (data, container) {
                if (data.element) {
                    var $wrapper = $("<span></span><span>" + data.text + "</span>");
                    var $element = $(data.element);
                    $(container).attr("val", $element.val());
                    if ($element.attr("parent")) {
                        $(container).attr("parent", $element.attr("parent"));
                    }
                    return $wrapper;
                } else {
                    return data.text;
                }
            };

            $(this).select2(opts).on("select2:open", open);
        };

        function moveOption(id) {
            if (id) {
                $(".select2-results__options li[parent=" + id + "]").insertAfter(".select2-results__options li[val=" + id + "]");
                $(".select2-results__options li[parent=" + id + "]").each(function() {
                    moveOption($(this.attr("val")));
                });
            } else {
                $(".select2-results__options li:not([parent])").appendTo(".select2-results__options ul");
                $(".select2-results__options li:not([parant])").each(function() {
                    moveOption($(this).attr("val"));
                })
            }
        }

        function switchAction(id, open) {
            $(".select2-results__options li[parent='" + id+ "']").each(function() {
                switchAction($(this).attr("val"), open);
            });
            if(open) {
                $(".select2-results__options li[val=" + id + "] span[class]:eq(0)").removeClass("glyphicon-chevron-right").addClass("glyphicon-chevron-down");
                $(".select2-results__options li[parent='" + id + "']").slideDown();
            } else {
                $(".select2-results__options li[val=" + id + "] span[class]:eq(0)").addClass("glyphicon-chevron-right").removeClass("glyphicon-chevron-down");
                $(".select2-results__options li[parent='" + id + "']").slideUp();
            }
        }

        function getLevel(id) {
            var level = 0;
            while ($(".select2-results__options li[parent][val='" + id + "']").length > 0) {
                id = $(".select2-results__options li[val='" + id + "']").attr("parent");
                level++;
            }
            return level;
        }

        function open() {
            setTimeout(function() {
                moveOption();

                $(".select-results__options li").each(function() {
                    var $this = $(this);
                    if ($this.attr("parent")) {
                        $(this).siblings("li[val=" + $this.attr("parent") + "]").find("span:eq(0)").addClass("glyphicon glyphicon-chevron-down switch")
                            .css({
                                "padding": "0 10px",
                                "cursor": "default"
                            });
                        $(this).siblings("li[val=" + $this.attr("parent") + "]").find("span:eq(1)").css("font-weight", "bold");
                    }
                    if (!$this.attr("style")) {
                        var paddingLeft = getLevel($this.attr("val")) * 2;
                        $("li[parent='" + $this.attr("parent") + "]").css("padding-left", paddingLeft + "em");
                    }
                });

                $(".switch").mousedown(function() {
                    switchAction($(this).parent().attr("val"), $(this).hasClass("glyphicon-chevron-right"));
                    event.stopPropagation();
                });

                $(".switch").mouseup(function() {
                    return false;
                });
            }, 0);
        }
    })(jQuery);

    /**
     * 绑定数据内容到指定的Select2控件
     *
     */
    function bindSelect2(ctrlName, url) {
        var control = $('#' + ctrlName);
        // 设置select2
        control.select2({
            allowClear: true,
            formatResult: formatResult,
            formatSelection: formatSelection,
            escapeMarkup: function (m) {
                return m;
            }
        });

        // 绑定Ajax内容
        $.getJSON(url, function (data) {
            control.empty();  // 清空下拉框
            $.each(data, function(i, item) {
                control.append("<option value='" + item.Value + "'>&nbsp;" + item.Text + "</option>");
            });
        });
    }

    /**
     * 初始化duallistbox
     * @param fetchUrl      获取后台选中数据的url
     * @param queryParam    参数
     * @param valueName     返回json中坐标属性（如id）
     * @param elementId     页面dualListBox对应的
     * @param callback      处理前端的dualListBox对象的回调函数
     */
    function initDualListBox(fetchUrl, queryParam, valueName, elementId, callback) {
        var paramData = {
            param: queryParam
        };

        $.ajax({
            url: fetchUrl,
            type: 'get',
            data: paramData,
            dataType: "json",
            async: false,  // 同步方式执行，可以成功对全局变量赋值
            success: function (objs) {
                $.each(objs, function (i, ele) {
                    var val = ele["" + valueName + ""];
                    $("#" + elementId + " option[value='" + val + "']").attr("selected", true);
                });

                try {
                    if (typeof(eval(callback)) === "function") {
                        callback();
                    }
                } catch (e) {
                }
                //dualListBox.bootstrapDualListbox('refresh', true);
            },
            error: function (e) {
                alert(e.msg);
            }
        });
    }
</script>