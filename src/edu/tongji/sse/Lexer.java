package edu.tongji.sse;

import com.sun.javafx.geom.AreaOp;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by huage on 2017/3/28.
 */
public class Lexer {
    public int test = 0;
    public int line = 1;
    private char peek = ' ';
    private char lastpeek = ' ';
    private  BufferedReader reader = null;
    private Boolean isEnd = false;

    private Boolean varibleOneOrSecond =true;
    private String lastVariable ="";
    private int lastline = 0;


    public Boolean getReaderIsEnd(){
        return this.isEnd;
    }

    public void saveToken(List<String> list,String outputFileName) throws IOException{
        FileWriter writer = new FileWriter(outputFileName);
        writer.write("token info");

        writer.write("\n");

        if (list.size()>0)
        {
            for (String x :list) {
                writer.write(x+"\t\t" +"\n");
            }

        }
        writer.flush();
        writer.close();
    }

    public Lexer(String inputFileName) throws IOException{
        reader = new BufferedReader(new FileReader(inputFileName));
    }
    public Lexer(String input,String xml)throws IOException{
        InputStream myIn=new ByteArrayInputStream(input.getBytes());
        reader = new BufferedReader(new InputStreamReader(myIn));
    }


    public void getChar() throws IOException{
        lastpeek = peek;
        peek = (char)reader.read();
        if((int)peek == 0xffff){
            this.isEnd = true;
        }
    }

    public Boolean getChar(char x) throws IOException {
        lastpeek = peek;
        getChar();
        if (peek!=x){
            return false;
        }
        peek = ' ';
        return true;
    }

    public int scan() throws IOException {
        for (; ; getChar()) {
            if (getReaderIsEnd())
            {
                break;
            }
            else if (peek == ' ' || peek == '\t' || peek == '\r')   //||peek == '\r'
                continue;
            else if (peek == '\n') {
                line = line + 1;
                continue;
            }
            else if(peek == '@'){
                reader.readLine();
                line = line + 1;
                continue;
            }
            else if (peek == '/') {
                getChar();
                if (peek=='*') {
                    for (; ; getChar()) {
                        if (peek == '\n')
                            line = line + 1;
                        if (peek == '/' && lastpeek=='*') {
                            getChar();
                            break;
                        }
                        if (getReaderIsEnd()){
                            break;
                        }
                        else
                            continue;
                    }
                }
                else if (peek=='/'){
                    reader.readLine();
                    line = line + 1;
                }
            } else
                break;
        }

        switch (peek) {
            case  '\\':
                getChar();
                getChar();
                return -1;
            case '[':
                getChar();
                return KeyWordAndType.LEFTMIDBRACKET;
            case ']':
                getChar();
                return KeyWordAndType.RIGHTMIDBRACKET;
            case '(':
                getChar();
                return  KeyWordAndType.LEFTSMALLBRACKET;
            case ')':
                getChar();
                return  KeyWordAndType.RIGHTSMALLBRACKET;
            case '.':
                getChar();
                return  KeyWordAndType.DOT;

            case ',':
                getChar();
                return  KeyWordAndType.COMMA;
            case ':':
                getChar();
                return  KeyWordAndType.COLON;
            case ';':
                getChar();                    //是不是需要返回分号还有待考证
                return KeyWordAndType.SEMICOLON;
            //
            case '{':
                getChar();
                return KeyWordAndType.LEFTLARGEBRACKET;
            case '}':
                getChar();
                return KeyWordAndType.RIGHTLARGEBRACKET;
            case '+':
                getChar();
                return  KeyWordAndType.PLUS;
            case '-':
                getChar();
                return KeyWordAndType.MIN;
            case '*':
                getChar();
                return KeyWordAndType.MUL;
            case '/':
                getChar();
                return  KeyWordAndType.DIV;
            case '=':
                if (getChar('=')) {

                    return KeyWordAndType.EQ;
                } else {
                    return KeyWordAndType.ASSIGNMENT;
                }
            case '>':
                if (getChar('=')) {

                    return KeyWordAndType.GTE;
                } else {
                    return KeyWordAndType.GT;
                }
            case '<':
                if (getChar('=')) {
                    ;
                    return KeyWordAndType.LT;
                } else {

                    return KeyWordAndType.LTE;
                }
            case '!':
                if (getChar('=')) {

                    return KeyWordAndType.UEQ;
                } else {

                    return KeyWordAndType.NOT;
                }
            case '&':
                if (getChar('&')){
                    return KeyWordAndType.AND;
                }
                else {
                    return KeyWordAndType.AND;
                }
            case '|':
                if (getChar('|')){
                    return KeyWordAndType.OR;
                }
                else {
                    return KeyWordAndType.OR;
                }


        }

        if (peek == '\"') {
            StringBuffer sb = new StringBuffer();

            sb.append(peek);
            do {

                if (peek=='\\')
                {
                    getChar();
                    getChar();
                }
                else {
                    getChar();
                }
                sb.append(peek);
            }while (peek!='\"'&&!getReaderIsEnd());
            getChar();
            //判断一行里面的多个变量。
            return KeyWordAndType.DIGIT;

        }

        if (peek == '\'') {
            StringBuffer sb = new StringBuffer();
            sb.append(peek);
            do {
                if (peek=='\\')
                {
                    getChar();
                    getChar();
                }
                else {
                    getChar();
                }
                sb.append(peek);
            }while (peek!='\''&&!getReaderIsEnd());
            getChar();
            return KeyWordAndType.DIGIT;
        }

        if (Character.isDigit(peek)) {
            int value = 0;
            do {
                value = 10 * value + Character.digit(peek, 10);
                getChar();
            } while (Character.isDigit(peek));


            return KeyWordAndType.DIGIT;          //值 位移？
        }


        if (Character.isLetter(peek)) {
            StringBuffer sb = new StringBuffer();

            /* 首先得到整个的一个分割 */
            do {
                sb.append(peek);
                getChar();
            } while (Character.isLetterOrDigit(peek) || peek == '_' || peek == '-');
            String s = sb.toString();
            //直接跳行 加快速度。
            if (s.equals("import")||s.equals("package")){
                reader.readLine();
                line = line + 1;
                return -1;
            }

            else {
                if (s.endsWith(KeyWordAndType.exceptionKeyword)) {
                    return -1;
                }
                for(int i =0;i<KeyWordAndType.neglectfulKeyword.length;i++){
                    if(s.equals(KeyWordAndType.neglectfulKeyword[i]))
                    {
                        return -1;
                    }
                }
                Integer isKeyWord = KeyWordAndType.map.get(s);
                if(isKeyWord!=null){
                    return isKeyWord.intValue();
                }

                if (lastline!=line) {
                    lastVariable = sb.toString();
                    lastline = line;
                    return KeyWordAndType.DIGIT;   //有时间加加好判断
                } else if (lastVariable.equals(sb.toString())) {

                    return KeyWordAndType.DIGIT;
                } else {
                    lastVariable = sb.toString();
                    return KeyWordAndType.SecondDIGIT;
                }

            }


        }

        if ((int) peek != 0xffff) {
            char x = peek;
            getChar();
            //TODO   % 等字符
//            System.out.println(x);
            return -1;   // 空格判断

        }

        return -2; //            null   改成-2

    }

}
