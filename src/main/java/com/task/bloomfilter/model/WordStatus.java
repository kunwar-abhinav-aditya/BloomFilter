package com.task.bloomfilter.model;

import com.task.bloomfilter.enums.PresenceStatus;
import lombok.Data;

@Data
public class WordStatus {

    private PresenceStatus presenceStatus;
    private String details;

    public WordStatus(PresenceStatus presenceStatus, String details) {
        this.presenceStatus = presenceStatus;
        this.details = details;
    }
}
