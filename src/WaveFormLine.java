import processing.core.PApplet;

public class WaveFormLine {

	double ax;
	double ay;
	double bx;
	double by;
	// private float slope;
	private double unitX;
	private double unitY;
	private double magnitude;

	public WaveFormLine(double ax, double ay, double bx, double by) {
		this.ax = ax;
		this.ay = ay;

		this.bx = bx;
		this.by = by;

		double normalX = (bx - ax);
		double normalY = (by - ay);
//		System.out.println("" + normalX + " " + normalY);
		magnitude = Math.sqrt(normalX * normalX + normalY * normalY);
		unitX = normalX / magnitude;
		unitY = normalY / magnitude;
		// new Translator2D(ax-bx, ay-by);
		// slope = (ax-bx)/(ay-by);
		// System.out.println(slope);
		//System.out.println(magnitude);
//		System.out.println(magnitude);
//		System.out.println(unitX + " " + unitY);
//		System.out.println(Math.atan2(unitX, unitY));
		
//		rotate((float) -Math.atan2(unitY, unitX));
	}

	public void draw(PApplet screen, float[] buffer) {
//		screen.pushMatrix();
//		screen.translate((float) ax, (float) ay);
//		screen.rotate((float) Math.atan2(unitY, unitX));
//		screen.beginShape();
		double stepSize = magnitude/buffer.length;
		for (int i = 0; i < buffer.length; i++) {
//			screen.vertex((float) (i*stepSize), (float) (buffer[i] * 20), 0f);
			screen.vertex((float) (i*stepSize+ax), (float) (buffer[i] * 20 + ay), 0f);
			// screen.vertex(ax+i, ay + buffer.get(i)*50);
//			particle.draw(screen);
		}
//		screen.endShape();
//		screen.popMatrix();
	}

	public void drift(float x, float y, float z) {
		ax += x;
		ay += y;
		bx += x;
		by += y;
	}

	
	private void rotate(double paramDouble){
		System.out.println(paramDouble);
	    double d1 = Math.sin(paramDouble);
	    if (d1 == 1.0D)
	    {
	      rotate90();
	    }
	    else if (d1 == -1.0D)
	    {
	      rotate270();
	    }
	    else
	    {
	      double d2 = Math.cos(paramDouble);
	      if (d2 == -1.0D)
	      {
	        rotate180();
	      }
	      else if (d2 != 1.0D)
	      {
	        double d3 = ax;
	        double d4 = ay;
	        ax = (d2 * d3 + d1 * d4);
	        ay = (-d1 * d3 + d2 * d4);
	        d3 = bx;
	        d4 = by;
	        bx = (d2 * d3 + d1 * d4);
	        by = (-d1 * d3 + d2 * d4);
	      }
	    }		
		
	}
	
	  private final void rotate90()
	  {
	    double d = ax;
	    ax = ay;
	    ay = (-d);
	    d = bx;
	    bx = by;
	    by = (-d);
	  }
	  
	  private final void rotate180()
	  {
	    ax = (-ax);
	    by = (-by);
	  }
	  
	  private final void rotate270()
	  {
	    double d = ax;
	    ax = (-ay);
	    ay = d;
	    d = bx;
	    bx = (-by);
	    by = d;
	  }
	  	
}
