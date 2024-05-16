package com.sparta.schedulemanagement.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Schedule {
    private String title;
    private String contents;
    private String writer;
    private String password;
    private Date date;
}
