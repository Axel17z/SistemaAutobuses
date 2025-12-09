/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemaautobuses.controlador;

import dao.PasajeroDAO;
import modelo.Pasajero;
import java.util.List;

public class PasajeroController {
    private PasajeroDAO pasajeroDAO;
    
    public PasajeroController() {
        pasajeroDAO = new PasajeroDAO();
    }
    
    public boolean insertarPasajero(Pasajero pasajero) {
        return pasajeroDAO.insertarPasajero(pasajero);
    }
    
    public List<Pasajero> obtenerTodosPasajeros() {
        return pasajeroDAO.obtenerTodosPasajeros();
    }
    
    public Pasajero obtenerPasajeroPorId(int id) {
        return pasajeroDAO.obtenerPasajeroPorId(id);
    }
    
    public boolean actualizarPasajero(Pasajero pasajero) {
        return pasajeroDAO.actualizarPasajero(pasajero);
    }
    
    public boolean eliminarPasajero(int id) {
        return pasajeroDAO.eliminarPasajero(id);
    }
    
    public List<Pasajero> buscarPasajeros(String criterio) {
        return pasajeroDAO.buscarPasajeros(criterio);
    }
}
