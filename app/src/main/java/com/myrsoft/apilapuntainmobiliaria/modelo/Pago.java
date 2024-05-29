package com.myrsoft.apilapuntainmobiliaria.modelo;

import java.io.Serializable;

public class Pago implements Serializable {
    private int id;
    private int numero;
    private Contrato contrato;
    private double importe;
    private String fecha;

    public Pago() {}

    public Pago(int id, int numero, Contrato contrato, double importe, String fecha) {
        this.id = id;
        this.numero = numero;
        this.contrato = contrato;
        this.importe = importe;
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
