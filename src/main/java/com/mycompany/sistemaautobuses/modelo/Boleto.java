/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemaautobuses.modelo;

import java.util.Date;

public class Boleto {
    private int idBoleto;
    private int idPasajero;
    private int idAutobus;
    private int origen;
    private int destino;
    private Date fechaViaje;
    private String horaSalida;
    private String asiento;
    private double precio;
    
    private String nombrePasajero;
    private String placaAutobus;
    private String nombreOrigen;
    private String nombreDestino;
    
    public Boleto() {}
    
    public int getIdBoleto() { return idBoleto; }
    public void setIdBoleto(int idBoleto) { this.idBoleto = idBoleto; }
    
    public int getIdPasajero() { return idPasajero; }
    public void setIdPasajero(int idPasajero) { this.idPasajero = idPasajero; }
    
    public int getIdAutobus() { return idAutobus; }
    public void setIdAutobus(int idAutobus) { this.idAutobus = idAutobus; }
    
    public int getOrigen() { return origen; }
    public void setOrigen(int origen) { this.origen = origen; }
    
    public int getDestino() { return destino; }
    public void setDestino(int destino) { this.destino = destino; }
    
    public Date getFechaViaje() { return fechaViaje; }
    public void setFechaViaje(Date fechaViaje) { this.fechaViaje = fechaViaje; }
    
    public String getHoraSalida() { return horaSalida; }
    public void setHoraSalida(String horaSalida) { this.horaSalida = horaSalida; }
    
    public String getAsiento() { return asiento; }
    public void setAsiento(String asiento) { this.asiento = asiento; }
    
    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
    
    public String getNombrePasajero() { return nombrePasajero; }
    public void setNombrePasajero(String nombrePasajero) { this.nombrePasajero = nombrePasajero; }
    
    public String getPlacaAutobus() { return placaAutobus; }
    public void setPlacaAutobus(String placaAutobus) { this.placaAutobus = placaAutobus; }
    
    public String getNombreOrigen() { return nombreOrigen; }
    public void setNombreOrigen(String nombreOrigen) { this.nombreOrigen = nombreOrigen; }
    
    public String getNombreDestino() { return nombreDestino; }
    public void setNombreDestino(String nombreDestino) { this.nombreDestino = nombreDestino; }
}
