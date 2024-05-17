package com.sparta.schedulemanagement.controller;

import com.sparta.schedulemanagement.dto.ScheduleRequestDto;
import com.sparta.schedulemanagement.dto.ScheduleResponseDto;
import com.sparta.schedulemanagement.entity.Schedule;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ScheduleController {
    private final Map<Long, Schedule> schedules = new HashMap<>();

    @PostMapping("/schedule")
    public ScheduleResponseDto createSchedule(@RequestBody ScheduleRequestDto requestDto) {
        // RequestDto -> Entity
        Schedule schedule = new Schedule(requestDto);
        // Memo Max ID Check
        Long maxId = schedules.size() > 0 ? Collections.max(schedules.keySet()) + 1 : 1;
        schedule.setId(maxId);
        // 날짜 저장
        schedule.setDate(new Date());
        // DB 저장
        schedules.put(schedule.getId(), schedule);
        // Entity -> ResponseDto
        ScheduleResponseDto responseDto = new ScheduleResponseDto(schedule);

        return responseDto;
    }

    @GetMapping("/schedule")
    public List<ScheduleResponseDto> getSchedules() {
        // Schedule List -> ResponseDto List
        List<ScheduleResponseDto> responseDtoList = schedules.values().stream()
                .map(ScheduleResponseDto::new)
                .toList();

        return responseDtoList;
    }

    @PutMapping("/schedule/{id}")
    public Long updateSchedule(@PathVariable Long id, @RequestBody ScheduleRequestDto requestDto) {
        // 해당 일정이 DB에 존재하는지 확인
        if (schedules.containsKey(id)) {
            // 해당 일정 가져오기
            Schedule schedule = schedules.get(id);
            // schedule 수정
            schedule.update(requestDto);
            return id;
        } else {
            throw new IllegalArgumentException("해당 일정은 존재하지 않습니다.");
        }
    }
}
