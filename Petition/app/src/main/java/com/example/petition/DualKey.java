package com.example.petition;

import java.util.Objects;

public class DualKey {
    public String key1;
    public String key2;

    public DualKey(String key1, String key2){
        super();
        this.key1=key1;
        this.key2=key2;
    }

    public String getKey1(){return key1;}
    public String getKey2(){return key2;}
    public String getKeys(){return key1+key2;}

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;

        DualKey other = (DualKey) o;

        return other.key1 == this.key1 && other.key2 == this.key2;
    }

    @Override
    public int hashCode() {
        int h = (key1+key2).hashCode();
        return h; // import java.util.Objects; 가 필요함
    }
}

