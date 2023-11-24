/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package archivosbinarios;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class VentasGUI extends JFrame implements ActionListener {
    JPanel panelFire = new JPanel();
    JLabel lblTitulo = new JLabel();
    JButton botonRegresar = new JButton();
    JLabel lblCodigo = new JLabel();
    JLabel lblMonto=new JLabel();
    JTextArea txtCodigo = new JTextArea();
    JTextArea txtMonto = new JTextArea();
    JButton botonAgregar = new JButton();
    
    public VentasGUI() {
        setSize(500,500);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        panelFire.setSize(500, 500);
        panelFire.setLayout(null);
        add(panelFire);
        
        lblTitulo.setText("Agregar Ventas");
        lblTitulo.setBounds(120, 30, 280, 35);
        lblTitulo.setFont(new Font(null, Font.PLAIN, 30));
        lblTitulo.setForeground(Color.black);
        panelFire.add(lblTitulo); 
        
        botonRegresar.setText("Regresar");
        botonRegresar.setBounds(100, 400, 130, 30);
        botonRegresar.addActionListener(this);
        botonRegresar.setBackground(Color.white);
        panelFire.add(botonRegresar);
        
        lblCodigo.setText("Codigo del empleado: ");
        lblCodigo.setBounds(60, 200, 150, 30);
        panelFire.add(lblCodigo); 
        
        lblMonto.setText("Monto de Venta: ");
        lblMonto.setBounds(95, 300, 150, 30);
        panelFire.add(lblMonto); 
        
        txtCodigo.setText("");
        txtCodigo.setBounds(200, 200, 180, 30);
        panelFire.add(txtCodigo);
        
        txtMonto.setText("");
        txtMonto.setBounds(200, 300, 180, 30);
        panelFire.add(txtMonto);
        
        botonAgregar.setText("Agregar");
        botonAgregar.setBounds(250, 400, 130, 30);
        botonAgregar.addActionListener(this);
        botonAgregar.setBackground(Color.white);
        panelFire.add(botonAgregar);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botonRegresar) {
            MenuPrincipal menu = new MenuPrincipal();
            menu.setVisible(true);
            this.dispose();
        }
        
        if (e.getSource() == botonAgregar) {
            String code = txtCodigo.getText();
            int codigo = Integer.parseInt(code); 
            String monto=txtMonto.getText();
            double amount=Double.parseDouble(monto);
            EmpleadosManager emp = new EmpleadosManager();
            try {
                emp.addSaleToEmployee(codigo,amount);
                MenuPrincipal menu = new MenuPrincipal();
                menu.setVisible(true);
                this.dispose();
            } catch (IOException ex) {
                System.out.println("Error no se pudo agregar");
            }
        }
    }
}