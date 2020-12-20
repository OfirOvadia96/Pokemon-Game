package gameClient;

import gameClient.*;
import javax.sound.sampled.*;
import javax.swing.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;


/*
 * This class use JFrame class
 * This class will work with the user who will play the game
 */

public class myFrame extends JFrame {
    MyPanel The_panel;
    Arena The_Game;
    
    public myFrame() { //constructor
        super();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    
    public void InitFrame(Arena The_Arena) {
    	The_Game = The_Arena;
        initPanel(); 
    }

   //Initialize the panel on which the graph of its vertices will be drawn by the Pokemon agents and all other data
    public void initPanel() {
    	The_panel = new MyPanel(The_Game);
        this.add(The_panel);
    }

    
    public MyPanel getPanel() {
        return The_panel;
    }

}