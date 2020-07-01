package com.company;


import controlP5.ControlEvent;
import controlP5.ControlP5;
import controlP5.DropdownList;
import processing.core.PApplet;   //Download processing add
import processing.core.PSurface;
import themidibus.MidiBus;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import java.io.IOException;
import java.util.Map;

public class Sketch3 extends PApplet {

  //  Sketch1 sketch1;        //create instance of other sketch so they know of each other
    IShedMidi shedMidi;
    Main tomain;

    ControlP5 cp5;
    DropdownList d1,d2;    //Dropdownlist from CP5
    int sliderValue = 100;      // for cp5 demo
    int myColorBackground = color(128);

    public Sketch3() {          //Constructor - super() calls Constructor of Parent Class (here PApplet)
        super();
    }

    public void runIt() {
        PApplet.runSketch(new String[]{this.getClass().getSimpleName()}, this);
    }


 /*  public void setOtherWindowRef(Sketch1 refOtherWin) {
        sketch1 = refOtherWin;
    }*/

    public void setIShedMidiRef(IShedMidi theShedMidi) {          // Setter Injection
        shedMidi = theShedMidi;
    }

    public void setMainRef(Main themain) {          // Setter Injection
        tomain = themain;
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
        this.surface.setTitle("*** MGA MUSIC SKETCH 3 ***");
        this.surface.setLocation(50,50);
gui();
    }

    public void draw() {
        background(myColorBackground);
        //fill(sliderValue);
        // rect(0, 0, width, 100);

        String s = "The quick brown fox jumps over the lazy dog. TheMidibus.";
        fill(50);
        text(s, 10, 10, 70, 80);  // Text wraps within text box

    }
/////////////////////MIDI SELECTION DROP DOWN/////////////
public  void gui() {            //  For dropdownlist on save and load songs ... replacement for ketai list on android version

    cp5 = new ControlP5(this);
    cp5.enableShortcuts();

    cp5 = new ControlP5(this);
    // create a DropdownList
    d1 = cp5.addDropdownList("myList-d1")
             .setPosition(100, 100)
         //   .setPosition((bblock * 1.2f) + 2 * bblock + bblock*12, (bblock / 2) + bblock * 8)
         //   .setHeight(bblock*2)
         //   .setWidth((int) (bblock*1.5f))
         //   .setOpen(false)
          //  .setItemHeight((int) (bblock))

            .setVisible(true)
    ;
    customize(d1); // customize the first list

    d2 = cp5.addDropdownList("myList-d2")
            .setPosition(200, 100)
            .setVisible(true)
    ;
    //customize(d2);
    d2.addItem("theMidiBus", 0);
    d2.addItem("JAVASYNTH", 1);
    }
    public void customize(DropdownList ddl) {
        // a convenience function to customize a DropdownList
        ddl.setBackgroundColor(color(190));
      //  ddl.setItemHeight(bblock/3);
        ddl.setBarHeight(30);
        ddl.setCaptionLabel("Midi Out...");
        // ddl.cap
        // ddl.setla
        /*ddl.captionLabel().set("dropdown");
        ddl.captionLabel().style().marginTop = 3;
        ddl.captionLabel().style().marginLeft = 3;
        ddl.valueLabel().style().marginTop = 3;*/


       // String[] theMidiOutputs= MidiBus.availableOutputs();
        String[] theMidiOutputs= shedMidi.getavailableOutputs();

        for (int i = 0; i < theMidiOutputs.length; i++) {
            System.out.println("MIDIOUT: " + theMidiOutputs[i]);
            ddl.addItem(theMidiOutputs[i], i);
        }


        //ddl.scroll(0);

        ddl.setColorBackground(color(60));
        ddl.setColorActive(color(255, 128));
    }
    public void controlEvent(ControlEvent theEvent) throws MidiUnavailableException, InvalidMidiDataException, IOException {
        // DropdownList is of type ControlGroup.
        // A controlEvent will be triggered from inside the ControlGroup class.
        // therefore you need to check the originator of the Event with
        // if (theEvent.isGroup())
        // to avoid an error message thrown by controlP5.

        if (theEvent.isGroup()) {
            // check if the Event was triggered from a ControlGroup
            println("event from group : "+theEvent.getGroup().getValue()+" from "+theEvent.getGroup());
            System.out.println("EVENT GROUP" );

        }
        else if (theEvent.isController()) {
            println("event from controller : "+theEvent.getController().getValue()+" from "+theEvent.getController());
            System.out.println("EVENT CONTROLLER" );

            if(theEvent.isFrom(d1)) {    //event from Dropdown List d1
                int port = (int) theEvent.getController().getValue();
                selectit(port);
            }

            if(theEvent.isFrom(d2)) {    //event from Dropdown List d1
                int port = (int) theEvent.getController().getValue();
                tomain.setMidiApi(port);
                //setIM(port);

            }
         //    shedMidi.setMidiOutputPort(port);

            Map<String,Object> item = d1.getItem((int)theEvent.getController().getValue());

            System.out.println( item.keySet().toString());

          //  d1.getItem((int)(int)theEvent.getController().getValue()).get("text");
            System.out.println(  d1.getItem((int)(int)theEvent.getController().getValue()));
            System.out.println(  d1.getItem((int)(int)theEvent.getController().getValue()).get("text").toString());

           //selectit(d1.getItem((int)(int)theEvent.getController().getValue()).get("text").toString());

        }
    }

    public void selectit(int port) {
        shedMidi.setMidiOutputPort(port);
    }


}