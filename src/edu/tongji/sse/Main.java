package edu.tongji.sse;

import edu.tongji.sse.model.Line;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by huage on 2017/4/9.
 */
public class Main {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        Filetraves filetraves =new Filetraves();
        Word word = new Word();
        List<File> fileLists = filetraves.directoryAllList(new File("C:\\Users\\huage\\Desktop\\wingsoft"));
        if (fileLists!=null&&fileLists.size()>0){
            List<List<Line>> totalresult = new ArrayList<List<Line>>();
            for (int i=0;i<fileLists.size();i++) {
                try {
                    List<List<Line>> tmp = word.segment(fileLists.get(i).toString(), "myout\\" + fileLists.get(i).getName() + "_output");
                    totalresult.addAll(tmp);
                }
                catch (Exception e){
                    System.out.println(fileLists.get(i)+"-------------------------------------"+e.toString());
                    e.printStackTrace();
                }
            }

            System.out.println("new test"+totalresult.size());
            System.out.println("filenum"+ fileLists.size());
//            System.out.println(totalresult);

            List<List<Line>> tokenResult = new ArrayList<List<Line>>();
            Shingling shingling =new Shingling();
            for (int i=0;i<totalresult.size();i++)
            {
                List<Line> temp= shingling.generateHash(totalresult.get(i), 35, 3);
                if (temp!=null) {
                    tokenResult.add(temp);
                }
            }
            System.out.println(tokenResult.size());

            List<String> sout = new ArrayList<String>();
            int total = 0;
            for (int i=0;i<tokenResult.size()-1;i++)
                for (int j =i+1;j<tokenResult.size();j++){
                String result =Compare.lCcompare(tokenResult.get(i),tokenResult.get(j));
                    if(result!=null) {
                        sout.add(i + "和" + j + "之间存在克隆");
//                        j = tokenResult.size();
                        sout.add(result);
                    }
                    total++;
//                    System.out.println(total);
                }
            System.out.println(sout);
        }

        long end = System.currentTimeMillis();
        System.out.println(end - start);


    }
}
