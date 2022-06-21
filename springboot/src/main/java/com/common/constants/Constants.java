package com.common.constants;

public class Constants {
    /**
     * Success
     */
    public static final Integer SUCCESS = 0;

    /**
     * Fail
     */
    public static final Integer FAIL = 1;

    /**
     * ERROR
     */
    public static final Integer ERROR = 1;

    /**
     * Logic delete sign-not deleted
     */
    public static final int DELETE_FLG_FALSE = 0;
    /**
     * Logic delete sign-deleted
     */
    public static final int DELETE_FLG_TRUE = 1;

    public class LoginMethod{

        public static final int BY_EMAIL = 0;

        public static final int BY_PHONE = 1;
    }

    public static final String SESSION_USER_KEY = "UserInfo";
}
