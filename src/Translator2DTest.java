import static org.junit.Assert.*;

import org.junit.Test;

public class Translator2DTest {

	@Test
	public void test180degrees() {
		Particle particle = Translator2D.translate(new Particle(2, 0, 0), 0, 0, -1, 0);
		assertEquals(-2, particle.getX(), 0.01f);
		assertEquals(0, particle.getY(), 0.01f);
	}

	@Test
	public void test90degrees() {
		Particle particle = Translator2D.translate(new Particle(2, 0, 0), 0, 0, 0, -1);
		assertEquals(0, particle.getX(), 0.01f);
		assertEquals(-2, particle.getY(), 0.01f);
	}
}
