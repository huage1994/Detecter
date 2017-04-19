package edu.tongji.sse;

import edu.tongji.sse.config.Config;
import edu.tongji.sse.model.Line;

import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 * Created by huage on 2017/3/28.
 */
public class Word {
    public static HashMap<Integer, Integer> equivHashMap = new HashMap<>();
    public static List<Integer> neglectList = new ArrayList<>();



    public  List<List<Line>> segment(String inputFileName, String outputFileName) throws IOException {
        Config config = new Config();
        int flag =0;  //用来做        if  有没有{}号判断
        List<List<Line>> totalList = new ArrayList<List<Line>>();;
        List<String> list = new ArrayList<String>();
        List<Line> tmpList = new ArrayList<Line>();
        List<Integer> lineTokenNumList = new ArrayList<Integer>();
        Stack<Integer> stack = new Stack<Integer>();

        Lexer lexer = new Lexer(inputFileName);
        int lastword = -1;
        int word = -1;
        int storageLine = 0;
        while (lexer.getReaderIsEnd() == false){

            do {
                lastword = word;
                word = lexer.scan();

            } while (word == -1&&!lexer.getReaderIsEnd());

            switch (flag){
                case 0:
                    if(word == KeyWordAndType.LEFTLARGEBRACKET){



                        stack.push(lexer.line);
//                        System.out.println(stack.size());
                    }
                    else if(word == KeyWordAndType.RIGHTLARGEBRACKET){
                        if (stack.size()>0) {
                            stack.pop();
                        }

                    }
                    if (stack.size()>1){
                        flag = 1;
                    }
                    break;
                case 1:


                    if(word == KeyWordAndType.LEFTLARGEBRACKET){
                        list.add("-------------------"+lexer.line+"{");       //增加一个list或者是一个栈来识别代码块   超过6行  sta
                        if(stack.size()==1) {
                            tmpList = new ArrayList<Line>();
                            lineTokenNumList = new ArrayList<Integer>();
                        }
//                        tmpList.add(KeyWordAndType.LEFTLARGEBRACKET);      // To do               left该不该存
                        else {
                            Integer tmp = 0;
                            if (lineTokenNumList!=null&&lineTokenNumList.size()>0){
                                Integer pow = 1;
                                for (int i=0;i<lineTokenNumList.size();i++)
                                {

                                    tmp += lineTokenNumList.get(i) *pow;
                                    pow *=36;
                                }

                                if (equivHashMap.get(tmp)!=null){
                                    tmp = equivHashMap.get(tmp);
                                }

                                if (!neglectList.contains(tmp)) {
                                    tmpList.add(new Line(tmp, lexer.line));
                                }

                            }
                            lineTokenNumList = new ArrayList<Integer>();

                        }
                        stack.push(lexer.line);

                    }
                    else if(word == KeyWordAndType.RIGHTLARGEBRACKET){

//                        tmpList.add(KeyWordAndType.RIGHTLARGEBRACKET);

//                        if (stack.size()>0)
//                            System.out.println(stack.peek());

                        if (stack.size()==2&&(lexer.line-stack.peek())>4&&tmpList.size()>3){
                            List<Line> x = new ArrayList<Line>();
                            x.addAll(tmpList);         //存的是指针 所以要替换一下
                            totalList.add(x);
                        }
//                        System.out.println(lexer.line+"si"+stack.size());
                        if (!stack.empty()) {
                            stack.pop();
                        }


                    }

                    else if (lastword==KeyWordAndType.SYSTEM&&word==KeyWordAndType.DOT)
                    {
                        storageLine = lexer.line;
                        flag = 3;
                    }
                    else if ((lastword == KeyWordAndType.LEFTSMALLBRACKET ) &&word == KeyWordAndType.ID)
                    {
                        flag = 2;

                    }
                    else if (word == KeyWordAndType.SEMICOLON){
                        Integer tmp = 0;
                        if (lineTokenNumList!=null&&lineTokenNumList.size()>0){
                            Integer pow = 1;
                            for (int i=0;i<lineTokenNumList.size();i++)
                            {

                                tmp += lineTokenNumList.get(i) *pow;
                                pow *=36;
                            }

                            if (equivHashMap.get(tmp)!=null){
                                tmp = equivHashMap.get(tmp);
                            }

                            if (!neglectList.contains(tmp)) {
                                tmpList.add(new Line(tmp, lexer.line));
                            }
                        }
                        lineTokenNumList = new ArrayList<Integer>();
                    }
                    else  if(word>=0){
                        //TO DO LIST
                        //先写这个 先别转token 先测试一波。  //这个地方要加一个 判断 常量后面有没有加号的  状态三。

                        lineTokenNumList.add(word);

                    }
                    break;

                case 2:
                    if (word==KeyWordAndType.COMMA){
                        lineTokenNumList.add(KeyWordAndType.COMMA);
                    }
                    if (word==KeyWordAndType.RIGHTSMALLBRACKET){
                        flag = 1;
                        lineTokenNumList.add(word);
                    }
                    break;


                //去除打印语句。
                case 3:
                    if (word==KeyWordAndType.OUT){
                        flag = 4;
                    }
                    else {
                        lineTokenNumList.add(KeyWordAndType.ID);
                        lineTokenNumList.add(KeyWordAndType.DOT);
                        lineTokenNumList.add(KeyWordAndType.ID);
                        flag=1;
                    }
                    break;
                case 4:
                    if (storageLine!=lexer.line)
                    {
                        lineTokenNumList.add(word);
                        flag = 1;
                    }
                    break;
            }



        }

//        lexer.saveToken(list);



//        List<String> outputList = new ArrayList<String>();
//        for (int i = 0; i<totalList.size();i++){
//            System.out.println("第"+i+"个");
//            System.out.println(totalList.get(i).size());
//            System.out.println(totalList.get(i));
//
//            outputList.add(totalList.get(i).toString());
//        }
//        lexer.saveToken(outputList,outputFileName);
//        System.out.println(lexer.line);
        return totalList;
    }

    public Integer getOnelineCode(String x) throws IOException {
        Lexer lexer = new Lexer(x,"xml");
        List<Integer> list = new ArrayList<>();
        while (lexer.getReaderIsEnd() == false){
            Integer word = lexer.scan();
            if (word>=0){
                list.add(word);
            }
        }
        Integer pow = 1;
        Integer tmp=0;
        for (int i=0;i<list.size();i++)
        {

            tmp += list.get(i) *pow;
            pow *=36;
        }

        return tmp;
    }

    public static void main(String[] args) throws IOException {
        Word word = new Word();
//        System.out.println(word.segment("C:\\Users\\huage\\Desktop\\myOwnTest\\CommonAction.java", "newtest.txt"));

        System.out.println(word.segment("F:\\迅雷下载\\JDK-master\\src\\javax\\xml\\bind\\annotation\\XmlSchemaTypes.java", "newtest.txt"));
    }
}
