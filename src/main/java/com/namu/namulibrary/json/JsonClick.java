package com.namu.namulibrary.json;

public class JsonClick implements JsonTextObj {

    private final StringBuilder base;

    public JsonClick(ClickAction action, String value) {
        base = new StringBuilder(",\"clickEvent\":{\"action\":\"")
                .append(action.toString().toLowerCase())
                .append("\",\"value\":\"")
                .append(value)
                .append("\"}");
    }

    @Override
    public String toJsonText() {
        base.append("}");
        return base.toString();
    }

    @Override
    public JsonTextObj add(JsonTextObj obj) {
        if(obj.addAble()) base.append(obj.getBase());
        return this;
    }

    @Override
    public String getBase() {
        return base.toString();
    }

    @Override
    public boolean addAble() {
        return true;
    }

    public enum ClickAction {
        OPEN_URL, RUN_COMMAND, SUGGEST_COMMAND, SUGGEST_MESSAGE;
    }
}
