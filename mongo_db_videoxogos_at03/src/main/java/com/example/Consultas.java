package com.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bson.Document;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Projections;

public class Consultas {
    /**
     * Método que inserta un jugador en la colección
     * 
     * @param name
     * @param nombreJuego
     * @param puntuación
     * @param duracion
     * @param nivel
     */
    public void insertarJugador(String name, String nombreJuego, int puntuación, int duracion, int nivel) {
        try (MongoProvider mongoProvider = new MongoProvider()) {
            MongoCollection<Document> colection = mongoProvider.obtenerColeccion("partidas");

            colection.insertOne(new Document("nombreJugador", name).append("nombre del juego", nombreJuego)
                    .append("puntuación", puntuación)
                    .append("duración", duracion).append("nivel", nivel));

        } catch (Exception e) {
            System.out.println("Error al insertar un jugador" + e.getMessage());
        }
    }

    /**
     * Método que obtiene la puntuación total de cada jugador
     */
    public void puntuacionTotal() {
        try (MongoProvider mongoProvider = new MongoProvider()) {
            MongoCollection<Document> collection = mongoProvider.obtenerColeccion("partidas");
            List<Document> consulta = collection
                    .aggregate(Arrays.asList(
                            Aggregates.group("$nombreJugador", Accumulators.sum("Total de puntos", "$puntuación"))))
                    .into(new ArrayList<>());

            for (Document document : consulta) {
                String nombreJugador = document.getString("_id");
                int total = document.getInteger("Total de puntos");
                System.out.println("Nombre del jugador: " + nombreJugador + " Total de puntos: " + total);
            }

        } catch (Exception e) {
            System.out.println("Error al consultar la puntuación total de los empleados" + e.getMessage());
        }
    }

    /**
     * Método que muestra la mejor partida de cada jugador segun sus puntuaciones
     */
    public void mejorPartida() {
        try (MongoProvider mongoProvider = new MongoProvider()) {
            MongoCollection<Document> collection = mongoProvider.obtenerColeccion("partidas");
            List<Document> consulta = collection
                    .aggregate(Arrays.asList(
                            Aggregates.group("$nombreJugador", Accumulators.max("Puntuacion máxima", "$puntuación"))))
                    .into(new ArrayList<>());

            for (Document document : consulta) {
                String nombre = document.getString("_id");
                int puntuacionMaxima = document.getInteger("Puntuacion máxima");
                System.out.println("Nomnbre del jugador: " + nombre + " Puntuación máxima: " + puntuacionMaxima);
            }
        } catch (Exception e) {
            System.out.println("No se pudo obtener las puntuaciones máximas de los jugadores" + e.getMessage());
        }
    }

    /**
     * Método que muestra la partida más corta de cada juego.
     */
    public void partidaMasCorta() {
        try (MongoProvider mongoProvider = new MongoProvider()) {
            MongoCollection<Document> collection = mongoProvider.obtenerColeccion("partidas");
            List<Document> consulta = collection
                    .aggregate(Arrays.asList(
                            Aggregates.group("$nombre del juego", Accumulators.min("Partida más Corta", "$duración"))))
                    .into(new ArrayList<>());

            for (Document document : consulta) {
                String juego = document.getString("_id");
                int duracionMinima = document.getInteger("Partida más Corta");
                System.out.println("Juego: " + juego + " , La partida mas corta duró: " + duracionMinima);
            }
        } catch (Exception e) {
            System.out.println("No se pudo obtener las duraciones minimas de las partidas");
        }
    }

    /**
     * Método que muestra el ranking de jugadores ordenados por el toatl de puntos.
     */
    public void rankingDeJugadores() {
        try (MongoProvider mongoProvider = new MongoProvider()) {
            MongoCollection<Document> collection = mongoProvider.obtenerColeccion("partidas");
            List<Document> consulta = collection
                    .aggregate(Arrays.asList(
                            Aggregates.group("$nombreJugador", Accumulators.sum("Total de puntos", "$puntuación")),
                            Aggregates.sort(new Document("Total de puntos", -1))))
                    .into(new ArrayList<>());

            for (Document document : consulta) {
                String nombre = document.getString("_id");
                int puntuación = document.getInteger("Total de puntos");
                System.out.println("nombre: " + nombre + ", puntuación: " + puntuación);
            }
        } catch (Exception e) {
            System.out.println("Error al realizar la consulta para obtener el ranking de jugadores " + e.getMessage());
        }
    }

    /**
     * Muestra el nombre del jugador, nombre del juego y la puntuación excluyendo el
     * ID
     */
    public void listaSimplificada() {
        try (MongoProvider mongoProvider = new MongoProvider()) {
            MongoCollection<Document> collection = mongoProvider.obtenerColeccion("partidas");
            List<Document> consulta = collection.aggregate(Arrays.asList(Aggregates.project(Projections.fields(
                    Projections.include("nombreJugador", "nombre del juego", "puntuación"))))).into(new ArrayList<>());

            for (Document document : consulta) {
                String nombre = document.getString("nombreJugador");
                String nombreJuego = document.getString("nombre del juego");
                int puntuacion = document.getInteger("puntuación");

                System.out.println("nombre: " + nombre + ", Juego: " + nombreJuego + ", puntuación: " + puntuacion);
            }

        } catch (Exception e) {
            System.out.println("Error en la consulta " + e.getMessage());
        }

    }

    /**
     * Muestra la media de puntuaciones por cada juego
     */
    public void juegosMasPuntuables() {
        try (MongoProvider mongoProvider = new MongoProvider()) {
            MongoCollection<Document> collection = mongoProvider.obtenerColeccion("partidas");

            collection.aggregate(
                    Arrays.asList(Aggregates.group("$nombre del juego",
                            Accumulators.avg("puntuacion media", "$puntuación")),
                            Aggregates.sort(new Document("puntuacion media", 1))))
                    .forEach(doc -> {
                        String juego = doc.getString("_id");
                        double avg = doc.getDouble("puntuacion media");
                        System.out.println("Juego: " + juego + " , puntuación media: " + avg);
                    });
        } catch (Exception e) {
            System.out.println("No se pudo obtener el promedio de la puntuación de cada partida" + e.getMessage());
        }
    }

}
