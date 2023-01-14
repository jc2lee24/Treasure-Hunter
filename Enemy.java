import java.awt.Graphics;
import java.awt.Color;
import java.util.*;

public class Enemy implements Runnable{
    int element;//1 is water, 2 is land
    int x, y, previousX, previousY;
    boolean canRight, canLeft, canUp, canDown;
    HashMap<Location, Tile> tempMap;

    public Enemy(int x, int y, int element){
        this.x = x;
        this.y = y;
        this.previousX = x;
        this.previousY = y;
        this.element = element;
    }
    
    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public int getPreviousX(){
        return previousX;
    }

    public int getPreviousY(){
        return previousY;
    }

    public void getMap(HashMap temp){
        this.tempMap = temp;
    }
    
    public void stopThread(){
        Thread.currentThread().interrupt();
    }
    public void run(){
        while(true){
            if(Thread.interrupted()){
                break;
            }
            try{
                Thread.sleep(500);
            }
            catch(InterruptedException ex){
                Thread.currentThread().interrupt();
            }
  
            int rand = (int)(Math.random() * 4);

            Tile right = tempMap.get(new Location(x + 1, y));
            Tile left = tempMap.get(new Location(x - 1, y));
            Tile up = tempMap.get(new Location(x, y - 1));
            Tile down = tempMap.get(new Location(x, y + 1));
            Tile lastTile = tempMap.get(new Location(x, y));
            if(!lastTile.containsEnemy()){
                Thread.currentThread().interrupt();
                tempMap.get(new Location(x, y)).removeEnemy();
                if(right != null){
                    tempMap.get(new Location(x + 1, y)).removeEnemy();
                }
                if(left != null){
                    tempMap.get(new Location(x - 1, y)).removeEnemy();
                }
                if(down != null){
                    tempMap.get(new Location(x, y + 1)).removeEnemy();
                }
                if(up != null){
                    tempMap.get(new Location(x, y - 1)).removeEnemy();
                }
            }

            if(rand == 1){//move right
                if(right != null && !right.containsEnemy() && right.getElement() == this.element ){
                    tempMap.get(new Location(x + 1, y)).addEnemy();
                    tempMap.get(new Location(x, y)).removeEnemy();
                    previousX = x;
                    previousY = y;
                    x++;
                }
            }
            else if(rand == 2){//move left
                if(left != null && !left.containsEnemy() && left.getElement() == this.element ){
                    tempMap.get(new Location(x - 1, y)).addEnemy();
                    tempMap.get(new Location(x, y)).removeEnemy();
                    previousX = x;
                    previousY = y;
                    x--;
                }

            }
            else if(rand == 3){//move up
                if(up != null && !up.containsEnemy() && up.getElement() == this.element ){
                    tempMap.get(new Location(x, y - 1)).addEnemy();
                    tempMap.get(new Location(x, y)).removeEnemy();
                    previousX = x;
                    previousY = y;
                    y--;
                }
            }
            else{//move down
                if(down != null && !down.containsEnemy() && down.getElement() == this.element ){
                    tempMap.get(new Location(x, y + 1)).addEnemy();
                    tempMap.get(new Location(x, y)).removeEnemy();
                    previousX = x;
                    previousY = y;
                    y++;
                }
            }
        }
    }
}
