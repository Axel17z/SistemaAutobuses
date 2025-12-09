/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemaautobuses.modelo;

public class Pasajero {
    private int idPasajero;
    private String nombre;
    private String apellidos;
    private String telefono;
    private String email;
    
    public Pasajero() {}
    
    public Pasajero(int idPasajero, String nombre, String apellidos, String telefono, String email) {
        this.idPasajero = idPasajero;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.email = email;
    }
    
    public int getIdPasajero() { return idPasajero; }
    public void setIdPasajero(int idPasajero) { this.idPasajero = idPasajero; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getNombreCompleto() {
        return nombre + " " + apellidos;
    }
    
    @Override
    public String toString() {
        return getNombreCompleto();
    }
}

