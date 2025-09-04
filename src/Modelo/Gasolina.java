/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author simclub01
 */
public class Gasolina {
    private int cantidadTotalGasolina;
    private int idGasolina;
    private String tipoGasolina;
    private int precioGalon;

    public Gasolina(String tipoGasolina) {
        this.cantidadTotalGasolina=26417;
        this.tipoGasolina = tipoGasolina;
        if(tipoGasolina=="Diesel"){
            this.idGasolina=2;
            this.precioGalon=8884;
        }
        else if(tipoGasolina=="Corriente"){
            this.idGasolina=1;
            this.precioGalon=9048;
        }
        
    }

    public void setCantidadTotalGasolina(int cantidadTotalGasolina) {
        this.cantidadTotalGasolina = cantidadTotalGasolina;
    }

    public double getCantidadTotalGasolina() {
        return cantidadTotalGasolina;
    }
    
    public int getIdGasolina() {
        return idGasolina;
    }

    public void setIdGasolina(int idGasolina) {
        this.idGasolina = idGasolina;
    }

    public String getTipoGasolina() {
        return tipoGasolina;
    }

    public void setTipoGasolina(String tipoGasolina) {
        this.tipoGasolina = tipoGasolina;
    }

    public int getPrecioGalon() {
        return precioGalon;
    }

    public void setPrecioGalon(int precioGalon) {
        this.precioGalon = precioGalon;
    }
    
    
    
}
