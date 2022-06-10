package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.*;
import java.util.List;

import static java.lang.System.exit;

public class PlayerGUI extends JFrame implements ActionListener {

    HelpfulFunctions functii=new HelpfulFunctions();
    SongRepository songRepo=new SongRepository();
    Menu meniu;
    JLabel title=null;
    JButton chooseFolderButton=null;
    JButton playFavouriteSong=null;
    JButton quitButton=null;
    String activeFolderPath=null;
    Music musicPlayer=null;
    public PlayerGUI(Menu m){
        this.meniu=m;
        title=new JLabel(" Music ♥\n");
        title.setBounds(20,240,250,100);
        title.setFont(new Font("Radio Canada", Font.BOLD, 45));
        meniu.add(title);

        chooseFolderButton=new JButton("Choose Music Folder");
        chooseFolderButton.setFont(new Font("Radio Canada",Font.BOLD,13));
        chooseFolderButton.setBounds(320,30,200,160);
        chooseFolderButton.addActionListener(this);
        meniu.add(chooseFolderButton);

        playFavouriteSong=new JButton("Play ♥ song");
        playFavouriteSong.setBounds(320,220,200,160);
        playFavouriteSong.setFont(new Font("Radio Canada",Font.BOLD,13));
        meniu.add(playFavouriteSong);
        playFavouriteSong.addActionListener(this);

        quitButton=new JButton("Quit");
        quitButton.setBounds(150,450,250,80);
        quitButton.setFont(new Font("Radio Canada",Font.BOLD,13));
        quitButton.addActionListener(this);
        meniu.add(quitButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource().equals(quitButton)){
            System.out.println("quitButton pressed");
            exit(1);
        }
        else if (e.getSource().equals(playFavouriteSong)){
            List<Song> melodiiPref=new ArrayList<Song>();
            melodiiPref=songRepo.getMostListened().subList(0,2);
            System.out.println("Your top 3 songs are: ");
            for(Song p:melodiiPref){
                System.out.println(p.getSongName()+' '+p.getReplayNumber());
            }
            System.out.println("Now playing: "+melodiiPref.get(0).getSongName());
            functii.playSong(melodiiPref.get(0).getFile());
        }
        else if(e.getSource().equals(chooseFolderButton)){
            if(functii.clip!=null){
                functii.stopSong();
            }
            System.out.println("Choose folder");
            JFileChooser chooseActiveFolder=new JFileChooser();
            chooseActiveFolder.setCurrentDirectory(new java.io.File("C:\\Users\\Diana\\Music\\"));
            chooseActiveFolder.setDialogTitle("Change Sync Folder");
            chooseActiveFolder.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooseActiveFolder.setAcceptAllFileFilterUsed(false);
            if (chooseActiveFolder.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                activeFolderPath=chooseActiveFolder.getCurrentDirectory().getAbsolutePath();
                System.out.println(activeFolderPath);
                meniu.remove(title);
                meniu.remove(chooseFolderButton);
                meniu.remove(quitButton);
                meniu.remove(playFavouriteSong);
                meniu.remove(title);
                meniu.revalidate();
                meniu.repaint();
                musicPlayer=new Music(meniu,activeFolderPath);
            }
        }
    }
}
