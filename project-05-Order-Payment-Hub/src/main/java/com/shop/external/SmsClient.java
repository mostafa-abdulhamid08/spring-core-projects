package com.shop.external;

public class SmsClient {

    private final String host;
    private boolean connected;

    public SmsClient(String host) {
        this.host = host;
    }

    public void connect() {
        connected = true;
        System.out.println("[sms] connected to " + host);
    }

    public void send(String message) {
        if (!connected) {
            throw new IllegalStateException("sms client is not connected");
        }
        System.out.println("[SMS] " + message);
    }

    public void disconnect() {
        connected = false;
        System.out.println("[sms] disconnected");
    }
}