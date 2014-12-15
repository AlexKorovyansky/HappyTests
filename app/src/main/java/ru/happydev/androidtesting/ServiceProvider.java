/*
 * Copyright (C) 2014 Medlert, Inc.
 */
package ru.happydev.androidtesting;

import retrofit.RestAdapter;

public class ServiceProvider {

    private static ServiceProvider ourInstance = new ServiceProvider();
    private MutableEndpoint mutableEndpoint;
    private BackendService backendService;

    public static ServiceProvider getInstance() {
        return ourInstance;
    }

    public MutableEndpoint getMutableEndpoint() {
        return mutableEndpoint;
    }

    public BackendService getBackendService() {
        return backendService;
    }

    private static MutableEndpoint makeMutableEndpoint() {
        return new MutableEndpoint("https://api.happydev.ru");
    }

    private static BackendService makeBackendService(MutableEndpoint mutableEndpoint) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(mutableEndpoint)
                .build();
        return restAdapter.create(BackendService.class);
    }

    private ServiceProvider() {
        mutableEndpoint = makeMutableEndpoint();
        backendService = makeBackendService(mutableEndpoint);
    }

}
