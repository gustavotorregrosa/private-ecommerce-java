package dev.torregrosa.app.shared.auth;

public class LoginNotificationRun implements Runnable {

    private String message;

    public LoginNotificationRun(String message) {
        this.message = message;
    }

    @Override
    public void run() {
        System.out.println("Login notification: " + message);
       
    }
    
}
