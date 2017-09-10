package net.javayum.patterns.springenvironment.domain;

public class PropertyDTO {

    private final String key;
    private final String value;

    public PropertyDTO(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
