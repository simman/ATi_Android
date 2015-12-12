package net.toutoo.smshelper;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by SimMan on 15/12/8.
 */
public class ApiClient {
    private static final String BASE_URL = "https://api.simman.cc/";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), getReuqestParamems(params), responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), getReuqestParamems(params), responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

    private static RequestParams getReuqestParamems(RequestParams params) {
        String time = String.valueOf(System.currentTimeMillis());
        String token = Token.getToken();
        params.add("token", token);
        params.add("timeStamp", time);

        return params;
    }
}
