/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemaautobuses.controlador;

import dao.BoletoDAO;
import modelo.Boleto;
import java.util.List;

public class BoletoController {
    private BoletoDAO boletoDAO;
    
    public BoletoController() {
        boletoDAO = new BoletoDAO();
    }
    
    public boolean insertarBoleto(Boleto boleto) {
        return boletoDAO.insertarBoleto(boleto);
    }
    
    public List<Boleto> obtenerTodosBoletos() {
        return boletoDAO.obtenerTodosBoletos();
    }
    
    public Boleto obtenerBoletoPorId(int id) {
        return boletoDAO.obtenerBoletoPorId(id);
    }
    
    public boolean actualizarBoleto(Boleto boleto) {
        return boletoDAO.actualizarBoleto(boleto);
    }
    
    public boolean eliminarBoleto(int id) {
        return boletoDAO.eliminarBoleto(id);
    }
    
    public List<String> obtenerAsientosOcupados(int idAutobus, String fecha, String hora) {
        return boletoDAO.obtenerAsientosOcupados(idAutobus, fecha, hora);
    }
}
