package com.company;//import themidibus.MidiBus;

import themidibus.*;

public class MShedMidi implements IShedMidi {                                  //TODO This is a mess combination of Midibus and Javasynth, maybe implement as Interface
    MidiBus myBus; // The MidiBus
  //  Sketch3 sketch3;         //select the output from a cp5 drop doen list in Sketch3!
    byte[] buffer1 = new byte[3];
   
    public MShedMidi()  {
        //
        MidiBus.list();

      //  myBus = new MidiBus(this, 0, 3);    //!!!
   //     myBus = new MidiBus(this, 0, 3);    //!!!
       myBus = new MidiBus(this, -1, "Microsoft GS Wavetable Synth");
       
    }


    @Override
    public void setMidiOutputPort(int thePortNumber) {
        myBus = new MidiBus(this, 0,thePortNumber );
    }

    @Override
    public void sendMidi(int thenote, int thevelocity, int thechannel) {
        //Send MIDI note data ...

        byte[] buffer = new byte[32];
        int numBytes = 0;
        int channel = 1; // MIDI channels 1-16 are encoded as 0-15.
        buffer[numBytes++] = (byte)(0x90 + (thechannel - 1)); // note on
        buffer[numBytes++] = (byte)thenote; // closed hi hat
        buffer[numBytes++] = (byte)thevelocity; // max velocity
        int offset = 0;
        // post is non-blocking
        try {
            //inputPort.send(buffer, offset, numBytes);                            
            myBus.sendNoteOn(thechannel, thenote, thevelocity); // Send a Midi noteOn
            //System.out.println("data sent");
            //  System.out.System.out.println("CHANNEL: "+thechannel +"NOTE" +thenote + "VELOCITY" +thevelocity );
        }
        catch (Exception e) {
            //  System.out.println("error sending midi data");
        }
    }
    @Override
    public void sendMidioff(int thenote, int thechannel) {
        //Send MIDI note data ... for info .send() is a methid from MidiReceiver class which

        byte[] buffer = new byte[32];
        int numBytes = 0;
        int channel = 1; // MIDI channels 1-16 are encoded as 0-15.
        buffer[numBytes++] = (byte)(0x90 + (thechannel - 1)); // note on
        buffer[numBytes++] = (byte)thenote; // closed hi hat
        buffer[numBytes++] = (byte)0; // max velocity
        int offset = 0;
        // post is non-blocking
        try {
            // inputPort.send(buffer, offset, numBytes);
              myBus.sendNoteOff(thechannel, thenote, 0); // Send a Midi nodeOff
            // System.out.println("data sent");
        }
        catch (Exception e) {
            //  System.out.println("error sending midi data");
        }
    }
    @Override
    public void sendMidiPrg(int thechannel, int prgnumber) {

        byte[] buffer = new byte[32];
        int numBytes = 0;
        int channel = 1; // MIDI channels 1-16 are encoded as 0-15.
        buffer[numBytes++] = (byte)(191+thechannel); // Prgchange
        buffer[numBytes++] = (byte)prgnumber; // Prgnumber
        int offset = 0;
        // post is non-blocking
        try {
            //   inputPort.send(buffer, offset, numBytes);
            myBus.sendMessage(buffer);
//a.set
            // a.setMessage(191, thechannel,  prgnumber );
            // synthRcvr.send(a,0);          //TODO
            // System.out.println("data sent");
           System.out.println( "PRDCHANGE");
        }
        catch (Exception e) {
            //   System.out.println("error sending midi data");
           System.out.println( "ERROR PRGCHANGE");

        }
    }

    @Override
    public void sendMidiBank(int thechannel, int thebank, int theprg) {

        byte[] buffer = new byte[32];
        int numBytes = 0;
        int channel = 1; // MIDI channels 1-16 are encoded as 0-15.
        buffer[numBytes++] = (byte)(175+thechannel); // Prgchange
        buffer[numBytes++] = (byte)0;
        buffer[numBytes++] = (byte)thebank;  // Prgnumber
        buffer[numBytes++] = (byte)32;
        buffer[numBytes++] = (byte)0;
        buffer[numBytes++] = (byte)(191+thechannel);
        buffer[numBytes++] = (byte)theprg;
        int offset = 0;
        // post is non-blocking
        try {
            //inputPort.send(buffer, offset, numBytes);
             myBus.sendMessage(buffer);
           System.out.println(" SHEDMIDI :BANK: " + thebank +"Prg: " +theprg + "Channel:" +thechannel );
        }
        catch (Exception e) {
            //  System.out.println("error sending midi data");
          System.out.println("ERROR SHEDMIDI :BANK: " + thebank +"Prg: " +theprg + "Channel:" +thechannel );
        }
    }

    @Override
    public void sendMidiVol(int thechannel, int thevalue) {

        byte[] buffer = new byte[32];
        int numBytes = 0;
        int channel = 1; // MIDI channels 1-16 are encoded as 0-15.
        buffer[numBytes++] = (byte)(175+thechannel); // Prgchange
        buffer[numBytes++] = (byte)7; // Volume
        buffer[numBytes++] = (byte)thevalue; // Volume
        int offset = 0;
        // post is non-blocking
        try {
            // inputPort.send(buffer, offset, numBytes);
            myBus.sendMessage(buffer);
           //System.out.println("data sent");
        }
        catch (Exception e) {
            // System.out.println("error sending midi data");
        }
    }
    @Override
    public void sendMidiRel(int thechannel, int thevalue) {

        byte[] buffer = new byte[32];
        int numBytes = 0;
        int channel = 1; // MIDI channels 1-16 are encoded as 0-15.
        buffer[numBytes++] = (byte)(175+thechannel); // Prgchange   //fluidynth release time
        buffer[numBytes++] = (byte)99;
        buffer[numBytes++] = (byte)120;
        buffer[numBytes++] = (byte)98;
        buffer[numBytes++] = (byte)38;
        buffer[numBytes++] = (byte)38;
        buffer[numBytes++] = (byte)66;
        buffer[numBytes++] = (byte)6;
        buffer[numBytes++] = (byte)thevalue; // Volume
        int offset = 0;
        // post is non-blocking
        try {
            //inputPort.send(buffer, offset, numBytes);
            myBus.sendMessage(buffer);

         
/*
https://nickfever.com/Music/midi-cc-list
                1 Modulation
                5 Portamento  //65 Portamonto switch // Doesnt work with javasysnth
                8 Ballance       //92 Effect 2 Depth tremolo amount

                71 VCF Reso
                72 VCA Release
                73 ATTACK
                74 VcF Cutoff
                91 Effect 1 Depth reverb send

                93	Effect 3 Depth	Usually controls chorus amount
*/

           /*c1.setMessage(ShortMessage.CONTROL_CHANGE, thechannel, 65,  127);
            synthRcvr.send(c1,-1);
            c1.setMessage(ShortMessage.CONTROL_CHANGE, thechannel, 84,  thevalue);
            synthRcvr.send(c1,-1);*/
         //   c1.setMessage(ShortMessage.CONTROL_CHANGE, thechannel, 8,  thevalue);
         //   synthRcvr.send(c1,-1);

            // c1.setMessage(ShortMessage.CONTROL_CHANGE, thechannel, 74,  thevalue);
            // synthRcvr.send(c1,-1);

           System.out.println( "RELEASE: " + thevalue);
            //   System.out.println("data sent");
        }
        catch (Exception e) {
            //  System.out.println("error sending midi data");
        }
    }

    @Override
    public void sendMidialloff(int thechannel) {


        buffer1[0] = (byte)(175+thechannel); // Prgchange
        buffer1[1] = (byte)123; // Volume
        buffer1[2] = (byte)0; // Volume

        // post is non-blocking
        try {
            // inputPort.send(buffer1, 0, 3);
            myBus.sendMessage(buffer1);
       
            //  System.out.println("data sent");
        }
        catch (Exception e) {
            //  System.out.println("error sending midi data");
        }
    }
    @Override
    public void sendMidiStart() {
        //Send MIDI note data ... for info .send() is a method from MidiReceiver class which
        byte[] buffer = new byte[32];
        int numBytes = 0;
        int channel = 1; // MIDI channels 1-16 are encoded as 0-15.
        buffer[numBytes++] = (byte)(176); // note on
        buffer[numBytes++] = (byte)(116);
        buffer[numBytes++] = (byte)(127);
        int offset = 0;
        // post is non-blocking
        try {
            //inputPort.send(buffer, offset, numBytes);
            myBus.sendMessage(buffer);
            // System.out.println("data sent");
        }
        catch (Exception e) {
            //  System.out.println("error sending midi data");
        }
    }
    @Override
    public void sendMidiClock() {
        //Send MIDI note data ... for info .send() is a methid from MidiReceiver class which
        byte[] buffer = new byte[32];
        int numBytes = 0;
        int channel = 1; // MIDI channels 1-16 are encoded as 0-15.
        buffer[numBytes++] = (byte)(248); // note on

        int offset = 0;
        // post is non-blocking
        try {
            //inputPort.send(buffer, offset, numBytes);
            myBus.sendMessage(buffer);
            //System.out.println("data sent");
        }
        catch (Exception e) {
            // System.out.println("error sending midi data");
        }
    }

    @Override
    public String[] getavailableOutputs() {
        return MidiBus.availableOutputs();
    }

}
