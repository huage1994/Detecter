package edu.tongji.sse;

import java.io.IOException;
import java.util.*;

/**
 * Created by huage on 2017/4/4.
 */
public class Shingling {
    public static void main(String[] args) throws IOException {
//        long start = System.currentTimeMillis();
//        Word word = new Word();
//        word.segment("input.txt","outputToken.txt"); //就应该返回这个结果---totalList
//        System.out.println("this is new ");
//        System.out.println(word.totalList.get(0).toString());
//        System.out.println(word.totalList.get(1).toString());
//        Shingling shingling =new Shingling();
//        List<Integer> result = shingling.generateHash(word.totalList.get(0), 35, 6);
//        List<Integer> result1 = shingling.generateHash(word.totalList.get(1), 35, 6);
//        System.out.println(result.size());
//        System.out.println(result1.size());
//        System.out.println("new new one");
//        System.out.println(result1.size());
//        System.out.println("winnowing");
//        System.out.println(shingling.winnowing(result,7).size());
//        System.out.println(shingling.winnowing(result1,7).size());
//        Compare.lCcompare(shingling.winnowing(result,7),shingling.winnowing(result1,7));
//        long end = System.currentTimeMillis();
//        System.out.println(end -start);
    }

    public List<Integer> generateHash(List<Integer> list,int base, int n){
        List<Integer> result = new ArrayList<>();
        Integer firstShingle = 0;
        Integer shingle = 0;
        Double pow;
        Double basePow = Math.pow(base, n);
        Integer basePowInt = basePow.intValue();

        if (list.size()>n) {
            for (int i = 0; i < n; i++) {
                 pow = Math.pow(base, n - i - 1);
                firstShingle += list.get(i) * pow.intValue();
            }
            result.add(firstShingle);

            for (int i =1;i<list.size()-n+1;i++){
                firstShingle = result.get(i-1);
                shingle = firstShingle * base - list.get(i - 1) * basePowInt + list.get(i + n - 1);
                result.add(shingle);
            }


        }
        else {
            System.out.println("list太小了");
        }
        return result;
    }

    public List<Integer> winnowing(List<Integer> list, int n){
        Map<Integer, Integer> mapResult = new HashMap<>();
        List<Integer> result = new ArrayList<>();
        Integer maxHash;
        Integer maxPos;
        for(int i = 0;i<list.size() - n+1;i++) {
            maxHash = list.get(i);
            maxPos = i;
            for (int j = i+1; j < i + n; j++) {
                Integer temp = list.get(j);
                if (temp>maxHash){
                    maxHash = temp;
                    maxPos = j;
                }
            }

            if (!mapResult.containsKey(maxPos)) {
                mapResult.put(maxPos, maxHash);
                result.add(maxHash);
            }

        }

        return result;
    }




    public void testmax(){
        System.out.println(Math.pow(1,2));

        int x1 = 2147483647;
        long z = 35;
        for (int i =2 ;z<x1;i++)
        {
            z = z*35;
            System.out.println(i);
        }
        System.out.println(x1);
    }



}
