package com.kj.musinsaproject.response;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class JsonGenerator {
    public static final String ERROR_CODE = "errorCode";
    public static final String ERROR_MSG = "errorMessage";

    private static final Gson gson;

    private JsonGenerator(){}
    static {
        GsonBuilder gsonBuilder = new GsonBuilder();

        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateDeserializer());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer());

        gson = gsonBuilder.setPrettyPrinting().create();
    }

    public static String getSuccessJsonResponse() {
        return getSuccessJsonResponse(null);
    }
    public static String getSuccessJsonResponse(Object obj) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("status", 200);
        jsonObject.addProperty("message", "success");
        if(obj != null)
           jsonObject.addProperty("data", gson.toJson(obj));

        return jsonObject.toString();
    }

    public static String getErrorJsonResponse(ErrorCode errorCode) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty(ERROR_CODE, errorCode.getValue());
        jsonObject.addProperty(ERROR_MSG,
                switch(errorCode) {
                    case UNEXPECTED_ATTRIBUTE -> "invalid attribute";
                    case DELETE_FAILED -> "fail to delete data";
                    case INVALID_ARGUMENT -> "invalid argument";
                    case DATA_NOT_FOUND -> "data not found";

                    case FOREIGN_KEY_VIOLATION -> "foreign key violation";
                    case UNIQUE_KEY_VIOLATION -> "unique key violation";
                    case INTERNAL_SERVER_ERROR -> "internal server error";
                }
        );

        return jsonObject.toString();
    }
}

class LocalDateSerializer implements JsonSerializer <LocalDate> {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public JsonElement serialize(LocalDate localDate, Type srcType, JsonSerializationContext context) {
        return new JsonPrimitive(formatter.format(localDate));
    }
}

class LocalDateDeserializer implements JsonDeserializer <LocalDate> {
    @Override
    public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        return LocalDate.parse(json.getAsString(),
                DateTimeFormatter.ofPattern("yyyy-MM-dd").withLocale(Locale.KOREA));
    }
}

class LocalDateTimeSerializer implements JsonSerializer <LocalDateTime> {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public JsonElement serialize(LocalDateTime localDateTime, Type srcType, JsonSerializationContext context) {
        return new JsonPrimitive(formatter.format(localDateTime));
    }
}

class LocalDateTimeDeserializer implements JsonDeserializer <LocalDateTime> {
    @Override
    public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        return LocalDateTime.parse(json.getAsString(),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withLocale(Locale.KOREA));
    }
}