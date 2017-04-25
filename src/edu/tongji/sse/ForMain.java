package edu.tongji.sse;

import edu.tongji.sse.model.Line;
import edu.tongji.sse.model.SegmentAndLine;

import java.io.File;
import java.util.*;

/**
 * Created by huage on 2017/4/18.
 */
public class ForMain {

    public List<String> segmentToFilelist = new ArrayList<>();    //segment to which File
    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        ForMain secondMain = new ForMain();
        int n = 3;
        TreeMap<Long,List<SegmentAndLine>> lineHashMap = new TreeMap<>();  //这边换成了linked ，Treemap  就有序了。
        Integer linenum = 0;
        List<List<Line>> segList = secondMain.segmentAllFile("F:\\迅雷下载\\JDK-master");
//        List<List<Line>> segList = secondMain.segmentAllFile("C:\\Users\\huage\\Desktop\\wingsoft");
//        System.out.println(segList);
        Shingling shingling = new Shingling();
        //////////////////
        List<List<Line>> segAfterShingling = new ArrayList<>();
        for (int i =0;i<segList.size();i++) {
            segAfterShingling.add(shingling.generateHash(segList.get(i), 37, n));
        }
        System.out.println("------------");

        ////////////////////////
        List<List<Line>> nextShingling = new ArrayList<>();
        for (int i =0;i<segList.size();i++) {
            nextShingling.add(shingling.generateHash(segList.get(i), 37, n+1));
        }
        System.out.println("------------");

//////////////////////////////////////
        for (int i=0;i<segAfterShingling.size();i++){
            List<Line> segment = segAfterShingling.get(i);
            for (int j=0;j<segAfterShingling.get(i).size();j++){             //零的话 也可以处理。
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

        for (int i =0;i<segAfterShingling.size();i++){
            for (int j = 0;j<segAfterShingling.get(i).size();j++){
                if (j+n+1<segList.get(i).size()) {
                    Long verify = segAfterShingling.get(i).get(j).lineHash * 37L + segList.get(i).get(j + n).lineHash;
                    if (!verify.equals(nextShingling.get(i).get(j).lineHash)) {
                        System.out.println(verify);
                       System.out.println("wrong " + i + "hang0" + j);
                       throw new Exception();
                    }
                }
            }
        }
        System.out.println(45489459173343L*37+segList.get(0).get(1 + 3).lineHash);


        TreeMap<Long, List<SegmentAndLine>> nextTreeMap = secondMain.getNextShingle(lineHashMap, segList, 4);
        TreeMap<Long, List<SegmentAndLine>> next2TreeMap = secondMain.getNextShingle(nextTreeMap, segList, 5);
        TreeMap<Long, List<SegmentAndLine>> next3TreeMap = secondMain.getNextShingle(next2TreeMap, segList, 6);
        TreeMap<Long, List<SegmentAndLine>> nextnTreeMap = secondMain.getNextShingle(next3TreeMap, segList, 7);;
        for (int i=0;i<50;i++){
            System.out.println("第"+(i+8));
            nextnTreeMap = secondMain.getNextShingle(nextnTreeMap, segList, i+8);
        }
//        System.out.println(next2TreeMap);
//        System.out.println("fsdf:"+secondMain.segmentToFilelist.get(10703)+segList.get(10703).get(162).lineNum);
//        System.out.println("fsdf:"+secondMain.segmentToFilelist.get(10705)+segList.get(10705).get(20).lineNum);
        Integer z =0;
        Integer x= 0;
        Iterator iterator = next3TreeMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            List<Line> list = (List<Line>) entry.getValue();
            if (list.size()>1) {
                z++;
                x +=list.size();
            }
        }
        System.out.println(z);
        System.out.println(x);
    }

//        Iterator iterator = lineHashMap.entrySet().iterator();
//        while (iterator.hasNext()){
//        Map.Entry entry = (Map.Entry) iterator.next();
//        List<Line> list = (List<Line>) entry.getValue();
//
//
//    }

    public TreeMap<Long,List<SegmentAndLine>> getNextShingle(TreeMap<Long,List<SegmentAndLine>> inputHashMap, List<List<Line>>  segList,int n){

        System.out.println("n----n---------");
        TreeMap<Long, List<SegmentAndLine>> plus1LineMap = new TreeMap<>();
        Integer onlyOneKindofLine=0;
        Integer onlyOneKind=0;
        Iterator iterator = inputHashMap.entrySet().iterator();
        int[] zz = new int[segList.size()];

        while (iterator.hasNext()){
            Map.Entry entry = (Map.Entry) iterator.next();
            List<SegmentAndLine> sameLineList = (List<SegmentAndLine>) entry.getValue();
            Long key = (Long) entry.getKey();
            if (sameLineList.size()>1){
                onlyOneKind++;
                for (int i=0;i<sameLineList.size();i++){
                    onlyOneKindofLine++;
                    SegmentAndLine pluSameLineSegAndLine = sameLineList.get(i);
                    zz[pluSameLineSegAndLine.segNum]=1;
                    //TODO 这个地方是说明可以找出来哪个地方存在克隆的。
                    //System.out.println("存在重复"+secondMain.segmentToFilelist.get(sameLineList.get(i).segNum) + segList.get(sameLineList.get(i).segNum).get(sameLineList.get(i).lineNum).lineNum);
                    //TODO
                    //改成加n
                    if(pluSameLineSegAndLine.lineNum+n > segList.get(pluSameLineSegAndLine.segNum).size())
                    {
                        continue;
                    }
                    Long plusKey = key % 99999999;
                     plusKey = plusKey*14 + segList.get(pluSameLineSegAndLine.segNum).get(pluSameLineSegAndLine.lineNum+n-1).lineHash;
                    List<SegmentAndLine> pluSameLineList = plus1LineMap.get(plusKey);
                    if (pluSameLineList==null) {
                        pluSameLineList = new ArrayList<>();
                        pluSameLineList.add(pluSameLineSegAndLine);
                        plus1LineMap.put(plusKey, pluSameLineList);
                    }
                    else {
                        pluSameLineList.add(pluSameLineSegAndLine);
                    }
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
        return plus1LineMap;
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
                        segmentToFilelist.add(fileLists.get(i).toString());
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
