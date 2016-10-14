package andevcba.com.githubmvp.data.net;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Interceptor that logs the outgoing request and the incoming response.
 * See: https://github.com/square/okhttp/wiki/Interceptors
 *
 * @author lucas.nobile
 */

public class LoggingInterceptor implements Interceptor {

    private static final String REQUEST_TAG = "Request: ";
    private static final String RESPONSE_TAG = "Response: ";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        String requestStr = REQUEST_TAG + request.method() + " " + request.url();
        System.out.println(requestStr);

        Response response = chain.proceed(request);

        // NOTE: this will consume the response and NO data will show on UI.
        // Use this for testing purpose only.
        String responseStr = RESPONSE_TAG + response.code() + " " + response.body().string();
        System.out.println(responseStr);

        return response;
    }
}
