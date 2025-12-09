/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemaautobuses.controlador;

import dao.AutobusDAO;
import modelo.Autobus;
import java.util.List;

public class AutobusController {
    private AutobusDAO autobusDAO;
    
    public AutobusController() {
        autobusDAO = new AutobusDAO();
    }
    
    public boolean insertarAutobus(Autobus autobus) {
        return autobusDAO.insertarAutobus(autobus);
    }
    
    public List<Autobus> obtenerTodosAutobuses() {
        return autobusDAO.obtenerTodosAutobuses();
    }
    
    public Autobus obtenerAutobusPorId(int id) {
        return autobusDAO.obtenerAutobusPorId(id);
    }
    
    public boolean actualizarAutobus(Autobus autobus) {
        return autobusDAO.actualizarAutobus(autobus);
    }
    
    public boolean eliminarAutobus(int id) {
        return autobusDAO.eliminarAutobus(id);
    }
    
    public List<Autobus> obtenerAutobusesActivos() {
        return autobusDAO.obtenerAutobusesActivos();
    }
}
