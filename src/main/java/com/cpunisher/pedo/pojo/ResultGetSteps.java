package com.cpunisher.pedo.pojo;

public class ResultGetSteps {

    private String name;
    private StepInfo stepInfo;
    private String sessionId;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StepInfo getStepInfo() {
        return stepInfo;
    }

    public void setStepInfo(StepInfo stepInfo) {
        this.stepInfo = stepInfo;
    }
}
