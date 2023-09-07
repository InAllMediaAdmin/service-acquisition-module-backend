package com.iam.serviceacquisition.service;

import com.iam.user.account.common.api.UserAccountClient;
import com.iam.user.account.common.enums.UserRole;
import com.iam.user.account.common.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import retrofit2.Response;

import javax.validation.constraints.NotNull;
import java.util.List;

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

    public List<UserDTO> getAllUsers(Integer page, Integer pageSize, String sortBy, UserRole role){
        try {
            final Response<Page<UserDTO>> response = userAccountClient.getAllUsers(page, pageSize, sortBy, role).execute();
            HttpStatus statusCode = HttpStatus.valueOf(response.code());
            if (statusCode.is2xxSuccessful()) {
                return response.body().getContent();
            } else {
                throw new ResponseStatusException(statusCode, response.message());
            }
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Get ALL Users Error", e);
        }
    }

    @Cacheable(value = "findUserById", keyGenerator = "cacheKeyGenerator")
    public UserDTO findUserById(@NotNull Long id) {
        try {
            Response<UserDTO> response = userAccountClient.findById(id).execute();
            HttpStatus statusCode = HttpStatus.valueOf(response.code());
            if (statusCode.is2xxSuccessful()) {
                UserDTO user = response.body();
                return user;
            } else{
                throw new ResponseStatusException(statusCode, response.message());
            }
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Find User Error", e);
        }
    }

}
