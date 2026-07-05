package com.notificationSystem.services;

import com.notificationSystem.beans.AppLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class NotificationManager {
    // ======================================================
    // 1. Constructor Injection ✅ الأفضل
    // Spring بيحقن كل الـ implementations في Map تلقائياً
    // Key = Bean name | Value = object
    // ======================================================
    private final Map<String, NotificationService> allNotifications;
    // ======================================================
    // 2. Setter Injection — للـ Optional dependencies
    // ======================================================
    private AppLogger appLogger;
    // ======================================================
    // 3. Field Injection ❌ تجنبه في الكود الحقيقي
    // موجود هنا للمقارنة فقط
    // ======================================================
    @Autowired
    @Qualifier("smsNotification")
    private NotificationService smsService;


    @Autowired
    public NotificationManager(Map<String, NotificationService> allNotifications) {
        this.allNotifications = allNotifications;
        System.out.println("NotificationManager: created — found " +
                allNotifications.size() + " notification types");
    }

    @Autowired
    public void setAppLogger(AppLogger appLogger)
    {
        this.appLogger= appLogger;
        System.out.println("NotificationManager: AppLogger injected via Setter ✅");

    }
    public void notifyAll(String message) {
        appLogger.log("Sending to all channels: " + message);
        allNotifications.forEach((name, service) ->
        {
            System.out.println("Using bean "+ name);
            service.sendNotification(message);
        });
    }
public void notifyByType(String type, String message)
{
    NotificationService service = allNotifications.get(type);
    if(service == null)
    {
        System.out.println("NotificationManager: unknown type → " + type);
        return;
    }
    appLogger.log("Sending via " + type + ": " + message);
    service.sendNotification(message);
}

public void notifyViaSMSFieldInjection(String message)
{
        appLogger.log("Using Field Injection to send SMS:");
        smsService.sendNotification(message);
}

public void listAvailableTypes()
{
    System.out.println("Available notification types:");
    allNotifications.forEach((name, service)->

                System.out.println("  → " + name + " (" + service.getType() + ")")
    );
}


}
