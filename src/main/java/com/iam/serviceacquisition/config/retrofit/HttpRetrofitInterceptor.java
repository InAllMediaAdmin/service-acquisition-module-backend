package com.iam.serviceacquisition.config.retrofit;

import java.io.IOException;
import java.util.Optional;

import com.iam.serviceacquisition.config.jwt.RequestContext;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

@Slf4j
public class HttpRetrofitInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        final Request original = chain.request();
        final Request.Builder requestBuilder = original.newBuilder();

        final Optional<String> token = Optional.ofNullable(RequestContext.getInstance().getToken());

        token.filter(t -> !t.isEmpty())
             .ifPresent(
                        t -> {
                            log.debug("Adding to header token: {}", t);
                            requestBuilder.header(RequestContext.AUTHORIZATION_HEADER, "Bearer " + t);
                        });

        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}

