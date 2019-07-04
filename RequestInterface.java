package com.example.anuj.appointmentrequest;



import com.example.anuj.appointmentrequest.models.ServerRequest;
import com.example.anuj.appointmentrequest.models.ServerResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RequestInterface {

    @POST("learn2crack-login-register/")
    Call<ServerResponse> operation(@Body ServerRequest request);

}
