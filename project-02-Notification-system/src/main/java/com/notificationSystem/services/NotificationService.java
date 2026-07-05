package com.notificationSystem.services;

public interface NotificationService {
    void sendNotification(String message);
    String getType();
}
