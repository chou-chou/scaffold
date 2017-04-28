package com.hrp.utils.tree;

import com.hrp.utils.lang.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TreeNode
 *
 * @author KVLT
 * @date 2017-04-17.
 */
public class TreeNode {

    public static final String TREENODE_ATTRIBUTE_VALUE_FALSE = "0";

    public static final Object TREENODE_ATTRIBUTE_VALUE_TRUE = "1";

    public static final String TREENODE_ATTRIBUTE_NAME_CANSELECTED = "canselected";

    public static final String TREENODE_ATTRIBUTE_NAME_NAME = "name";

    public static final String TREENODE_ATTRIBUTE_NAME_ID = "id";

    public static final String LEAF_NODE = "leaf";

    public static final String CHECK_NODE = "checked";

    public static final int SIMPLE_TREE_NODE = 1;

    private static final int TYPE_NULL = -1;

    public static final Long NUN_CHECKED = new Long(0);
    public static final Long CAN_CHECKED = new Long(1);
    public static final Long BE_CHECKED  = new Long(2);
    public static final Long IS_CHECKTREE = new Long(1);
    private TreeNode parent = null;

    private List<TreeNode> children = new ArrayList<TreeNode>();

    @SuppressWarnings("unused")
    private int dataType = TreeNode.TYPE_NULL;

    private Map attributes = new HashMap();

    private Long check = new Long(0);



    public TreeNode() {
    }

    /**
     * @param data
     * @param dataType
     */
    public TreeNode(Object data, String url, String target, String rel, int dataType) {
        this.dataType = dataType;
        parseData(data, dataType);
        attributes.put("url", url);
        attributes.put("target", target);
        attributes.put("rel", rel);
    }

    public TreeNode(Object data, int dataType) {
        this.dataType = dataType;
        parseData(data, dataType);
    }

    /**
     * @param data
     * @param dataType
     */
    private void parseData(Object data, int dataType) {
        ITreeNode node = (ITreeNode)data;
        attributes = node.attributeForTreeGet();
    }

    /**
     *
     * @param translator
     * @param tree_node_name
     * @return
     */
	/*public String toBuffer(String text) {

		StringBuffer treeData = new StringBuffer();
		treeData.append("<li>");
		String id = "";
	    String url = "";
		String target = "";
		String rel = "";
		String name = "";
		for(Object key : attributes.keySet()){
			if(key.equals("id")) id = attributes.get(key).toString();
			if(key.equals("text")) text = attributes.get(key).toString();
			if(key.equals("url")) url = attributes.get(key).toString();
			if(key.equals("target")) target = attributes.get(key).toString();
			if(key.equals("rel")) rel = attributes.get(key).toString();
			if(key.equals("name")) name = attributes.get(key).toString();
		}

		if(!hasChildren()){
			if(!check.equals(IS_CHECKTREE)){
				treeData.append("<a href='").append(url).append("?id=").append(id).append("' ");
				treeData.append("target='").append(target).append("' ");
				treeData.append("rel='").append(rel).append("' ");
				treeData.append("title='").append(text).append("'>").append(text);
			}else{
				treeData.append("<a tname='").append(name).append("'");
				treeData.append(" tvalue='").append(id).append("' >").append(text);
			}
			treeData.append("</a>");
		}else{
			if(!check.equals(IS_CHECKTREE)){
				treeData.append("<a href='#' ");
				treeData.append("title='").append(text).append("' >").append(text);
			}else{
				treeData.append("<a tname='").append(name).append("'");
				treeData.append(" tvalue='").append(id).append("' >").append(text);
			}
			treeData.append("</a>");
			treeData.append("<ul>");
			int i=0;
			for(TreeNode node:children){
				i++;
				treeData.append(node.toBuffer(""));
			}
			treeData.append("</ul>");
		}

		treeData.append("</li>");

		return treeData.toString();
	}*/
    /**
     * 生成节点的json数据
     *
     * @return
     */
    public String toBuffer(String text) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("{");

        //设置节点的选择模式
        if(check.equals(BE_CHECKED))
            buffer.append(CHECK_NODE).append(":true,");
        else if(check.equals(CAN_CHECKED))
            buffer.append(CHECK_NODE).append(":false,");
        //设定属性
        for(Object key : attributes.keySet()){
            if(attributes.get(key)!=null&& StringUtil.isNotNullOrBlank(attributes.get(key).toString())){
                buffer.append((String)key);
                buffer.append(":'");
                buffer.append(attributes.get(key));
                //if("url".equals(key.toString())) buffer.append("?id=").append(attributes.get("id"));
                buffer.append("',");
            }
        }
        if(attributes.get("lvl")!=null){
            if(!attributes.get("lvl").equals("5")){
                buffer.append("isParent").append(":true,");
            }
        }
        if(attributes.get("isleaf")!=null){
            if(!attributes.get("isleaf").equals("1")){
                buffer.append("isParent").append(":true,");
            }else{
                buffer.append("isParent").append(":false,");
            }
        }
        //设定叶子属性
        if(!hasChildren()){
            buffer.append(LEAF_NODE).append(":true");
        }else{

            buffer.append("children:[");
            int i=0;
            for(TreeNode node:children){
                if(i>0) buffer.append(",");

                //设定子节点的选择模式
                if(node.getCheck().equals(NUN_CHECKED) && !getCheck().equals(NUN_CHECKED))
                    node.setCheck(getCheck());

                //添加子节点
                buffer.append(node.toBuffer(""));
                i++;
            }
            buffer.append("]");
        }
        buffer.append("}");
        return buffer.toString();
    }

    public void addChild(TreeNode treeNode) {
        treeNode.setParent(this);
        children.add(treeNode);
    }

    /**
     *
     * @return Returns the parent.
     */
    public TreeNode getParent() {
        return parent;
    }

    /**
     *
     * @param parent
     *            The parent to set.
     */
    public void setParent(TreeNode parent) {
        this.parent = parent;
    }

    /**
     *
     * @return
     */
    public boolean hasChildren() {
        Long state =new Long(1);
        if(attributes.get("state")!=null){
            if(attributes.get("state").equals(state)){
                return true;
            }
        }
        return !children.isEmpty();
    }

    /**
     *
     * @return
     */
    public List getChildren() {
        return children;
    }

    /**
     */
    public void setDataType(int type) {
        dataType = type;
    }

    /**
     */
    @SuppressWarnings("unchecked")
    public void disabled() {
        attributes.put(TREENODE_ATTRIBUTE_NAME_CANSELECTED,
                TREENODE_ATTRIBUTE_VALUE_FALSE);
    }

    /**
     */
    @SuppressWarnings("unchecked")
    public void enabled() {
        attributes.put(TREENODE_ATTRIBUTE_NAME_CANSELECTED,
                TREENODE_ATTRIBUTE_VALUE_TRUE);
    }

    /**
     *
     * @param name
     */
    @SuppressWarnings("unchecked")
    public void setName(String name) {
        attributes.put(TREENODE_ATTRIBUTE_NAME_NAME, name);
    }

    /**
     *
     * @param id
     */
    @SuppressWarnings("unchecked")
    public void setId(String id) {
        attributes.put(TREENODE_ATTRIBUTE_NAME_ID, id);
    }

    /**
     *
     * @return
     */
    public String getId() {
        return (String) attributes.get(TREENODE_ATTRIBUTE_NAME_ID);
    }

    /**
     * @param name
     * @param value
     */
    @SuppressWarnings("unchecked")
    public void setAttribute(String name, String value) {
        attributes.put(name, value);
    }

    public Long getCheck() {
        return check;
    }

    public void setCheck(Long check) {
        this.check = check;
    }

}
