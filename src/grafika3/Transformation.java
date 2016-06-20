package grafika3;

public class Transformation {
	protected Matrix M;
	
        public Transformation(){
            this(1,0,0,0,1,0,0,0);
        }
        
	public Transformation(double a, double b, double c, 
			double d, double e, double f,
			double g, double h){
		
		double [][] array = {
				{a, b, c},
				{d, e, f},
				{g, h, 1}
				};
		
		this.M = new Matrix(array);
		
	}
	
	public Transformation(Matrix M){
		if(M.height() != 3 || M.width()!=3)
			throw new RuntimeException("Wrong matrix dimension");
		
		if(M.get(2, 2)!=1)
			throw new RuntimeException();
		
		this.M = M;
	}
	
	public Point transform(Point p){
            double x = p.getX();
            double y = p.getY();
            double w = 1;
            
            Matrix pm = new Matrix(3,1);
            
            pm.set(0, 0, x);
            pm.set(1, 0, y);
            pm.set(2, 0, w);
            
            pm = M.multiply(pm);
            
            x = pm.get(0, 0);
            y = pm.get(1, 0);
            w = pm.get(2, 0);
            
            p = new Point(x/w, y/w);
            
            return p;
        }
        
        public Transformation compose(Transformation other){
            return new Transformation(other.M.multiply(this.M));
        }
}
