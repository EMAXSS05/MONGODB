package com.example;


public final class App {
   
    public static void main(String[] args) {
        
        Consultas consultas= new Consultas();
        try {
           /*  consultas.insertarJugador("Mario", "Space Invaders", 1400, 15, 3);
             consultas.insertarJugador("Juan", "call of duty", 1250, 35, 5);
             consultas.insertarJugador("Felix", "fornite", 1000, 160, 10);
             consultas.insertarJugador("Dayanna", "valorant", 1457, 500, 23); */

             System.out.println("----Total de puntuaciones por jugador-----");
             consultas.puntuacionTotal();

             System.out.println();
             System.out.println("-----Mejor partidada de cada jugador--------");
             consultas.mejorPartida();

             System.out.println("----Partida mas corta por juego-----");

             consultas.partidaMasCorta();
             consultas.rankingDeJugadores();

             System.out.println("------Lista simplificada--------");
             consultas.listaSimplificada();

             System.out.println("------Juegos más puntuables-------");
             consultas.juegosMasPuntuables();
        } catch (Exception e) {
            System.out.println("Error al ejecutar el prgrama"+e.getMessage());
        }
    }
}
