package com.iam.serviceacquisition.service;

import com.iam.user.account.common.api.UserAccountClient;
import com.iam.user.account.common.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import retrofit2.Response;

@Service
public class UserAccountService {

    private final UserAccountClient userAccountClient;

    @Autowired
    public UserAccountService(final UserAccountClient userAccountClient){
        this.userAccountClient = userAccountClient;
    }


    public UserDTO getCurrentUser(){
        try {
            Response<UserDTO> response = userAccountClient.getCurrentUser().execute();
            HttpStatus statusCode = HttpStatus.valueOf(response.code());
            if (statusCode.is2xxSuccessful()) {
                return response.body();
            } else {
                throw new ResponseStatusException(statusCode, response.message());
            }
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Get Current User Error", e);
        }
    }

}
