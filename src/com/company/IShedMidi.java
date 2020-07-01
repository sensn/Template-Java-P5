package com.company;

public interface IShedMidi {
    void setMidiOutputPort(int thePortNumber);

    void sendMidi(int thenote, int thevelocity, int thechannel);

    void sendMidioff(int thenote, int thechannel);

    void sendMidiPrg(int thechannel, int prgnumber);

    void sendMidiBank(int thechannel, int thebank, int theprg);

    void sendMidiVol(int thechannel, int thevalue);

    void sendMidiRel(int thechannel, int thevalue);

    void sendMidialloff(int thechannel);

    void sendMidiStart();

    void sendMidiClock();

    String[] getavailableOutputs();
}
