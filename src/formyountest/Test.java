package formyountest;

import edu.tongji.sse.*;
import edu.tongji.sse.Word;
import edu.tongji.sse.model.Line;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huage on 2017/4/11.
 */
public class Test {

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        Filetraves filetraves =new Filetraves();
        Word word = new Word();
//        List<File> fileLists = filetraves.directoryAllList(new File("F:\\迅雷下载\\JDK-master"));
        List<File> fileLists = filetraves.directoryAllList(new File("C:\\Users\\huage\\Desktop\\myOwnTest"));
        if (fileLists!=null&&fileLists.size()>0) {
            List<List<Line>> totalresult = new ArrayList<List<Line>>();
            for (int i = 0; i < fileLists.size(); i++) {
                try {
                    List<List<Line>> tmp = word.segment(fileLists.get(i).toString(), "myout\\" + fileLists.get(i).getName() + "_output");
                    totalresult.addAll(tmp);
                } catch (Exception e) {

                    System.out.println(fileLists.get(i) + "-------------------------------------" + e.toString());
                    e.printStackTrace();
                }

            }
            Shingling shingling = new Shingling();
            for (int i=0;i<totalresult.size();i++) {
//                shingling.generateWinnowHash(shingling.generateHash(totalresult.get(i), 35, 6),3);

            }
            System.out.println(Shingling.hashMap.size());
            System.out.println(Shingling.z);
            System.out.println(Shingling.hashMap);


            //设置输出 那些存在

            long end = System.currentTimeMillis();
            System.out.println("time is " +(end-start));
            System.out.println(fileLists.size());
            Integer x = 0;
            for (int i=0;i<totalresult.size();i++) {
                x += totalresult.get(i).size();
            }
            System.out.println(x);
        }
    }
}
