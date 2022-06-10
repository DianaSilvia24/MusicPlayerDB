package com.company;

import javax.swing.*;
import java.awt.*;

public class Menu extends JFrame {
    PlayerGUI meniu_initial;

    public Menu(){
        this.setVisible(true);
        this.setSize(600, 600);
        this.setTitle("MusicPlayer");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);
        meniu_initial=new PlayerGUI(this);
    }
}
