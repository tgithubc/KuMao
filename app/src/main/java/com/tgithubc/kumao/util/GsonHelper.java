//package com.tgithubc.kumao.util;
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.google.gson.JsonSyntaxException;
//
//import java.lang.reflect.ParameterizedType;
//import java.lang.reflect.Type;
//
///**
// * Created by tc :)
// */
//public class GsonHelper {
//
//    private Gson mGson;
//
//    public static GsonHelper getInstance() {
//        return SingleHolder.INSTANCE;
//    }
//
//    private static class SingleHolder {
//        private static final GsonHelper INSTANCE = new GsonHelper();
//    }
//
//    private GsonHelper() {
//        mGson = new GsonBuilder()
//                .enableComplexMapKeySerialization()
//                .serializeNulls().setDateFormat("yyyy-MM-dd HH:mm:ss:SSS")
//                .setLenient()
//                .create();
//    }
//
//    public Gson getGson() {
//        return mGson;
//    }
//
//    public <T> T fromJson(String json, Class<T> classOfT) throws JsonSyntaxException {
//        return mGson.fromJson(json, classOfT);
//    }
//
//    public <T> T fromJson(String json, Type typeOfT) throws JsonSyntaxException {
//        return mGson.fromJson(json, typeOfT);
//    }
//
//    public String toJson(Object src) {
//        return mGson.toJson(src);
//    }
//
//    public static ParameterizedType type(final Class raw, final Type... args) {
//        return new ParameterizedType() {
//            public Type getRawType() {
//                return raw;
//            }
//
//            public Type[] getActualTypeArguments() {
//                return args;
//            }
//
//            public Type getOwnerType() {
//                return null;
//            }
//        };
//    }
//}