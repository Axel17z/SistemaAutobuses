/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemaautobuses.dao;

import modelo.Pasajero;
import modelo.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PasajeroDAO {
    private Connection conexion;
    
    public PasajeroDAO() {
        try {
            conexion = Conexion.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public boolean insertarPasajero(Pasajero pasajero) {
        String sql = "INSERT INTO pasajero (nombre, apellidos, telefono, email) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, pasajero.getNombre());
            ps.setString(2, pasajero.getApellidos());
            ps.setString(3, pasajero.getTelefono());
            ps.setString(4, pasajero.getEmail());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Pasajero> obtenerTodosPasajeros() {
        List<Pasajero> pasajeros = new ArrayList<>();
        String sql = "SELECT * FROM pasajero ORDER BY apellidos, nombre";
        
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Pasajero pasajero = new Pasajero();
                pasajero.setIdPasajero(rs.getInt("id_pasajero"));
                pasajero.setNombre(rs.getString("nombre"));
                pasajero.setApellidos(rs.getString("apellidos"));
                pasajero.setTelefono(rs.getString("telefono"));
                pasajero.setEmail(rs.getString("email"));
                pasajeros.add(pasajero);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pasajeros;
    }
    
    public Pasajero obtenerPasajeroPorId(int id) {
        Pasajero pasajero = null;
        String sql = "SELECT * FROM pasajero WHERE id_pasajero = ?";
        
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                pasajero = new Pasajero();
                pasajero.setIdPasajero(rs.getInt("id_pasajero"));
                pasajero.setNombre(rs.getString("nombre"));
                pasajero.setApellidos(rs.getString("apellidos"));
                pasajero.setTelefono(rs.getString("telefono"));
                pasajero.setEmail(rs.getString("email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pasajero;
    }
    
    public boolean actualizarPasajero(Pasajero pasajero) {
        String sql = "UPDATE pasajero SET nombre = ?, apellidos = ?, telefono = ?, email = ? WHERE id_pasajero = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, pasajero.getNombre());
            ps.setString(2, pasajero.getApellidos());
            ps.setString(3, pasajero.getTelefono());
            ps.setString(4, pasajero.getEmail());
            ps.setInt(5, pasajero.getIdPasajero());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean eliminarPasajero(int id) {
        String sql = "DELETE FROM pasajero WHERE id_pasajero = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Pasajero> buscarPasajeros(String criterio) {
        List<Pasajero> pasajeros = new ArrayList<>();
        String sql = "SELECT * FROM pasajero WHERE nombre LIKE ? OR apellidos LIKE ? OR telefono LIKE ? ORDER BY apellidos";
        
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            String likeCriterio = "%" + criterio + "%";
            ps.setString(1, likeCriterio);
            ps.setString(2, likeCriterio);
            ps.setString(3, likeCriterio);
            
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Pasajero pasajero = new Pasajero();
                pasajero.setIdPasajero(rs.getInt("id_pasajero"));
                pasajero.setNombre(rs.getString("nombre"));
                pasajero.setApellidos(rs.getString("apellidos"));
                pasajero.setTelefono(rs.getString("telefono"));
                pasajero.setEmail(rs.getString("email"));
                pasajeros.add(pasajero);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pasajeros;
    }
}
