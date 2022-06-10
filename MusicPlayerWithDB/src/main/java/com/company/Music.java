package com.company;

import javax.persistence.NonUniqueResultException;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.persistence.NoResultException;
import java.util.*;

public class Music extends JFrame implements ActionListener{

    SongRepository songRepo=new SongRepository();
    HelpfulFunctions command;
    Menu meniu=null;
    String folder=null;
    JButton pausePlayButton=null;
    JButton forwards=null;
    JButton backwards=null;
    JButton stopBttn=null;
    List<Song> melodii=null;
    JButton randomizeBttn=null;
    JButton replay=null;
    boolean isSongPaused=false;
    Random rd=null;
    long songTimeStamp;
    Clip clip=null;
    boolean shuffle;
    int indexMelodieActiva=0;
    List<Song> melodiiRedate=null;
    int indexMelodieInPlaylist=0;
    List<Song> cautareMelodii(File fisier){

        List<File> files= Arrays.asList(fisier.listFiles());
        List<Song>melodii=new ArrayList<Song>();
        for(File p:files){

            if(p.isDirectory()) {
                melodii.addAll(cautareMelodii(p));

            }
            else if(p.getName().endsWith(".wav")){
                try{
                    melodii.add(songRepo.getByPath(p.getAbsolutePath()));
                    System.out.println(p.getName());
                }
                catch(NoResultException e){
                    Song dummy=new Song(p);
                    songRepo.create(dummy);
                    melodii.add(dummy);
                }
                catch(NonUniqueResultException e){

                }

            }
        }
        return melodii;
    }

    public Music(Menu m,String folderPath){

        melodiiRedate=new LinkedList<Song>();
        this.folder=folderPath;
        this.meniu=m;
        meniu.setSize(600,300);
        this.folder=folderPath;
        System.out.println("Folderpath"+folder);
        File fisier=new File(folder);
        melodii=cautareMelodii(fisier);
        command=new HelpfulFunctions();
        rd=new Random();
        indexMelodieActiva=0;
        melodiiRedate.add(melodii.get(indexMelodieActiva));
        command.playSong(melodii.get(indexMelodieActiva).getFile());
        System.out.println("");
        /*for(Song q:melodii){
            System.out.println(q.songName);
        }(here for debug purposes*/

        System.out.println("Melodii redate: \n");
        System.out.println(melodii.get(indexMelodieActiva).getSongName());
        //lastSong button
        backwards=new JButton("Previous");
        backwards.setBounds(10,150,80,40);
        backwards.addActionListener(this);

        //stopBttn
        stopBttn=new JButton("Stop");
        stopBttn.setBounds(backwards.getX()+90,backwards.getY(),backwards.getWidth(),backwards.getHeight());
        stopBttn.addActionListener(this);

        // play/pause button
        pausePlayButton=new JButton("Play/Pause");
        pausePlayButton.setBounds(stopBttn.getX()+90,150,100,40);
        pausePlayButton.addActionListener(this);

        //nextSong button
        forwards=new JButton("Next");
        forwards.setBounds(pausePlayButton.getX()+pausePlayButton.getWidth()+10,150,80,40);
        forwards.addActionListener(this);

        //randomizeBttn

        randomizeBttn=new JButton("Shuffle");
        randomizeBttn.setBounds(forwards.getX()+90,forwards.getY(),forwards.getWidth(),forwards.getHeight());
        randomizeBttn.addActionListener(this);

        //replay
        replay=new JButton("Replay");
        replay.setBounds(randomizeBttn.getX()+90,forwards.getY(),forwards.getWidth(),forwards.getHeight());
        replay.addActionListener(this);

        //adding the buttons
        meniu.add(pausePlayButton);
        meniu.add(forwards);
        meniu.add(backwards);
        meniu.add(stopBttn);
        meniu.add(randomizeBttn);
        meniu.add(replay);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource().equals(pausePlayButton)) {
            command.pauseSong(isSongPaused);
            isSongPaused=!isSongPaused;
        }
        if(e.getSource().equals(replay)){
            isSongPaused=false;
            command.replay();
            songRepo.replayUpdate(melodii.get(indexMelodieActiva));
            System.out.println(melodii.get(indexMelodieActiva).replayNumber);
        }
        if(e.getSource().equals(forwards)){
            command.stopSong();
            if(shuffle){
                indexMelodieActiva=rd.nextInt(0, melodii.size())-1;
            }
            else{
                indexMelodieActiva++;
                if(indexMelodieActiva>(melodii.size()-1)){
                    indexMelodieActiva=0;
                }
            }
            if(!melodiiRedate.contains(melodii.get(indexMelodieActiva))){
                melodiiRedate.add(melodii.get(indexMelodieActiva));
            }
            indexMelodieInPlaylist=melodiiRedate.size()-1;
            command.playSong(melodii.get(indexMelodieActiva).getFile());
            System.out.println(melodii.get(indexMelodieActiva).getSongName());
        }

        if(e.getSource().equals(backwards)){
            if(indexMelodieInPlaylist>0){indexMelodieInPlaylist-=1;}
            command.stopSong();
            command.playSong(melodiiRedate.get(indexMelodieInPlaylist).getFile());
        }
        if(e.getSource().equals(randomizeBttn)){
            shuffle=!shuffle;
        }

        if(e.getSource().equals(stopBttn)){
            command.stopSong();
        }
    }
}
