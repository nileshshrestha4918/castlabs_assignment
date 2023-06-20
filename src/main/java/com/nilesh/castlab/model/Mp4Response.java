package com.nilesh.castlab.model;

import java.util.List;

public class Mp4Response {

    private String status;
    private List<Mp4Box> data;
    private String message;

    public Mp4Response(String status, List<Mp4Box> data) {
        this.status = status;
        this.data = data;
    }

    public Mp4Response(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public List<Mp4Box> getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}
