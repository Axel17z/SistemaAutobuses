/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemaautobuses.controlador;

import dao.CiudadDAO;
import modelo.Ciudad;
import java.util.List;

public class CiudadController {
    private CiudadDAO ciudadDAO;
    
    public CiudadController() {
        ciudadDAO = new CiudadDAO();
    }
    
    public boolean insertarCiudad(Ciudad ciudad) {
        return ciudadDAO.insertarCiudad(ciudad);
    }
    
    public List<Ciudad> obtenerTodasCiudades() {
        return ciudadDAO.obtenerTodasCiudades();
    }
    
    public Ciudad obtenerCiudadPorId(int id) {
        return ciudadDAO.obtenerCiudadPorId(id);
    }
    
    public boolean actualizarCiudad(Ciudad ciudad) {
        return ciudadDAO.actualizarCiudad(ciudad);
    }
    
    public boolean eliminarCiudad(int id) {
        return ciudadDAO.eliminarCiudad(id);
    }
}
