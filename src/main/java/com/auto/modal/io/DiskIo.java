package com.auto.modal.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @Description: 磁盘及其文件夹操作
 * @Param:
 * @Auther: zl
 * @Date: 2020/2/13 12:50
 */
public class DiskIo {

    public static boolean createDir(String dirPath) {
        File dir = new File(dirPath);
        if (!dir.exists()) {
            if (dir.mkdirs())
                return true;
            else
                return false;
        } else
            return true;
    }

    public static boolean deleteDir(String dirPath) {
        File dir = new File(dirPath);
        if (!dir.exists())
            return true;
        else {
            if (dir.isFile())
                dir.delete();
            else
                for (File f : dir.listFiles())
                    deleteDir(f.getPath());

        }
        return dir.delete();
    }

    public static boolean removeDir(String srcDirPath, String desDirPath) {
        File sDir = new File(srcDirPath);
        File dDir = new File(desDirPath);
        String tDirPath = srcDirPath;
        if (!sDir.exists())
            return true;
        if (sDir.isFile()) {
            FileIo.removeFile(sDir.getPath(), dDir.getPath());
        } else {
            desDirPath = dDir.getPath() + srcDirPath.replace(tDirPath, "");
            File dir = new File(desDirPath);
            if (!dir.exists())
                createDir(dir.getPath());
            for (File f : sDir.listFiles()) {
                String desDirPath1 = desDirPath + f.separator + f.getName();
                removeDir(f.getPath(), desDirPath1);
            }
            deleteDir(srcDirPath);

        }
        return false;

    }

    public static boolean copyDir(String srcDirPath, String desDirPath) {
        File sDir = new File(srcDirPath);
        File dDir = new File(desDirPath);
        String tDirPath = srcDirPath;
        if (!sDir.exists())
            return true;
        if (sDir.isFile()) {
            copyFile(sDir.getPath(), dDir.getPath());
        } else {
            desDirPath = dDir.getPath() + srcDirPath.replace(tDirPath, "");
            File dir = new File(desDirPath);
            if (!dir.exists())
                createDir(dir.getPath());
            for (File f : sDir.listFiles()) {
                String desDirPath1 = desDirPath + f.separator + f.getName();
                copyDir(f.getPath(), desDirPath1);
            }
            //DeleteDir(srcDirPath);
        }
        return true;

    }

    public static ArrayList<String> getFileListFromDir(String srcDirPath) {
        //非递归实现文件遍历 使用文件夹queue
        File file = new File(srcDirPath);
        String filename = "";
        ArrayList<String> fileList = new ArrayList<String>();
        if (!file.exists()) {
            return null;

        } else {
            if (file.isFile()) {
                fileList.add(srcDirPath);
                return fileList;
            } else {
                //System.out.println(file.length());
                Queue<String> dirList = new LinkedList<String>();
                dirList.add(srcDirPath);
                String seekDirPath;
                while (!dirList.isEmpty()) {
                    seekDirPath = dirList.poll();
                    file = new File(seekDirPath);
                    for (File f : file.listFiles()) {
                        if (f.isFile()) {
                            fileList.add(f.getAbsolutePath());

                        } else {
                            dirList.add(f.getAbsolutePath());
                        }
                    }
                }

            }
        }
        System.out.println("文件数量：" + fileList.size());
        return fileList;


    }

    public static boolean copyFile(String oFilePath, String nFilePath) {
        File f = new File(oFilePath);
        String dir = nFilePath.replace(f.getName(), "");
        createDir(dir);
        File f1 = new File(nFilePath);
        if (!f.exists()) {
            return false;
        } else {

            try {
                Files.copy(f.toPath(), f1.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

}
