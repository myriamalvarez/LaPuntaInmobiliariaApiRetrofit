package com.myrsoft.apilapuntainmobiliaria.modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Contrato implements Serializable {
    private int id;
    private String desde;
    private String hasta;
    private double valor;
    private Inquilino inquilino;
    private Inmueble inmueble;

    public Contrato() {
    }

    public Contrato(int id, String desde, String hasta, double valor, Inquilino inquilino, Inmueble inmueble) {
        this.id = id;
        this.desde = desde;
        this.hasta = hasta;
        this.valor = valor;
        this.inquilino = inquilino;
        this.inmueble = inmueble;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesde() {
        return desde;
    }

    public void setDesde(String desde) {
        this.desde = desde;
    }

    public String getHasta() {
        return hasta;
    }

    public void setHasta(String hasta) {
        this.hasta = hasta;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Inquilino getInquilino() {
        return inquilino;
    }

    public void setInquilino(Inquilino inquilino) {
        this.inquilino = inquilino;
    }

    public Inmueble getInmueble() {
        return inmueble;
    }

    public void setInmueble(Inmueble inmueble) {
        this.inmueble = inmueble;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contrato contrato = (Contrato) o;
        return id == contrato.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
