package edu.tongji.sse.model;

/**
 * Created by huage on 2017/5/2.
 */
public class Token {
    public int tokenHash;
    public int line;

    public Token(int tokenHash, int line) {
        this.tokenHash = tokenHash;
        this.line = line;
    }

    @Override
    public String toString() {
        return "Token{" +
                "tokenHash=" + tokenHash +
                ", line=" + line +
                '}';
    }
}
