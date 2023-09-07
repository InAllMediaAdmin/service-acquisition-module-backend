package com.iam.serviceacquisition.api;

import com.iam.serviceacquisition.domain.dto.SearchRequestDTO;
import com.iam.serviceacquisition.domain.dto.TalentDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;

import java.util.List;

public interface TalentClient {

    @GET("/talent/by-search-request")
    Call<List<TalentDTO>> getTalentsBySearchRequest(@Body SearchRequestDTO searchRequestDTO);

}
