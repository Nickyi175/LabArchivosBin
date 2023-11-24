/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package archivosbinarios;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MenuPrincipal extends JFrame implements ActionListener {
    JPanel panelMenu = new JPanel();
    JButton botonAddEmployee = new JButton();
    JButton botonEmployeeList = new JButton();
    JButton botonAddSale = new JButton();
    JButton botonPayEmployee = new JButton();
    JButton botonFireEmployee = new JButton();
    JButton botonExit = new JButton();
    JLabel lblMenu = new JLabel();
    
    
    public MenuPrincipal() {
        setSize(500,700);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        panelMenu.setSize(500, 700);
        panelMenu.setLayout(null);
        add(panelMenu);
        
        lblMenu.setText("MENU PRINCIPAL");
        lblMenu.setBounds(120, 30, 280, 35);
        lblMenu.setFont(new Font(null, Font.PLAIN, 30));
        lblMenu.setForeground(Color.black);
        panelMenu.add(lblMenu);        
        
        botonAddEmployee.setText("Agregar Empleado");
        botonAddEmployee.setBounds(175, 100, 140, 30);
        botonAddEmployee.addActionListener(this);
        botonAddEmployee.setBackground(Color.white);
        panelMenu.add(botonAddEmployee);
        
        botonEmployeeList.setText("Lista de empleados activos");
        botonEmployeeList.setBounds(145, 200, 200, 30);
        botonEmployeeList.addActionListener(this);
        botonEmployeeList.setBackground(Color.white);
        panelMenu.add(botonEmployeeList);
        
        botonAddSale.setText("Agregar Venta");
        botonAddSale.setBounds(145, 300, 200, 30);
        botonAddSale.addActionListener(this);
        botonAddSale.setBackground(Color.white);
        panelMenu.add(botonAddSale);
        
        botonPayEmployee.setText("Pagar Empleado");
        botonPayEmployee.setBounds(145, 400, 200, 30);
        botonPayEmployee.addActionListener(this);
        botonPayEmployee.setBackground(Color.white);
        panelMenu.add(botonPayEmployee);
        
        botonFireEmployee.setText("Despedir empleado");
        botonFireEmployee.setBounds(170, 500, 150, 30);
        botonFireEmployee.addActionListener(this);
        botonFireEmployee.setBackground(Color.white);
        panelMenu.add(botonFireEmployee);        
        
        botonExit.setText("Salir");
        botonExit.setBounds(180, 600, 130, 30);
        botonExit.addActionListener(this);
        botonExit.setBackground(Color.white);
        panelMenu.add(botonExit);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botonExit) 
            System.exit(0);
        
        if (e.getSource() == botonAddEmployee) {
            addEmployeeGui add = new addEmployeeGui();
            add.setVisible(true);
            this.dispose();
        }
        
        if (e.getSource() == botonEmployeeList) {
            ListadoEmpleadosActivos lista = new ListadoEmpleadosActivos();
            lista.setVisible(true);
            this.dispose();
        }
        
        if (e.getSource() == botonFireEmployee) {
            FireEmployeeGui fire = new FireEmployeeGui();
            fire.setVisible(true);
            this.dispose();
        }
        
        if (e.getSource() == botonPayEmployee) {
            PayEmployeeGui pay = new PayEmployeeGui();
            pay.setVisible(true);
            this.dispose();
        }
        
        if (e.getSource() == botonAddSale) {
            VentasGUI ventas = new VentasGUI();
            ventas.setVisible(true);
            this.dispose();
        }
        
    }    
}

