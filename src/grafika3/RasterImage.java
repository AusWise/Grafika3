/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grafika3;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import javax.imageio.ImageIO;

/**
 *
 * @author auswise
 */
public class RasterImage implements Image,Iterable<Entry<Point, Color>>{
    
    private HashMap<Point, Color> colorMap;
    private int minX, minY, maxX, maxY;
    
    public RasterImage(String path) throws IOException{
        this(new File(path));
    }
    
    public RasterImage(File file) throws IOException{
        this(ImageIO.read(file));
    }
    
    public RasterImage(BufferedImage image){
        this();
        int width = image.getWidth();
        int height = image.getHeight();
        int x_c = width/2;
        int y_c = height/2;
        int _x,_y;
        for(int x=0;x<width;x++)
            for(int y=0;y<height;y++){
                _x = x-x_c;
                _y = y_c-y;
                this.add(new Point(_x,_y), new Color(image.getRGB(x, y)));
            }
    }
    
    public RasterImage(){
        minX = minY = Integer.MAX_VALUE;
        maxX = maxY = Integer.MIN_VALUE;
        colorMap = new HashMap<Point, Color>();
    }
    
    private void add(int x, int y, Color color){
        add(new Point(x,y),color);
    }
    
    private void add(Point point, Color color){
        assert color!=null;
        
        int x = (int) point.getX();
        int y = (int) point.getY();
        
        if(this.minX > x)
            this.minX = x;
        if(this.minY > y)
            this.minY = y;
        if(this.maxX < x)
            this.maxX = x;
        if(this.maxY < y)
            this.maxY = y;
        
        colorMap.put(point, color);
    }
    
    public Color get(int x, int y){
        return get(new Point(x,y));
    }
    
    public Color get(Point point){
        return colorMap.get(point);
    }
    
    @Override
    public RasterImage transform(Transformation transformation){
        RasterImage newImage = new RasterImage();
        
        Point upperLeft = new Point(minX, maxY);
        Point upperRight = new Point(maxX, maxY);
        Point lowerLeft = new Point(minX, minY);
        Point lowerRight = new Point(maxX, minY);
        
        upperLeft = transformation.transform(upperLeft);
        upperRight = transformation.transform(upperRight);
        lowerLeft = transformation.transform(lowerLeft);
        lowerRight = transformation.transform(lowerRight);
        
        int maxX = (int) max(upperLeft.getX(), upperRight.getX(), lowerLeft.getX(), lowerRight.getX());
        int minX = (int) min(upperLeft.getX(), upperRight.getX(), lowerLeft.getX(), lowerRight.getX());
        int maxY = (int) max(upperLeft.getY(), upperRight.getY(), lowerLeft.getY(), lowerRight.getY());
        int minY = (int) min(upperLeft.getY(), upperRight.getY(), lowerLeft.getY(), lowerRight.getY());
        
        Transformation invTransformation = new InverseTransformation(transformation);
        Point oldPoint;
        for(int x=minX;x<=maxX;x++)
            for(int y=minY;y<=maxY;y++){
                oldPoint = invTransformation.transform(new Point(x,y));
                Color color = interpolation(oldPoint.getX(),oldPoint.getY());
                if(color!=null)
                    newImage.add(x,y,color);
//                else
//                    newImage.add(x,y,Color.BLUE);
                    
                
            }
        
        return newImage;
    }
    
    private Color interpolation(double x, double y){
        int i = (int)x;
        int j = (int)y;
        
        if(i>x)
            i--;
        
        if(j>y)
            j--;
        
        if(!contains(i,j) && !contains(i,j+1) && !contains(i+1, j) && !contains(i+1,j+1))
            return null;
        
//        System.out.println(!contains(i,j+1));
        
        double alpha = x-i;
        double beta = y-j;
        
//        System.out.println("x=" + x + " i=" + i + " y=" + y + " j=" + j);
        
        Color A = get(i,j);
        Color B = get(i+1,j);
        Color C = get(i,j+1);
        Color D = get(i+1,j+1);
        
        if(A==null)
            A = Color.WHITE;
        if(B==null)
            B = Color.WHITE;
        if(C==null)
            C = Color.WHITE;
        if(D==null)
            D = Color.WHITE;
        
        
        
        int rA = A.getRed();
        int rB = B.getRed();
        int rC = C.getRed();
        int rD = D.getRed();
        
        double rXab = alpha*rB + (1.0D-alpha)*rA;
        double rXcd = alpha*rD + (1.0D-alpha)*rC;
        
        double rX = beta*rXcd + (1.0D-beta)*rXab;
        
        int gA = A.getGreen();
        int gB = B.getGreen();
        int gC = C.getGreen();
        int gD = D.getGreen();
        
        double gXab = alpha*gB + (1.0D-alpha)*gA;
        double gXcd = alpha*gD + (1.0D-alpha)*gC;
        
        double gX = beta*gXcd + (1.0D-beta)*gXab;
        
        int bA = A.getBlue();
        int bB = B.getBlue();
        int bC = C.getBlue();
        int bD = D.getBlue();
        
//        System.out.println("rA=" + rA + " gA=" + gA + " bA=" + bA);
//        System.out.println("rB=" + rB + " gB=" + gB + " bB=" + bB);
//        System.out.println("rC=" + rC + " gC=" + gC + " bC=" + bC);
//        System.out.println("rD=" + rD + " gD=" + gD + " bD=" + bD);
//        
        double bXab = alpha*bB + (1.0D-alpha)*bA;
        double bXcd = alpha*bD + (1.0D-alpha)*bC;
        
//        System.out.println("alpha=" + alpha);
//        System.out.println("beta=" + beta);
//        
//        System.out.println("rXab=" + rXab + " gXab=" + gXab + " bXab=" + bXab);
//        System.out.println("rXcd=" + rXcd + " gXcd=" + gXcd + " bXcd=" + bXcd);
//        
        double bX = beta*bXcd + (1.0D-beta)*bXab;
        
//        System.out.println("rX=" + rX + " gX=" + gX + " bX=" + bX);
        
        Color X = new Color((int)rX, (int)gX, (int)bX);
        
        return X;
    }

    public int getMinX() {
        return minX;
    }

    public int getMinY() {
        return minY;
    }

    public int getMaxX() {
        return maxX;
    }

    public int getMaxY() {
        return maxY;
    }

    @Override
    public Iterator<Entry<Point, Color>> iterator() {
        return colorMap.entrySet().iterator();
    }
    
    private double max(double a, double b, double c, double d){
        return Math.max(Math.max(a, b), Math.max(c, d));
    }
    
    private double min(double a, double b, double c, double d){
        return Math.min(Math.min(a, b), Math.min(c, d));
    }
    
    public boolean contains(Point point){
        return colorMap.containsKey(point);
    }
    
    public boolean contains(int x, int y){
        return contains(new Point(x,y));
    }
}
