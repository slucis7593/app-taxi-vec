package com.vec.android.apptaxi.api;

import com.vec.android.apptaxi.model.ResponseData;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by vuduc on 9/5/15.
 */
public interface TaxiAppService {
    @FormUrlEncoded
    @POST("/TaxiAccount.asmx/LogIn")
    public Call<ResponseData> login(@Field("email") String email,
                                    @Field("password") String password,
                                    @Field("machine_code") String machineCode,
                                    @Field("os_type") String osType);
}
