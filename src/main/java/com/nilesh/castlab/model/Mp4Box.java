package com.nilesh.castlab.model;

import java.util.ArrayList;
import java.util.List;

public class Mp4Box {
    private String boxType;
    private int boxSize;
    private List<Mp4Box> childBoxes;

    public Mp4Box(String boxType, int boxSize) {
        this.boxType = boxType;
        this.boxSize = boxSize;
        this.childBoxes = new ArrayList<>();
    }

    public String getBoxType() {
        return boxType;
    }

    public void setBoxType(String boxType) {
        this.boxType = boxType;
    }

    public int getBoxSize() {
        return boxSize;
    }

    public void setBoxSize(int boxSize) {
        this.boxSize = boxSize;
    }

    public List<Mp4Box> getChildBoxes() {
        return childBoxes;
    }

    public void setChildBoxes(List<Mp4Box> childBoxes) {
        this.childBoxes = childBoxes;
    }

    public void addChildBox(Mp4Box childBox) {
        this.childBoxes.add(childBox);
    }

    @Override
    public String toString() {
        return "Mp4Box{" +
                "boxType='" + boxType + '\'' +
                ", boxSize=" + boxSize +
                ", childBoxes=" + childBoxes +
                '}';
    }
}
