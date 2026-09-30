package model;

import java.io.Serializable;

public class Artista implements Serializable {

    private int id;
    private String nombre;
    private String genero;
    private String pais;

    public Artista() {
    }

    public Artista(int id, String nombre, String genero, String pais) {
        this.id = id;
        this.nombre = nombre;
        this.genero = genero;
        this.pais = pais;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    @Override
    public String toString() {
        return "Artista{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", genero='" + genero + '\'' +
                ", pais='" + pais + '\'' +
                '}';
    }
}