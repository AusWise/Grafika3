package grafika3;

public class InverseTransformation extends Transformation {
	public InverseTransformation(Transformation transformation){
		super(transformation.M.inverse());
	}
}
