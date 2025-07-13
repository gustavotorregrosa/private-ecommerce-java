package dev.torregrosa.app.shared.socket;

import org.springframework.web.socket.WebSocketSession;


public class WebSocketFunction {

    public WebSocketSession currentSession;
    public String callingSessionID;
    public String messagePayload;
    public Runnable functionToBeRun;

    public WebSocketFunction(Runnable functionToBeRun) {
        this.functionToBeRun = functionToBeRun;
    }

    public void setParams(WebSocketSession currentSession, String callingSessionID, String messagePayload) {
        this.currentSession = currentSession;
        this.callingSessionID = callingSessionID;
        this.messagePayload = messagePayload;
    }

    public void run() {
        if (functionToBeRun != null && callingSessionID != null && currentSession != null && !callingSessionID.equals(currentSession.getId())) {
            functionToBeRun.run();
        } else {
            System.out.println("Function to be run is null.");
        }
    }


    
}
