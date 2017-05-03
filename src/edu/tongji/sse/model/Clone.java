package edu.tongji.sse.model;

/**
 * Created by huage on 2017/5/2.
 */
public class Clone {
    String fileUrl;
    int start;
    int end;

    public Clone(String fileUrl, int start, int end) {
        this.fileUrl = fileUrl;
        this.start = start;
        this.end = end;
    }

    public boolean compare(Clone clone){
        if (this.fileUrl!=clone.fileUrl){
            return false;
        }
        else {
            if (this.start<clone.end &&((clone.end - this.start)>((this.end - this.start)/6))){
                return true;
            }
            else if (this.end<clone.start&&((clone.start - this.end)>((this.start - this.end)/6))){
                return true;
            }
            else {
                return false;
            }
        }
    }

    public static void main(String[] args) {
        Clone clone1 = new Clone("1",1,200);
        Clone clone2 = new Clone("1",2,199);
        Clone clone3 = new Clone("1",150,380);
        System.out.println(clone2.compare(clone2));
        System.out.println(clone2.compare(clone1));
        System.out.println(clone3.compare(clone1));
    }

    @Override
    public String toString() {
        return "Clone{" +
                "fileUrl='" + fileUrl + '\'' +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}
