package com.company;
import controlP5.ControlEvent;
import controlP5.ControlP5;
import processing.core.PApplet;   //Download processing add
import processing.core.PSurface;
public class Sketch2 extends PApplet {

    Sketch1 sketch1;        //create instance of other sketch so they know of each other 
    ControlP5 cp5;
    IShedMidi shedMidi;



    int sliderValue = 100;      // for cp5 demo
    int myColorBackground = color(128);



    public Sketch2() {          //Constructor - super() calls Constructor of Parent Class (here PApplet)
        super();
    }

    public void runIt() {
        PApplet.runSketch(new String[]{this.getClass().getSimpleName()}, this);
    }


    public void setOtherWindowRef(Sketch1 refOtherWin) {
        sketch1 = refOtherWin;
    }

    public void setIShedMidiRef(IShedMidi theShedMidi) {          // Setter Injection
        shedMidi = theShedMidi;
    }

    public void settings() {                  //neede for Processing in Java gets called before setuo()
        // size(300, 300, JAVA2D);
        size(700, 400);
        //   fullScreen(1);
        //   smooth(4);

        println("Main's  sketchPath: \t\"" + sketchPath("") + "\"");
        println("Main's  dataPath: \t\"" + dataPath("") + "\"\n");
    }

    public void setup() {
        //  noLoop();
        // frameRate(60);
        surface.setResizable(true);
        this.surface.setTitle("*** MGA MUSIC SKETCH 2 ***");
        this.surface.setLocation(100,100);
    }

    public void draw() {
        background(myColorBackground);
        //fill(sliderValue);
       // rect(0, 0, width, 100);

        String s = "The quick brown fox jumps over the Midibus.  ==> Click Canvas to send Midi Note-on Event! <<=   Ohh- and you don't need Jdk 14 - I guess Jdk 11 would be OK too.  ";
        fill(50);
        text(s, 10, 10, 150, 380);  // Text wraps within text box

    }

      public void mousePressed() {
//        sketch1.getSurface().setVisible(true);
    shedMidi.sendMidi(56,100,0);

    }

}