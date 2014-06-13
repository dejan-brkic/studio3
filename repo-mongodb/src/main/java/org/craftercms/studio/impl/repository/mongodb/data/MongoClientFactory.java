package org.craftercms.studio.impl.repository.mongodb.data;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.FactoryBean;

/**
 */
public class MongoClientFactory implements FactoryBean<MongoClient> {

    private MongoClientOptions mongoOptions;
    private List<MongoServer> mongoServers;

    @Override
    public MongoClient getObject() throws Exception {
        List<ServerAddress> addressList = processServers();
        return new MongoClient(addressList, mongoOptions);
    }

    private List<ServerAddress> processServers() throws UnknownHostException {
        List<ServerAddress> addresses = new ArrayList<>(mongoServers.size());
        for (MongoServer server : mongoServers) {
            addresses.add(new ServerAddress(server.getHost(), server.getPort()));
        }
        return addresses;
    }

    @Override
    public Class<?> getObjectType() {
        return MongoClient.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public void setMongoOptions(final MongoClientOptions mongoOptions) {
        this.mongoOptions = mongoOptions;
    }

    public void setMongoServers(final List<MongoServer> mongoServers) {
        this.mongoServers = mongoServers;
    }
}
