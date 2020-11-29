package com.example.petition;

public class DateData {
    private String keyword;
    private Integer agree;
    private Integer total;
    private String RelateWord;

    public DateData(String keyword, Integer agree, Integer total, String relateWord) {
        super();
        this.keyword = keyword;
        this.agree = agree;
        this.total = total;
        this.RelateWord = relateWord;
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
    public String getRelateWord() {
        return RelateWord;
    }
}
