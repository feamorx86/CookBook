package com.feamorx.cookebook.parsers;

import java.util.HashMap;

/**
 * Created by x86 on 08.01.2017.
 */

public class ParserFactory {

    private static ParserFactory instance = null;

    private HashMap<Class, Serializer> serializers;

    private ParserFactory() {
        serializers = new HashMap<>();

        serializers.put(CategoryJsonSerializer.class, new CategoryJsonSerializer());
        serializers.put(IngredientJsonSerializer.class, new IngredientJsonSerializer());
        serializers.put(RecipeIngredientJsonSerializer.class, new RecipeIngredientJsonSerializer());
        serializers.put(RecipeJsonSerializer.class, new RecipeJsonSerializer());
        serializers.put(DataJsonSerializer.class, new DataJsonSerializer());
    }

    public static ParserFactory get() {
        if (instance == null) {
            instance = new ParserFactory();
        }
        return instance;
    }

    public Serializer forClass(Class clazz) {
        Serializer serializer = serializers.get(clazz);
        return serializer;
    }
}
