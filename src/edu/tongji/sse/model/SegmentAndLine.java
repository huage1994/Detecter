package edu.tongji.sse.model;

/**
 * Created by huage on 2017/4/19.
 */
public class SegmentAndLine {

    public int segNum;
    public int lineNum;

    public SegmentAndLine(int segNum, int lineNum) {
        this.segNum = segNum;
        this.lineNum = lineNum;
    }

    @Override
    public String toString() {
        return "{"+
                "segNum=" + segNum +
                ", LineNum=" + lineNum +
                '}';
    }
}
