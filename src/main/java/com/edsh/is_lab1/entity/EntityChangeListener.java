package com.edsh.is_lab1.entity;

import com.edsh.is_lab1.repository.ChangeHistoryRepository;
import com.edsh.is_lab1.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.*;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class EntityChangeListener {

    private final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private final ChangeHistoryRepository changeHistoryRepository;
    private final UserService userService;

    public EntityChangeListener(@Lazy ChangeHistoryRepository changeHistoryRepository,
                                @Lazy UserService userService) {
        this.changeHistoryRepository = changeHistoryRepository;
        this.userService = userService;
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
    }

    @PostPersist
    public void onPrePersist(ChangeListenedEntity entity) {
        saveChangeHistory(entity, ChangeHistory.Action.CREATE);
    }

    @PostUpdate
    public void onPreUpdate(ChangeListenedEntity entity) {
        saveChangeHistory(entity, ChangeHistory.Action.UPDATE);
    }

    @PreRemove
    public void onPreRemove(ChangeListenedEntity entity) {
        saveChangeHistory(entity, ChangeHistory.Action.DELETE);
    }

    private void saveChangeHistory(ChangeListenedEntity entity, ChangeHistory.Action action) {
        ChangeHistory changeHistory = new ChangeHistory();
        changeHistory.setEntityName(entity.getClass().getSimpleName());
        changeHistory.setEntityId(entity.getListenedId());
        changeHistory.setAction(action);
        changeHistory.setChangedBy(userService.getCurrentUser());

        try {
            changeHistory.setEntity(OBJECT_MAPPER.writeValueAsString(entity));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        changeHistoryRepository.save(changeHistory);
    }
}

