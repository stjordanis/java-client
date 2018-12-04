package org.arkecosystem.client;

import org.arkecosystem.client.api.AbstractAPI;
import org.arkecosystem.client.api;
import org.arkecosystem.client.http.Client;

import java.util.Map;

public class Connection<T extends AbstractAPI> {

    private Client client;
    private int version;

    private T api;

    public Connection(Map<String, Object> config) {
        this.version = 2;
        this.client = new Client(config.get("host").toString(), 2);

        this.api = (T) new Two(this.client);
    }

    public T api() {
        return this.api;
    }

}
