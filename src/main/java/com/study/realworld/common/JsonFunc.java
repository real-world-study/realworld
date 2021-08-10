package com.study.realworld.common;

import com.google.gson.JsonObject;

public class JsonFunc {
    public static String getErrorJson(ErrorCode errorCode, Object... objects) {
        JsonObject json = new JsonObject();

        json.addProperty("E", String.valueOf(errorCode.getCode()));

        if (objects == null || objects.length == 0) {
            return json.toString();
        }

        for (int i = 0; i < objects.length; i = i + 2) {
            json.addProperty(String.valueOf(objects[i]), String.valueOf(objects[i + 1]));
        }
        System.out.println(errorCode.name() + " - " + errorCode.getCode() + " - " + errorCode.getDesc());

        return json.toString();
    }

    public static String getResultJson(JsonObject result) {
        JsonObject json = new JsonObject();

        json.add("R", result);

        return result.toString();
    }
}
