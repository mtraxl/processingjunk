import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import ddf.minim.AudioBuffer;

public class Vandmand {
	List<ParticleGroup> particleGroups = new ArrayList<>();
	private C screen;
	List<LineGroup> lines = new ArrayList<>();

	public Vandmand(C screen){
		this.screen = screen;
	}
	
	
	public void beat(float size) {
		spawnParticles(size);
	}

	private void spawnParticles(float size) {
		int X = 100;
		int Y = 100;
		float SIZE = 100;// = size * 100;
		ParticleGroup particleGroup = new ParticleGroup();
		
		Optional<LineGroup> lastLineGroup = Optional.empty();
		if(!lines.isEmpty()){
			lastLineGroup = Optional.of(lines.get(lines.size()-1));
		}
		
		LineGroup lineGroup = new LineGroup();
		lineGroup.add(new WaveFormLine(X-SIZE, Y-SIZE, X-SIZE, Y+SIZE));
		lineGroup.add(new WaveFormLine(X-SIZE, Y+SIZE, X+SIZE, Y+SIZE));
		lineGroup.add(new WaveFormLine(X+SIZE, Y+SIZE, X+SIZE, Y-SIZE));
		lineGroup.add(new WaveFormLine(X+SIZE, Y-SIZE, X-SIZE, Y-SIZE));
		lines.add(lineGroup);

		
		LineGroup connectors = new LineGroup();
		lastLineGroup.ifPresent(g -> {
			for(int i = 0; i < g.lines.size(); i++){
				connectors.add(createConnector(lineGroup.lines.get(i), g.lines.get(i)));
				
			}
			
		});
		if(!connectors.lines.isEmpty()){
			lines.add(connectors);
		}
//		particleGroup.add(new Particle(X-SIZE, Y-SIZE, 0));
//		particleGroup.add(new Particle(X-SIZE, Y+SIZE, 0));
//		particleGroup.add(new Particle(X+SIZE, Y+SIZE, 0));
//		particleGroup.add(new Particle(X+SIZE, Y-SIZE, 0));
//		particleGroups.add(particleGroup);
	}
	
	private WaveFormLine createConnector(WaveFormLine waveFormLine, WaveFormLine waveFormLine2) {
		return new WaveFormLine(waveFormLine.ax, waveFormLine.ay, waveFormLine2.ax, waveFormLine2.ay);
	}


	public void draw(AudioBuffer buffer){
		screen.fill(111);
		lines.stream().forEach(g -> g.drift(1f, 0.5f, 0f));
		
//		Optional<ParticleGroup> previousGroup = Optional.empty();
		for(int i = 0; i < lines.size(); i++){
			lines.get(i).draw(screen, buffer);
			//previousGroup = Optional.of(particleGroups.get(i));
		}
	}

}
