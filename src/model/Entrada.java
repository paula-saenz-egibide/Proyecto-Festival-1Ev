package model;

import java.io.Serializable;

public class Entrada implements Serializable {

    private int id;
    private int idActuacion;
    private int idEspectador;
    private double precio;
    private String tipo;
    private boolean activa;

    public Entrada() {
    }

    public Entrada(int id, int idActuacion, int idEspectador,
                   double precio, String tipo, boolean activa) {
        this.id = id;
        this.idActuacion = idActuacion;
        this.idEspectador = idEspectador;
        this.precio = precio;
        this.tipo = tipo;
        this.activa = activa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdActuacion() {
        return idActuacion;
    }

    public void setIdActuacion(int idActuacion) {
        this.idActuacion = idActuacion;
    }

    public int getIdEspectador() {
        return idEspectador;
    }

    public void setIdEspectador(int idEspectador) {
        this.idEspectador = idEspectador;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    @Override
    public String toString() {
        return "Entrada{" +
                "id=" + id +
                ", idActuacion=" + idActuacion +
                ", idEspectador=" + idEspectador +
                ", precio=" + precio +
                ", tipo='" + tipo + '\'' +
                ", activa=" + activa +
                '}';
    }
}