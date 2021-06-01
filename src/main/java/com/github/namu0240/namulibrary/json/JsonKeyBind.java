package com.github.namu0240.namulibrary.json;

public class JsonKeyBind implements JsonTextObj {

    private final StringBuilder base;

    public JsonKeyBind(String keyBind, String rgbColor) {
        base = new StringBuilder("{\"keybind\":\"")
                .append(keyBind)
                .append("\",\"color\":\"")
                .append(rgbColor)
                .append("\"");
    }

    public JsonKeyBind(String keyBind) {
        base = new StringBuilder("{\"keybind\":\"")
                .append(keyBind)
                .append("\"");
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
        return null;
    }

    @Override
    public boolean addAble() {
        return false;
    }
}
