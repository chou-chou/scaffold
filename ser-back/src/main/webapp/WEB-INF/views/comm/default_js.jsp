<!-- 插件区js -->
<!--[if !IE]> -->
    <script src="<%=basePath%>/static/js/jquery-2.1.4.min.js"></script>
<!-- <![endif]-->

<!--[if IE]>
    <script src="<%=basePath%>/static/js/jquery-1.11.3.min.js"></script>
<![endif]-->

<script type="text/javascript">
    if ('ontouchstart' in document.documentElement)
        document.write("<script src='<%=basePath%>/static/js/jquery.mobile.custom.min.js'>" + "<" + "/script>");
</script>

<!--[if lte IE 8]>
    <script src="<%=basePath%>/static/js/excanvas.min.js"></script>
<![endif]-->

<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
    <script src="<%=basePath%>/static/js/html5shiv.min.js"></script>
    <script src="<%=basePath%>/static/js/respond.min.js"></script>
<![endif]-->

<script src="<%=basePath%>/static/js/bootstrap.min.js"></script>
<script src="<%=basePath%>/static/js/jquery-ui.custom.min.js"></script>
<script src="<%=basePath%>/static/js/jquery.ui.touch-punch.min.js"></script>
<script src="<%=basePath%>/static/js/jquery.sparkline.index.min.js"></script>
<script src="<%=basePath%>/static/js/jquery.flot.min.js"></script>
<script src="<%=basePath%>/static/js/jquery.flot.pie.min.js"></script>
<script src="<%=basePath%>/static/js/jquery.flot.resize.min.js"></script>

<!-- ace scripts -->
<script src="<%=basePath%>/static/js/ace-elements.min.js"></script>
<script src="<%=basePath%>/static/js/ace.min.js"></script>

<script src="<%=basePath%>/static/js/index.js"></script>

<!-- page specific plugin scripts -->
<script src="<%=basePath%>/static/js/jquery.dataTables.min.js"></script>
<script src="<%=basePath%>/static/js/jquery.dataTables.bootstrap.min.js"></script>
<%--<script src="<%=basePath%>/static/js/buttons.flash.min.js"></script>--%>
<script src="<%=basePath%>/static/js/buttons.html5.min.js"></script>
<script src="<%=basePath%>/static/js/buttons.print.min.js"></script>
<script src="<%=basePath%>/static/js/buttons.colVis.min.js"></script>
<script src="<%=basePath%>/static/js/dataTables.select.min.js"></script>

<script src="<%=basePath%>/plugins/zTree/3.5/jquery.ztree.all-3.5.min.js" ></script>
<script src="<%=basePath%>/plugins/layer/layer.js" ></script>

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

    // 绑定数据内容到指定的Select2控件
    function BindSelect2(ctrlName, url) {
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
</script>