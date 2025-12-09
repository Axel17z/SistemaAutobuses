/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.sistemaautobuses.vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import controlador.BoletoController;
import controlador.CiudadController;
import controlador.AutobusController;
import controlador.PasajeroController;
import modelo.Boleto;
import modelo.Ciudad;
import modelo.Autobus;
import modelo.Pasajero;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BoletoPanel extends JPanel {
    private JTable tablaBoletos;
    private DefaultTableModel modeloTabla;
    private JComboBox<Pasajero> cmbPasajero;
    private JComboBox<Autobus> cmbAutobus;
    private JComboBox<Ciudad> cmbOrigen, cmbDestino;
    private JTextField txtFechaViaje, txtHoraSalida, txtAsiento, txtPrecio;
    private JButton btnAgregar, btnActualizar, btnEliminar, btnLimpiar, btnVerAsientos;
    private BoletoController boletoController;
    private CiudadController ciudadController;
    private AutobusController autobusController;
    private PasajeroController pasajeroController;
    private int boletoSeleccionadoId = -1;
    
    public BoletoPanel() {
        boletoController = new BoletoController();
        ciudadController = new CiudadController();
        autobusController = new AutobusController();
        pasajeroController = new PasajeroController();
        initComponents();
        cargarCombos();
        cargarBoletos();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        
        JPanel panelFormulario = new JPanel(new GridLayout(8, 2, 10, 10));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos del Boleto"));
        
        panelFormulario.add(new JLabel("Pasajero:"));
        cmbPasajero = new JComboBox<>();
        panelFormulario.add(cmbPasajero);
        
        panelFormulario.add(new JLabel("Autobús:"));
        cmbAutobus = new JComboBox<>();
        panelFormulario.add(cmbAutobus);
        
        panelFormulario.add(new JLabel("Origen:"));
        cmbOrigen = new JComboBox<>();
        panelFormulario.add(cmbOrigen);
        
        panelFormulario.add(new JLabel("Destino:"));
        cmbDestino = new JComboBox<>();
        panelFormulario.add(cmbDestino);
        
        panelFormulario.add(new JLabel("Fecha Viaje (YYYY-MM-DD):"));
        txtFechaViaje = new JTextField(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        panelFormulario.add(txtFechaViaje);
        
        panelFormulario.add(new JLabel("Hora Salida (HH:MM):"));
        txtHoraSalida = new JTextField("08:00");
        panelFormulario.add(txtHoraSalida);
        
        panelFormulario.add(new JLabel("Asiento:"));
        JPanel panelAsiento = new JPanel(new FlowLayout());
        txtAsiento = new JTextField(5);
        btnVerAsientos = new JButton("Ver Disponibles");
        panelAsiento.add(txtAsiento);
        panelAsiento.add(btnVerAsientos);
        panelFormulario.add(panelAsiento);
        
        panelFormulario.add(new JLabel("Precio:"));
        txtPrecio = new JTextField();
        panelFormulario.add(txtPrecio);
        
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
        
        modeloTabla = new DefaultTableModel(new String[]{"ID", "Pasajero", "Autobús", "Origen", "Destino", "Fecha", "Hora", "Asiento", "Precio"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaBoletos = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaBoletos);
        
        add(panelFormulario, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        
        tablaBoletos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablaBoletos.getSelectedRow() != -1) {
                int fila = tablaBoletos.getSelectedRow();
                boletoSeleccionadoId = Integer.parseInt(modeloTabla.getValueAt(fila, 0).toString());
                
                Boleto boleto = boletoController.obtenerBoletoPorId(boletoSeleccionadoId);
                if (boleto != null) {
                    seleccionarPasajero(boleto.getIdPasajero());
                    seleccionarAutobus(boleto.getIdAutobus());
                    seleccionarOrigen(boleto.getOrigen());
                    seleccionarDestino(boleto.getDestino());
                    txtFechaViaje.setText(new SimpleDateFormat("yyyy-MM-dd").format(boleto.getFechaViaje()));
                    txtHoraSalida.setText(boleto.getHoraSalida());
                    txtAsiento.setText(boleto.getAsiento());
                    txtPrecio.setText(String.valueOf(boleto.getPrecio()));
                    
                    btnAgregar.setEnabled(false);
                    btnActualizar.setEnabled(true);
                    btnEliminar.setEnabled(true);
                }
            }
        });
        
        btnAgregar.addActionListener(e -> agregarBoleto());
        btnActualizar.addActionListener(e -> actualizarBoleto());
        btnEliminar.addActionListener(e -> eliminarBoleto());
        btnLimpiar.addActionListener(e -> limpiarFormulario());
        btnVerAsientos.addActionListener(e -> verAsientosDisponibles());
    }
    
    private void cargarCombos() {
        // Cargar pasajeros
        cmbPasajero.removeAllItems();
        List<Pasajero> pasajeros = pasajeroController.obtenerTodosPasajeros();
        for (Pasajero pasajero : pasajeros) {
            cmbPasajero.addItem(pasajero);
        }
        
        // Cargar autobuses activos
        cmbAutobus.removeAllItems();
        List<Autobus> autobuses = autobusController.obtenerAutobusesActivos();
        for (Autobus autobus : autobuses) {
            cmbAutobus.addItem(autobus);
        }
        
        // Cargar ciudades
        cmbOrigen.removeAllItems();
        cmbDestino.removeAllItems();
        List<Ciudad> ciudades = ciudadController.obtenerTodasCiudades();
        for (Ciudad ciudad : ciudades) {
            cmbOrigen.addItem(ciudad);
            cmbDestino.addItem(ciudad);
        }
    }
    
    private void seleccionarPasajero(int idPasajero) {
        for (int i = 0; i < cmbPasajero.getItemCount(); i++) {
            if (cmbPasajero.getItemAt(i).getIdPasajero() == idPasajero) {
                cmbPasajero.setSelectedIndex(i);
                break;
            }
        }
    }
    
    private void seleccionarAutobus(int idAutobus) {
        for (int i = 0; i < cmbAutobus.getItemCount(); i++) {
            if (cmbAutobus.getItemAt(i).getIdAutobus() == idAutobus) {
                cmbAutobus.setSelectedIndex(i);
                break;
            }
        }
    }
    
    private void seleccionarOrigen(int idCiudad) {
        for (int i = 0; i < cmbOrigen.getItemCount(); i++) {
            if (cmbOrigen.getItemAt(i).getIdCiudad() == idCiudad) {
                cmbOrigen.setSelectedIndex(i);
                break;
            }
        }
    }
    
    private void seleccionarDestino(int idCiudad) {
        for (int i = 0; i < cmbDestino.getItemCount(); i++) {
            if (cmbDestino.getItemAt(i).getIdCiudad() == idCiudad) {
                cmbDestino.setSelectedIndex(i);
                break;
            }
        }
    }
    
    private void cargarBoletos() {
        modeloTabla.setRowCount(0);
        List<Boleto> boletos = boletoController.obtenerTodosBoletos();
        for (Boleto boleto : boletos) {
            modeloTabla.addRow(new Object[]{
                boleto.getIdBoleto(),
                boleto.getNombrePasajero(),
                boleto.getPlacaAutobus(),
                boleto.getNombreOrigen(),
                boleto.getNombreDestino(),
                new SimpleDateFormat("yyyy-MM-dd").format(boleto.getFechaViaje()),
                boleto.getHoraSalida(),
                boleto.getAsiento(),
                String.format("$%.2f", boleto.getPrecio())
            });
        }
    }
    
    private void agregarBoleto() {
        if (validarCampos()) {
            Boleto boleto = new Boleto();
            boleto.setIdPasajero(((Pasajero)cmbPasajero.getSelectedItem()).getIdPasajero());
            boleto.setIdAutobus(((Autobus)cmbAutobus.getSelectedItem()).getIdAutobus());
            boleto.setOrigen(((Ciudad)cmbOrigen.getSelectedItem()).getIdCiudad());
            boleto.setDestino(((Ciudad)cmbDestino.getSelectedItem()).getIdCiudad());
            
            try {
                boleto.setFechaViaje(new SimpleDateFormat("yyyy-MM-dd").parse(txtFechaViaje.getText()));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Fecha inválida", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            boleto.setHoraSalida(txtHoraSalida.getText());
            boleto.setAsiento(txtAsiento.getText());
            boleto.setPrecio(Double.parseDouble(txtPrecio.getText()));
            
            if (boletoController.insertarBoleto(boleto)) {
                JOptionPane.showMessageDialog(this, "Boleto agregado exitosamente");
                limpiarFormulario();
                cargarBoletos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al agregar boleto", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void actualizarBoleto() {
        if (validarCampos() && boletoSeleccionadoId != -1) {
            Boleto boleto = new Boleto();
            boleto.setIdBoleto(boletoSeleccionadoId);
            boleto.setIdPasajero(((Pasajero)cmbPasajero.getSelectedItem()).getIdPasajero());
            boleto.setIdAutobus(((Autobus)cmbAutobus.getSelectedItem()).getIdAutobus());
            boleto.setOrigen(((Ciudad)cmbOrigen.getSelectedItem()).getIdCiudad());
            boleto.setDestino(((Ciudad)cmbDestino.getSelectedItem()).getIdCiudad());
            
            try {
                boleto.setFechaViaje(new SimpleDateFormat("yyyy-MM-dd").parse(txtFechaViaje.getText()));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Fecha inválida", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            boleto.setHoraSalida(txtHoraSalida.getText());
            boleto.setAsiento(txtAsiento.getText());
            boleto.setPrecio(Double.parseDouble(txtPrecio.getText()));
            
            if (boletoController.actualizarBoleto(boleto)) {
                JOptionPane.showMessageDialog(this, "Boleto actualizado exitosamente");
                limpiarFormulario();
                cargarBoletos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar boleto", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void eliminarBoleto() {
        if (boletoSeleccionadoId != -1) {
            int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de eliminar este boleto?", "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);
            
            if (confirmacion == JOptionPane.YES_OPTION) {
                if (boletoController.eliminarBoleto(boletoSeleccionadoId)) {
                    JOptionPane.showMessageDialog(this, "Boleto eliminado exitosamente");
                    limpiarFormulario();
                    cargarBoletos();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar boleto", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    
    private void verAsientosDisponibles() {
        if (cmbAutobus.getSelectedItem() != null && !txtFechaViaje.getText().isEmpty() && !txtHoraSalida.getText().isEmpty()) {
            Autobus autobus = (Autobus) cmbAutobus.getSelectedItem();
            List<String> asientosOcupados = boletoController.obtenerAsientosOcupados(
                autobus.getIdAutobus(), 
                txtFechaViaje.getText(), 
                txtHoraSalida.getText()
            );
            
            StringBuilder mensaje = new StringBuilder("Asientos ocupados en este viaje:\n");
            if (asientosOcupados.isEmpty()) {
                mensaje.append("Ninguno - Todos los asientos están disponibles");
            } else {
                for (String asiento : as  mensaje.append(asiento);
                if (asientosOcupados.indexOf(asiento) < asientosOcupados.size() - 1) {
                    mensaje.append(", ");
                }
            }
            
            JOptionPane.showMessageDialog(this, mensaje.toString(), "Asientos Ocupados", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione autobús, fecha y hora primero", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void limpiarFormulario() {
        cmbPasajero.setSelectedIndex(0);
        cmbAutobus.setSelectedIndex(0);
        cmbOrigen.setSelectedIndex(0);
        cmbDestino.setSelectedIndex(0);
        txtFechaViaje.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        txtHoraSalida.setText("08:00");
        txtAsiento.setText("");
        txtPrecio.setText("");
        boletoSeleccionadoId = -1;
        tablaBoletos.clearSelection();
        btnAgregar.setEnabled(true);
        btnActualizar.setEnabled(false);
        btnEliminar.setEnabled(false);
    }
    
    private boolean validarCampos() {
        if (cmbPasajero.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un pasajero", "Validación", JOptionPane.WARNING_MESSAGE);
            cmbPasajero.requestFocus();
            return false;
        }
        if (cmbAutobus.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un autobús", "Validación", JOptionPane.WARNING_MESSAGE);
            cmbAutobus.requestFocus();
            return false;
        }
        if (cmbOrigen.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Seleccione origen", "Validación", JOptionPane.WARNING_MESSAGE);
            cmbOrigen.requestFocus();
            return false;
        }
        if (cmbDestino.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Seleccione destino", "Validación", JOptionPane.WARNING_MESSAGE);
            cmbDestino.requestFocus();
            return false;
        }
        if (cmbOrigen.getSelectedItem() == cmbDestino.getSelectedItem()) {
            JOptionPane.showMessageDialog(this, "Origen y destino deben ser diferentes", "Validación", JOptionPane.WARNING_MESSAGE);
            cmbOrigen.requestFocus();
            return false;
        }
        if (txtFechaViaje.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "La fecha es requerida", "Validación", JOptionPane.WARNING_MESSAGE);
            txtFechaViaje.requestFocus();
            return false;
        }
        if (txtHoraSalida.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "La hora es requerida", "Validación", JOptionPane.WARNING_MESSAGE);
            txtHoraSalida.requestFocus();
            return false;
        }
        if (txtAsiento.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El asiento es requerido", "Validación", JOptionPane.WARNING_MESSAGE);
            txtAsiento.requestFocus();
            return false;
        }
        if (txtPrecio.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El precio es requerido", "Validación", JOptionPane.WARNING_MESSAGE);
            txtPrecio.requestFocus();
            return false;
        }
        try {
            double precio = Double.parseDouble(txtPrecio.getText());
            if (precio <= 0) {
                JOptionPane.showMessageDialog(this, "El precio debe ser mayor a 0", "Validación", JOptionPane.WARNING_MESSAGE);
                txtPrecio.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El precio debe ser un número válido", "Validación", JOptionPane.WARNING_MESSAGE);
            txtPrecio.requestFocus();
            return false;
        }
        return true;
    }
}
