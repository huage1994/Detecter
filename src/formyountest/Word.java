package formyountest;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by huage on 2017/3/28.
 */
public class Word {


    public List<List<String>> segment(String inputFileName, String outputFileName) throws IOException {
        int flag =0;  //用来做        if  有没有{}号判断
        List<List<String>> totalList = new ArrayList<List<String>>();
        List<String> list = new ArrayList<String>();
        List<String> tmpList = new ArrayList<String>();
        List<String> smallist =new ArrayList<String>();
        Stack<Integer> stack = new Stack<Integer>();
        long start = System.currentTimeMillis();
        Lexer lexer = new Lexer(inputFileName);
        String lastword = null;
        String word = null;
        int storageLine = 0;
        while (lexer.getReaderIsEnd() == false){

            do {
                lastword = word;
                word = lexer.scan();
                System.out.println(word);
            } while (word==null);

            switch (flag){
                case 0:
                    if (word.equals("{")) {


                        stack.push(lexer.line);
                        System.out.println(stack.size());
                    } else {
                        if (word.equals("}")) {

                            stack.pop();
                        }
                    }
                    if (stack.size()>1){
                        flag = 1;
                    }
                    break;
                case 1:

                    if (word.equals("{")) {
                        list.add("-------------------" + lexer.line + "{");       //增加一个list或者是一个栈来识别代码块   超过6行  sta
                        if (stack.size() == 1) {
                            tmpList = new ArrayList<String>();
                            smallist = new ArrayList<String>();
                        }
                        else {
                            tmpList.addAll(smallist);
                            tmpList.add("隔离");
                            smallist = new ArrayList<String>();

                        }

                        stack.push(lexer.line);

                    } else {
                        if (word.equals("}")) {


//                        if (stack.size()>0)
//                            System.out.println(stack.peek());

                            if (stack.size() == 2 && (lexer.line - stack.peek()) > 4) {
                                List<String> x = new ArrayList<String>();
                                x.addAll(tmpList);         //存的是指针 所以要替换一下
                                totalList.add(x);
                            }
                            else{

                            }
                            stack.pop();


                        }
                        else if (word.equals(";")){
                            tmpList.addAll(smallist);
                            tmpList.add("隔离");
                            tmpList.add(lexer.line+" ");
                            smallist = new ArrayList<String>();
                        }
                        else {
                            smallist.add(word);
                        }
                    }

                    break;





            }



        }

        System.out.println(lexer.line);
//        lexer.saveToken(list);
        long end = System.currentTimeMillis();
        System.out.println("timeis" + (end - start));

        List<String> outputList = new ArrayList<String>();
        for (int i = 0; i<totalList.size();i++){
            System.out.println("第"+i+"个");
            System.out.println(totalList.get(i).size());
            System.out.println(totalList.get(i));

            outputList.add(totalList.get(i).toString());

        }
        lexer.saveToken(outputList,outputFileName);
        System.out.println(totalList.size());
        System.out.println(lexer.line + "长度");
        return totalList;
    }

    public static void main(String[] args) throws IOException {
        Word word = new Word();
//        word.segment("C:\\Users\\huage\\Desktop\\wingsoft\\core\\action\\CommonAction.java","outputToken1.txt");
        word.segment("F:\\迅雷下载\\JDK-master\\src\\javax\\swing\\PopupFactory.java","outputToken1.txt");

    }
}
