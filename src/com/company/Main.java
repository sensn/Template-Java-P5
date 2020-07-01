package com.company;

import processing.core.PApplet;
import processing.core.PSurface;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import java.io.IOException;

public class Main {
    IShedMidi shedMidi;
    Sketch1 sketch1;
    Sketch2 sketch2;
    Sketch3 sketch3;

    public static void main(String[] args) throws MidiUnavailableException, InvalidMidiDataException, IOException {
	    // entry Point
        Main themain;
        themain= new Main();
        themain.run();
    }

    public void run() throws MidiUnavailableException, InvalidMidiDataException, IOException {
                    // Create an instance (object) of Interface IShedMidi
        shedMidi =new MShedMidi();                  // assign shedMidi to be of type MShedMidi  -- using theMidiBus
        //shedMidi =new JShedMidi();             //  or for Java Synth implementation of Interface IShedMidi



        sketch1 =new Sketch1();                     // create sketches -- calls Constructor
        sketch2 =new Sketch2();

        sketch1.setOtherWindowRef(sketch2);         // setter Dependency injection - used so the two sketches can communicate...
        sketch2.setOtherWindowRef(sketch1);

        sketch1.setIShedMidiRef(shedMidi);           //set dependency reference of shedMidi - so all  Sketches can use "shedMidi.sendMidi()" ...
        sketch2.setIShedMidiRef(shedMidi);

        sketch1.runIt();                                 // actuallly run the sketches...
        sketch2.runIt();

        //rooool another one...
                                  // Here i create another Sketch - used for selecting Midi ports when the Midibus is used - Mshedmidi
        sketch3 = new Sketch3();                        // it doesn't now about the other two sketches but
        sketch3.setIShedMidiRef(shedMidi);
        sketch3.setMainRef(this);
        // here I set IshedMidi Reference (to select Output ports)...
        sketch3.runIt();

    }
    public void setMidiApi(int sel) throws MidiUnavailableException, InvalidMidiDataException, IOException {
        if(sel==0) {
            shedMidi=new MShedMidi();
        }else{
            shedMidi=new JShedMidi();
        }
        sketch1.setIShedMidiRef(shedMidi);           //set dependency reference of shedMidi - so all  Sketches can use "shedMidi.sendMidi()" ...
        sketch2.setIShedMidiRef(shedMidi);
        sketch3.setIShedMidiRef(shedMidi);
    }
}
