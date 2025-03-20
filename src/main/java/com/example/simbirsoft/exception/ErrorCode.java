package com.example.simbirsoft.exception;

public enum ErrorCode {
    NOT_FOUND,                // Ресурс не найден
    VALIDATION_ERROR,         // Ошибка валидации данных
    DATABASE_ERROR,           // Ошибка базы данных
    EXTERNAL_SERVICE_ERROR,   // Ошибка внешнего сервиса
    INTERNAL_SERVER_ERROR,    // Внутренняя ошибка сервера
    UNAVAILABLE               // Недоступность ресурса (например, нельзя забронировать билет)
}
