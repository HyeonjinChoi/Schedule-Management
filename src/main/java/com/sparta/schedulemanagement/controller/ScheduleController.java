package com.sparta.schedulemanagement.controller;

import com.sparta.schedulemanagement.dto.ScheduleRequestDto;
import com.sparta.schedulemanagement.dto.ScheduleResponseDto;
import com.sparta.schedulemanagement.entity.Schedule;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ScheduleController {
    private final List<Schedule> schedules = new ArrayList<>();

    @PostMapping("/schedule")
    public ScheduleResponseDto createSchedule(@RequestBody ScheduleRequestDto requestDto) {
        // RequestDto -> Entity
        Schedule schedule = new Schedule(requestDto);
        // 날짜 저장
        schedule.setDate(new Date());
        // DB 저장
        schedules.add(schedule);
        // Entity -> ResponseDto
        ScheduleResponseDto responseDto = new ScheduleResponseDto(schedule);

        return responseDto;
    }

    @GetMapping("/schedule")
    public List<ScheduleResponseDto> getSchedules() {
        // Schedule List -> ResponseDto List
        List<ScheduleResponseDto> responseDtoList = schedules.stream()
                .map(ScheduleResponseDto::new)
                .toList();

        return responseDtoList;
    }
}
