package ru.happydev.androidtesting;

import retrofit.Endpoint;

public class MutableEndpoint implements Endpoint {

    private String url;

    public MutableEndpoint(String url) {
        this.url = url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String getName() {
        return "MutableEndpoint (url=" + getUrl() + ")";
    }
}
