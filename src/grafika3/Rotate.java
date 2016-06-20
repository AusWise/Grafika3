package grafika3;
import java.lang.Math;

public class Rotate extends Transformation{
	public Rotate(double alpha){
		super(Math.cos(Math.toRadians(alpha)), -Math.sin(Math.toRadians(alpha)), 0, 
				Math.sin(Math.toRadians(alpha)), Math.cos(Math.toRadians(alpha)), 0, 
				0, 0);
	}
}
