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
    public static final String USER_REGISTER_URL = BASE_URL + "login/register";
    public static final String USER_LOGIN_URL = BASE_URL + "login";
    public static final String USER_RESET_PASSWORD = BASE_URL + "login/forgot";
    public static final String USER_VERIFY_OTP = BASE_URL + "login/enterotp";
    public static final String USER_RESEND_OTP = BASE_URL + "login/resendotp";

    public enum ApiId {
        USER_REGISTER_URL, USER_LOGIN_URL, USER_RESET_PASSWORD, USER_VERIFY_OTP, USER_RESEND_OTP
    }
}
