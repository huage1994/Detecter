package edu.tongji.sse;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by huage on 2017/3/23.
 */
public class Filetraves {

    public List<File> lists = new ArrayList<File>();
    public List<File> directoryAllList(File dir){
        if (!dir.isDirectory()){
            System.out.println(dir+"不是目录或者不存在");
            return null;
        }
        else {
            directorylist(dir);
            return lists;
        }
    }
    public  void directorylist(File dir){

        File[] sonFiles = dir.listFiles();
        if(sonFiles!=null&&sonFiles.length>0) {

            for (File sonFile : sonFiles) {
                if (sonFile.isDirectory()) {
                    directorylist(sonFile);
                } else {
                    if (sonFile.getName().endsWith(".java")) {
                        System.out.println(sonFile.getName());
                        lists.add(sonFile);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Date date1 = new Date();
        Filetraves filetraves =new Filetraves();
        List<File> lists = filetraves.directoryAllList(new File("C:\\Users\\huage\\Desktop\\wingsoft"));
        Date date2 = new Date();
        System.out.println(date2.getTime() - date1.getTime());

        if (lists.size()>0) {
            System.out.println(lists.size()+"个文件");
        }
        else {
            System.out.println("无此类文件");
        }
        File file = new File("c:\\Program Files\\Git\\mingw64\\doc\\git-credential-manager\\README.md");
        System.out.println(file.isDirectory());
    }
}
