package com.microservices.otmp.system.common;


public class ConstantSystem {

    private ConstantSystem() {
        throw new IllegalStateException("Utility class");
    }

    public static final String NUMBER_SPECIAL_CHARACTER = "-";

    public static final String UNDERLINE = "_";

    public static final String COMMA = ",";

    public static final String BLANK = " ";

    public static final String STRING = "";

    public static final String SEMICOLON = ";";

    public static final String SLASH = "/";

    public static final Integer BYTE_1024 = 1024;

    public static final Integer DELETE_VALUE = 1;

    public static final Integer NOT_DELETE_VALUE = 0;

    public static final String DEFAULT_USERNAME = "123";

    public static final String STATUS_ENABLE = "0";

    public static final String STATUS_DISABLE = "1";

}
