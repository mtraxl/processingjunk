import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import processing.core.PApplet;

public class ParticleGroup {
	List<Particle> particles = new ArrayList<>();

	public void add(Particle particle) {
		particles.add(particle);
	}

	public void drift(float x, float y, float z) {
		particles.stream().forEach(p -> p.drift(x, y, z));
	}

	public void draw(PApplet screen, Optional<ParticleGroup> previousGroup){
		screen.beginShape(screen.LINES);
		screen.stroke(0);
		for(Particle particle: particles){
			particle.draw(screen);
		}
		particles.get(0).draw(screen);
		screen.endShape();
		
		previousGroup.ifPresent(pg -> connectWithPreviousShape(screen, pg));
	}

	private void connectWithPreviousShape(PApplet screen, ParticleGroup pg) {
		screen.beginShape(screen.LINES);
		for(int i = 0; i < particles.size(); i++){
			particles.get(i).draw(screen);
			pg.particles.get(i).draw(screen);
		}
		
		screen.endShape();
	}

}
