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


public class addEmployeeGui extends JFrame implements ActionListener {
    JPanel panelAdd = new JPanel();
    JLabel lblTitulo = new JLabel();
    JTextArea txtnombre = new JTextArea();
    JTextArea txtsalario = new JTextArea();
    JButton botonRegresar = new JButton();
    JLabel lblNombre = new JLabel();
    JLabel lblsalario = new JLabel();
    JButton botonAgregar = new JButton();
    
    public addEmployeeGui() {
        setSize(500,500);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        panelAdd.setSize(500, 500);
        panelAdd.setLayout(null);
        add(panelAdd);
        
        lblTitulo.setText("Agregar Empleado");
        lblTitulo.setBounds(120, 30, 280, 35);
        lblTitulo.setFont(new Font(null, Font.PLAIN, 30));
        lblTitulo.setForeground(Color.black);
        panelAdd.add(lblTitulo); 
        
        botonRegresar.setText("Regresar");
        botonRegresar.setBounds(100, 400, 130, 30);
        botonRegresar.addActionListener(this);
        botonRegresar.setBackground(Color.white);
        panelAdd.add(botonRegresar);
        
        txtnombre.setText("");
        txtnombre.setBounds(200, 150, 200, 30);
        panelAdd.add(txtnombre);
        
        txtsalario.setBounds(200,225,200,30);
        txtsalario.setText("");
        panelAdd.add(txtsalario);
        
        botonAgregar.setText("Agregar");
        botonAgregar.setBounds(250,400,130,30);
        botonAgregar.addActionListener(this);
        botonAgregar.setBackground(Color.white);
        panelAdd.add(botonAgregar);
        
        lblNombre.setText("Nombre: ");
        lblNombre.setBounds(100, 150, 100, 30);
        panelAdd.add(lblNombre);
        
        lblsalario.setText("Salario: ");
        lblsalario.setBounds(100, 225, 110, 30);
        panelAdd.add(lblsalario);
    }    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botonRegresar) {
            MenuPrincipal menu = new MenuPrincipal();
            menu.setVisible(true);
            this.dispose();
        }
        
        if (e.getSource() == botonAgregar) {
            String nombre, salario;
            double salary;
            nombre = txtnombre.getText();
            salario = txtsalario.getText();
            salary = Double.parseDouble(salario);
            
            EmpleadosManager emp = new EmpleadosManager();
            
            try {
                emp.addEmployes(nombre, salary);
                //JOptionPane.showMessageDialog(null, "Se agrego el empleado " + nombre + " con un salario de $" + salario);
                MenuPrincipal menu = new MenuPrincipal();
                menu.setVisible(true);
                this.dispose();
            } catch (IOException ex) {
                System.out.println("Error, no se pudo agregar el empleado");
            }
            
        }
    }
    
}

