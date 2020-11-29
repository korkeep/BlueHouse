package com.example.petition;

import java.util.Date;

public class Petition {
    private String id;
    private String begin;
    private String end;
    private String category;
    private String title;
    private StringBuilder keyword;
    private Integer agree;

    // GET 함수
    public String getID() {
        return id;
    }
    public String getBegin() {
        return begin;
    }
    public String getEnd() {
        return end;
    }
    public String getCategory() {
        return category;
    }
    public String getTitle() {
        return title;
    }
    public StringBuilder getKeyword() {
        return keyword;
    }
    public Integer getAgree() {
        return agree;
    }

    // SET 함수
    public void setID(String id) {
        this.id = id;
    }
    public void setBegin(String begin) {
        this.begin = begin;
    }
    public void setEnd(String end) {
        this.end = end;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setKeyword(StringBuilder keyword) {
        this.keyword = keyword;
    }
    public void setAgree(Integer agree) {
        this.agree = agree;
    }
}
