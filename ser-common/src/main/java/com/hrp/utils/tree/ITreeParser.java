package com.hrp.utils.tree;

/**
 * ITreeParser
 *
 * @author KVLT
 * @date 2017-04-17.
 */
public interface ITreeParser {
    TreeNode parse(Object data, int dataType);
    TreeNode parse(Object data, int dataType, String url,String classes, String target, String rel);
}
