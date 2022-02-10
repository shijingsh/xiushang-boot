package com.xiushang.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidPassword {

    public static boolean isValidPassword(String password){
        String pattern = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,20}$";

        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(password);
        return  m.matches();
    }

}
