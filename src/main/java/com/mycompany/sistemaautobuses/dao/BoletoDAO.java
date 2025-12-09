/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemaautobuses.dao;

import modelo.Boleto;
import modelo.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BoletoDAO {
    private Connection conexion;
    
    public BoletoDAO() {
        try {
            conexion = Conexion.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public boolean insertarBoleto(Boleto boleto) {
        String sql = "INSERT INTO boleto (id_pasajero, id_autobus, origen, destino, fecha_viaje, hora_salida, asiento, precio) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, boleto.getIdPasajero());
            ps.setInt(2, boleto.getIdAutobus());
            ps.setInt(3, boleto.getOrigen());
            ps.setInt(4, boleto.getDestino());
            ps.setDate(5, new java.sql.Date(boleto.getFechaViaje().getTime()));
            ps.setString(6, boleto.getHoraSalida());
            ps.setString(7, boleto.getAsiento());
            ps.setDouble(8, boleto.getPrecio());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Boleto> obtenerTodosBoletos() {
        List<Boleto> boletos = new ArrayList<>();
        String sql = "SELECT b.*, p.nombre as nombre_pasajero, p.apellidos, a.placa, " +
                    "c1.nombre as ciudad_origen, c2.nombre as ciudad_destino " +
                    "FROM boleto b " +
                    "JOIN pasajero p ON b.id_pasajero = p.id_pasajero " +
                    "JOIN autobus a ON b.id_autobus = a.id_autobus " +
                    "JOIN ciudad c1 ON b.origen = c1.id_ciudad " +
                    "JOIN ciudad c2 ON b.destino = c2.id_ciudad " +
                    "ORDER BY b.fecha_viaje DESC";
        
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Boleto boleto = new Boleto();
                boleto.setIdBoleto(rs.getInt("id_boleto"));
                boleto.setIdPasajero(rs.getInt("id_pasajero"));
                boleto.setIdAutobus(rs.getInt("id_autobus"));
                boleto.setOrigen(rs.getInt("origen"));
                boleto.setDestino(rs.getInt("destino"));
                boleto.setFechaViaje(rs.getDate("fecha_viaje"));
                boleto.setHoraSalida(rs.getString("hora_salida"));
                boleto.setAsiento(rs.getString("asiento"));
                boleto.setPrecio(rs.getDouble("precio"));
                
                boleto.setNombrePasajero(rs.getString("nombre_pasajero") + " " + rs.getString("apellidos"));
                boleto.setPlacaAutobus(rs.getString("placa"));
                boleto.setNombreOrigen(rs.getString("ciudad_origen"));
                boleto.setNombreDestino(rs.getString("ciudad_destino"));
                
                boletos.add(boleto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return boletos;
    }
    
    public Boleto obtenerBoletoPorId(int id) {
        Boleto boleto = null;
        String sql = "SELECT b.*, p.nombre as nombre_pasajero, p.apellidos, a.placa, " +
                    "c1.nombre as ciudad_origen, c2.nombre as ciudad_destino " +
                    "FROM boleto b " +
                    "JOIN pasajero p ON b.id_pasajero = p.id_pasajero " +
                    "JOIN autobus a ON b.id_autobus = a.id_autobus " +
                    "JOIN ciudad c1 ON b.origen = c1.id_ciudad " +
                    "JOIN ciudad c2 ON b.destino = c2.id_ciudad " +
                    "WHERE b.id_boleto = ?";
        
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                boleto = new Boleto();
                boleto.setIdBoleto(rs.getInt("id_boleto"));
                boleto.setIdPasajero(rs.getInt("id_pasajero"));
                boleto.setIdAutobus(rs.getInt("id_autobus"));
                boleto.setOrigen(rs.getInt("origen"));
                boleto.setDestino(rs.getInt("destino"));
                boleto.setFechaViaje(rs.getDate("fecha_viaje"));
                boleto.setHoraSalida(rs.getString("hora_salida"));
                boleto.setAsiento(rs.getString("asiento"));
                boleto.setPrecio(rs.getDouble("precio"));
                
                boleto.setNombrePasajero(rs.getString("nombre_pasajero") + " " + rs.getString("apellidos"));
                boleto.setPlacaAutobus(rs.getString("placa"));
                boleto.setNombreOrigen(rs.getString("ciudad_origen"));
                boleto.setNombreDestino(rs.getString("ciudad_destino"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return boleto;
    }
    
    public boolean actualizarBoleto(Boleto boleto) {
        String sql = "UPDATE boleto SET id_pasajero = ?, id_autobus = ?, origen = ?, destino = ?, " +
                    "fecha_viaje = ?, hora_salida = ?, asiento = ?, precio = ? WHERE id_boleto = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, boleto.getIdPasajero());
            ps.setInt(2, boleto.getIdAutobus());
            ps.setInt(3, boleto.getOrigen());
            ps.setInt(4, boleto.getDestino());
            ps.setDate(5, new java.sql.Date(boleto.getFechaViaje().getTime()));
            ps.setString(6, boleto.getHoraSalida());
            ps.setString(7, boleto.getAsiento());
            ps.setDouble(8, boleto.getPrecio());
            ps.setInt(9, boleto.getIdBoleto());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean eliminarBoleto(int id) {
        String sql = "DELETE FROM boleto WHERE id_boleto = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<String> obtenerAsientosOcupados(int idAutobus, String fecha, String hora) {
        List<String> asientos = new ArrayList<>();
        String sql = "SELECT asiento FROM boleto WHERE id_autobus = ? AND fecha_viaje = ? AND hora_salida = ?";
        
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idAutobus);
            ps.setString(2, fecha);
            ps.setString(3, hora);
            
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                asientos.add(rs.getString("asiento"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return asientos;
    }
}
