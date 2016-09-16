import java.util.ArrayList;
import java.util.List;

import ddf.minim.AudioBuffer;
import processing.core.PApplet;

public class WaveFormCircle {
	private C screen;
	
	LineGroup group = new LineGroup();

	private float x;

	private float y;

	public WaveFormCircle(C screen, int points, float radius, float x, float y) {
		this.screen = screen;
		this.x = x;
		this.y = y;
		double radians_per_point = (PApplet.PI*2)/points;
		
		WaveFormLine previousLine = null;
		float initialX = (float) Math.cos(0) * radius;
		float initialY = (float) Math.sin(0) * radius;
		for(int i = 1; i <= points; i++){

			float xpos = (float) Math.cos(radians_per_point*i) * radius;
			float ypos = (float) Math.sin(radians_per_point*i) * radius;

			double xprev;
			double yprev;
			if(previousLine != null){
				xprev = previousLine.bx;
				yprev = previousLine.by;
			}
			else{
				xprev = initialX;
				yprev = initialY;
			}
			
			
			WaveFormLine line = new WaveFormLine(xprev, yprev, xpos, ypos);
//			System.out.println("xprev" + xprev + " yprev" + yprev + " xpos" + xpos + " ypos" + ypos + " index " + i);
			group.add(line);
			previousLine = line;
			
		}
		
		
	}
	
	int counter = 0;
	public void draw(AudioBuffer buffer, float scale){
		counter++;
		if(counter > 100*group.lines.size()-1){
			counter = 0;
		}
		screen.pushMatrix();
		screen.translate(x, y);
		screen.beginShape(PApplet.QUADS);
		screen.stroke(0);
		screen.fill(0, 255, 0);

//		screen.scale(scale+0.5f);
//		screen.stroke(gray);
		group.draw(screen, buffer);
//		group.lines.get(counter/100).draw(screen, buffer.toArray());
		
		screen.endShape();
		screen.popMatrix();
	}

}
