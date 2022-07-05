package com.example.volley;

public class Cupon {
    String Corte;
    String Bulto;
    String Talla;
    String Operacion;
    String Costo;
    String Cantidad;

    public Cupon(String corte, String bulto, String talla, String operacion, String costo, String cantidad){
        this.Corte = corte;
        this.Bulto = bulto;
        this.Talla = talla;
        this.Operacion = operacion;
        this.Costo = costo;
        this.Cantidad = cantidad;
    }

    public void setCorte(String corte) {
        Corte = corte;
    }

    public void setBulto(String bulto) {
        Bulto = bulto;
    }

    public void setTalla(String talla) {
        Talla = talla;
    }

    public void setOperacion(String operacion) {
        Operacion = operacion;
    }

    public void setCosto(String costo) {
        Costo = costo;
    }

    public void setCantidad(String cantidad) {
        Cantidad = cantidad;
    }

    public String getCorte() {
        return Corte;
    }

    public String getBulto() {
        return Bulto;
    }

    public String getTalla() {
        return Talla;
    }

    public String getOperacion() {
        return Operacion;
    }

    public String getCosto() {
        return Costo;
    }

    public String getCantidad() {
        return Cantidad;
    }
}
