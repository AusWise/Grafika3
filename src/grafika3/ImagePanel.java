/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grafika3;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import javax.swing.JPanel;

/**
 *
 * @author auswise
 */
public class ImagePanel extends JPanel {
    
    VectorImage vectorImage;
    RasterImage rasterImage;
    
    public ImagePanel(){
        rasterImage=null;
        vectorImage=null;
        this.setBackground(Color.WHITE);
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if(rasterImage!=null)
            this.paintRasterImage(g);
        
        if(vectorImage!=null)
            this.paintVectorImage(g);
    }
    
    private void paintRasterImage(Graphics g){
        Graphics2D g2d = (Graphics2D)g;
        int x_c = this.getWidth()/2;
        int y_c = this.getHeight()/2;
        int minX = rasterImage.getMinX();
        int minY = rasterImage.getMinY();
        int maxX = rasterImage.getMaxX();
        int maxY = rasterImage.getMaxY();
        int _x,_y;
        Color color;
        for(int x = minX;x<=maxX;x++){
            for(int y=minY;y<=maxY;y++){
                _x = x+x_c;
                _y = y_c-y;
                if(rasterImage.contains(x,y)){
                    color = rasterImage.get(x,y);
                    g2d.setColor(color);
                    g2d.fillRect(_x, _y, 1, 1);
                }
            }
        }
    }
    
    private void paintVectorImage(Graphics g){
        Graphics2D g2d = (Graphics2D)g;
        
        int npoints = vectorImage.pointsCount();
        int [] xpoints = new int[npoints];
        int [] ypoints = new int[npoints];
        
        Point point;
        int x,y;
        
        int x_c = this.getWidth()/2;
        int y_c = this.getHeight()/2;
        for(int i=0;i<npoints;i++){
            point = vectorImage.get(i);
            x = (int)point.getX();
            y = (int)point.getY();
            xpoints[i] = x+x_c;
            ypoints[i] = y_c-y;
        }
        
        Polygon polygon = new Polygon(xpoints, ypoints, npoints);
        
        g2d.setColor(vectorImage.getColor());
        g2d.fill(polygon);
    }
    
    public void setVectorImage(VectorImage vectorImage) {
        this.vectorImage = vectorImage;
    }
    
    public void setRasterImage(RasterImage rasterImage){
        this.rasterImage = rasterImage;
    }

    public VectorImage getVectorImage() {
        return vectorImage;
    }

    public RasterImage getRasterImage() {
        return rasterImage;
    }
}
