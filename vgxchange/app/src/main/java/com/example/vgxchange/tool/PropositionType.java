package com.example.vgxchange.tool;

public enum PropositionType {

    BUYING("achat", "blue"),
    RENTAL("location", "green");

    private final String displayValue;
    private final String colorString;

    private PropositionType(String displayValue, String colorString) {
        this.displayValue = displayValue;
        this.colorString = colorString;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    public String getColorString() {
        return colorString;
    }

}
