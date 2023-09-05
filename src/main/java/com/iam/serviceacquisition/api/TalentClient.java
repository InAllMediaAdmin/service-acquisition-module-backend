package com.iam.serviceacquisition.api;

import com.iam.serviceacquisition.model.dto.TalentDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface TalentClient {

    @POST("/talent")
    Call<TalentDTO> createTalent(@Body final TalentDTO talentDTO);

}
