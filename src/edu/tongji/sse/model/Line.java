package edu.tongji.sse.model;

/**
 * Created by huage on 2017/4/16.
 */
public class Line {
    public Integer lineHash;
    public int lineNum;

    public Line(Integer lineHash, int lineNum) {
        this.lineHash = lineHash;
        this.lineNum = lineNum;
    }

    @Override
    public String toString() {
        return "{"+
                "Hash=" + lineHash +
                ", Num=" + lineNum +
                '}';
    }
}
