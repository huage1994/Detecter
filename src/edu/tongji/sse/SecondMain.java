package edu.tongji.sse;

import com.sun.org.apache.bcel.internal.generic.NEW;
import edu.tongji.sse.model.Line;
import edu.tongji.sse.model.SegmentAndLine;

import java.io.File;
import java.util.*;

/**
 * Created by huage on 2017/4/18.
 */
public class SecondMain {

    public List<Integer> segmentToFilelist = new ArrayList<>();    //segment to which File
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        SecondMain  secondMain = new SecondMain();

        TreeMap<Long,List<SegmentAndLine>> lineHashMap = new TreeMap<>();  //这边换成了linked ，Treemap  就有序了。
        Integer linenum = 0;
//        List<List<Line>> segList = secondMain.segmentAllFile("C:\\Users\\huage\\Desktop\\wingsoft");
        List<List<Line>> segList = secondMain.segmentAllFile("F:\\迅雷下载\\JDK-master");
        ////////////shingling 转换
        List<List<Line>> segAfterShingling = new ArrayList<>();
        Shingling shingling = new Shingling();
        for (int i=0;i<segList.size();i++){

            segAfterShingling.add(shingling.generateHash(segList.get(i), 37, 6));
        }


        /////////////

        for (int i=0;i<segAfterShingling.size();i++){
            List<Line> segment = segAfterShingling.get(i);
            for (int j=0;j<segAfterShingling.get(i).size();j++){
                SegmentAndLine segmentAndLine = new SegmentAndLine(i, j);
                List<SegmentAndLine> sameLineList = lineHashMap.get(segment.get(j).lineHash);
                if (sameLineList==null) {
                    sameLineList = new ArrayList<>();
                    sameLineList.add(segmentAndLine);
                    lineHashMap.put(segment.get(j).lineHash, sameLineList);
                }
                else {
                    sameLineList.add(segmentAndLine);
                }
                linenum++;
            }
        }

        System.out.println(linenum);
        System.out.println(lineHashMap.size());
        Integer onlyOneKindofLine=0;
        Integer onlyOneKind=0;
        Iterator iterator = lineHashMap.entrySet().iterator();

        int[] zz = new int[segList.size()];
        while (iterator.hasNext()){
            Map.Entry entry = (Map.Entry) iterator.next();
            List<SegmentAndLine> sameLineList = (List<SegmentAndLine>) entry.getValue();
            if (sameLineList.size()>1){
                onlyOneKind++;

                for (int i=0;i<sameLineList.size();i++){
                    onlyOneKindofLine++;
                    zz[sameLineList.get(i).segNum]=1;
                }
            }
        }

        System.out.println("line kind that more than one :"+onlyOneKind);
        System.out.println("line num whose kind that more than one :"+onlyOneKindofLine);
        int num=0;
        for (int i=0;i<zz.length;i++){
            if(zz[i] ==1)
            {
                num++;
            }
        }
        System.out.println("存在克隆的代码段"+num);
        System.out.println("总的代码段的数量"+segList.size());
        long end = System.currentTimeMillis();
        System.out.println("time is "+(end-start));

        System.out.println(Word.wordNum+"个有意义的词");
//        System.out.println(segAfterShingling);
    }



    public List<List<Line>> segmentAllFile(String url) {
        Filetraves filetraves = new Filetraves();
        Word word = new Word();
        List<File> fileLists = filetraves.directoryAllList(new File(url));
//        System.out.println(fileLists.size()+"个文件");
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
