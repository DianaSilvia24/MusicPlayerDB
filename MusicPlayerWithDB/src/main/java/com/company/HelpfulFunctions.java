package com.company;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class HelpfulFunctions {

    Clip clip=null;
    long clipTimeStamp=0;
    public void playSong(File f) {
        try {
            AudioInputStream song = AudioSystem.getAudioInputStream(f);
            clipTimeStamp=0;
            if(f.exists()){
                clip=AudioSystem.getClip();
                clip.open(song);
                clip.start();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public Clip getClip() {
        return clip;
    }

    public void pauseSong(boolean pauseCheck){
        if(!pauseCheck){
            clipTimeStamp=clip.getMicrosecondPosition();
            clip.stop();
        }
        else{
            clip.setMicrosecondPosition(clipTimeStamp);
            clip.start();
        }
    }
    public void replay(){
        clipTimeStamp=0;
        clip.stop();
        clip.setMicrosecondPosition(0);
        clip.start();
    }
    public void stopSong(){
        clipTimeStamp=0;
        clip.stop();
    }
}
