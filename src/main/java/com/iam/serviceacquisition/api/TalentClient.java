package com.iam.serviceacquisition.api;

import com.iam.user.account.common.model.talent.SearchRequestDTO;
import com.iam.user.account.common.model.talent.TalentDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;

import java.util.List;

public interface TalentClient {

    @GET("/talent/by-search-request")
    Call<List<TalentDTO>> getTalentsBySearchRequest(@Body SearchRequestDTO searchRequestDTO);

}
