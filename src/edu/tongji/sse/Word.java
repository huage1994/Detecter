package edu.tongji.sse;

import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by huage on 2017/3/28.
 */
public class Word {


    public  void segment(String inputFileName,String outputFileName) throws IOException {
        int flag =0;  //用来做        if  有没有{}号判断
        List<List<Integer>> totalList = new ArrayList<>();
        List<String> list = new ArrayList<>();
        List<Integer> tmpList = new ArrayList<>();

        Stack<Integer> stack = new Stack<>();
        long start = System.currentTimeMillis();
        Lexer lexer = new Lexer(inputFileName);
        int lastword = -1;
        int word = -1;
        int storageLine = 0;
        while (lexer.getReaderIsEnd() == false){

            do {
                lastword = word;
                word = lexer.scan();
            } while (word == -1);

            switch (flag){
                case 0:
                    if(word == KeyWordAndType.LEFTLARGEBRACKET){

                        tmpList.add(KeyWordAndType.LEFTLARGEBRACKET);

                        stack.push(lexer.line);
                        System.out.println(stack.size());
                    }
                    else if(word == KeyWordAndType.RIGHTLARGEBRACKET){
                        tmpList.add(KeyWordAndType.RIGHTLARGEBRACKET);
                        stack.pop();
                    }
                    if (stack.size()>1){
                        flag = 1;
                    }
                    break;
                case 1:


                    if(word == KeyWordAndType.LEFTLARGEBRACKET){
                        list.add("-------------------"+lexer.line+"{");       //增加一个list或者是一个栈来识别代码块   超过6行  sta
                        if(stack.size()==1) {
                            tmpList = new ArrayList<>();
                        }
                        tmpList.add(KeyWordAndType.LEFTLARGEBRACKET);

                        stack.push(lexer.line);
                        System.out.println(stack.peek());
                    }
                    else if(word == KeyWordAndType.RIGHTLARGEBRACKET){

                        tmpList.add(KeyWordAndType.RIGHTLARGEBRACKET);

                        if (stack.size()>0)
                            System.out.println(stack.peek());

                        if (stack.size()==2&&(lexer.line-stack.peek())>4){
                            List<Integer> x = new ArrayList<>();
                            x.addAll(tmpList);         //存的是指针 所以要替换一下
                            totalList.add(x);
                        }
                        stack.pop();

                    }
                    else if (lastword==KeyWordAndType.SYSTEM&&word==KeyWordAndType.DOT)
                    {
                        storageLine = lexer.line;
                        tmpList.remove(tmpList.size()-1);
                        flag = 3;
                    }
                    else  {
            //TO DO LIST
             //先写这个 先别转token 先测试一波。  //这个地方要加一个 判断 常量后面有没有加号的  状态三。
                        if ((lastword == KeyWordAndType.LEFTSMALLBRACKET|| lastword == KeyWordAndType.COMMA) &&word == KeyWordAndType.ID)
                        {
                            flag = 2;

                        }
                        tmpList.add(word);
                    }
                    break;

                case 2:
                    if (word==KeyWordAndType.COMMA||word==KeyWordAndType.RIGHTSMALLBRACKET){
                        flag = 1;
                        tmpList.add(word);
                    }
                    break;


                //去除打印语句。
                case 3:
                    if (word==KeyWordAndType.OUT){
                        flag = 4;
                    }
                    else {
                        tmpList.add(KeyWordAndType.ID);
                        tmpList.add(KeyWordAndType.DOT);
                        tmpList.add(KeyWordAndType.ID);
                        flag=1;
                    }
                    break;
                case 4:
                    if (storageLine!=lexer.line)
                    {
                        tmpList.add(word);
                        flag = 1;
                    }
                    break;

            }



        }

        System.out.println(lexer.line);
//        lexer.saveToken(list);
        long end = System.currentTimeMillis();
        System.out.println("timeis" + (end - start));

        List<String> outputList = new ArrayList<>();
        for (int i = 0; i<totalList.size();i++){
            System.out.println("第"+i+"个");
            System.out.println(totalList.get(i).size());
            System.out.println(totalList.get(i));

            outputList.add(totalList.get(i).toString());
        }
        lexer.saveToken(outputList,outputFileName);
    }

    public static void main(String[] args) throws IOException {
        Word word = new Word();
        word.segment("C:\\Users\\huage\\Desktop\\wingsoft\\core\\action\\CommonAction.java","outputToken.txt");

    }
}
