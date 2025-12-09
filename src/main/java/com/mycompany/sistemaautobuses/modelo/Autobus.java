/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemaautobuses.modelo;

public class Autobus {
    private int idAutobus;
    private String modelo;
    private int capacidad;
    private String placa;
    private String estatus;
    
    public Autobus() {}
    
    public Autobus(int idAutobus, String modelo, int capacidad, String placa, String estatus) {
        this.idAutobus = idAutobus;
        this.modelo = modelo;
        this.capacidad = capacidad;
        this.placa = placa;
        this.estatus = estatus;
    }
    
    public int getIdAutobus() { return idAutobus; }
    public void setIdAutobus(int idAutobus) { this.idAutobus = idAutobus; }
    
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    
    public int getCapacidad() { return capacidad; }
    public void setCapacidad(int capacidad) { this.capacidad = capacidad; }
    
    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }
    
    public String getEstatus() { return estatus; }
    public void setEstatus(String estatus) { this.estatus = estatus; }
    
    @Override
    public String toString() {
        return modelo + " - " + placa + " (" + capacidad + " asientos)";
    }
}
