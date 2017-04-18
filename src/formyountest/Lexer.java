package formyountest;

import edu.tongji.sse.KeyWordAndType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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


    public Boolean getReaderIsEnd(){
        return this.isEnd;
    }

    public void saveToken(List<String> list,String outputFileName) throws IOException{
        FileWriter writer = new FileWriter(outputFileName);
        writer.write("符号信息");
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

    public String scan() throws IOException {
        for (; ; getChar()) {
            if (peek == ' ' || peek == '\t' || peek == '\r')   //||peek == '\r'
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
                            line = line +1;
                        if (lastpeek == '*' &&peek== '/' )
                            break;
                        if(getReaderIsEnd())
                            break;
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
                return "\\";
            case '[':
                getChar();
                return "[";
            case ']':
                getChar();
                return "]";
            case '(':
                getChar();
                return  "(";
            case ')':
                getChar();
                return ")";
            case '.':
                getChar();
                return  ".";

            case ',':
                getChar();
                return  ",";
            case ':':
                getChar();
                return  ":";
            case ';':
                getChar();                    //是不是需要返回分号还有待考证
                return ";";
            //
            case '{':
                getChar();
                return "{";
            case '}':
                getChar();
                return "}";
            case '+':
                getChar();
                return "+";
            case '-':
                getChar();
                return "-";
            case '*':
                getChar();
                return "*";
            case '/':
                getChar();
                return  "/";
            case '=':
                if (getChar('=')) {

                    return "==";
                } else {
                    return "=";
                }
            case '>':
                if (getChar('=')) {

                    return ">=";
                } else {
                    return ">";
                }
            case '<':
                if (getChar('=')) {
                    ;
                    return "<=";
                } else {

                    return "<";
                }
            case '!':
                if (getChar('=')) {

                    return "==";
                } else {

                    return "!";
                }
            case '&':
                if (getChar('&')){
                    return "&";
                }
                else {
                    return "&&";
                }
            case '|':
                if (getChar('|')){
                    return "||";
                }
                else {
                    return"|";
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
            }while (peek!='\"'&&getReaderIsEnd()==false); ///就是这个地方。
            getChar();
            return sb.toString();   //有时间加加好判断
        }

        if (peek == '\'') {
            StringBuffer sb = new StringBuffer();
            sb.append(peek);
            do {

                getChar();
                if (peek=='\\')
                {
                    getChar();
                    getChar();
                }
                else {
                    getChar();
                }
                sb.append(peek);
            }while ((peek!='\'')&&getReaderIsEnd()==false);
            getChar();
            return sb.toString();
        }

        if (Character.isDigit(peek)) {
            int value = 0;
            do {
                value = 10 * value + Character.digit(peek, 10);
                getChar();
            } while (Character.isDigit(peek)&&getReaderIsEnd()==false);


            return value+"";          //值 位移？
        }


        if (Character.isLetter(peek)) {
            StringBuffer sb = new StringBuffer();

            /* 首先得到整个的一个分割 */
            do {
                sb.append(peek);
                getChar();
            } while ((Character.isLetterOrDigit(peek) || peek == '_' || peek == '-')&&getReaderIsEnd()==false);
            String s = sb.toString();
            //直接跳行 加快速度。
            if (s.equals("import")||s.equals("package")){
                System.out.println("package");
                reader.readLine();
                line = line + 1;
                return s;
            }

            else {
                if (s.endsWith(KeyWordAndType.exceptionKeyword)) {
                    return s;
                }
                for(int i =0;i<KeyWordAndType.neglectfulKeyword.length;i++){
                    if(s.equals(KeyWordAndType.neglectfulKeyword[i]))
                    {
                        return s;
                    }
                }
                Integer isKeyWord = KeyWordAndType.map.get(s);
                if(isKeyWord!=null){
                    return s;
                }

            }

            return  s;
        }

        if ((int) peek != 0xffff) {
            char x = peek;
            getChar();
            System.out.println(x);
            return x+"";   // 空格判断

        }

        return " "; //            null   改成-2

    }

}
