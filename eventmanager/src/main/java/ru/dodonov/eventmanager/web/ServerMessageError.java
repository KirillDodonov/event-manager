package ru.dodonov.eventmanager.web;

import java.time.LocalDateTime;

public record ServerMessageError(
        String message,
        String detailedMessage,
        LocalDateTime errorTime
) {
}
