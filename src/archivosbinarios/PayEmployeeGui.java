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
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class PayEmployeeGui extends JFrame implements ActionListener{
JPanel panelPay = new JPanel();
    JLabel lblTitulo = new JLabel();
    JButton botonRegresar = new JButton();
    JLabel lblCodigo = new JLabel();
    JTextArea txtCodigo = new JTextArea();
    JButton botonPagar = new JButton();
    
    public PayEmployeeGui() {
        setSize(500,500);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        panelPay.setSize(500, 500);
        panelPay.setLayout(null);
        add(panelPay);
        
        lblTitulo.setText("Pagar al Empleado");
        lblTitulo.setBounds(120, 30, 280, 35);
        lblTitulo.setFont(new Font(null, Font.PLAIN, 30));
        lblTitulo.setForeground(Color.black);
        panelPay.add(lblTitulo); 
        
        botonRegresar.setText("Regresar");
        botonRegresar.setBounds(100, 400, 130, 30);
        botonRegresar.addActionListener(this);
        botonRegresar.setBackground(Color.white);
        panelPay.add(botonRegresar);
        
        lblCodigo.setText("Codigo: ");
        lblCodigo.setBounds(100, 200, 50, 30);
        panelPay.add(lblCodigo); 
        
        txtCodigo.setText("");
        txtCodigo.setBounds(180, 200, 200, 30);
        panelPay.add(txtCodigo);
        
        botonPagar.setText("Pagar");
        botonPagar.setBounds(250, 400, 130, 30);
        botonPagar.addActionListener(this);
        botonPagar.setBackground(Color.white);
        panelPay.add(botonPagar);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botonRegresar) {
            MenuPrincipal menu = new MenuPrincipal();
            menu.setVisible(true);
            this.dispose();
        }
        
        if (e.getSource() == botonPagar) {
    String code = txtCodigo.getText();
    int codigo = Integer.parseInt(code);
    
    EmpleadosManager emp = new EmpleadosManager();
    try {
        emp.payEmployee(codigo);
    } catch (IOException ex) {
        System.out.println("Error no se le pudo pagar al empleado");
    }
}

    }
}
