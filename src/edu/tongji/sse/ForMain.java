package edu.tongji.sse;

import edu.tongji.sse.model.*;

import java.io.File;
import java.util.*;

/**
 * Created by huage on 2017/4/18.
 */
public class ForMain {

    public List<String> segmentToFilelist = new ArrayList<>();    //segment to which File
    public static List<Unit> lastUnits = new ArrayList<>();
    public static List<Token> statistics = new ArrayList<>();

    public List<CloneSet> getCloneSets(int n,String url){
        List<CloneSet> cloneSets = new ArrayList<>();
        long start = System.currentTimeMillis();
        ForMain secondMain = new ForMain();
        HashMap<Long,List<SegmentAndLine>> lineHashMap = new HashMap<>();
        List<List<Line>> segList = secondMain.segmentAllFile(url);
        Shingling shingling = new Shingling();
        List<List<Line>> segAfterShingling = new ArrayList<>();
        for (int i =0;i<segList.size();i++) {
            segAfterShingling.add(shingling.generateHash(segList.get(i), 37, n));
        }

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
                Unit unit = new Unit(segment.get(j).lineHash, segmentAndLine);
                secondMain.lastUnits.add(unit);
            }
        }

        List<HashMap<Long, List<SegmentAndLine>>> mapList = new ArrayList<>();
        mapList.add(lineHashMap);  //第0个 是 3 shingling

        String resultReport ="Report:";
        String resultdetailReport ="detail:";
        String cloneset = "cloneset:\n";

        //开始迭代。
        int x = 0;
        for (int i = 1;lastUnits.size()!=0;i++){
            mapList.add(secondMain.getNextShingle(mapList.get(i-1),lastUnits, segList, n+i));
            x++;
        }
        System.out.println(x+"skh ");
        int clonenum;
        int cloneId = 0;
        for (int i = 0;i<mapList.size();i++){
            clonenum = 0;
            Iterator iterator = mapList.get(i).entrySet().iterator();
            while (iterator.hasNext())
            {
                Map.Entry entry = (Map.Entry) iterator.next();
                List<SegmentAndLine> list = (List<SegmentAndLine>) entry.getValue();
                if (list.size()>1) {

                    List<Clone> clones = new ArrayList<>();
                     Clone compareClone = new Clone("",-1,-1);
                    for (SegmentAndLine seg:list){
                        Clone clone = new Clone(secondMain.segmentToFilelist.get(seg.segNum),segList.get(seg.segNum).get(seg.lineNum).preLineNum,segList.get(seg.segNum).get(seg.lineNum+(i)+n-1).lineNum);
                        if (!clone.compare(compareClone)) {
                            clones.add(clone);
                        }
                        compareClone = clone;
                    }
                    if (clones.size()>1) {
                        cloneId++;
                        clonenum++;
                        CloneSet cloneSet = new CloneSet(cloneId, n + i, clones);
                        cloneSets.add(cloneSet);
                    }
                    //   n+i

                    ///抽空删掉。TODO
                    int length = 12;
                    if (i==length){
                        cloneset += clonenum+" <";
                        for (SegmentAndLine se :
                                list) {
                            cloneset += secondMain.segmentToFilelist.get(se.segNum) + " " + segList.get(se.segNum).get(se.lineNum).preLineNum + "行到"+(segList.get(se.segNum).get(se.lineNum+(i)+n-1).lineNum)+",";
                        }
                        cloneset += ">\n";
                    }
                }
            }
//            resultdetailReport += mapList.get(i).size() +"类" + "     "+x+"代码行数"+ "\n";
            statistics.add(new Token(i+n,clonenum));
        }
        System.out.println(resultReport);
        System.out.println(resultdetailReport);
        System.out.println(cloneset);
        System.out.println(lastUnits.size());
        return cloneSets;
    }

    public List<Token> getStatistics(){
        //要在 getCloneSets函数之后调用。
        return statistics;
    }
    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        ForMain secondMain = new ForMain();
        int n = 3;
        HashMap<Long,List<SegmentAndLine>> lineHashMap = new HashMap<>();  //这边换成了linked ，HashMap  就有序了。
        Integer linenum = 0;
        List<List<Line>> segList = secondMain.segmentAllFile("F:\\迅雷下载\\JDK-master");
//        List<List<Line>> segList = secondMain.segmentAllFile("C:\\Users\\huage\\Desktop\\wingsoft");
        Shingling shingling = new Shingling();
        //////////////////
        List<List<Line>> segAfterShingling = new ArrayList<>();
        for (int i =0;i<segList.size();i++) {
            segAfterShingling.add(shingling.generateHash(segList.get(i), 37, n));
        }
        System.out.println("------------");

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
                Unit unit = new Unit(segment.get(j).lineHash, segmentAndLine);
                secondMain.lastUnits.add(unit);
                linenum++;
            }
        }



        HashMap<Long, List<SegmentAndLine>> nextHashMap = secondMain.getNextShingle(lineHashMap, lastUnits,segList, 4);
        HashMap<Long, List<SegmentAndLine>> next2HashMap = secondMain.getNextShingle(nextHashMap,lastUnits, segList, 5);
        HashMap<Long, List<SegmentAndLine>> next3HashMap = secondMain.getNextShingle(next2HashMap,lastUnits, segList, 6);
        HashMap<Long, List<SegmentAndLine>> next4HashMap = secondMain.getNextShingle(next3HashMap,lastUnits, segList, 7);
        HashMap<Long, List<SegmentAndLine>> nextnHashMap = secondMain.getNextShingle(next4HashMap,lastUnits, segList, 8);
        String resultReport ="Report:";
        String resultdetailReport ="detail:";
        String cloneset = "cloneset:\n";
        List<HashMap<Long, List<SegmentAndLine>>> mapList = new ArrayList<>();
        mapList.add(nextnHashMap);
        for (int i = 1;i<412;i++){
            if (i==1)
            System.out.println(getTotal(mapList.get(i-1))+"sdfsdf"+mapList.get(i-1).size()+"ffffff"+getMoreThanOneTotal(mapList.get(i-1)));
            mapList.add(secondMain.getNextShingle(mapList.get(i-1),lastUnits, segList, 8+i));
            if (i==1)
                System.out.println(getTotal(mapList.get(i-1))+"sdfsdf"+mapList.get(i-1).size()+"ffffff"+getMoreThanOneTotal(mapList.get(i-1)));
        }
        int clonenum=0;
        for (int i = 1;i<412;i++){

            Iterator iterator = mapList.get(i).entrySet().iterator();
            while (iterator.hasNext())
            {
                Map.Entry entry = (Map.Entry) iterator.next();
                List<SegmentAndLine> list = (List<SegmentAndLine>) entry.getValue();
                if (list.size()>1) {
                    int length = 1;
                    if (i==length){
                        clonenum++;
                        cloneset += clonenum+" <";
                        for (SegmentAndLine se :
                                list) {
                            cloneset += secondMain.segmentToFilelist.get(se.segNum) + " " + segList.get(se.segNum).get(se.lineNum).lineNum + "行到"+(segList.get(se.segNum).get(se.lineNum+(i)+7).lineNum)+",";
                        }
                        cloneset += ">\n";
                    }
                }
            }
//            resultdetailReport += mapList.get(i).size() +"类" + "     "+x+"代码行数"+ "\n";

        }
        System.out.println(resultReport);
        System.out.println(resultdetailReport);
        System.out.println(cloneset);
        int nummm=0;
        System.out.println(secondMain.lastUnits.size());
        for (int i =0;i<secondMain.lastUnits.size();i++){
            Unit unit = secondMain.lastUnits.get(i);
            List<SegmentAndLine> list = nextnHashMap.get(unit.lineHash);
            if(list!=null&&list.size()>1){
                nummm++;
            }
        }
        System.out.println(nummm+"-------"+lastUnits);
        Integer z =0;
        Integer x= 0;
        Iterator iterator = lineHashMap.entrySet().iterator();
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
        long end = System.currentTimeMillis();
        System.out.println("time is "+(end-start));
    }


    public HashMap<Long,List<SegmentAndLine>> getNextShingle(HashMap<Long,List<SegmentAndLine>> inputHashMap,List<Unit> inputList, List<List<Line>>  segList,int n){

        System.out.println("n----n---------");
        HashMap<Long, List<SegmentAndLine>> plus1LineMap = new HashMap<>();
        Integer onlyOneKindofLine=0;
        Integer onlyOneKind=0;
        Iterator iterator = inputHashMap.entrySet().iterator();
        int[] zz = new int[segList.size()];
        List<Unit> outputUnits = new ArrayList<>();

        for (int i =0;i<inputList.size();i++){
            Unit unit = inputList.get(i);
            if(inputHashMap.get(unit.lineHash).size()>1){
                onlyOneKindofLine++;
                if(unit.segmentAndLine.lineNum+n > segList.get(unit.segmentAndLine.segNum).size())
                {
                    continue;
                }
                Long key = unit.lineHash;
                Long plusKey = key % 99999999;
                plusKey = plusKey*14 + segList.get(unit.segmentAndLine.segNum).get(unit.segmentAndLine.lineNum+n-1).lineHash;
                outputUnits.add(new Unit(plusKey, unit.segmentAndLine));
                List<SegmentAndLine> pluSameLineList = plus1LineMap.get(plusKey);
                if (pluSameLineList==null) {
                    pluSameLineList = new ArrayList<>();
                    pluSameLineList.add(unit.segmentAndLine);
                    plus1LineMap.put(plusKey, pluSameLineList);
                }
                else {
                    pluSameLineList.add(unit.segmentAndLine);
                }
            }
        }
        lastUnits = outputUnits;


        //消除
        Integer deletedNum=0;
        SegmentAndLine cacheSeg = new SegmentAndLine(-1, -1);
        int j = 0;

        System.out.println(inputHashMap.size());
        for (int i =0;i<outputUnits.size();i++){

            Unit unit = outputUnits.get(i);

            //TODO 可能顺序有问题。  最后一个没有处理
            if (plus1LineMap.get(unit.lineHash).size()>1){
                if (unit.segmentAndLine.compare(cacheSeg)==1)        //后一个 肯定比前一个的下一行 大或者相等。
                {
                    ///////////
                    while (cacheSeg.compare(inputList.get(j).segmentAndLine)==1){
                        j++;
                    }
                    Unit inputUnit = inputList.get(j);
                    if (cacheSeg.compare(inputUnit.segmentAndLine)==0){
                        List<SegmentAndLine> list = inputHashMap.get(inputUnit.lineHash);
                        //TODO 二分查找。 finish
                        //TODO 判断n+1的那个 finish
                        //TODO 判断 445的情况
                        int key = inputUnit.segmentAndLine.compareInList(list);
                        if (key==-1){
                            System.out.println("不存在把");
                        }
                        else
                        {
                            list.remove(key);
                            if (list.size()==0)
                            {
                                inputHashMap.remove(inputUnit.lineHash);
                            }
                            deletedNum++;
                        }
                    }

                    /////////
                }
                ////////////
                while (unit.segmentAndLine.compare(inputList.get(j).segmentAndLine)==1){
                    j++;
                }
                Unit inputUnit = inputList.get(j);
                //下面这个if是无用的  用来做异常处理
                if (unit.segmentAndLine.compare(inputUnit.segmentAndLine)==0){
                    List<SegmentAndLine> list = inputHashMap.get(inputUnit.lineHash);
                    //TODO 二分查找。
                    //TODO 判断 445的情况  用output的size和 list的size判断即可。

                    int key = inputUnit.segmentAndLine.compareInList(list);
                    if (key==-1){
                        System.out.println("不存在把");
                    }
                    else
                    {
                        list.remove(key);
                        if (list.size()==0)
                        {
                            inputHashMap.remove(inputUnit.lineHash);
                        }
                        deletedNum++;
                    }

                }
                cacheSeg = unit.segmentAndLine.getNextLineSeg();
            }

        }
//////////////////////新增加的
        if (j+1<inputList.size()) {
            Unit inputUnit = inputList.get(j + 1);
            if (cacheSeg.compare(inputUnit.segmentAndLine) == 0) {
                List<SegmentAndLine> list = inputHashMap.get(inputUnit.lineHash);
                //TODO 二分查找。 finish
                //TODO 判断n+1的那个 finish
                //TODO 判断 445的情况
                int key = inputUnit.segmentAndLine.compareInList(list);
                if (key == -1) {
                    System.out.println("不存在把");
                } else {
                    list.remove(key);
                    if (list.size() == 0) {
                        inputHashMap.remove(inputUnit.lineHash);
                    }
                    deletedNum++;
                }
            }
        }
        else {
            System.out.println("-fzzfz---------------");
        }
//////////////////////新增加的

        System.out.println("deleted is "+deletedNum);
        System.out.println(inputHashMap.size());
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

    public HashMap<Long,List<SegmentAndLine>> getNextShingle(HashMap<Long,List<SegmentAndLine>> inputHashMap, List<List<Line>>  segList,int n){

        System.out.println("n----n---------");
        HashMap<Long, List<SegmentAndLine>> plus1LineMap = new HashMap<>();
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

    public static int getTotal(HashMap<Long, List<SegmentAndLine>> HashMap){
        Iterator iterator = HashMap.entrySet().iterator();
        int x=0;
        while (iterator.hasNext())
        {
            Map.Entry entry = (Map.Entry) iterator.next();
            List<SegmentAndLine> list = (List<SegmentAndLine>) entry.getValue();

            x += list.size();
        }
        return  x;
    }
    public static int getMoreThanOneTotal(HashMap<Long, List<SegmentAndLine>> HashMap){
        Iterator iterator = HashMap.entrySet().iterator();
        int x=0;
        while (iterator.hasNext())
        {
            Map.Entry entry = (Map.Entry) iterator.next();
            List<SegmentAndLine> list = (List<SegmentAndLine>) entry.getValue();
            if (list.size()>1) {
                x += list.size();
            }
        }
        return  x;
    }
}
