package grafika3;

public class Move extends Transformation {
	public Move(double px, double py){
		super(1,0,px,
				0,1,py,
				0,0);
	}
}
