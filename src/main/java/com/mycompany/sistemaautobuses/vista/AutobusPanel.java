/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.sistemaautobuses.vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import controlador.AutobusController;
import modelo.Autobus;
import java.awt.*;
import java.util.List;

public class AutobusPanel extends JPanel {
    private JTable tablaAutobuses;
    private DefaultTableModel modeloTabla;
    private JTextField txtModelo, txtCapacidad, txtPlaca;
    private JComboBox<String> cmbEstatus;
    private JButton btnAgregar, btnActualizar, btnEliminar, btnLimpiar;
    private AutobusController controller;
    private int autobusSeleccionadoId = -1;
    
    public AutobusPanel() {
        controller = new AutobusController();
        initComponents();
        cargarAutobuses();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        
        JPanel panelFormulario = new JPanel(new GridLayout(5, 2, 10, 10));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos del Autobús"));
        
        panelFormulario.add(new JLabel("Modelo:"));
        txtModelo = new JTextField();
        panelFormulario.add(txtModelo);
        
        panelFormulario.add(new JLabel("Capacidad:"));
        txtCapacidad = new JTextField();
        panelFormulario.add(txtCapacidad);
        
        panelFormulario.add(new JLabel("Placa:"));
        txtPlaca = new JTextField();
        panelFormulario.add(txtPlaca);
        
        panelFormulario.add(new JLabel("Estatus:"));
        cmbEstatus = new JComboBox<>(new String[]{"Activo", "Inactivo", "En Mantenimiento"});
        panelFormulario.add(cmbEstatus);
        
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
        
        modeloTabla = new DefaultTableModel(new String[]{"ID", "Modelo", "Capacidad", "Placa", "Estatus"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaAutobuses = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaAutobuses);
        
        add(panelFormulario, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        
        tablaAutobuses.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablaAutobuses.getSelectedRow() != -1) {
                int fila = tablaAutobuses.getSelectedRow();
                autobusSeleccionadoId = Integer.parseInt(modeloTabla.getValueAt(fila, 0).toString());
                txtModelo.setText(modeloTabla.getValueAt(fila, 1).toString());
                txtCapacidad.setText(modeloTabla.getValueAt(fila, 2).toString());
                txtPlaca.setText(modeloTabla.getValueAt(fila, 3).toString());
                cmbEstatus.setSelectedItem(modeloTabla.getValueAt(fila, 4).toString());
                btnAgregar.setEnabled(false);
                btnActualizar.setEnabled(true);
                btnEliminar.setEnabled(true);
            }
        });
        
        btnAgregar.addActionListener(e -> agregarAutobus());
        btnActualizar.addActionListener(e -> actualizarAutobus());
        btnEliminar.addActionListener(e -> eliminarAutobus());
        btnLimpiar.addActionListener(e -> limpiarFormulario());
    }
    
    private void cargarAutobuses() {
        modeloTabla.setRowCount(0);
        List<Autobus> autobuses = controller.obtenerTodosAutobuses();
        for (Autobus autobus : autobuses) {
            modeloTabla.addRow(new Object[]{
                autobus.getIdAutobus(),
                autobus.getModelo(),
                autobus.getCapacidad(),
                autobus.getPlaca(),
                autobus.getEstatus()
            });
        }
    }
    
    private void agregarAutobus() {
        if (validarCampos()) {
            Autobus autobus = new Autobus();
            autobus.setModelo(txtModelo.getText());
            autobus.setCapacidad(Integer.parseInt(txtCapacidad.getText()));
            autobus.setPlaca(txtPlaca.getText());
            autobus.setEstatus(cmbEstatus.getSelectedItem().toString());
            
            if (controller.insertarAutobus(autobus)) {
                JOptionPane.showMessageDialog(this, "Autobús agregado exitosamente");
                limpiarFormulario();
                cargarAutobuses();
            } else {
                JOptionPane.showMessageDialog(this, "Error al agregar autobús", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void actualizarAutobus() {
        if (validarCampos() && autobusSeleccionadoId != -1) {
            Autobus autobus = new Autobus();
            autobus.setIdAutobus(autobusSeleccionadoId);
            autobus.setModelo(txtModelo.getText());
            autobus.setCapacidad(Integer.parseInt(txtCapacidad.getText()));
            autobus.setPlaca(txtPlaca.getText());
            autobus.setEstatus(cmbEstatus.getSelectedItem().toString());
            
            if (controller.actualizarAutobus(autobus)) {
                JOptionPane.showMessageDialog(this, "Autobús actualizado exitosamente");
                limpiarFormulario();
                cargarAutobuses();
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar autobús", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void eliminarAutobus() {
        if (autobusSeleccionadoId != -1) {
            int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de eliminar este autobús?", "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);
            
            if (confirmacion == JOptionPane.YES_OPTION) {
                if (controller.eliminarAutobus(autobusSeleccionadoId)) {
                    JOptionPane.showMessageDialog(this, "Autobús eliminado exitosamente");
                    limpiarFormulario();
                    cargarAutobuses();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar autobús", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    
    private void limpiarFormulario() {
        txtModelo.setText("");
        txtCapacidad.setText("");
        txtPlaca.setText("");
        cmbEstatus.setSelectedIndex(0);
        autobusSeleccionadoId = -1;
        tablaAutobuses.clearSelection();
        btnAgregar.setEnabled(true);
        btnActualizar.setEnabled(false);
        btnEliminar.setEnabled(false);
    }
    
    private boolean validarCampos() {
        if (txtModelo.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El modelo es requerido", "Validación", JOptionPane.WARNING_MESSAGE);
            txtModelo.requestFocus();
            return false;
        }
        if (txtCapacidad.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "La capacidad es requerida", "Validación", JOptionPane.WARNING_MESSAGE);
            txtCapacidad.requestFocus();
            return false;
        }
        if (txtPlaca.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "La placa es requerida", "Validación", JOptionPane.WARNING_MESSAGE);
            txtPlaca.requestFocus();
            return false;
        }
        try {
            int capacidad = Integer.parseInt(txtCapacidad.getText());
            if (capacidad <= 0) {
                JOptionPane.showMessageDialog(this, "La capacidad debe ser mayor a 0", "Validación", JOptionPane.WARNING_MESSAGE);
                txtCapacidad.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "La capacidad debe ser un número válido", "Validación", JOptionPane.WARNING_MESSAGE);
            txtCapacidad.requestFocus();
            return false;
        }
        return true;
    }
}
