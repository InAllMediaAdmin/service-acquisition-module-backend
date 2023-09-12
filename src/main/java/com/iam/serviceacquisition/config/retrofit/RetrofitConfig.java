package com.iam.serviceacquisition.config.retrofit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iam.serviceacquisition.config.retrofit.HttpRetrofitInterceptor;
import com.iam.user.account.common.api.UserAccountClient;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static java.util.concurrent.TimeUnit.MINUTES;
import static okhttp3.logging.HttpLoggingInterceptor.Level.BODY;

@Configuration
@Slf4j
public class RetrofitConfig {

    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient
                .Builder()
                .addInterceptor(new HttpLoggingInterceptor(log::debug).setLevel(BODY))
                .addInterceptor(new HttpRetrofitInterceptor())
                .readTimeout(1, MINUTES)
                .connectTimeout(1, MINUTES)
                .build();
    }

    @Bean
    public Retrofit userAccountRetrofit(final OkHttpClient okHttpClient,
                                        @Value("${iam.user.account-url}") final String baseUrl,
                                        final ObjectMapper objectMapper) {
        return retrofit(baseUrl, okHttpClient, objectMapper);
    }

    @Bean
    public UserAccountClient userAccountClient(final Retrofit userAccountRetrofit) {
        return userAccountRetrofit.create(UserAccountClient.class);
    }

    private Retrofit retrofit(final String baseUrl,
                              final OkHttpClient okHttpClient,
                              final ObjectMapper objectMapper) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .build();
    }

}
