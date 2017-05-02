package edu.tongji.sse;

import edu.tongji.sse.config.Config;
import edu.tongji.sse.model.Line;
import edu.tongji.sse.model.Token;

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
    public static HashMap<Long, Long> equivHashMap = new HashMap<>();
    public static List<Long> neglectList = new ArrayList<>();
    public  static int wordNum;


    public  List<List<Line>> segment(String inputFileName, String outputFileName) throws IOException {
        Config config = new Config();
        int flag =0;  //用来做        if  有没有{}号判断
        List<List<Line>> totalList = new ArrayList<List<Line>>();;
        List<String> list = new ArrayList<String>();
        List<Line> tmpList = new ArrayList<Line>();
        List<Token> lineTokenNumList = new ArrayList<Token>();
        Stack<Integer> stack = new Stack<Integer>();

        Lexer lexer = new Lexer(inputFileName);
        int lastword = -1;
        int word = -1;
        int storageLine = 0;
        int preLineNum;
        while (lexer.getReaderIsEnd() == false){

            do {
                lastword = word;
                word = lexer.scan();

            } while (word == -1&&!lexer.getReaderIsEnd());
            wordNum++;
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
                            lineTokenNumList = new ArrayList<Token>();
                        }
//                        tmpList.add(KeyWordAndType.LEFTLARGEBRACKET);      // To do               left该不该存
                        else {
                            Long tmp = 0L;
                            if (lineTokenNumList!=null&&lineTokenNumList.size()>0){
                                Integer pow = 1;
                                for (int i=0;i<lineTokenNumList.size();i++)
                                {

                                    tmp += lineTokenNumList.get(i).tokenHash *pow;
                                    pow *=36;
                                }
                                tmp %= 999999;
                                tmp = Math.abs(tmp);
                                if (equivHashMap.get(tmp)!=null){
                                    tmp = equivHashMap.get(tmp);
                                }

                                if (!neglectList.contains(tmp)) {
                                    tmpList.add(new Line(tmp, lexer.line,lineTokenNumList.get(0).line));
                                }

                            }
                            lineTokenNumList = new ArrayList<Token>();

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
                        Long tmp = 0L;
                        if (lineTokenNumList!=null&&lineTokenNumList.size()>0){
                            Integer pow = 1;
                            for (int i=0;i<lineTokenNumList.size();i++)
                            {

                                tmp += lineTokenNumList.get(i).tokenHash *pow;
                                pow *=36;
                            }
                            tmp %= 999999;
                            tmp = Math.abs(tmp);
                            if (equivHashMap.get(tmp)!=null){
                                tmp = equivHashMap.get(tmp);
                            }

                            if (!neglectList.contains(tmp)) {
                                tmpList.add(new Line(tmp, lexer.line,lineTokenNumList.get(0).line));
                            }
                        }
                        lineTokenNumList = new ArrayList<Token>();
                    }
                    else  if(word>=0){
                        //TO DO LIST
                        //先写这个 先别转token 先测试一波。  //这个地方要加一个 判断 常量后面有没有加号的  状态三。

                        lineTokenNumList.add(new Token(word,lexer.line));

                    }
                    break;

                case 2:
                    if (word==KeyWordAndType.COMMA){
                        lineTokenNumList.add(new Token(KeyWordAndType.COMMA,lexer.line));
                    }
                    if (word==KeyWordAndType.RIGHTSMALLBRACKET){
                        flag = 1;
                        lineTokenNumList.add(new Token(word,lexer.line));
                    }
                    break;


                //去除打印语句。
                case 3:
                    if (word==KeyWordAndType.OUT){
                        flag = 4;
                    }
                    else {
                        lineTokenNumList.add(new Token(KeyWordAndType.ID,lexer.line));
                        lineTokenNumList.add(new Token(KeyWordAndType.DOT,lexer.line));
                        lineTokenNumList.add(new Token(KeyWordAndType.ID,lexer.line));
                        flag=1;
                    }
                    break;
                case 4:
                    if (storageLine!=lexer.line)
                    {
                        lineTokenNumList.add(new Token(word,lexer.line));
                        flag = 1;
                    }
                    break;
            }



        }



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

    public Long getOnelineCode(String oneLineCode) throws IOException {
        Lexer lexer = new Lexer(oneLineCode,"xml");
        int flag =1;  //用来做        if  有没有{}号判断
        List<List<Line>> totalList = new ArrayList<List<Line>>();;
        List<String> list = new ArrayList<String>();
        List<Line> tmpList = new ArrayList<Line>();
        List<Integer> lineTokenNumList = new ArrayList<Integer>();
        Stack<Integer> stack = new Stack<Integer>();

        int lastword = -1;
        int word = -1;
        int storageLine = 0;
        while (lexer.getReaderIsEnd() == false){

            do {
                lastword = word;
                word = lexer.scan();

            } while (word == -1&&!lexer.getReaderIsEnd());
            wordNum++;
            switch (flag){

                case 1:

                    if(word == KeyWordAndType.LEFTLARGEBRACKET){
                        list.add("-------------------"+lexer.line+"{");       //增加一个list或者是一个栈来识别代码块   超过6行  sta
                        if(stack.size()==1) {
                            tmpList = new ArrayList<Line>();
                            lineTokenNumList = new ArrayList<Integer>();
                        }
//                        tmpList.add(KeyWordAndType.LEFTLARGEBRACKET);      // To do               left该不该存
                        else {
                            Long tmp = 0L;
                            if (lineTokenNumList!=null&&lineTokenNumList.size()>0){
                                Integer pow = 1;
                                for (int i=0;i<lineTokenNumList.size();i++)
                                {

                                    tmp += lineTokenNumList.get(i) *pow;
                                    pow *=36;
                                }
                                tmp %= 999999;
                                tmp = Math.abs(tmp);
                                return tmp;

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
                        Long tmp = 0L;
                        if (lineTokenNumList!=null&&lineTokenNumList.size()>0){
                            Integer pow = 1;
                            for (int i=0;i<lineTokenNumList.size();i++)
                            {

                                tmp += lineTokenNumList.get(i) *pow;
                                pow *=36;
                            }
                            tmp %= 999999;
                            tmp = Math.abs(tmp);
                          return tmp;
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
        Long tmp =0L;
        Integer pow = 1;
        for (int i=0;i<lineTokenNumList.size();i++)
        {

            tmp += lineTokenNumList.get(i) *pow;
            pow *=36;
        }
        tmp %= 999999;
        tmp = Math.abs(tmp);
        return tmp;

    }

    public static void main(String[] args) throws IOException {
        Word word = new Word();
//        System.out.println(word.segment("C:\\Users\\huage\\Desktop\\myOwnTest\\CommonAction.java", "newtest.txt"));

        System.out.println(word.segment("input.txt", "newtest.txt"));
        System.out.println("--------------------------------------");
//        System.out.println(word.segment("C:\\Users\\huage\\Desktop\\wingsoft\\core\\action\\CrossFieldRebuildAction.java", "newtest.txt"));

        System.out.println(word.getOnelineCode(" switch (transferType);"));
    }
}
