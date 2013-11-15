/*
 * Copyright (C) 2007-2013 Crafter Software Corporation.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.craftercms.studio.impl.dal.mongodb;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import org.apache.commons.lang.StringUtils;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

/**
 * DAL client.
 *
 * @author Dejan Brkic
 */
public class DALClient {

    private MongoClient mongoClient;
    private String database;
    private String username;
    private String password;

    private Jongo jongo;

    public void init() {

        DB db = mongoClient.getDB(database);
        if (!StringUtils.isBlank(password)) {
            boolean authenticated = db.authenticate(username, password.toCharArray());
            if (!authenticated) {
                throw new MongoException("Authentication failed for database " + database);
            }
        }
        jongo = new Jongo(db);
    }

    public MongoCollection getCollection(String name) {
        return jongo.getCollection(name);
    }

    // Getters and setters
    public MongoClient getMongoClient() {
        return mongoClient;
    }

    public void setMongoClient(final MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(final String database) {
        this.database = database;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }
}
