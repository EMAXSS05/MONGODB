package com.example;

import org.bson.Document;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public final class MongoProvider implements AutoCloseable {

    private final MongoClient mongoClient;

    private final MongoDatabase database;

    public MongoProvider(){

        String uri= envOrDefault("MongoDB_URI", "mongodb://admin:admin123@localhost:27017/?authSource=admin");

        String dbName= envOrDefault("MonfoDB", "arcade");


        MongoClientSettings settings= MongoClientSettings.builder().applyConnectionString(new ConnectionString(uri)).build();

        this.mongoClient= MongoClients.create(settings);
        this.database=mongoClient.getDatabase(dbName);


    }



    public MongoCollection<Document> obtenerColeccion(String coleccion){
        return database.getCollection(coleccion);
    }



@Override
public void close() throws Exception {
    mongoClient.close();
    
}

private static String envOrDefault(String key, String def){
    String llave= System.getenv(key);
    if (llave==null || key.isBlank()) {
          llave= def;
    }
    return llave;
}


}
