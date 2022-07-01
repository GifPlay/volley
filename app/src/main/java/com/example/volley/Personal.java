package com.example.volley;

public class Personal {
    String Nombre;
    String Telefono;
    String Status;
    String Fotografia;
    String Puesto;
    String IdPersonal;

    public Personal(){

    }

    public Personal(String nombre, String telefono, String status, String puesto, String fotografia, String idPersonal){
        this.Nombre = nombre;
        this.Telefono = telefono;
        this.Status = status;
        this.Puesto = puesto;
        this.Fotografia = fotografia;
        this.IdPersonal = idPersonal;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public void setFotografia(String fotografia) {
        Fotografia = fotografia;
    }

    public void setPuesto(String puesto) {
        Puesto = puesto;
    }

    public void setIdPersonal(String idPersonal) {
        IdPersonal = idPersonal;
    }

    public String getNombre() {
        return Nombre;
    }

    public String getTelefono() {
        return Telefono;
    }

    public String getStatus() {
        return Status;
    }

    public String getFotografia() {
        return Fotografia;
    }

    public String getPuesto() {
        return Puesto;
    }

    public String getIdPersonal() {
        return IdPersonal;
    }
}
