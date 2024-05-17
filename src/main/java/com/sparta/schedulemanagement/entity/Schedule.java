package com.sparta.schedulemanagement.entity;

import com.sparta.schedulemanagement.dto.ScheduleRequestDto;
import com.sparta.schedulemanagement.dto.ScheduleResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class Schedule {
    private Long id;
    private String title;
    private String contents;
    private String writer;
    private String password;
    private LocalDate date;

    public Schedule(ScheduleRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
        this.writer = requestDto.getWriter();
        this.password = requestDto.getPassword();
    }

    public void update(ScheduleRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
        this.writer = requestDto.getWriter();
        this.password = requestDto.getPassword();
    }
}
