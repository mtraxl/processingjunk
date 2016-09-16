
public class Translator2D {
	
	
	
	public static Particle translate(Particle particleToTranslate, float originX, float originY, float unitX, float unitY){
		float translatedX = particleToTranslate.getX()*unitX+originX;
		float translatedY = particleToTranslate.getY()*unitY+originY;
		return new Particle(translatedX, translatedY, 0f);
	}
}
