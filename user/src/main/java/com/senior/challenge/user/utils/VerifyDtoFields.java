package com.senior.challenge.user.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

public class VerifyDtoFields {

    private VerifyDtoFields() {
    }

    //This gets all the dto fields, verify the existence of same name
    //in entity and if it`s present, sets the value in the entity

    public static void verifyNullAndAddToObject(Object dto, Object entity) {
        Method[] dtoMethods = dto.getClass().getDeclaredMethods();
        for (Method method : dtoMethods) {
            var type = method.getName().substring(0, 3);
            var main = method.getName().substring(3);
            String setMethod = "set" + main;
            if (type.equals("get")) {
                try {
                    if(Optional.ofNullable(method.invoke(dto)).isPresent()) {
                        var entityMethod = entity.getClass().getDeclaredMethod(setMethod, method.getReturnType());
                        entityMethod.invoke(entity, method.invoke(dto));
                    }
                } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {

                }
            }
        }
    }
}
