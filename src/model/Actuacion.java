package model;

import java.io.Serializable;
import java.time.LocalDate;

public class Actuacion implements Serializable {

    private int id;
    private int idArtista;
    private int idEscenario;
    private LocalDate fecha;
    private String hora;
    private int duracion;

    public Actuacion() {
    }

    public Actuacion(int id, int idArtista, int idEscenario,
                     LocalDate fecha, String hora, int duracion) {
        this.id = id;
        this.idArtista = idArtista;
        this.idEscenario = idEscenario;
        this.fecha = fecha;
        this.hora = hora;
        this.duracion = duracion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdArtista() {
        return idArtista;
    }

    public void setIdArtista(int idArtista) {
        this.idArtista = idArtista;
    }

    public int getIdEscenario() {
        return idEscenario;
    }

    public void setIdEscenario(int idEscenario) {
        this.idEscenario = idEscenario;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    @Override
    public String toString() {
        return "Actuacion{" +
                "id=" + id +
                ", idArtista=" + idArtista +
                ", idEscenario=" + idEscenario +
                ", fecha=" + fecha +
                ", hora='" + hora + '\'' +
                ", duracion=" + duracion +
                '}';
    }
}