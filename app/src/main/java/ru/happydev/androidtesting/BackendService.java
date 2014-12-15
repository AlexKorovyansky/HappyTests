/*
 * Copyright (C) 2014 Medlert, Inc.
 */
package ru.happydev.androidtesting;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

public interface BackendService {

    @FormUrlEncoded
    @POST("/registration")
    public void regisiter(@Field("firstName") String name, @Field("lastName") String surname, Callback<String> callback);

}
