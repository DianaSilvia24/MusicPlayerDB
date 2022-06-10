package com.company;

import javax.persistence.*;
import java.io.File;

@Entity
@Table(name="songs")
@NamedQueries({
        @NamedQuery(name = "Song.findByName", query = "select c from Song c where c.songName = :name"),
        @NamedQuery(name = "Song.findAll", query = "select c from Song c"),
        @NamedQuery(name="Song.findByPath",query="select c from Song c where c.songPath= :songPath"),
        @NamedQuery(name="Song.findById",query="select c from Song c where c.id= :id")
})
public class Song {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id=-1;

    File file=null;

    @Column(name="songName")
    String songName=null;

    @Column(name="songPath")
    String songPath=null;

    @Column(name="replayNo")
    int replayNumber=0;

    public Song(){

    }
    Song(File f){
        file=f;
        songName=f.getName();
        songPath=f.getPath();
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSongPath() {
        return songPath;
    }

    public void setSongPath(String songPath) {
        this.songPath = songPath;
    }

    public int compareTo(Song c){
        if(c.replayNumber>this.replayNumber) return 1;
        else if(c.replayNumber<this.replayNumber) return -1;
        else return 0;
    }
    public void replay(){
        replayNumber++;
    }
    public int getId(){
        return id;
    }
    public void setReplayNumber(int x){
        this.replayNumber=x;
    }
    public int getReplayNumber(){
        return this.replayNumber;
    }
}
