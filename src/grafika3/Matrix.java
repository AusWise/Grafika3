package grafika3;

public class Matrix {
	private Jama.Matrix matrix;
	
	private Matrix(){
		matrix = null;
	}
	
	public Matrix(Matrix other){
		this(other.height(), other.width());
		
		for(int i=0; i<height();i++)
			for(int j=0;j<width();j++)
				this.set(i, j, other.get(i, j));
	}
	
	public Matrix(double [][] array){
		matrix = new Jama.Matrix(array);
	}
	
	public Matrix(int rowsCount, int columnCount){
		matrix = new Jama.Matrix(rowsCount, columnCount);
	}
	
	public double get(int i, int j){
		if(i<0 || i>=this.height() || j<0 || j>=this.width())
			throw new RuntimeException();
		
		return matrix.get(i, j);
	}
	
	public void set(int i, int j, double value){
		if(i<0 || i>=this.height() || j<0 || j>=this.width())
			throw new RuntimeException();
		
		matrix.set(i, j, value);
	}
	
	public Matrix add(Matrix other){
		if(this.height()!=other.height() || this.width()!=other.width())
			throw new RuntimeException("Matrix are not aligned");
		
		Jama.Matrix matrix = this.matrix.plus(other.matrix);
		Matrix result = new Matrix();
		result.matrix = matrix;
		return result;
	}
	
	public Matrix substract(Matrix other){
		if(this.height()!=other.height() || this.width()!=other.width())
			throw new RuntimeException("Matrix are not aligned");
		
		Jama.Matrix matrix = this.matrix.minus(other.matrix);
		Matrix result = new Matrix();
		result.matrix = matrix;
		return result;
	}
	
	public Matrix multiply(Matrix other){
		if(this.width()!=other.height())
			throw new RuntimeException("Matrix are not aligned");
		
		Jama.Matrix matrix = this.matrix.times(other.matrix);
		Matrix result = new Matrix();
		result.matrix = matrix;
		return result;
	}
	
	public Matrix multiply(double scalar){
		Jama.Matrix matrix = this.matrix.times(scalar);
		Matrix result = new Matrix();
		result.matrix = matrix;
		return result;
	}
	
	public Matrix transpose(){
		Jama.Matrix matrix = this.matrix.transpose();
		Matrix result = new Matrix();
		result.matrix = matrix;
		return result;
	}
	
	public Matrix inverse(){
		Jama.Matrix matrix = this.matrix.inverse();
		Matrix result = new Matrix();
		result.matrix = matrix;
		return result;
	}
	
	public int height(){
		return matrix.getRowDimension();
	}
	
	public int width(){
		return matrix.getColumnDimension();
	}
}
