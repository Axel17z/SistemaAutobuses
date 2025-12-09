/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.sistemaautobuses.vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import controlador.CiudadController;
import modelo.Ciudad;
import java.awt.*;
import java.util.List;

public class CiudadPanel extends JPanel {
    private JTable tablaCiudades;
    private DefaultTableModel modeloTabla;
    private JTextField txtNombre, txtEstado;
    private JButton btnAgregar, btnActualizar, btnEliminar, btnLimpiar;
    private CiudadController controller;
    private int ciudadSeleccionadaId = -1;
    
    public CiudadPanel() {
        controller = new CiudadController();
        initComponents();
        cargarCiudades();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        
        JPanel panelFormulario = new JPanel(new GridLayout(3, 2, 10, 10));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos de Ciudad"));
        
        panelFormulario.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelFormulario.add(txtNombre);
        
        panelFormulario.add(new JLabel("Estado:"));
        txtEstado = new JTextField();
        panelFormulario.add(txtEstado);
        
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
        
        modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombre", "Estado"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaCiudades = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaCiudades);
        
        add(panelFormulario, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        
        tablaCiudades.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablaCiudades.getSelectedRow() != -1) {
                int fila = tablaCiudades.getSelectedRow();
                ciudadSeleccionadaId = Integer.parseInt(modeloTabla.getValueAt(fila, 0).toString());
                txtNombre.setText(modeloTabla.getValueAt(fila, 1).toString());
                txtEstado.setText(modeloTabla.getValueAt(fila, 2).toString());
                btnAgregar.setEnabled(false);
                btnActualizar.setEnabled(true);
                btnEliminar.setEnabled(true);
            }
        });
        
        btnAgregar.addActionListener(e -> agregarCiudad());
        btnActualizar.addActionListener(e -> actualizarCiudad());
        btnEliminar.addActionListener(e -> eliminarCiudad());
        btnLimpiar.addActionListener(e -> limpiarFormulario());
    }
    
    private void cargarCiudades() {
        modeloTabla.setRowCount(0);
        List<Ciudad> ciudades = controller.obtenerTodasCiudades();
        for (Ciudad ciudad : ciudades) {
            modeloTabla.addRow(new Object[]{
                ciudad.getIdCiudad(),
                ciudad.getNombre(),
                ciudad.getEstado()
            });
        }
    }
    
    private void agregarCiudad() {
        if (validarCampos()) {
            Ciudad ciudad = new Ciudad();
            ciudad.setNombre(txtNombre.getText());
            ciudad.setEstado(txtEstado.getText());
            
            if (controller.insertarCiudad(ciudad)) {
                JOptionPane.showMessageDialog(this, "Ciudad agregada exitosamente");
                limpiarFormulario();
                cargarCiudades();
            } else {
                JOptionPane.showMessageDialog(this, "Error al agregar ciudad", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void actualizarCiudad() {
        if (validarCampos() && ciudadSeleccionadaId != -1) {
            Ciudad ciudad = new Ciudad();
            ciudad.setIdCiudad(ciudadSeleccionadaId);
            ciudad.setNombre(txtNombre.getText());
            ciudad.setEstado(txtEstado.getText());
            
            if (controller.actualizarCiudad(ciudad)) {
                JOptionPane.showMessageDialog(this, "Ciudad actualizada exitosamente");
                limpiarFormulario();
                cargarCiudades();
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar ciudad", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void eliminarCiudad() {
        if (ciudadSeleccionadaId != -1) {
            int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de eliminar esta ciudad?", "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);
            
            if (confirmacion == JOptionPane.YES_OPTION) {
                if (controller.eliminarCiudad(ciudadSeleccionadaId)) {
                    JOptionPane.showMessageDialog(this, "Ciudad eliminada exitosamente");
                    limpiarFormulario();
                    cargarCiudades();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar ciudad", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    
    private void limpiarFormulario() {
        txtNombre.setText("");
        txtEstado.setText("");
        ciudadSeleccionadaId = -1;
        tablaCiudades.clearSelection();
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
        if (txtEstado.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El estado es requerido", "Validación", JOptionPane.WARNING_MESSAGE);
            txtEstado.requestFocus();
            return false;
        }
        return true;
    }
}
