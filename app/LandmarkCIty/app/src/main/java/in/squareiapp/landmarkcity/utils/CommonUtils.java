package in.squareiapp.landmarkcity.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import in.squareiapp.landmarkcity.R;
import in.squareiapp.landmarkcity.application.LandmarkCityApplication;

/**
 * Created by mohit kumar on 7/26/2017.
 */

public class CommonUtils {
    public enum DocumentType {
        DOCUMENT, VIDEO, IMAGE
    }

    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {

            if (email.contains("..") || email.contains(".@")) {
                isValid = false;
            } else {
                isValid = true;
            }
        }
        return isValid;
    }

    public static String getTimeFromDateString(String dateString) {
        String formattedString = "";
        try {
            // String source = "2013-02-19T11:20:16.393Z";
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = null;
            date = formatter.parse(dateString);
            SimpleDateFormat formatterNew = new SimpleDateFormat("hh:mm a");
            formattedString = formatterNew.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedString;
    }

    public static boolean isNetworkAvailable(Context con) {
        try {
            ConnectivityManager cm = (ConnectivityManager) con.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnected()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static byte[] getImageBytes(Bitmap bmp) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return imageBytes;
    }

    public static long generateRandomNoTimeMillis() {

        int max = 9999;
        int min = 1000;
        Date date = new Date();


        Random random = new Random();


        Log.e("random no", "" + date.getTime() + random.nextInt((max - min) + 1) + min);

        return date.getTime() + random.nextInt((max - min) + 1) + min;

    }

    public static long generateRandomPassword() {

        int max = 99999;
        int min = 10000;
        Date date = new Date();


        Random random = new Random();


        Log.e("random no", "" + date.getTime() + random.nextInt((max - min) + 1) + min);

        return date.getTime() + random.nextInt((max - min) + 1) + min;

    }

    public static float getDeviceDensity() {
        Context context = LandmarkCityApplication.getInstance().getApplicationContext();
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return (metrics.densityDpi / 160f);

    }

    public static DisplayMetrics getDeviceMetrix() {
        Context context = LandmarkCityApplication.getInstance().getApplicationContext();
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return metrics;
    }

    public static int convertDpToPixel(float dp) {
        Context context = LandmarkCityApplication.getInstance().getApplicationContext();
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        Float px = dp * (metrics.densityDpi / 160f);
        return px.intValue();
    }

    public static float convertPixelsToDp(float px) {
        Context context = LandmarkCityApplication.getInstance().getApplicationContext();
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / (metrics.densityDpi / 160f);
        return dp;
    }

    public static int getPixels(Context context, int dp) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics);
    }

    public static Integer getInteger(String val) {
        Integer integer = null;
        try {
            integer = Integer.parseInt(val);
        } catch (Exception e) {

        }
        return integer;
    }

    public static Boolean getBoolean(String val) {
        try {
            return Boolean.parseBoolean(val);
        } catch (Exception e) {

        }
        return null;
    }

    public static Double getDouble(String val) {
        try {
            return Double.parseDouble(val);
        } catch (Exception e) {

        }
        return null;
    }

    public static Long getLong(String val) {
        try {
            return Long.parseLong(val);
        } catch (Exception e) {

        }
        return null;
    }

    public static boolean isValidString(String val) {
        return (val != null && !"".equals(val) && !"null".equals(val.toLowerCase()));
    }

    public static boolean isStringSame(String str1, String str2) {
        if (str1 == null && str2 == null) return true;
        if (str1 != null && str1.equals(str2)) return true;
        return str2 != null && str2.equals(str1);
    }

    public static boolean isExternalURL(String str) {
        if (str == null) return false;
        return str.indexOf("http://") == 0 || str.indexOf("https://") == 0 || str.indexOf("www.") == 0;
    }

    public static void openURL(Activity activity, String url) {
        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        activity.startActivity(webIntent);
    }

    public String validateNumber(Context context, String s) {

        if (s.length() != 10) {
            return context.getString(R.string.error_phone_no_invalid);
        }
        if (s.charAt(0) == '0') {
            return context.getString(R.string.error_additional_zero);
        }
        return "";
    }

    public String validatePassword(Context context, String s) {

        if (s.length() < 4) {
            return context.getString(R.string.error_invalid_password);
        }

        return "";
    }

    public static String getContentType(String extension) {
        switch (extension.toLowerCase().trim()) {
            case "pdf":
                return DocumentType.DOCUMENT.name();
            case "jpeg":
                return DocumentType.IMAGE.name();
            case "jpg":
                return DocumentType.IMAGE.name();
            case "png":
                return DocumentType.IMAGE.name();
            case "mp4":
                return DocumentType.VIDEO.name();
            case "wmv":
                return DocumentType.VIDEO.name();
            case "mpeg":
                return DocumentType.VIDEO.name();
            default:
                break;

        }
        return "";
    }
}
