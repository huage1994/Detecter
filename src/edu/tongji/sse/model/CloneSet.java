package edu.tongji.sse.model;

import java.util.List;

/**
 * Created by huage on 2017/5/2.
 */
public class CloneSet {
    public Integer id;
    public Integer cloneClassLength;
    public List<Clone> clones;

    public CloneSet(Integer id, Integer cloneClassLength, List<Clone> clones) {
        this.id = id;
        this.cloneClassLength = cloneClassLength;
        this.clones = clones;
    }


    @Override
    public String toString() {
        return "CloneSet{" +
                "id=" + id +
                ", cloneClassLength=" + cloneClassLength +
                '\n'+
                ", clones=" + clones +
                '}'
                +'\n';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCloneClassLength() {
        return cloneClassLength;
    }

    public void setCloneClassLength(Integer cloneClassLength) {
        this.cloneClassLength = cloneClassLength;
    }

    public List<Clone> getClones() {
        return clones;
    }

    public void setClones(List<Clone> clones) {
        this.clones = clones;
    }
}
