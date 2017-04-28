package com.hrp.utils.tree;

import com.hrp.utils.lang.StringUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TreeParser
 *
 * @author KVLT
 * @date 2017-04-17.
 */
public class TreeParser implements ITreeParser {

    private static String TREECLASS = "tree";
    private static String CHECKTREE = "treeCheck";
    private Map treeNodeMap = new HashMap();



    public TreeNode parse(Object data, int dataType) {
        List datas = (List) data;
        TreeNode root = new TreeNode();
        root.setAttribute("id", "-1");
        root.setAttribute("name", "\\全部\\");
        root.setAttribute("isParent", "true");

        if (datas != null && !datas.isEmpty()) {
            for(Object obj:datas){
                ITreeNode entity = (ITreeNode)obj;
                TreeNode item = new TreeNode(entity, dataType);
                treeNodeMap.put(entity.idGet(), item);
            }

            for(Object obj:datas){
                ITreeNode  entity = (ITreeNode)obj;

                TreeNode parent = (TreeNode) treeNodeMap.get(entity.parentIdGet());
                TreeNode treeNode = (TreeNode) treeNodeMap.get(entity.idGet());
                if (parent == null) {
                    root.addChild(treeNode);
                } else {
                    parent.addChild(treeNode);
                }
            }
        }
        return root;
    }

    public TreeNode parse(Object data, int dataType, String url, String classes, String target, String rel) {
        List datas = (List) data;
        TreeNode root = new TreeNode();
        root.setAttribute("id", "-1");
        root.setAttribute("text", "全部");


        if (datas != null && !datas.isEmpty()) {
            for(Object obj:datas){
                ITreeNode entity = (ITreeNode)obj;
                TreeNode item = new TreeNode(entity, dataType);
                item.setAttribute("url", url);
                item.setAttribute("target", target);
                item.setAttribute("rel", rel);
                if(classes.contains(CHECKTREE)) item.setCheck(TreeNode.IS_CHECKTREE);
                treeNodeMap.put(entity.idGet(), item);
            }

            for(Object obj:datas){
                ITreeNode  entity = (ITreeNode)obj;

                TreeNode parent = (TreeNode) treeNodeMap.get(entity.parentIdGet());
                TreeNode treeNode = (TreeNode) treeNodeMap.get(entity.idGet());
                if (parent == null) {
                    root.addChild(treeNode);
                } else {
                    parent.addChild(treeNode);
                }
            }
        }
        return root;
    }
    /**
     *
     * @param list 树实体list
     * @param url  一般树跳转的链接
     * @param classes 树的样式：有一般的树,带图标树和checkbox树(tree treeFolder treeCheck) 展开或收拢
     * @param target
     * @param rel
     * @param oncheckFun checkbox树时获取勾选值的JS方法
     * @return
     */
    public static String list2Tree(List list, String url, String classes, String target, String rel, String oncheckFun){
        return list2Tree(list, url, classes, target,  rel, oncheckFun, new TreeParser());
    }
    /**
     * @param list
     * @param parser
     * @return
     */
    public static String list2Tree(List list, String url, String classes, String target, String rel, String oncheckFun, ITreeParser parser){
        if(!StringUtil.isNotNullOrBlank(classes) || !classes.contains(TREECLASS)) classes = TREECLASS;
        TreeNode node = parser.parse(list, 0, url, classes, target, rel);
        StringBuffer treeData = new StringBuffer();
        treeData.append("<ul class='").append(classes).append("' ");
        if(classes.contains(CHECKTREE)) treeData.append(" oncheck='").append(oncheckFun).append("' ");
        treeData.append(" >");
        treeData.append(node.toBuffer(""));
        treeData.append("</ul>");
        return treeData.toString();
    }


    public static String list2Tree(List list, String url, String target, String rel){
        return list2Tree(list, url, target, rel, new TreeParser());
    }

    public static String list2Tree(List list, String url, String target, String rel, ITreeParser parser){
        StringBuffer treeData = new StringBuffer();
        String result = "";
        if (list != null && !list.isEmpty()) {
            treeData.append("[");
            for(Object obj:list){
                ITreeNode entity = (ITreeNode)obj;
                TreeNode item = new TreeNode(entity, url, target, rel, 0);
                treeData.append(item.toBuffer("")).append(",");
            }
            result = treeData.substring(0, treeData.length()-1)+"]";
        }

        System.out.println("treeData.toString()__"+result);

        return result;
    }

    public static String list2Tree(List list){
        return list2Tree(list, new TreeParser());
    }

    public static String list2Tree(List list, ITreeParser parser){
        StringBuffer treeData = new StringBuffer();
        String result = "";

        if (list != null && !list.isEmpty()) {
            treeData.append("[");
            for(Object obj:list){
                ITreeNode entity = (ITreeNode)obj;
                TreeNode item = new TreeNode(entity, 0);
                treeData.append(item.toBuffer("")).append(",");
            }
            result = treeData.substring(0, treeData.length()-1)+"]";
//			result = "[{id:'7',lvl:'1',name:'1',isleaf:'1',pk:'7',isParent:true,leaf:true},{id:'8',lvl:'1',name:'11',isleaf:'1',pk:'8',isParent:false,leaf:true},{id:'9',lvl:'1',name:'12',isleaf:'1',pk:'9',isParent:false,leaf:true},{id:'10',lvl:'1',name:'13',isleaf:'1',pk:'10',isParent:false,leaf:true}]";
        }
        System.out.println("treeData.toString()__"+result);
        return result.replaceAll("(\r\n|\r|\n|\n\r)", "");
    }

}
