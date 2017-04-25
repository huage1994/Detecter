package edu.tongji.sse.model;

/**
 * Created by huage on 2017/4/25.
 */
public class Unit {
    public Long lineHash;
    public SegmentAndLine segmentAndLine;

    public Unit(Long lineHash, SegmentAndLine segmentAndLine) {
        this.lineHash = lineHash;
        this.segmentAndLine = segmentAndLine;
    }



    @Override
    public String toString() {
        return "Unit{" +
                "lineHash=" + lineHash +
                ", segmentAndLine=" + segmentAndLine +
                '}';
    }
}
