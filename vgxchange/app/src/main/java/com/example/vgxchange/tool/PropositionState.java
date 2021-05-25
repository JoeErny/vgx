package com.example.vgxchange.tool;

public enum PropositionState {

    WAITING("en attente", "yellow"),
    ACCEPTED("acceptée", "green"),
    DECLINED("refusée", "red"),
    TERMINATED("terminée", "grey");

    private final String displayValue;
    private final String colorString;

    private PropositionState(String displayValue, String colorHex) {
        this.displayValue = displayValue;
        this.colorString = colorHex;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    public String getColorString() {
        return colorString;
    }
}



