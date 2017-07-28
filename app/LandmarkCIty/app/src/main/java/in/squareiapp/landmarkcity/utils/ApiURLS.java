package in.squareiapp.landmarkcity.utils;

import com.android.volley.Request;

/**
 * Created by mohit kumar on 7/24/2017.
 */

public class ApiURLS {

    /* REQUEST METHODS  */
    public static final int REQUEST_POST = Request.Method.POST;
    public static final int REQUEST_GET = Request.Method.GET;
    public static final int REQUEST_PUT = Request.Method.PUT;
    public static final int REQUEST_DELETE = Request.Method.DELETE;

    public static final String BASE_URL = "http://www.squarei.in/api/lm/";
    public static final String USER_REGISTER_URL = BASE_URL + "login/register"; /////done
    public static final String USER_LOGIN_URL = BASE_URL + "login"; ///done
    public static final String USER_RESET_PASSWORD = BASE_URL + "login/forgot";
    public static final String RESET_PASSWORD = BASE_URL + "login/reset";
    public static final String USER_VERIFY_OTP = BASE_URL + "login/enterotp"; //done
    public static final String USER_RESEND_OTP = BASE_URL + "login/resendotp"; //done
    public static final String USERS_POST = BASE_URL + "friend/posts";
    public static final String POST_LIKE = BASE_URL + "friend/posts/like";
    public static final String GET_NOTICE = BASE_URL + "notice/get";
    public static final String GET_NEWS = BASE_URL + "news";
    public static final String GET_UPDATES = BASE_URL + "update/get";
    public static final String GET_PROFILE = BASE_URL + "profile";

    public enum ApiId {
        USER_REGISTER_URL, USER_LOGIN_URL, USER_RESET_PASSWORD, RESET_PASSWORD, USER_VERIFY_OTP, USER_RESEND_OTP, USERS_POST, POST_LIKE, GET_NOTICE, GET_NEWS, GET_UPDATES, GET_PROFILE
    }
}
