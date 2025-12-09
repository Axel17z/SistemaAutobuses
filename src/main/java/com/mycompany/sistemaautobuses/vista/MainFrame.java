/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.sistemaautobuses.vista;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private JTabbedPane tabbedPane;
    
    public MainFrame() {
        setTitle("Sistema de Gestión de Autobuses");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        
        initComponents();
    }
    
    private void initComponents() {
        tabbedPane = new JTabbedPane();
        
        CiudadPanel ciudadPanel = new CiudadPanel();
        AutobusPanel autobusPanel = new AutobusPanel();
        PasajeroPanel pasajeroPanel = new PasajeroPanel();
        BoletoPanel boletoPanel = new BoletoPanel();
        
        tabbedPane.addTab("🏙️ Ciudades", ciudadPanel);
        tabbedPane.addTab("🚌 Autobuses", autobusPanel);
        tabbedPane.addTab("👥 Pasajeros", pasajeroPanel);
        tabbedPane.addTab("🎫 Boletos", boletoPanel);
        
        add(tabbedPane, BorderLayout.CENTER);
        
        JMenuBar menuBar = new JMenuBar();
        JMenu menuArchivo = new JMenu("Archivo");
        JMenuItem menuSalir = new JMenuItem("Salir");
        menuSalir.addActionListener(e -> System.exit(0));
        menuArchivo.add(menuSalir);
        
        JMenu menuAyuda = new JMenu("Ayuda");
        JMenuItem menuAcercaDe = new JMenuItem("Acerca de");
        menuAcercaDe.addActionListener(e -> 
            JOptionPane.showMessageDialog(this, 
                "Sistema de Gestión de Autobuses\nVersión 1.0\n© 2024",
                "Acerca de", 
                JOptionPane.INFORMATION_MESSAGE)
        );
        menuAyuda.add(menuAcercaDe);
        
        menuBar.add(menuArchivo);
        menuBar.add(menuAyuda);
        setJMenuBar(menuBar);
        
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelInferior.add(new JLabel("Sistema de Gestión de Autobuses - Todos los derechos reservados"));
        add(panelInferior, BorderLayout.SOUTH);
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
