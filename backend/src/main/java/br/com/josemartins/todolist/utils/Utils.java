package br.com.josemartins.todolist.utils;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class Utils {

    public static void copyNotNullProperties (Object source, Object target) {
        BeanUtils.copyProperties(source, target, getNullProperties(source));
    }

    
    public static String[] getNullProperties (Object source) {

        final BeanWrapper src = new BeanWrapperImpl(source);
        
        final PropertyDescriptor[] sourceKeys = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();

        for(PropertyDescriptor key: sourceKeys){
            Object keyValue = src.getPropertyValue(key.getName());

            if(keyValue == null) {
                emptyNames.add(key.getName());
            }
        }

        String[] emptyFields = new String[emptyNames.size()];

        return emptyNames.toArray(emptyFields);
    }
}
