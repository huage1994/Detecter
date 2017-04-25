package edu.tongji.sse.model;

import java.util.HashMap;
import java.util.List;

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
    public SegmentAndLine getNextLineSeg(){

        //TODO 这个地方  +1  不能用++；要不然结果完全相反。
        SegmentAndLine segmentAndLine = new SegmentAndLine(this.segNum, this.lineNum+1);
        return segmentAndLine;
    }


    public int compare(SegmentAndLine y){
        if (this.segNum>y.segNum) {
            return 1;
        }
        else if (this.segNum == y.segNum){
            if (this.lineNum>y.lineNum){
                return 1;
            }
            else if (this.lineNum==y.lineNum){
                return 0;
            }
            else {
                return -1;
            }
        }
        else {
            return -1;
        }
    }

    public int compareInList(List<SegmentAndLine> list){

        int low = 0;
        int high = list.size() - 1;
        while ((low <= high) && (low <= list.size() - 1)
                && (high <= list.size() - 1)) {
            int middle = (high + low) >> 1;
            int re = this.compare(list.get(middle));
            if (re==0) {
                return middle;
            } else if (re==-1) {
                high = middle - 1;
            } else {
                low = middle + 1;
            }
        }
        return -1;

    }



    @Override
    public String toString() {
        return "{"+
                "segNum=" + segNum +
                ", LineNum=" + lineNum +
                '}';
    }
}
