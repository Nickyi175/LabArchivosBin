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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class ListadoEmpleadosActivos extends JFrame implements ActionListener { 
    JPanel panelLista = new JPanel();
    JLabel lblTitulo = new JLabel();
    JButton botonRegresar = new JButton();
    JTextArea textArea = new JTextArea();
    JScrollPane ScrollLista = new JScrollPane(textArea);
    JLabel codigoLabel = new JLabel();
    JLabel nombreLabel = new JLabel();
    JLabel salarioLabel = new JLabel();
    JLabel fechaLabel = new JLabel();
    EmpleadosManager emp = new EmpleadosManager();
    String list;
    
    public ListadoEmpleadosActivos() {
        setSize(500,500);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        panelLista.setSize(500, 500);
        panelLista.setLayout(null);
        add(panelLista);
        
        lblTitulo.setText("Lista de empleados activos");
        lblTitulo.setBounds(60, 30, 390, 35);
        lblTitulo.setFont(new Font(null, Font.PLAIN, 30));
        lblTitulo.setForeground(Color.black);
        panelLista.add(lblTitulo); 
        
        try {
            list = emp.listado();
        } catch (IOException ex) {
            System.out.println("Error no se puede mostrar el listado");
        }
        textArea.setText(list);
        textArea.setBounds(50, 100, 400, 300);
        //panelLista.add(textArea);
        
        ScrollLista.setBounds(50, 100, 400, 300);
        panelLista.add(ScrollLista);
        
        botonRegresar.setText("Regresar");
        botonRegresar.setBounds(100, 420, 130, 30);
        botonRegresar.addActionListener(this);
        botonRegresar.setBackground(Color.white);
        panelLista.add(botonRegresar);
        
        codigoLabel.setText("Codigo");
        codigoLabel.setBounds(40, 100, WIDTH, HEIGHT);
        panelLista.add(codigoLabel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botonRegresar) {
            MenuPrincipal menu = new MenuPrincipal();
            menu.setVisible(true);
            this.dispose();
        }
    }
}

