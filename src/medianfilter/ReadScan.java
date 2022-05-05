package medianfilter;

import java.awt.Color;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;

public class ReadScan {
    
    public ArrayList<Punkt> p;
    public ArrayList<Punkt> polar;
    public ArrayList<Punkt> filterDaten;
    
    public ReadScan(String file){
        p = new ArrayList<Punkt>();
        polar = new ArrayList();
        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null)
            {
                String[] s = line.split("\\s+");

                double distance = Double.valueOf(s[1]);
                double radian = Math.toRadians(Double.valueOf(s[0]));

                double x = Math.cos(radian) * distance;
                double y = Math.sin(radian) * distance;

                p.add(new Punkt(x,y));
                polar.add(new Punkt(Double.valueOf(s[0]), distance));
                // x = winkel y = dist
            }
            reader.close();
        }
        catch (Exception e){
             e.printStackTrace();
        }
        filterDaten = getFilterData();
    }
    
    public ArrayList<Punkt> getFilterData(){
        ArrayList<Punkt> p = new ArrayList();
        ArrayList<Double> sorted = new ArrayList();
        
        for (int i = 0; i < polar.size(); i++) {
            sorted.add(polar.get(i).y);
        }
        
        Collections.sort(sorted);
        
        double median = 0;
        if(sorted.size() % 2 == 0){
            
            double sum = sorted.get(sorted.size() / 2) + sorted.get(sorted.size() / 2 -1);
            median = sum / 2;
        
        }else{
            
            median = sorted.get(sorted.size() / 2);
        
        }
        
        System.out.println(median);
        
        for (int i = 0; i < polar.size(); i++) {
            if(!(polar.get(i).y > median+200)){
                p.add(new Punkt(this.p.get(i).x, this.p.get(i).y));
            }
        }
        return p;
    }
    
    private float scale = 0.15f;
    private int offsetX = 300;
    private int offsetY = 300;
    
    public void zeichneDaten(Graphics g){
        g.setColor(Color.red);
        for (int i = 0; i < this.p.size(); i++) {
            g.drawRect((int) (this.p.get(i).x * scale) + offsetX, (int) (this.p.get(i).y * scale)+ offsetY,1, 1);
        }
    }
    
    public void zeichneFilterDaten(Graphics g){
        g.setColor(Color.black);
        for (int i = 0; i < filterDaten.size(); i++) {
            g.drawRect((int) (filterDaten.get(i).x * scale) + offsetX, (int) (filterDaten.get(i).y * scale)+ offsetY,1, 1);
        }
    }
    
    
    public ArrayList<Punkt> getPointArrList(){
        return p;
    }
   
}
