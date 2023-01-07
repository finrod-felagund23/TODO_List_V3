package com.tirkisovkadyr.todolistv3;

public class TODO {
    private String theme;
    private String description;

    public TODO(String theme, String description) {
        this.theme = theme;
        this.description = description;
    }

    public TODO() { }

    public String getTheme() {
        return theme;
    }
    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
