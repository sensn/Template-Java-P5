package com.company;

import processing.core.PApplet;
import processing.core.PSurface;

public class Main {

    public static void main(String[] args) {
	    // entry Point

        Sketch1 sketch1;
        Sketch2 sketch2;

        sketch1 =new Sketch1();
        sketch2 =new Sketch2();

        sketch1.setOtherWindowRef(sketch2);
        sketch2.setOtherWindowRef(sketch1);

        sketch1.runIt();
        sketch2.runIt();

    }
}
