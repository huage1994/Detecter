package edu.tongji.sse;

import com.sun.org.apache.bcel.internal.generic.PUTFIELD;
import com.sun.org.apache.regexp.internal.RE;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by huage on 2017/3/28.
 */
public class KeyWordAndType {

    // 共计35个
        /** 运算符*/
        public static final int PLUS = 3;//加
        public static final int MIN = 4;//减
        public static final int MUL = 5;//乘
        public static final int DIV = 6;//除
        public static final int PERCENT = 36;
        public static final int AND = 7;//与
        public static final int OR = 8;//或
        public static final int NOT = 9;//非
        public static final int EQ = 11;//等于
        public static final int UEQ  = 14; //不等于

        public static final int LT = 12;//小于
        public static final int LTE = 12;//小等于
        public static final int GT = 13;//大于
        public static final int GTE = 13;//大于
    // 左括号， 右括号， . , [ ]
        public static final int LEFTSMALLBRACKET = 1;
        public static final int RIGHTSMALLBRACKET = 14;
        public static final int DOT =     10;
        public static final int LEFTMIDBRACKET = 2;
        public static final int RIGHTMIDBRACKET = 15;

        public static final int ASSIGNMENT = 90;

        public static final int COMMA = 34;
        public static final int COLON = 35;

        public static final int LEFTLARGEBRACKET = 32;
        public static final int RIGHTLARGEBRACKET = 33;

       /** 界符{ ',', ';', '{', '}', '(', ')', '[', ']', '_', ':', '.', '"' };*/


        /**标识符*/
        public static final int ID = 0;
        /** 常数*/
        public static final int DIGIT = 0;


        public static final int BREAK = 16;
        public static final int BYTE = 17;
        public static final int CASE = 18;
        //这个可以去掉,先这么写吧 try 和catch 应该去掉。。   stack大于1的时候 { } 应该录进去
        public static final int CATCH = 19;
        public static final int CHAR = 20;
        public static final int STRING = 20;
        public static final int CONTINUE = 21;
        public static final int ELSE = 22;
        public static final int FLOAT = 23;
        public static final int DOUBLE = 23;
        public static final int FOR = 24;
        public static final int INT = 25;
        public static final int LONG = 25;
        public static final int SHORT = 25;
        public static final int NEW = 26;
        public static final int IF = 27;
        public static final int RETURN = 28;
        public static final int SWITCH = 29;
        public static final int TRY = 30;
        public static final int WHILE = 31;

        public static final Map<String,Integer> map = new HashMap<String, Integer>(){{
            put("break", BREAK);
            put("byte", BYTE);
            put("case", CASE);
            put("catch", CATCH);
            put("char",CHAR);
            put("String",STRING);
            put("continue",CONTINUE);
            put("else",ELSE);
            put("float",FLOAT);
            put("double",DOUBLE);
            put("for",FOR);
            put("int",INT);
            put("long",LONG);
            put("short",SHORT);
            put("new",NEW);
            put("if",IF);
            put("return", RETURN);
            put("switch", SWITCH);
            put("try", TRY);
            put("while", WHILE);

            //方便鉴别
            put("System", SYSTEM);
            put("out", OUT);
        }};



    public static final String[] neglectfulKeyword = {
    "synchronized", "transient",  "volatile", "private", "protected",
    "public", "static", "final", "throws","interface", "assert","void"};

    public static final String exceptionKeyword = "Exception";


    //不常用
//    public static final int SYNCHRONIZED ;
//    public static final int TRANSIENT ;
//    public static final int VOLATILE ;

    // 需要忽略的
    //    public static final int PRIVATE = 29;
    //    public static final int PROTECTED = 30;
    //    public static final int PUBLIC = 31;
    //   public static final int STATIC = 34;
    //     public static final int FINAL = 15;
        // SYNCHRONIZED
    // VOLATILE



    //   THROWS
    // EXCEPTION

    //  ASSERT
    //
    //
    //
    // public static final int FINALLY = 16;
    //         public static final int VOID = 43;
  //     public static final int INSTANCEOF = 22;
    //  public static final int ENUM = 48;
    //      public static final int THROW = 39;
    //     public static final int INTERFACE = 24;

    //       public static final int DEFAULT = 10;
   // public static final int DO = 11;
    //  public static final int BOOLEAN = 2;

//      public static final int SUPER = 35;
    //       public static final int CLASS = 8;


    //System  out
    public static final int SYSTEM = -4;
    public static final int OUT = -5;
    public static final int SEMICOLON = -6;
}
