package edu.tongji.sse;

import edu.tongji.sse.model.Line;

import java.util.List;

/**
 * Created by huage on 2017/4/5.
 */
public class Compare {

    public static void main(String[] args) {
        Integer x =500000000;

        Integer y =500000000;
        System.out.println(x==y);
        System.out.println(x.hashCode());
         x = 10;
         y =10;
        System.out.println(y.hashCode());
        System.out.println(x.hashCode());


    }
    public static String lCcompare(List<Line> list1, List<Line> list2){

        int[][] array = new int[list1.size()][list2.size()];
        String result = null;
//        System.out.println(array);
        for (int i =0;i<list1.size();i++) {
            for (int j = 0; j < list2.size(); j++) {
                if (list1.get(i).lineHash.equals(list2.get(j).lineHash)) {
                    if (i==0||j==0) {
                        array[i][j] = 1;
                    }
                    else{
                        array[i][j] = array[i-1][j-1] +1;
                        if (array[i][j]>4){
                            result = list1.get(i-array[i][j]+1).lineNum+"-"+list1.get(i).lineNum+" : "+list2.get(j-array[i][j]+1).lineNum+"-"+list2.get(j).lineNum;
                        }
                    }

                }
//                System.out.print(array[i][j] + ",");
            }
//            System.out.println("");
        }
        return result;
    }

}
