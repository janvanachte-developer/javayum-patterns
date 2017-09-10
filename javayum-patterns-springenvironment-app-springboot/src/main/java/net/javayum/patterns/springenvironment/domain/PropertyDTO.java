package net.javayum.patterns.springenvironment.domain;

public class PropertyDTO implements Property {

    private String key;
    private String value;

    public PropertyDTO(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public PropertyDTO() {
    }

    @Override
    public String getKey() {
        return key;
    }
    public void setKey(String key) { this.key = key;}

    @Override
    public String getValue() {
        return value;
    }
    public void setValue(String value) { this.value = value;}
}
