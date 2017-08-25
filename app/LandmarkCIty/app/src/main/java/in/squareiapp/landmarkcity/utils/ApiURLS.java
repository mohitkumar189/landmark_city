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
    public static final String UPDATE_WEATHER = BASE_URL + "update/weather";

    public static final String GET_FRIENDS = BASE_URL + "friend";
    public static final String GET_FRIENDS_REQUESTS = BASE_URL + "friend/requests";
    public static final String GET_FRIENDS_SUGGESTIONS = BASE_URL + "friend/suggestions";
    public static final String SEND_FRIEND_REQUEST = BASE_URL + "friend/reqsend";
    public static final String GET_STORES_CATEGORIES = BASE_URL + "store/type";
    public static final String GET_GREETINGS = BASE_URL + "greeting";
    public static final String GET_STORES = BASE_URL + "store";
    public static final String SEARCH_STORES = BASE_URL + "store/search";
    public static final String SEND_SOS = BASE_URL + "sos/push";

    public enum ApiId {
        USER_REGISTER_URL, USER_LOGIN_URL, USER_RESET_PASSWORD, RESET_PASSWORD, USER_VERIFY_OTP,
        USER_RESEND_OTP, USERS_POST, POST_LIKE, GET_NOTICE, GET_NEWS, GET_UPDATES, GET_PROFILE,UPDATE_WEATHER,

        GET_FRIENDS,GET_FRIENDS_SUGGESTIONS,SEND_FRIEND_REQUEST,GET_STORES_CATEGORIES,GET_GREETINGS,GET_STORES,SEND_SOS,SEARCH_STORES
    }
}