import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ddf.minim.AudioBuffer;

public class LineGroup {

	List<WaveFormLine> lines = new ArrayList<>();

	public void add(WaveFormLine waveFormLine) {
		lines.add(waveFormLine);
	}

	public void drift(float x, float y, float z) {
		lines.forEach(g -> g.drift(x, y, z));
	}

	public void draw(C screen, AudioBuffer buffer) {
		int bufferSplit = (int) Math.floor(new Double(buffer.size()/lines.size()));
		for(int j = 0; j < lines.size(); j++){
			lines.get(j).draw(screen, Arrays.copyOfRange(buffer.toArray(), (j)*bufferSplit, (j+1)*bufferSplit-1));
		}
//		lines.get(lines.size()-1).draw(screen, Arrays.copyOfRange(buffer.toArray(), buffer.size()-bufferSplit, buffer.size()-1));
	}
	
	
	
}
