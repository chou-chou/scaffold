package com.hrp.utils;

import com.hrp.pojo.TreeNode;
import com.hrp.utils.lang.StringUtil;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

/**
 * TreeUtil
 *
 * @author KVLT
 * @date 2017-03-23.
 */
public class TreeUtil {
    public static List<TreeNode> getNodeList(Map<String, TreeNode> nodelist) {
        List<TreeNode> tnlist = new ArrayList<TreeNode>();
        for (String id : nodelist.keySet()) {
            TreeNode node = nodelist.get(id);
            if (StringUtil.isEmpty(node.getParentId())) {
                tnlist.add(node);
            } else {
                if (nodelist.get(node.getParentId()).getNodes() == null)
                    nodelist.get(node.getParentId()).setNodes(new ArrayList<TreeNode>());
                nodelist.get(node.getParentId()).getNodes().add(node);
            }
        }
        return tnlist;
    }
}
