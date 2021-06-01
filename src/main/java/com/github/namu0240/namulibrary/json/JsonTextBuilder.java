package com.github.namu0240.namulibrary.json;

public class JsonTextBuilder {

    private StringBuilder base;

    public JsonTextBuilder() {
        base = new StringBuilder("[\"\"");
    }

    public JsonTextBuilder add(String msg) {
        base
                .append(",")
                .append("{\"text\":\"")
                .append(msg)
                .append("\"}");
        return this;
    }

    public JsonTextBuilder add(String msg, JsonTextObj obj) {
        if(obj.addAble()) {
            base
                    .append(",")
                    .append("{\"text\":\"")
                    .append(msg)
                    .append("\"")
                    .append(obj.toJsonText());
        } else {
            base
                    .append(obj.toJsonText());
        }
        return this;
    }

    public JsonTextBuilder addKeyBind(JsonKeyBind keyBind) {
        base
                .append(",")
                .append(keyBind.toJsonText());
        return this;
    }

    public String build() {
        base.append("]");
        return base.toString();
    }

}

