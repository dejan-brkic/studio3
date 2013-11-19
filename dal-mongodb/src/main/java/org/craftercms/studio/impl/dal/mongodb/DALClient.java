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

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import de.undercouch.bson4jackson.types.ObjectId;
import org.apache.commons.lang.StringUtils;
import org.jongo.Jongo;
import org.jongo.Mapper;
import org.jongo.MongoCollection;
import org.jongo.Oid;
import org.jongo.marshall.jackson.JacksonMapper;
import org.jongo.marshall.jackson.oid.Id;

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

            Mapper mapper = new JacksonMapper.Builder().addDeserializer(ObjectId.class,
                new JsonDeserializer<ObjectId>() {
                @Override
                public ObjectId deserialize(final JsonParser jsonParser, final DeserializationContext
                    deserializationContext) throws IOException, JsonProcessingException {
                    org.bson.types.ObjectId objectId = new org.bson.types.ObjectId(Oid.withOid(jsonParser.getText()));
                    return new ObjectId(objectId.getTimeSecond(), objectId.getMachine(), objectId.getInc());
                }
            })
            .addSerializer(ObjectId.class, new JsonSerializer<ObjectId>() {

                @Override
                public void serialize(final ObjectId objectId, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
                    org.bson.types.ObjectId o = new org.bson.types.ObjectId(objectId.getTime(),
                        objectId.getMachine(), objectId.getInc());
                    jsonGenerator.writeString(o.toString());
                }
            }).build();

            jongo=new Jongo(db);
            }

    public MongoCollection getCollection(String name) {
        MongoCollection mc = jongo.getCollection(name);
        return jongo.getCollection(name);
    }

    public DB getDb() {
        return mongoClient.getDB(database);
    }

    public DBCollection getDBCollection(String collectionName) {
        DB db = mongoClient.getDB(database);
        return db.getCollection(collectionName);
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
