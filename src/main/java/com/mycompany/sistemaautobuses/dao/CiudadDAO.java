/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemaautobuses.dao;

import modelo.Ciudad;
import modelo.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CiudadDAO {
    private Connection conexion;
    
    public CiudadDAO() {
        try {
            conexion = Conexion.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public boolean insertarCiudad(Ciudad ciudad) {
        String sql = "INSERT INTO ciudad (nombre, estado) VALUES (?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, ciudad.getNombre());
            ps.setString(2, ciudad.getEstado());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Ciudad> obtenerTodasCiudades() {
        List<Ciudad> ciudades = new ArrayList<>();
        String sql = "SELECT * FROM ciudad ORDER BY nombre";
        
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Ciudad ciudad = new Ciudad();
                ciudad.setIdCiudad(rs.getInt("id_ciudad"));
                ciudad.setNombre(rs.getString("nombre"));
                ciudad.setEstado(rs.getString("estado"));
                ciudades.add(ciudad);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ciudades;
    }
    
    public Ciudad obtenerCiudadPorId(int id) {
        Ciudad ciudad = null;
        String sql = "SELECT * FROM ciudad WHERE id_ciudad = ?";
        
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                ciudad = new Ciudad();
                ciudad.setIdCiudad(rs.getInt("id_ciudad"));
                ciudad.setNombre(rs.getString("nombre"));
                ciudad.setEstado(rs.getString("estado"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ciudad;
    }
    
    public boolean actualizarCiudad(Ciudad ciudad) {
        String sql = "UPDATE ciudad SET nombre = ?, estado = ? WHERE id_ciudad = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, ciudad.getNombre());
            ps.setString(2, ciudad.getEstado());
            ps.setInt(3, ciudad.getIdCiudad());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean eliminarCiudad(int id) {
        String sql = "DELETE FROM ciudad WHERE id_ciudad = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
