package com.study.realworld.common;

import com.google.gson.JsonObject;
import com.study.realworld.secure.SecurityFunc;

import java.security.Key;

public class Func {
    public static String getErrorJson(Errors error, Object... objects) {
        JsonObject json = new JsonObject();

        json.addProperty("E", String.valueOf(error.getCode()));

        if (objects == null && objects.length == 0) {
            return json.toString();
        }

        for (int i = 0; i < objects.length; i = i + 2) {
            json.addProperty(String.valueOf(objects[i]), String.valueOf(objects[i + 1]));
        }
        System.out.println(error.name() + " - " + error.getCode() + " - " + error.getDesc());

        return json.toString();
    }

    public static String getResultJson(JsonObject result) {
        JsonObject json = new JsonObject();

        json.add("R", result);

        return result.toString();
    }
}
