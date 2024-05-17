package com.sparta.schedulemanagement.dto;

import com.sparta.schedulemanagement.entity.Schedule;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Date;

@Getter
public class ScheduleResponseDto {
    private String title;
    private String contents;
    private String writer;
    private LocalDate date;

    public ScheduleResponseDto(Schedule schedule) {
        this.title = schedule.getTitle();
        this.contents = schedule.getContents();
        this.writer = schedule.getWriter();
        this.date = schedule.getDate();
    }
}
