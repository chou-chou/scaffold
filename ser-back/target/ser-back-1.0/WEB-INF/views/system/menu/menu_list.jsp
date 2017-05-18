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
            <%--<div class="page-header"><!--  position-relative -->
                <form id="menuFrm" class="form-horizontal" action="menu/list.do" method="post" name="menuFrm">
                    <!-- class="form-inline" -->
                    <div class="form-group">
                        <label class="col-sm-2 control-label on-padding-left" style="float:left;" for="menuInfo"><!-- no-padding-right -->
                            菜单名称：
                        </label>
                        <div class="col-sm-6">
                            <input type="text" id="menuInfo" name="menuInfo" class="form-control" value="">
                        </div>
                        <div class="col-sm-4">
                            <button type="submit" class="btn btn-purple btn-sm">
                                <span class="ace-icon fa fa-search icon-on-right bigger-110"></span>
                                搜索
                            </button>
                        </div>
                    </div>
                </form>
            </div>--%>

            <div class="page-content">
                <%--<div class="row">--%>
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
                            <!-- 菜单树 -->
                            <div class="col-sm-4">
                                <div class="widget-box widget-color-blue2">
                                    <div class="widget-header">
                                        <h4 class="widget-title lighter smaller">菜单树</h4>
                                    </div>

                                    <div class="widget-body">
                                        <div class="widget-main padding-8">
                                            <ul id="menuTree"></ul>
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
    jQuery(function ($) {
        var sampleData = initiateDemoData();  //see below

        $('#menuTree').ace_tree({
            dataSource: sampleData['dataSource1'],
            multiSelect: true,
            cacheItems: true,
            'open-icon': 'ace-icon tree-minus',
            'close-icon': 'ace-icon tree-plus',
            'itemSelect': true,
            'folderSelect': false,
            'selected-icon': 'ace-icon fa fa-check',
            'unselected-icon': 'ace-icon fa fa-times',
            loadingHTML: '<div class="tree-loading"><i class="ace-icon fa fa-refresh fa-spin blue"></i></div>'
        });

        /**
         //Use something like this to reload data
         $('#tree1').find("li:not([data-template])").remove();
         $('#tree1').tree('render');
         */


        /**
         //please refer to docs for more info
         $('#tree1')
         .on('loaded.fu.tree', function(e) {
	})
         .on('updated.fu.tree', function(e, result) {
	})
         .on('selected.fu.tree', function(e) {
	})
         .on('deselected.fu.tree', function(e) {
	})
         .on('opened.fu.tree', function(e) {
	})
         .on('closed.fu.tree', function(e) {
	});
         */

        function initiateDemoData() {
            var tree_data = {
                'for-sale': {text: 'For Sale', type: 'folder'},
                'vehicles': {text: 'Vehicles', type: 'folder'},
                'rentals': {text: 'Rentals', type: 'folder'},
                'real-estate': {text: 'Real Estate', type: 'folder'},
                'pets': {text: 'Pets', type: 'folder'},
                'tickets': {text: 'Tickets', type: 'item'},
                'services': {text: 'Services', type: 'item'},
                'personals': {text: 'Personals', type: 'item'}
            }
            tree_data['for-sale']['additionalParameters'] = {
                'children': {
                    'appliances': {text: 'Appliances', type: 'item'},
                    'arts-crafts': {text: 'Arts & Crafts', type: 'item'},
                    'clothing': {text: 'Clothing', type: 'item'},
                    'computers': {text: 'Computers', type: 'item'},
                    'jewelry': {text: 'Jewelry', type: 'item'},
                    'office-business': {text: 'Office & Business', type: 'item'},
                    'sports-fitness': {text: 'Sports & Fitness', type: 'item'}
                }
            }
            tree_data['vehicles']['additionalParameters'] = {
                'children': {
                    'cars': {text: 'Cars', type: 'folder'},
                    'motorcycles': {text: 'Motorcycles', type: 'item'},
                    'boats': {text: 'Boats', type: 'item'}
                }
            };
            tree_data['vehicles']['additionalParameters']['children']['cars']['additionalParameters'] = {
                'children': {
                    'classics': {text: 'Classics', type: 'item'},
                    'convertibles': {text: 'Convertibles', type: 'item'},
                    'coupes': {text: 'Coupes', type: 'item'},
                    'hatchbacks': {text: 'Hatchbacks', type: 'item'},
                    'hybrids': {text: 'Hybrids', type: 'item'},
                    'suvs': {text: 'SUVs', type: 'item'},
                    'sedans': {text: 'Sedans', type: 'item'},
                    'trucks': {text: 'Trucks', type: 'item'}
                }
            };

            tree_data['rentals']['additionalParameters'] = {
                'children': {
                    'apartments-rentals': {text: 'Apartments', type: 'item'},
                    'office-space-rentals': {text: 'Office Space', type: 'item'},
                    'vacation-rentals': {text: 'Vacation Rentals', type: 'item'}
                }
            };
            tree_data['real-estate']['additionalParameters'] = {
                'children': {
                    'apartments': {text: 'Apartments', type: 'item'},
                    'villas': {text: 'Villas', type: 'item'},
                    'plots': {text: 'Plots', type: 'item'}
                }
            };
            tree_data['pets']['additionalParameters'] = {
                'children': {
                    'cats': {text: 'Cats', type: 'item'},
                    'dogs': {text: 'Dogs', type: 'item'},
                    'horses': {text: 'Horses', type: 'item'},
                    'reptiles': {text: 'Reptiles', type: 'item'}
                }
            };

            var dataSource1 = function (options, callback) {
                var $data = null;
                if (!("text" in options) && !("type" in options)) {
                    $data = tree_data;//the root tree
                    callback({data: $data});
                    return;
                }
                else if ("type" in options && options.type == "folder") {
                    if ("additionalParameters" in options && "children" in options.additionalParameters)
                        $data = options.additionalParameters.children || {};
                    else $data = {}//no data
                }

                if ($data != null)//this setTimeout is only for mimicking some random delay
                    setTimeout(function () {
                        callback({data: $data});
                    }, parseInt(Math.random() * 500) + 200);

                //we have used static data here
                //but you can retrieve your data dynamically from a server using ajax call
                //checkout examples/treeview.html and examples/treeview.js for more info
            }

            return {'dataSource1': dataSource1}
        }
    });

</script>

</body>
</html>
