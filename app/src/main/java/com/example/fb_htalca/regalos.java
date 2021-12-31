package com.example.fb_htalca;

import java.io.Serializable;

public class regalos implements Serializable {
    private String foto;
    private String mensaje;
    public regalos(){
        foto = "";
        mensaje = "";

    }

    public regalos(String foto, String mensaje){
        this.foto = foto;
        this.mensaje= mensaje;

    }

    public String getFoto() {
        return foto;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
