package com.blaese.error.manager.util.enums;

public enum Level {

    ERROR(0, "Error"),
    WARNING(1, "Warning"),
    DEBUG(2, "Debug");

    private final int id;
    private final String description;

    Level(int id, String description) {
        this.id = id;
        this.description = description;
    }

    private int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public static Level toEnum(Integer id) {

        if (id == null) {
            return null;
        }

        for (Level level : Level.values()) {
            if (id.equals(level.getId())) {
                return level;
            }
        }

        throw new IllegalArgumentException("Id inválido: " + id);
    }
}