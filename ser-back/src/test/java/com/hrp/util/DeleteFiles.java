package com.hrp.util;

import java.io.File;

/**
 * DeleteFiles
 *
 * @author KVLT
 * @date 2017-05-09.
 */
public class DeleteFiles {
    public static void deleteFiles( File file ){

        if( file.isDirectory() ){
            // 如果是一个目录, 先查找这个目录下面的所有文件或目录
            // 再根据情况删除
            File[] files = file.listFiles();
            if( files.length == 0 ){
                //如果是一个空目录,直接删除
                file.delete();
            } else {
                // 如果不是空目录, 则递归
                for( File f : files ){
                    deleteFiles(f);
                    // 递归删除完之后, 再把本目录删除
                    f.getParentFile().delete();
                }
            }
        } else {
            // 如果是一个文件,直接进行删除
            file.delete();
        }
    }

    public static void main(String[] args) {
        File file = new File("E:\\JS桌面程序\\demo\\node_modules");
        deleteFiles(file);
    }
}
