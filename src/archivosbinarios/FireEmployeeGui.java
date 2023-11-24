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


public class FireEmployeeGui extends JFrame implements ActionListener {

    JPanel panelFire = new JPanel();
    JLabel lblTitulo = new JLabel();
    JButton botonRegresar = new JButton();
    JLabel lblCodigo = new JLabel();
    JTextArea txtCodigo = new JTextArea();
    JButton botonDespedir = new JButton();

    public FireEmployeeGui() {
        setSize(500, 500);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panelFire.setSize(500, 500);
        panelFire.setLayout(null);
        add(panelFire);

        lblTitulo.setText("Despedir Empleado");
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
        lblCodigo.setBounds(100, 200, 50, 30);
        panelFire.add(lblCodigo);

        txtCodigo.setText("");
        txtCodigo.setBounds(180, 200, 200, 30);
        panelFire.add(txtCodigo);

        botonDespedir.setText("Despedir");
        botonDespedir.setBounds(250, 400, 130, 30);
        botonDespedir.addActionListener(this);
        botonDespedir.setBackground(Color.white);
        panelFire.add(botonDespedir);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botonRegresar) {
            MenuPrincipal menu = new MenuPrincipal();
            menu.setVisible(true);
            this.dispose();
        }

        if (e.getSource() == botonDespedir) {
            String code = txtCodigo.getText();
            int codigo = Integer.parseInt(code);

            EmpleadosManager emp = new EmpleadosManager();
            try {
                if (emp.fireEmployee(codigo)) {
                    JOptionPane.showMessageDialog(null, "Se despidio el empleado con codigo " + codigo);
                } else {
                    JOptionPane.showMessageDialog(null, "No existe" + codigo);
                }

            } catch (IOException ex) {
                System.out.println("Error no se pudo despedir el empleado");
            }
        }
    }
}
