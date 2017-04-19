package edu.tongji.sse;

import com.sun.org.apache.bcel.internal.generic.NEW;
import edu.tongji.sse.model.Line;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huage on 2017/4/18.
 */
public class SecondMain {

    public List<Integer> segmentToFilelist = new ArrayList<>();    //segment to which File
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        SecondMain  secondMain = new SecondMain();
        System.out.println(secondMain.segmentAllFile("F:\\迅雷下载\\JDK-master").size());

        long end = System.currentTimeMillis();
        System.out.println("time is "+(end-start));
    }



    public List<List<Line>> segmentAllFile(String url) {
        Filetraves filetraves = new Filetraves();
        Word word = new Word();
        List<File> fileLists = filetraves.directoryAllList(new File(url));

        List<List<Line>> totalresult = new ArrayList<List<Line>>();

        if (fileLists != null && fileLists.size() > 0) {
            for (int i = 0; i < fileLists.size(); i++) {
                try {
                    List<List<Line>> tmp = word.segment(fileLists.get(i).toString(), "myout\\" + fileLists.get(i).getName() + "_output");
                    totalresult.addAll(tmp);
                    for (int g = 0; g < tmp.size(); g++) {
                        segmentToFilelist.add(i);
                    }
                } catch (Exception e) {
                    System.out.println(fileLists.get(i) + "-------------------------------------" + e.toString());
                    e.printStackTrace();
                }

            }

        }
        else
        {
            System.out.println("文件无内容");
        }
        return totalresult;
    }
}
