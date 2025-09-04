/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author simclub01
 */
public class Venta {
    private int idVenta;
    private LocalDate fecha;
    private LocalTime hora;

    public Venta(float galones, int precioTotal, Gasolina gasolina) {
        this.galones = galones;
        this.gasolina = gasolina;
        this.precioTotal = precioTotal;
    }
    
    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }
    private float galones;
    private Gasolina gasolina;
    private int precioTotal;

    
    

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public float getGalones() {
        return galones;
    }

    public void setGalones(float galones) {
        this.galones = galones;
    }

    public Gasolina getGasolina() {
        return gasolina;
    }

    public void setGasolina(Gasolina gasolina) {
        this.gasolina = gasolina;
    }

    public int getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(int precioTotal) {
        this.precioTotal = precioTotal;
    }
    
    
    
}


