/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemaautobuses.dao;

import modelo.Autobus;
import modelo.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AutobusDAO {
    private Connection conexion;
    
    public AutobusDAO() {
        try {
            conexion = Conexion.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public boolean insertarAutobus(Autobus autobus) {
        String sql = "INSERT INTO autobus (modelo, capacidad, placa, estatus) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, autobus.getModelo());
            ps.setInt(2, autobus.getCapacidad());
            ps.setString(3, autobus.getPlaca());
            ps.setString(4, autobus.getEstatus());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Autobus> obtenerTodosAutobuses() {
        List<Autobus> autobuses = new ArrayList<>();
        String sql = "SELECT * FROM autobus ORDER BY modelo";
        
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Autobus autobus = new Autobus();
                autobus.setIdAutobus(rs.getInt("id_autobus"));
                autobus.setModelo(rs.getString("modelo"));
                autobus.setCapacidad(rs.getInt("capacidad"));
                autobus.setPlaca(rs.getString("placa"));
                autobus.setEstatus(rs.getString("estatus"));
                autobuses.add(autobus);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return autobuses;
    }
    
    public Autobus obtenerAutobusPorId(int id) {
        Autobus autobus = null;
        String sql = "SELECT * FROM autobus WHERE id_autobus = ?";
        
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                autobus = new Autobus();
                autobus.setIdAutobus(rs.getInt("id_autobus"));
                autobus.setModelo(rs.getString("modelo"));
                autobus.setCapacidad(rs.getInt("capacidad"));
                autobus.setPlaca(rs.getString("placa"));
                autobus.setEstatus(rs.getString("estatus"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return autobus;
    }
    
    public boolean actualizarAutobus(Autobus autobus) {
        String sql = "UPDATE autobus SET modelo = ?, capacidad = ?, placa = ?, estatus = ? WHERE id_autobus = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, autobus.getModelo());
            ps.setInt(2, autobus.getCapacidad());
            ps.setString(3, autobus.getPlaca());
            ps.setString(4, autobus.getEstatus());
            ps.setInt(5, autobus.getIdAutobus());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean eliminarAutobus(int id) {
        String sql = "DELETE FROM autobus WHERE id_autobus = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Autobus> obtenerAutobusesActivos() {
        List<Autobus> autobuses = new ArrayList<>();
        String sql = "SELECT * FROM autobus WHERE estatus = 'Activo' ORDER BY modelo";
        
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Autobus autobus = new Autobus();
                autobus.setIdAutobus(rs.getInt("id_autobus"));
                autobus.setModelo(rs.getString("modelo"));
                autobus.setCapacidad(rs.getInt("capacidad"));
                autobus.setPlaca(rs.getString("placa"));
                autobus.setEstatus(rs.getString("estatus"));
                autobuses.add(autobus);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return autobuses;
    }
}
