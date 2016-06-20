/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grafika3;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author auswise
 */
public class VectorImage implements Image, Iterable<Point> {

    private List<Point> points;
    private Color color;
    
    public VectorImage(Color color){
        this();
        this.color = color;
    }
    
    public VectorImage(String path) throws FileNotFoundException{
        this(new File(path));
    }
    
    public VectorImage(File file) throws FileNotFoundException{
        this();
        Scanner fileScanner = new Scanner(file);
        Scanner lineScanner;
        
        int r,g,b;
        if(fileScanner.hasNextLine()){
            lineScanner = new Scanner(fileScanner.nextLine());
            r = lineScanner.nextInt();
            g = lineScanner.nextInt();
            b = lineScanner.nextInt();
            color = new Color(r,g,b);
        }
        
        int x,y;
        while(fileScanner.hasNextLine()){
            lineScanner = new Scanner(fileScanner.nextLine());
            x = lineScanner.nextInt();
            y = lineScanner.nextInt();
            this.add(new Point(x,y));
        }        
    }
    
    public VectorImage(){
        points = new LinkedList<Point>();
    }
    
    public void add(Point point){
        points.add(point);
    }
    
    public void add(int x, int y){
        this.add(new Point(x,y));
    }
    
    public Point get(int i ){
        return points.get(i);
    }
    
    @Override
    public Iterator<Point> iterator() {
        return points.iterator();
    }
    
    public int pointsCount(){
        return points.size();
    }
    
    @Override
    public VectorImage transform(Transformation transformation){
        VectorImage newImage = new VectorImage(color);
        
        Point newPoint;
        for(Point point : this){
            newPoint = transformation.transform(point);
            newImage.add(newPoint);
        }
        
        return newImage;
    }
    
    public Color getColor(){
        return color;
    }
    
    public void setColor(Color color){
        this.color = color;
    }
}
