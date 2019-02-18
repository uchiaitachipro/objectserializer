package com.boat.objectserializer.reflect;

import java.lang.reflect.AccessibleObject;

public abstract class ReflectionAccessor {

    // the singleton instance, use getInstance() to obtain
    private static final ReflectionAccessor instance =  new PreJava9ReflectionAccessor();

    public abstract void makeAccessible(AccessibleObject ao);

    public static ReflectionAccessor getInstance() {
        return instance;
    }
}