package com.tirkisovkadyr.todolistv3;

public class JsonSavePOJO {
    private final String theme;
    private final String description;

    public JsonSavePOJO(String theme, String description) {
        this.theme = theme;
        this.description = description;
    }

    public String getTheme() {
        return theme;
    }

    public String getDescription() {
        return description;
    }
}
