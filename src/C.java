import processing.core.*;
import processing.data.*;
import processing.event.*;
import processing.opengl.*;

import ddf.minim.*;
import ddf.minim.analysis.*;
import ddf.minim.effects.*;
import ddf.minim.signals.*;
import ddf.minim.spi.*;
import ddf.minim.ugens.*;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;

public class C extends PApplet {

	float sphereSize = 100;
	boolean pulseDirection = false;
	int strokeSize = 127;
	AudioPlayer in;
	Minim minim;
	PFont f;
	EllipsisWaveformRenderer ellipsisWaveFormRenderer = new EllipsisWaveformRenderer(this);
	FFT fft;
	int bands = 512;
	float[] spectrum = new float[bands];
	private FFT fftLog;
	private BeatDetect beat;
	private Vandmand vandmand;
	private List<WaveFormCircle> waveCircles = new ArrayList<>();
	
//	private WaveFormLine line = new WaveFormLine(100, 100, 300, 200);
	public void setup() {
		minim = new Minim(this);
		vandmand = new Vandmand(this);

//		AudioPlayer billieJean = minim.loadFile("Mother Mar E  by Dizzy Love.mp3");
		AudioPlayer billieJean = minim.loadFile("06 - Billie Jean.mp3");
		// in = minim.getLineIn(Minim.STEREO, 512);
		in = billieJean;
		in.play();

		// create an FFT object for calculating logarithmically spaced averages
		fftLog = new FFT(in.bufferSize(), in.sampleRate());

		// calculate averages based on a miminum octave width of 22 Hz
		// split each octave into three bands
		// this should result in 30 averages
		fftLog.logAverages(22, 4);

		f = createFont("Arial", 16, true); // STEP 2 Create Font

		in.addListener(ellipsisWaveFormRenderer);
		fft = new FFT(in.bufferSize(), in.sampleRate());

		beat = new BeatDetect();
		beat.setSensitivity(500);
		// camera(width/2, height/2, 1000.0f, width/2, height/2, 0.0f,
		// 0.0f, 1.0f, 0.0f);
		// translate(50, 50, 0);
		// rotateX(-PI/6);
		// rotateY(PI/3);
		waveCircles.add(new WaveFormCircle(this, 40, 100f, 100, 150));
		waveCircles.add(new WaveFormCircle(this, 40, 100f, 300, 150));
		waveCircles.add(new WaveFormCircle(this, 20, 60f, 100, 350));
		waveCircles.add(new WaveFormCircle(this, 40, 100f, 300, 350));
		waveCircles.add(new WaveFormCircle(this, 40, 100f, 100, 550));
		waveCircles.add(new WaveFormCircle(this, 8, 60f, 300, 550));
		
		
	}

	int backgroundColor = 0;
	private boolean ellipsisToggle = false;
	private boolean sphereToggle = false;

	float sizeThing = 0;
	public void draw() {
		background(255);
		beat.detect(in.mix);
		if (beat.isOnset()) {
			vandmand.beat(in.mix.level());
			backgroundColor = 128;
		} else {
			backgroundColor *= 0.4;
		}
//		 background(backgroundColor);
		// controlCamera();
		lights();
		fftLog.forward(in.mix);
		if(sphereToggle){
			drawFrequencySpheres();
		}
		if (ellipsisToggle) {
			ellipsisWaveFormRenderer.draw();
		}
		// drawSphere(sphereSize*100, 500);
		// drawSphere(sphereSize*100, 200);
		// if(sphereSize > 200 || sphereSize < 0){
		// pulseDirection = !pulseDirection;
		// }
		// sphereSize += pulseDirection? 1: -1;
		// strokeSize += pulseDirection? -1: 1;

		//textFont(f, 16); // STEP 3 Specify font to be used
		// fill(255);
		// Boolean monitoring = in.isMonitoring();
		// text(sphereSize,10,100); // STEP 5 Display Text
		// if(buffer != null){
		// text(buffer.length, 50, 100);
		// for(int i = 0; i < buffer.length; i++){
		// text(buffer[i], 10, i*10);
		// }
		// }
		fft.forward(in.mix);
		if (reverse) {
			counter--;
		} else {
			counter++;
		}
		if (counter > 1560 || counter < 2) {
			reverse = !reverse;
		}
		// drawFrequencyBands(counter/10+1);
//		vandmand.draw(in.left);
//		line.draw(this, in.left);
		
		if(in.left.get(0) > 0.2f){
			sizeThing = in.left.get(0);
		}
		else{
			sizeThing *= 0.95f;
		}
		
		sizeThing = 1;
		waveCircles.stream().forEach(t -> t.draw(in.left, sizeThing));;
		// println(frameRate);
	}

	int counter = 1;
	boolean reverse = false;

	private void drawFrequencyBands(int numberOfBands) {
		int inverseGranularity = fft.specSize() / numberOfBands + 1;
		strokeWeight(inverseGranularity);
		for (int i = 0; i < fft.specSize(); i += inverseGranularity) {
			// draw the line for frequency band i, scaling it up a bit so we can
			// see it
			line(i + (inverseGranularity / 2), height - 100, i + (inverseGranularity / 2),
					height - 100 - fft.getBand(i));
		}
		strokeWeight(1);
	}

	private void controlCamera() {
		camera(width / 2, height / 2, 800.0f - counter, width / 2, height / 2, 0.0f, 0.0f, 1.0f, 0.0f);
		// translate(counter, counter, counter);
		// rotateX((counter*0.01f)%PI);
	}

	private void drawFrequencySpheres() {
		// since logarithmically spaced averages are not equally spaced
		// we can't precompute the width for all averages
		for (int i = 0; i < fftLog.avgSize() - 10; i++) {
			// float centerFrequency = fftLog.getAverageCenterFrequency(i);
			// // how wide is this average in Hz?
			// float averageWidth = fftLog.getAverageBandWidth(i);
			//
			// // we calculate the lowest and highest frequencies
			// // contained in this average using the center frequency
			// // and bandwidth of this average.
			// float lowFreq = centerFrequency - averageWidth/2;
			// float highFreq = centerFrequency + averageWidth/2;
			//
			// // freqToIndex converts a frequency in Hz to a spectrum band
			// index
			// // that can be passed to getBand. in this case, we simply use the
			// // index as coordinates for the rectangle we draw to represent
			// // the average.
			// int xl = (int)fftLog.freqToIndex(lowFreq);
			// int xr = (int)fftLog.freqToIndex(highFreq);
			//
			// // if the mouse is inside of this average's rectangle
			// // print the center frequency and set the fill color to red
			// if ( mouseX >= xl && mouseX < xr )
			// {
			// fill(255, 128);
			// text("Logarithmic Average Center Frequency: " + centerFrequency,
			// 5, height - 25);
			// fill(255, 0, 0);
			// }
			// else
			// {
			// fill(255);
			// }
			//
			drawSphere(fftLog.getAvg(i), i * 24);
		}
	}

	int timeCounter = 0;
	float targetSize = 0f;
	float sizeBuffer = 0f;

	public void drawSphere(float size, int position) {
		if (size < 2f) {
			size = 0f;
		}
		// if(size > 0.6f){
		// targetSize = size;
		// }
		// noFill();
		// if(sizeBuffer < targetSize){
		// sizeBuffer += 0.8f;
		// }
		// else{
		// targetSize = 0.0f;
		// sizeBuffer -= 0.2f;
		// }
		stroke(0);
		fill(128);
		pushMatrix();
		translate(position, height / 2, 0);
		sphere(size * 11 - size * 10.7f + 20);
		popMatrix();

	}

	public void settings() {
		size(800, 800, P3D);
	}

	static public void main(String[] passedArgs) {
		String[] appletArgs = new String[] { C.class.getName() };
		if (passedArgs != null)
			if (passedArgs != null) {
				PApplet.main(concat(appletArgs, passedArgs));
			} else {
				PApplet.main(appletArgs);
			}
	}
}

/**
 * Wicked cool effect by drawing things
 * 
 * 
 */
