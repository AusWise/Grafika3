/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grafika3;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
//import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.Timer;

/**
 *
 * @author auswise
 */
public class Frame extends JFrame implements ActionListener{
    private JMenuBar jMenuBar;
    private JMenu mnFile;
    private JMenuItem mntmOpen;
    private JMenuItem mntmLoad;
    private ImagePanel imagePanel;
        
    private Iterator<Image> riterator;
    private Iterator<Image> viterator;
    
    public Frame() {
        this.jMenuBar = new JMenuBar();
        this.add(this.jMenuBar, BorderLayout.NORTH);
        
        this.mnFile = new JMenu("File");
        this.jMenuBar.add(this.mnFile);
        
        this.mntmOpen = new JMenuItem("Open Image");
        this.mnFile.add(this.mntmOpen);
        this.mntmOpen.addActionListener(this);
        
        this.mntmLoad = new JMenuItem("Load Animation");
        this.mnFile.add(mntmLoad);
        this.mntmLoad.addActionListener(this);
        
        this.imagePanel = new ImagePanel();
        this.add(this.imagePanel,BorderLayout.CENTER);   
        
        this.riterator = null;
        this.viterator = null;
        
        this.setTitle("Grafika 3");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(new Dimension(300,300));
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == mntmOpen){
            JFileChooser fileChooser = new JFileChooser();
            int n = fileChooser.showOpenDialog(this);
            if(n == JFileChooser.APPROVE_OPTION){
                File file = fileChooser.getSelectedFile();
                try {
                    RasterImage rasterImage = new RasterImage(file);
                    VectorImage vectorImage = new VectorImage(new File(file.getAbsolutePath().substring(0, file.getAbsolutePath().length()-3) + "txt"));
                    this.imagePanel.setVectorImage(vectorImage);
                    this.imagePanel.setRasterImage(rasterImage);
                    this.repaint();
                } catch (IOException ex) {
                    Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        else if(e.getSource() == this.mntmLoad){
            try {
                JFileChooser fileChooser = new JFileChooser();
                int n = fileChooser.showOpenDialog(this);
                if(n == JFileChooser.APPROVE_OPTION){
                    File file = fileChooser.getSelectedFile();
                    Animation ranimation = new Animation(file);
                    Animation vanimation = new Animation(file);
                    
                    ranimation.setImage(imagePanel.getRasterImage());
                    vanimation.setImage(imagePanel.getVectorImage());
                    viterator = vanimation.iterator();
                    riterator = ranimation.iterator();
                    new Timer(100, this).start();
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else{
            if(riterator!=null && viterator!=null){
                if(riterator.hasNext() && viterator.hasNext()){
                    Image rimage = riterator.next();
                    Image vimage = viterator.next();
                    imagePanel.setVectorImage((VectorImage)vimage);
                    imagePanel.setRasterImage((RasterImage)rimage);
                    repaint();
                }
            }
        }
    }
   
}
