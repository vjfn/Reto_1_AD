package org.example;

/**
 * Clase que contiene todos los datos del cliente y los métodos get necesarios
 */
public class Cliente {
    private String codigo;
    private String nombreEmpresa;
    private String localidad;
    private String correo;
    private String nombreResponsable;

    public Cliente(String codigo,String nombreEmpresa, String localidad, String correo, String nombreResponsable) {
        this.codigo = codigo;
        this.nombreEmpresa = nombreEmpresa;
        this.localidad = localidad;
        this.correo = correo;
        this.nombreResponsable = nombreResponsable;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombreResponsable() {
        return nombreResponsable;
    }

    public void setNombreResponsable(String nombreResponsable) {
        this.nombreResponsable = nombreResponsable;
    }
}


