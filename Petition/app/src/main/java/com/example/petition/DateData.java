package com.example.petition;

public class DateData {
    private String keyword;
    private Integer agree;
    private Integer total;

    public DateData(String id, String keyword, Integer agree, Integer total) {
        super();
        this.keyword=keyword;
        this.agree = agree;
        this.total = total;
    }

    // GET 함수
    public String getKeyword() {
        return keyword;
    }
    public Integer getAgree() {
        return agree;
    }
    public Integer getTotal() {
        return total;
    }
}
