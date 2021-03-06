package org.arkecosystem.client.http;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import okhttp3.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Client {
    private String host;
    private int version;
    private OkHttpClient client;
    private MediaType JSON = MediaType.parse("application/json");

    public Client(String host, int version) {
        this.host = host;
        this.version = version;
        this.client = new OkHttpClient();
    }

    public LinkedTreeMap<String, Object> get(String url, Map<String, Object> params) throws IOException {
        HttpUrl.Builder httpBuider = HttpUrl.parse(this.host + url).newBuilder();

        if (params != null) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                httpBuider.addQueryParameter(entry.getKey(), entry.getValue().toString());
            }
        }

        Request request = new Request.Builder().url(httpBuider.build()).build();
        Response response = client.newCall(request).execute();
        return new Gson().fromJson(response.body().string(), new LinkedTreeMap<String, Object>().getClass());
    }

    public LinkedTreeMap<String, Object> get(String url) throws IOException {
        return get(url, new HashMap());
    }

    public LinkedTreeMap<String, Object> post(String url, Map payload) throws IOException {
        RequestBody body = RequestBody.create(JSON, new Gson().toJson(payload));
        Request request = new Request.Builder().url(this.host + url).post(body).build();
        Response response = client.newCall(request).execute();
        return new Gson().fromJson(response.body().string(), new LinkedTreeMap<String, Object>().getClass());
    }

    public OkHttpClient getClient() {
        return client;
    }

    public void setClient(OkHttpClient client) {
        this.client = client;
    }

}
