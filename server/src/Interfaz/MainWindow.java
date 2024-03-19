package Interfaz;

import Logica.CustomServer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow {

    public static void main(String[] args) {
        JFrame f=new JFrame();//creating instance of JFrame
        JButton btnOpenCommunity=new JButton("Open Community");//creating instance of JButton


        btnOpenCommunity.setBounds(1035,10,140, 40);//x axis, y axis, width, height
        f.add(btnOpenCommunity);//adding button in JFrame
        f.setSize(1200,800);//400 width and 500 height
        f.setLayout(null);//using no layout managers
        f.setVisible(true);//making the frame visible
        btnOpenCommunity.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("PRESS");
                CustomServer newServer = new CustomServer();
                newServer.OpenServer();

            };
        });
    }
}
