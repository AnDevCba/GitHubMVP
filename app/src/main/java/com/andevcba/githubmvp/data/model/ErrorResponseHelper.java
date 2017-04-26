package com.andevcba.githubmvp.data.model;

import android.util.Log;

import com.andevcba.githubmvp.data.net.ApiConstants;

import java.io.IOException;
import java.lang.annotation.Annotation;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Helper class to parse an {@link ErrorResponse}.
 *
 * @author lucas.nobile
 */
public class ErrorResponseHelper {

    private static final String TAG = "ErrorResponseHelper";
    private Retrofit retrofit;

    @Inject
    public ErrorResponseHelper(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    public ErrorResponse parseError(Response<?> response) {
        Converter<ResponseBody, ErrorResponse> converter =
                retrofit.responseBodyConverter(ErrorResponse.class, new Annotation[0]);

        ErrorResponse errorResponse;
        try {
            if (response.errorBody() != null) {
                errorResponse = converter.convert(response.errorBody());
            } else {
                errorResponse = new ErrorResponse();
                errorResponse.setCode(ApiConstants.NOT_FOUND_CODE);
                errorResponse.setMessage(ApiConstants.NOT_FOUND_MESSAGE);
            }
        } catch (IOException e) {
            Log.e(TAG, "IOException parsing error: ", e);
            return new ErrorResponse();
        }
        return errorResponse;
    }
}
