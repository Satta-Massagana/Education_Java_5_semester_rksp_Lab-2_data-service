package ru.baranov.data_service.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.baranov.user.api.EventsApi;
import ru.baranov.user.model.UserEvent;
import ru.baranov.data_service.data.UserEventRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserEventController implements EventsApi {

    private final UserEventRepository userEventRepository;

    @Override
    public ResponseEntity<List<UserEvent>> getAllUserEvents() {
        try {
            List<ru.baranov.data_service.data.UserEvent> entities = userEventRepository.findAll();
            log.info("Найдено записей в БД: {}", entities.size());

            List<UserEvent> dtos = entities.stream()
                    .map(this::entityToDto)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            log.error("Ошибка получения событий", e);
            return ResponseEntity.ok(Collections.emptyList());
        }
    }

    private UserEvent entityToDto(ru.baranov.data_service.data.UserEvent entity) {
        if (entity == null) return null;

        UserEvent dto = new UserEvent();
        dto.setId(entity.getId());
        dto.setEventType(entity.getEventType());

        if (entity.getEventTime() != null) {
            dto.setEventTime(entity.getEventTime().toInstant().atOffset(java.time.ZoneOffset.UTC));
        }

        return dto;
    }
}
