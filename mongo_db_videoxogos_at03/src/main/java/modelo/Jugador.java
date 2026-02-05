package modelo;

public class Jugador {
    private String nombreJugador;
    private String nombreJuego;
    private int puntuacion;
    private int duracion;
    private String nivel;
    public Jugador(String nombreJugador, String nombreJuego, int puntuacion, int duracion, String nivel) {
        this.nombreJugador = nombreJugador;
        this.nombreJuego = nombreJuego;
        this.puntuacion = puntuacion;
        this.duracion = duracion;
        this.nivel = nivel;
    }
    public String getNombreJugador() {
        return nombreJugador;
    }
    public void setNombreJugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
    }
    public String getNombreJuego() {
        return nombreJuego;
    }
    public void setNombreJuego(String nombreJuego) {
        this.nombreJuego = nombreJuego;
    }
    public int getPuntuacion() {
        return puntuacion;
    }
    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }
    public int getDuracion() {
        return duracion;
    }
    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }
    public String getNivel() {
        return nivel;
    }
    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    

}
