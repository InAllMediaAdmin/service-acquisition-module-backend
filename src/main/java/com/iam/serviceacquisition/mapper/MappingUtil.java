package com.iam.serviceacquisition.mapper;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.similarity.LevenshteinDistance;

import java.lang.reflect.Method;
import java.util.List;

@Slf4j
public class MappingUtil {
    private static final LevenshteinDistance levenshteinDistance = new LevenshteinDistance();

    public static <T> T findClosestStringMatch(T[] objects, String methodName, String inputToCompare) {
        return findClosestStringMatch(List.of(objects), methodName, inputToCompare, null);
    }

    public static <T> T findClosestStringMatch(T[] objects, String methodName, String inputToCompare, T defaultValue) {
        return findClosestStringMatch(List.of(objects), methodName, inputToCompare, defaultValue);
    }

    public static <T> T findClosestStringMatch(List<T> objects, String methodName, String inputToCompare){
        return findClosestStringMatch(objects, methodName, inputToCompare, null);
    }

    public static <T> T findClosestStringMatch(List<T> objects, String methodName, String inputToCompare, T defaultValue) {
        if (objects == null || objects.isEmpty() || StringUtils.isEmpty(inputToCompare)) {
            log.warn("The list of objects is empty.");
            return defaultValue;
        }

        T match = defaultValue;
        Integer currentMinimumDistance = Integer.MAX_VALUE;
        String lowerInput = inputToCompare.toLowerCase();


        for (T obj : objects) {
            try {
                // Get the method from the object's class
                Method method = obj.getClass().getMethod(methodName);

                // Invoke the method and get the result
                String result = ((String)method.invoke(obj)).toLowerCase();

                // Calculate the Levenshtein distance between the result and the provided input
                Integer distance = levenshteinDistance.apply(result, lowerInput);

                if(distance.equals(0)) {
                    return obj;
                }
                if(distance < currentMinimumDistance) {
                    currentMinimumDistance = distance;
                    match = obj;
                }
            } catch (NoSuchMethodException e) {
                System.out.println("The method " + methodName + " does not exist in the class " + obj.getClass().getName());
            } catch (Exception e) {
                System.out.println("An error occurred while invoking the method " + methodName + ": " + e.getMessage());
            }
        }
        return match;
    }

}

