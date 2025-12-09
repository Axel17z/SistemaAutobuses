/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.sistemaautobuses.vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import controlador.PasajeroController;
import modelo.Pasajero;
import java.awt.*;
import java.util.List;

public class PasajeroPanel extends JPanel {
    private JTable tablaPasajeros;
    private DefaultTableModel modeloTabla;
    private JTextField txtNombre, txtApellidos, txtTelefono, txtEmail, txtBuscar;
    private JButton btnAgregar, btnActualizar, btnEliminar, btnLimpiar, btnBuscar;
    private PasajeroController controller;
    private int pasajeroSeleccionadoId = -1;
    
    public PasajeroPanel() {
        controller = new PasajeroController();
        initComponents();
        cargarPasajeros();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        
        JPanel panelSuperior = new JPanel(new BorderLayout());
        
        JPanel panelBusqueda = new JPanel(new FlowLayout());
        panelBusqueda.add(new JLabel("Buscar:"));
        txtBuscar = new JTextField(20);
        panelBusqueda.add(txtBuscar);
        btnBuscar = new JButton("Buscar");
        panelBusqueda.add(btnBuscar);
        panelSuperior.add(panelBusqueda, BorderLayout.NORTH);
        
        JPanel panelFormulario = new JPanel(new GridLayout(5, 2, 10, 10));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos del Pasajero"));
        
        panelFormulario.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelFormulario.add(txtNombre);
        
        panelFormulario.add(new JLabel("Apellidos:"));
        txtApellidos = new JTextField();
        panelFormulario.add(txtApellidos);
        
        panelFormulario.add(new JLabel("Teléfono:"));
        txtTelefono = new JTextField();
        panelFormulario.add(txtTelefono);
        
        panelFormulario.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        panelFormulario.add(txtEmail);
        
        JPanel panelBotones = new JPanel(new FlowLayout());
        btnAgregar = new JButton("Agregar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");
        btnLimpiar = new JButton("Limpiar");
        
        btnActualizar.setEnabled(false);
        btnEliminar.setEnabled(false);
        
        panelBotones.add(btnAgregar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);
        
        panelFormulario.add(panelBotones);
        
        panelSuperior.add(panelFormulario, BorderLayout.CENTER);
        
        modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombre", "Apellidos", "Teléfono", "Email"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaPasajeros = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaPasajeros);
        
        add(panelSuperior, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        
        tablaPasajeros.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablaPasajeros.getSelectedRow() != -1) {
                int fila = tablaPasajeros.getSelectedRow();
                pasajeroSeleccionadoId = Integer.parseInt(modeloTabla.getValueAt(fila, 0).toString());
                txtNombre.setText(modeloTabla.getValueAt(fila, 1).toString());
                txtApellidos.setText(modeloTabla.getValueAt(fila, 2).toString());
                txtTelefono.setText(modeloTabla.getValueAt(fila, 3).toString());
                txtEmail.setText(modeloTabla.getValueAt(fila, 4).toString());
                btnAgregar.setEnabled(false);
                btnActualizar.setEnabled(true);
                btnEliminar.setEnabled(true);
            }
        });
        
        btnAgregar.addActionListener(e -> agregarPasajero());
        btnActualizar.addActionListener(e -> actualizarPasajero());
        btnEliminar.addActionListener(e -> eliminarPasajero());
        btnLimpiar.addActionListener(e -> limpiarFormulario());
        btnBuscar.addActionListener(e -> buscarPasajeros());
    }
    
    private void cargarPasajeros() {
        modeloTabla.setRowCount(0);
        List<Pasajero> pasajeros = controller.obtenerTodosPasajeros();
        for (Pasajero pasajero : pasajeros) {
            modeloTabla.addRow(new Object[]{
                pasajero.getIdPasajero(),
                pasajero.getNombre(),
                pasajero.getApellidos(),
                pasajero.getTelefono(),
                pasajero.getEmail()
            });
        }
    }
    
    private void buscarPasajeros() {
        String criterio = txtBuscar.getText().trim();
        if (!criterio.isEmpty()) {
            modeloTabla.setRowCount(0);
            List<Pasajero> pasajeros = controller.buscarPasajeros(criterio);
            for (Pasajero pasajero : pasajeros) {
                modeloTabla.addRow(new Object[]{
                    pasajero.getIdPasajero(),
                    pasajero.getNombre(),
                    pasajero.getApellidos(),
                    pasajero.getTelefono(),
                    pasajero.getEmail()
                });
            }
        } else {
            cargarPasajeros();
        }
    }
    
    private void agregarPasajero() {
        if (validarCampos()) {
            Pasajero pasajero = new Pasajero();
            pasajero.setNombre(txtNombre.getText());
            pasajero.setApellidos(txtApellidos.getText());
            pasajero.setTelefono(txtTelefono.getText());
            pasajero.setEmail(txtEmail.getText());
            
            if (controller.insertarPasajero(pasajero)) {
                JOptionPane.showMessageDialog(this, "Pasajero agregado exitosamente");
                limpiarFormulario();
                cargarPasajeros();
            } else {
                JOptionPane.showMessageDialog(this, "Error al agregar pasajero", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void actualizarPasajero() {
        if (validarCampos() && pasajeroSeleccionadoId != -1) {
            Pasajero pasajero = new Pasajero();
            pasajero.setIdPasajero(pasajeroSeleccionadoId);
            pasajero.setNombre(txtNombre.getText());
            pasajero.setApellidos(txtApellidos.getText());
            pasajero.setTelefono(txtTelefono.getText());
            pasajero.setEmail(txtEmail.getText());
            
            if (controller.actualizarPasajero(pasajero)) {
                JOptionPane.showMessageDialog(this, "Pasajero actualizado exitosamente");
                limpiarFormulario();
                cargarPasajeros();
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar pasajero", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void eliminarPasajero() {
        if (pasajeroSeleccionadoId != -1) {
            int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de eliminar este pasajero?", "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);
            
            if (confirmacion == JOptionPane.YES_OPTION) {
                if (controller.eliminarPasajero(pasajeroSeleccionadoId)) {
                    JOptionPane.showMessageDialog(this, "Pasajero eliminado exitosamente");
                    limpiarFormulario();
                    cargarPasajeros();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar pasajero", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    
    private void limpiarFormulario() {
        txtNombre.setText("");
        txtApellidos.setText("");
        txtTelefono.setText("");
        txtEmail.setText("");
        pasajeroSeleccionadoId = -1;
        tablaPasajeros.clearSelection();
        btnAgregar.setEnabled(true);
        btnActualizar.setEnabled(false);
        btnEliminar.setEnabled(false);
    }
    
    private boolean validarCampos() {
        if (txtNombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre es requerido", "Validación", JOptionPane.WARNING_MESSAGE);
            txtNombre.requestFocus();
            return false;
        }
        if (txtApellidos.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Los apellidos son requeridos", "Validación", JOptionPane.WARNING_MESSAGE);
            txtApellidos.requestFocus();
            return false;
        }
        if (!txtEmail.getText().trim().isEmpty() && !txtEmail.getText().contains("@")) {
            JOptionPane.showMessageDialog(this, "Email inválido", "Validación", JOptionPane.WARNING_MESSAGE);
            txtEmail.requestFocus();
            return false;
        }
        return true;
    }
}
