package com.example.bottomnavigation;

import java.util.StringTokenizer;

public class model {
    private String NAME;
    private String KEY;

    public model(String NAME, String KEY) {
        this.NAME = NAME;
        this.KEY = KEY;
    }

    public String getNAME() {
        return NAME;
    }

    public String getKEY() {
        return KEY;
    }
}
