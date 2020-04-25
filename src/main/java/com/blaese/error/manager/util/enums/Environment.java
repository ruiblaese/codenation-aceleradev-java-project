package com.blaese.error.manager.util.enums;

public enum Environment {

    HOMOLOGATION(0, "Homologation"),
    PRODUCTION(1, "Production"),
    DEVELOPMENT(2, "Development");

    private final int id;
    private final String description;

    Environment(int id, String description) {
        this.id = id;
        this.description = description;
    }

    private int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public static Environment toEnum(Integer id) {

        if (id == null) {
            return null;
        }

        for (Environment environment : Environment.values()) {
            if (id.equals(environment.getId())) {
                return environment;
            }
        }

        throw new IllegalArgumentException("Id inválido: " + id);
    }
}