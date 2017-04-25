package edu.tongji.sse;

import edu.tongji.sse.model.Line;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by huage on 2017/4/4.
 */
public class Shingling {
    public static HashMap<Integer, List<Integer>> hashMap = new HashMap<Integer, List<Integer>>();
    public static Integer z = 1;


    public List<Line> generateHash(List<Line> list,int base, int n){

        List<Line> result = new ArrayList<Line>();
        Long firstShingle = 0L;
        Long shingle = 0L;
        Double pow;
        Double basePow = Math.pow(base, n-1);
        Integer basePowInt = basePow.intValue();

        if (list.size()>n) {

            //TODO 判断n是否越界。 abs
            for (int i = 0; i < n; i++) {
                 pow = Math.pow(base, n - i - 1);
                firstShingle += list.get(i).lineHash * pow.intValue();
            }
            result.add(new Line(firstShingle,list.get(0).lineNum));

            for (int i =1;i<list.size()-n+1;i++){
                firstShingle = result.get(i-1).lineHash;
                shingle = (firstShingle - list.get(i - 1).lineHash * basePowInt) * base + list.get(i + n - 1).lineHash;
                result.add(new Line(shingle,list.get(i).lineNum));

            }


        }
        else {
            //TODO  删掉
            System.out.println("list太小了");

        }

        return result;
    }

    public List<Integer> generateWinnowHash(List<Integer> list, int n){

        List<Integer> result = new ArrayList<Integer>();
        Integer firstShingle = 0;
        Integer shingle = 0;


        if (list.size()>n) {
            for (int i = 0; i < n; i++) {

                firstShingle += list.get(i);
            }
            firstShingle /=n;

            result.add(firstShingle);
            List<Integer> tmp = hashMap.get(firstShingle);

            if (tmp!=null){
                tmp.add(z);
            }
            else {
                tmp = new ArrayList<Integer>();
                tmp.add(z);
                hashMap.put(firstShingle, tmp);
            }
            z++;
            for (int i =1;i<list.size()-n+1;i++){
                firstShingle = result.get(i-1);
                shingle = firstShingle * n - list.get(i - 1)  + list.get(i + n - 1);
                shingle /= n;
                result.add(shingle);

                tmp = hashMap.get(shingle);

                if (tmp!=null){
                    tmp.add(z);
                }
                else {
                    tmp = new ArrayList<Integer>();
                    tmp.add(z);
                    hashMap.put(shingle, tmp);
                }
                z++;
            }


        }
        else {
            System.out.println("list太小了");
            result.add(-1);
        }

        return result;
    }
    public List<Integer> winnowing(List<Integer> list, int n){
        Map<Integer, Integer> mapResult = new HashMap<Integer, Integer>();
        List<Integer> result = new ArrayList<Integer>();
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
