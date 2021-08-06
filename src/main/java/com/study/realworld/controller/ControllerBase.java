package com.study.realworld.controller;

import com.google.gson.JsonObject;

public interface ControllerBase {
    default String getString(JsonObject jsonObject, String name) {
        return jsonObject.get(name).toString();
    }
}
