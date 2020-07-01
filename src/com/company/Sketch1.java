package com.company;
import controlP5.ControlEvent;
import controlP5.ControlP5;
import processing.core.PApplet;   //Download processing add
import processing.core.PSurface;
public class Sketch1 extends PApplet {

    Sketch2 sketch2;        //create instance of other sketch so they know of each other 
    ControlP5 cp5;

    int sliderValue = 100;      // for cp5 demo
    int myColorBackground = color(128);
    
    public Sketch1() {          //Constructor - super() calls Constructor of Parent Class (here PApplet)
        super();
    }

    public void runIt() {
        PApplet.runSketch(new String[]{this.getClass().getSimpleName()}, this);
    }


    public void setOtherWindowRef(Sketch2 refOtherWin) {
        sketch2 = refOtherWin;
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
        this.surface.setTitle("*** MGA MUSIC SKETCH 1 ***");
        gui();
    }

    public void draw() {
        background(myColorBackground);
        fill(sliderValue);
        rect(0, 0, width, 100);
    }



    ///////////***********///////////////

    public void gui() {
        // By default all controllers are stored inside Tab 'default'
        // add a second tab with name 'extra'
        cp5 = new ControlP5(this);
        cp5.addTab("extra")
                .setColorBackground(color(0, 160, 100))
                .setColorLabel(color(255))
                .setColorActive(color(255, 128, 0))
        ;

        // if you want to receive a controlEvent when
        // a  tab is clicked, use activeEvent(true)

        cp5.getTab("default")
                .activateEvent(true)
                .setLabel("my default tab")
                .setId(1)
        ;

        cp5.getTab("extra")
                .activateEvent(true)
                .setId(2)
        ;


        // create a few controllers

        cp5.addButton("button")
                .setBroadcast(false)
                .setPosition(100, 100)
                .setSize(80, 40)
                .setValue(1)
                .setBroadcast(true)
                .getCaptionLabel().align(CENTER, CENTER)

        ;

        cp5.addButton("buttonValue")
                .setBroadcast(false)
                .setPosition(220, 100)
                .setSize(80, 40)
                .setValue(2)
                .setBroadcast(true)
                .getCaptionLabel().align(CENTER, CENTER)
        ;

        cp5.addSlider("tempoSlider")
                .setBroadcast(false)
                .setRange(30, 300)
                .setValue(128)
                .setPosition(100, 160)
                .setSize(200, 20)
                .setBroadcast(true)
        ;

        cp5.addSlider("sliderValue")
                .setBroadcast(false)
                .setRange(0, 255)
                .setValue(128)
                .setPosition(100, 200)
                .setSize(200, 20)
                .setBroadcast(true)
        ;

        // arrange controller in separate tabs

        cp5.getController("sliderValue").moveTo("extra");
        // cp5.getController("sliderValue").moveTo("default");
        cp5.getController("tempoSlider").moveTo("global");

        // Tab 'global' is a tab that lies on top of any
        // other tab and is always visible

    }

    public void controlEvent(ControlEvent theControlEvent) {
        if (theControlEvent.isTab()) {
            println("got an event from tab : " + theControlEvent.getTab().getName() + " with id " + theControlEvent.getTab().getId());
        }
//      println("got an EVENT : "+theControlEvent.getTab().getName()+" with id "+theControlEvent.getTab().getId());
    }

    public void tempoSlider(int theTempo) {
        myColorBackground = color(theTempo);
        println("a slider event. setting background to " + theTempo);

        sketch2.myColorBackground=theTempo;    // Set backgroundcolor of other sketch !!!

    }


    public void keyPressed() {
        if (keyCode == TAB) {
            cp5.getTab("extra").bringToFront();
        }
    }






  /* public void mousePressed() {
//        sketch2.getSurface().setVisible(true);
    }*/

    static final void removeExitEvent(final PSurface surf) {                         // Don't know if this is needed
        final java.awt.Window win
                = ((processing.awt.PSurfaceAWT.SmoothCanvas) surf.getNative()).getFrame();

        for (final java.awt.event.WindowListener evt : win.getWindowListeners())
            win.removeWindowListener(evt);
    }

}
    
