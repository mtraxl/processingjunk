import processing.core.PApplet;

public class Particle {
	private float x;
	private float y;
	private float z;

	public Particle(float x, float y, float z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void draw(PApplet screen) {
		screen.vertex(x, y, z);
	}

	public void drift(float moveX, float moveY, float moveZ) {
		x+=moveX;
		y+=moveY;
		z+=moveZ;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}
	
}
