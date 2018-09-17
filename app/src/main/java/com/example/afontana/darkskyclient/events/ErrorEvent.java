package com.example.afontana.darkskyclient.events;

public class ErrorEvent {
    private final String errorMessage;

    public ErrorEvent(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
