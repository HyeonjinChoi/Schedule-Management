package com.sparta.schedulemanagement.controller;

import com.sparta.schedulemanagement.dto.ScheduleRequestDto;
import com.sparta.schedulemanagement.dto.ScheduleResponseDto;
import com.sparta.schedulemanagement.entity.Schedule;
import org.springframework.cglib.core.Local;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
        schedule.setDate(LocalDate.now());
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
                .sorted(Comparator.comparing(ScheduleResponseDto::getDate).reversed())
                .toList();

        return responseDtoList;
    }

    @GetMapping("/schedule/{id}")
    public ScheduleResponseDto getSchedule(@PathVariable Long id) {
        // Entity -> ResponseDto
        ScheduleResponseDto responseDto = new ScheduleResponseDto(schedules.get(id));

        return responseDto;
    }

    @PutMapping("/schedule/{id}")
    public Long updateSchedule(@PathVariable Long id, @RequestBody ScheduleRequestDto requestDto) {
        // 해당 일정이 DB에 존재하는지 확인
        if (schedules.containsKey(id)) {
            // 해당 일정 가져오기
            Schedule schedule = schedules.get(id);
            // 비밀번호 검증
            if (!Objects.equals(schedule.getPassword(), requestDto.getPassword())){
                throw new IllegalArgumentException("비밀번호를 잘못 입력했습니다.");
            }
            // schedule 수정
            schedule.update(requestDto);
            return id;
        } else {
            throw new IllegalArgumentException("해당 일정은 존재하지 않습니다.");
        }
    }

    @DeleteMapping("/schedule/{id}")
    public Long deleteSchedule(@PathVariable Long id, @RequestParam String password) {
        // 해당 일정이 DB에 존재하는지 확인
        if (schedules.containsKey(id)) {
            // 해당 일정 가져오기
            Schedule schedule = schedules.get(id);
            // 비밀번호 검증
            if (!Objects.equals(schedule.getPassword(), password)){
                throw new IllegalArgumentException("비밀번호를 잘못 입력했습니다.");
            }
            // 해당 일정 삭제
            schedules.remove(id);
            return id;
        } else {
            throw new IllegalArgumentException("해당 일정은 존재하지 않습니다.");
        }
    }
}
