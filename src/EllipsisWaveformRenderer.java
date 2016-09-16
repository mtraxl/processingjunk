import ddf.minim.AudioListener;

public class EllipsisWaveformRenderer implements AudioListener {
	private float[] left;
	private float[] right;
	private int slow = 0;
	private C applet;
	float[] buffer;

	public EllipsisWaveformRenderer(C applet) {
		this.applet = applet;
		left = null;
		right = null;
	}

	public synchronized void samples(float[] samp) {
		left = samp;
	}

	public synchronized void samples(float[] sampL, float[] sampR) {
		left = sampL;
		right = sampR;
	}

	private float average(float[] floats) {
		int i = 0;
		float acc = 0.0f;
		for (; i < floats.length; i++) {
			acc += floats[i];
		}
		return acc / i;
	}

	public synchronized void draw() {
		// we've got a stereo signal if right or left are not null
		if (left != null && right != null) {
			buffer = left;
			if (slow == 1) {
				applet.sphereSize = average(left);
				slow = 0;
			}
			slow += 1;

			applet.noFill();
			applet.stroke(0);
			// beginShape();
			float displayRatio = (applet.height*1.0f)/(applet.width*1.0f);
			float bufferToScreenWidthRatio = (applet.width*1.0f)/(left.length*1.0f);
			for (int i = 0; i < left.length; i++) {
				applet.ellipse(i*bufferToScreenWidthRatio, (applet.height - i*displayRatio*bufferToScreenWidthRatio), left[i] * 100, left[i] * 100);
				// vertex(i, height/4 + left[i]*50);
			}
			// endShape();
			// beginShape();
			applet.stroke(255);
			for (int i = 0; i < right.length; i++) {
				// vertex(i, 3*(height/4) + right[i]*50);
				applet.ellipse(i*bufferToScreenWidthRatio, i*displayRatio*bufferToScreenWidthRatio, right[i] * 100, right[i] * 100);

			}
			// endShape();
		} else if (left != null) {
			applet.noFill();
			applet.stroke(255);
			applet.beginShape();
			for (int i = 0; i < left.length; i++) {
				applet.vertex(i, applet.height / 2 + left[i] * 50);
			}
			applet.endShape();
		}
	}
}
