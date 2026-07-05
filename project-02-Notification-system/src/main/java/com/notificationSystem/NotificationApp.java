package com.notificationSystem;

import com.notificationSystem.beans.AppLogger;
import com.notificationSystem.config.AppConfig;
import com.notificationSystem.config.NotificationConfig;
import com.notificationSystem.services.NotificationManager;
import com.notificationSystem.services.NotificationService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class NotificationApp
{
    public static void main(String[] args)
    {
        System.out.println("========== Starting Spring Context ==========");
        var context = new AnnotationConfigApplicationContext(AppConfig.class);
        System.out.println("========== Context Ready ==========\n");

        // 1. @Bean Demo
        System.out.println("--- @Bean vs @Component Demo ---");
        NotificationConfig.AppStats stats = context.getBean(NotificationConfig.AppStats.class);
        stats.printStats();

        // 2. Autowiring Demo
        System.out.println("\n--- Autowiring Demo ---");
        NotificationManager manager = context.getBean(NotificationManager.class);
        System.out.println();
        manager.listAvailableTypes();

        // 3. @Primary Demo
        System.out.println("\n--- @Primary Demo ---");
        NotificationService primaryService = context.getBean(NotificationService.class);
        System.out.println("Primary service is: " + primaryService.getType());
        primaryService.sendNotification("Hello from @Primary!");

        // 4. Notify All — Map Injection
        System.out.println("\n--- Notify All (Constructor Injection + Map) ---");
        manager.notifyAll("System maintenance at 2 AM");

        // 5. Dynamic Selection
        System.out.println("\n--- Notify By Type (Dynamic) ---");
        manager.notifyByType("emailNotification", "Your order has been placed!");
        manager.notifyByType("smsNotification",   "OTP: 482910");
        manager.notifyByType("pushNotification",  "You have a new message");
        manager.notifyByType("unknownChannel",    "This won't work");

        // 6. Field Injection Demo
        System.out.println("\n--- Field Injection Demo ---");
        manager.notifyViaSMSFieldInjection("Field injection SMS test");

        // 7. Singleton Proof
        System.out.println("\n--- Singleton Proof ---");
        NotificationManager m1 = context.getBean(NotificationManager.class);
        NotificationManager m2 = context.getBean(NotificationManager.class);
        System.out.println("m1 == m2 ? " + (m1 == m2));

        AppLogger l1 = context.getBean(AppLogger.class);
        AppLogger l2 = context.getBean(AppLogger.class);
        System.out.println("logger1 == logger2 ? " + (l1 == l2));

        // 8. context.close() → @PreDestroy
        System.out.println("\n========== Closing Context ==========");
        context.close();




    }
}
