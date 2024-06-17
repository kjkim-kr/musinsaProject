package com.kj.musinsaproject.response;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.kj.musinsaproject.product.SimpleProduct;
import com.kj.musinsaproject.product.SimpleProductByBrand;
import com.kj.musinsaproject.product.SimpleProductByCategory;

import java.lang.reflect.Type;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
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
        gsonBuilder.registerTypeAdapter(SimpleProductByCategory.class, new SimpleProductByCategorySerializer());
        gsonBuilder.registerTypeAdapter(SimpleProductByBrand.class, new SimpleProductByBrandSerializer());
        gsonBuilder.registerTypeAdapter(SimpleProduct.class, new SimpleProductSerializer());

        gson = gsonBuilder.setPrettyPrinting().create();
    }

    public static String getSuccessJsonResponse() {
        return getSuccessJsonResponse(null);
    }
    public static String getSuccessJsonResponse(Object obj) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("status", 200);
        jsonObject.addProperty("message", "success");
        if(obj != null) {
            jsonObject.add("data", gson.toJsonTree(obj));
        }
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

    public static String getMinMaxProductJsonResponse(String categoryName,
                                                      List<SimpleProductByCategory> minProduct,
                                                      List<SimpleProductByCategory> maxProduct) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("카테고리", categoryName);
        jsonObject.add("최저가",
                gson.toJsonTree(minProduct, new TypeToken<List<SimpleProductByCategory>>() {}.getType()));
        jsonObject.add("최고가",
                gson.toJsonTree(maxProduct, new TypeToken<List<SimpleProductByCategory>>() {}.getType()));

        return jsonObject.toString();
    }

    public static String getMinTotalPriceProductJsonResponse(
            String brandName,
            List<SimpleProductByBrand> productList) {
        JsonObject jsonObject = new JsonObject();

        JsonObject minJsonObject = new JsonObject();
        minJsonObject.addProperty("브랜드", brandName);
        minJsonObject.add("카테고리",
                gson.toJsonTree(productList, new TypeToken<List<SimpleProductByBrand>>() {}.getType()));
        minJsonObject.addProperty("총액",
                NumberFormat.getNumberInstance().format(
                        productList.stream().mapToInt(SimpleProductByBrand::getPrice).sum()
                )
        );

        jsonObject.add("최저가", minJsonObject);
        return jsonObject.toString();
    }

    public static String getMinPriceProductOverCategoryJsonResponse(List<SimpleProduct> productList) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("목록",
                gson.toJsonTree(productList, new TypeToken<List<SimpleProduct>>() {}.getType()));
        jsonObject.addProperty("총액",
                NumberFormat.getNumberInstance().format(
                        productList.stream().mapToInt(SimpleProduct::getPrice).sum()
                )
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

class SimpleProductByCategorySerializer implements JsonSerializer<SimpleProductByCategory> {
    @Override
    public JsonElement serialize(SimpleProductByCategory src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("브랜드명", src.getBrandName());
        jsonObject.addProperty("가격",
                NumberFormat.getNumberInstance().format(src.getPrice()));
        return jsonObject;
    }
}

class SimpleProductByBrandSerializer implements JsonSerializer<SimpleProductByBrand> {
    @Override
    public JsonElement serialize(SimpleProductByBrand src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("카테고리", src.getCategoryName());
        jsonObject.addProperty("가격",
                NumberFormat.getNumberInstance().format(src.getPrice()));
        return jsonObject;
    }
}

class SimpleProductSerializer implements JsonSerializer<SimpleProduct> {
    @Override
    public JsonElement serialize(SimpleProduct src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("카테고리", src.getCategoryName());
        jsonObject.addProperty("브랜드", src.getBrandName());
        jsonObject.addProperty("가격",
                NumberFormat.getNumberInstance().format(src.getPrice()));
        return jsonObject;
    }
}