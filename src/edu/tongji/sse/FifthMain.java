package edu.tongji.sse;

import edu.tongji.sse.model.CloneSet;

import java.util.List;

/**
 * Created by huage on 2017/5/2.
 */
public class FifthMain {
    public static void main(String[] args) {
        ForMain forMain = new ForMain();
        //"F:\\迅雷下载\\JDK-master"
        //"C:\\Users\\huage\\Desktop\\wingsoft"
        List<CloneSet> cloneSets = forMain.getCloneSets(3,"F:\\\\迅雷下载\\\\JDK-master");

        for (int i= cloneSets.size()-1;i>cloneSets.size()-100;i--){
            System.out.println(cloneSets.get(i));
        }
//        for (int i= 0;i<100;i++){
//            System.out.println(cloneSets.get(i));
//        }


        System.out.println("total is"+ cloneSets.size());
        int result = 0;
        for (int i=0;i<forMain.getStatistics().size();i++){
            result += forMain.getStatistics().get(i).line;
        }
        System.out.println(result);


    }
}
