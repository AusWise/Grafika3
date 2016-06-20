/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grafika3;

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
public class Animation implements Iterable<Image>{
    private List<Transformation> transformations;
    private Image image;
    
    public Animation(){
        transformations = new LinkedList<Transformation>();
    }
    
    public Animation(String path) throws FileNotFoundException{
        this(new File(path));
    }
    
    public Animation(File file) throws FileNotFoundException{
        this();
        Scanner fileScanner = new Scanner(file);
        Scanner lineScanner;
        
        String type;
        double sx,sy,px,py,angle;
        while(fileScanner.hasNext()){
            lineScanner = new Scanner(fileScanner.nextLine());
            type = lineScanner.next();
            if(type.equals("Scale")){
                sx = lineScanner.nextDouble();
                sy = lineScanner.nextDouble();
                this.add(new Scale(sx,sy));
            }
            else if(type.equals("Move")){
                px = lineScanner.nextDouble();
                py = lineScanner.nextDouble();
                this.add(new Move(px,py));
            }
            else if(type.equals("Rotate")){
                angle = lineScanner.nextDouble();
                this.add(new Rotate(angle));
            }
        }
        
    }
    
    public void add(Transformation transformation){
        transformations.add(transformation);
    }
    
    public void setImage(Image image){
        this.image = image;
    }

    @Override
    public Iterator<Image> iterator() {
        return new ImageIterator();
    }
    
    private class ImageIterator implements Iterator<Image>{
        
        Iterator<Transformation> transformationIterator;
        Transformation transformation;
        
        public ImageIterator(){
            this.transformationIterator = transformations.iterator();
            this.transformation = new Transformation();
        }
        
        @Override
        public boolean hasNext() {
            return transformationIterator.hasNext();
        }

        @Override
        public Image next() {
            Transformation t = transformationIterator.next();
            transformation = transformation.compose(t);
            return image.transform(transformation);
        }
    }
}
