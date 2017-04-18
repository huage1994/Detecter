package edu.tongji.sse;

import com.sun.corba.se.spi.orbutil.fsm.Input;

import javax.swing.*;
import java.io.*;
import java.security.Key;
import java.util.*;

/**
 * Created by huage on 2017/4/1.
 */
public class StackTest {

    public static void main(String[] args) throws IOException {
//        Stack<Integer> stack = new Stack<>();
//        int x  = 5;
//        stack.push(x);
//        stack.push(x);
//        stack.push(x);
//        System.out.println(stack.pop());
//        System.out.println(stack.peek());
//        List<String> list = new ArrayList<>();

//        List< List<String>> totalList = new ArrayList<>();
//        List<String> list = new ArrayList<>();
//        List<String> list1 = new ArrayList<>();
//        list.add("fsd");
//        list.add("222");
//        list1.add("f33");
//        totalList.add(list);
//        totalList.add(list1);
//
//        System.out.println(totalList.get(1));

//        List<String> list = new ArrayList<>();
//        list.add(null);
//        System.out.println(list.get(0));
//
//        for(int i =0;i<KeyWordAndType.neglectfulKeyword.length;i++){
//            System.out.println("final".equals(KeyWordAndType.neglectfulKeyword[i]));;
//            System.out.println(i);
//        }

            Set<String> set = new HashSet<String>();
            set.add("abc");
            set.add("ac");



//        Map<String,Integer> map = KeyWordAndType.map;
//        Integer x = map.get("test");
//        System.out.println(x);
//        int z =5;
//        System.out.println(" "+5);
//

//        Double x = Math.pow(2, 3);
//        System.out.println(x.intValue());
//
//        Scanner scanner = new Scanner(System.in);
//        String str = scanner.next();
//        String str1 = scanner.next();
//        System.out.println(str+str1);
//
//        List<Integer> list = new ArrayList<>();
//        list.add(2);
//        list.add(3);
//        list.set(1,5);
//        System.out.println(list);
//        for (int i=0;i<10;i++){
//            System.out.println(i);
//            if (i>5)
//            {
//                i=9;
//            }
//        }

        Filetraves filetraves =new Filetraves();
        List<File> fileLists = filetraves.directoryAllList(new File("F:\\迅雷下载\\JDK-master"));
        int line = 0;
        for (int i= 0;i<fileLists.size();i++){
            FileReader fileReader = new FileReader(fileLists.get(i));

            int x;
            while ((x = fileReader.read())!=-1){
                if ((char)x=='\n'){

                    line++;
                }
            }

        }
        System.out.println("line 长度"+line);
        System.out.println(fileLists.size());

//        Long re = new Long(1);
//        for (int i=0;i<18;i++){
//            re *=36;
//            System.out.println(re);
//            System.out.println(Integer.MAX_VALUE);
//        }

        Integer x =  2000000001;
        double z = x / 1000.0;
        System.out.println(z);

        Integer ww=1;
        for (int i=0;i<10;i++){
            ww *=36;
            System.out.println(ww);
        }
    }





}
