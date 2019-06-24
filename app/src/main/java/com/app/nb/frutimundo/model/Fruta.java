package com.app.nb.frutimundo.model;

public class Fruta {

    private String nombre;
    private String descripcion;
    private int imagenBackground;
    private int icono;
    private int cantidad;

    //Valores accesibles
    public static final int LIMITE_DE_CANTIDAD = 10;
    public static final int CANTIDAD_INICIAL = 0;

    public String getNombre() {
        return nombre;
    }

    public Fruta() {
    }

    public Fruta(String nombre, String descripcion, int imagenBackground, int icono, int cantidad) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagenBackground = imagenBackground;
        this.icono = icono;
        this.cantidad = cantidad;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getImagenBackground() {
        return imagenBackground;
    }

    public void setImagenBackground(int imagenBackground) {
        this.imagenBackground = imagenBackground;
    }

    public int getIcono() {
        return icono;
    }

    public void setIcono(int icono) {
        this.icono = icono;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    //Añadir cantidad
    public void addCantidad(int cantidad) {
        if (this.cantidad < LIMITE_DE_CANTIDAD)
            this.cantidad += cantidad;
    }

    //Restablecer cantidad
    public void resetCantidad() {
        this.cantidad = CANTIDAD_INICIAL;
    }
}
