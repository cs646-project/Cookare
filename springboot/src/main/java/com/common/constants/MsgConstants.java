package com.common.constants;

public class MsgConstants {

    public static class CheckDto{

        public static final String INVALID_LOGIN_INFO ="Wrong username or password. Please check again";

        public static final String USER_NOT_EXIST = "User Info Not exist.";

        public static final String WRONG_PASSWORD = "Wrong user name or password.";
    }

    public static class NormalReturn {

        public static final String LOG_OUT = "Successfully log out.";

        public static final String CREATE_USER = "Successfully created new user.";

        public static final String MODIFY_USER = "Successfully modified userinfo.";
    }

    public static class ERROR {
        public static final String MISSING_INFO = "Missing Required Info.";

        public static final String DUPLICATE_INFO = "Duplicated Information!";

        public static final String WRONG_USER = "Wrong user for action.";
    }
}
