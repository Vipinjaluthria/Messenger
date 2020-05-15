package com.example.bottomnavigation;

public class Messages {
    private String MESSAGE;
    private String TIMESTAMP;
    String FROM;

    public Messages(String MESSAGE, String TIMESTAMP, String FROM) {
        this.MESSAGE = MESSAGE;
        this.TIMESTAMP = TIMESTAMP;
        this.FROM = FROM;
    }

    public String getFROM() {
        return FROM;
    }

    public String getMESSAGE() {
        return MESSAGE;
    }

    public String getTIMESTAMP() {
        return TIMESTAMP;
    }
}
