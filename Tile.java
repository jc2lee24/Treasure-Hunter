import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;
import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Tile{
    
    private boolean hasEnemy, hasCoin, hasProjectile;
    private int element;//1 for water 2 for land
    private int x, y, startX, startY;
    private Image treasureTile, landTile, waterTile, landEnemy, waterEnemy;

    public Tile(int element, boolean Enemy, int x, int y, boolean hasCoin, boolean hasProjectile){
        this.element = element;
        this.x = x;
        this.y = y;
        this.hasCoin = hasCoin;
        this.hasEnemy = Enemy;
        this.hasProjectile = hasProjectile;

        try{
            landTile = ImageIO.read(new File("sandTile.png")).getScaledInstance(20, 20, Image.SCALE_DEFAULT);
            treasureTile = ImageIO.read(new File("treasureTile.png")).getScaledInstance(20, 20, Image.SCALE_DEFAULT);
            waterTile = ImageIO.read(new File("waterTile.png")).getScaledInstance(20, 20, Image.SCALE_DEFAULT);
            landEnemy = ImageIO.read(new File("landEnemy.png")).getScaledInstance(20, 20, Image.SCALE_DEFAULT);
            waterEnemy = ImageIO.read(new File("waterEnemy.png")).getScaledInstance(20, 20, Image.SCALE_DEFAULT);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }


    public boolean containsProjectile(){
        return hasProjectile;
    }

    public void addProjectile(){
        hasProjectile = true;
    }

    public void removeProjectile(){
        hasProjectile = false;
    }

    public void addEnemy(){
        hasEnemy = true;
    }

    public void removeEnemy(){
        hasEnemy = false;
    }


    public int getElement(){
        return element;
    }

    public boolean containsEnemy(){
        return hasEnemy;
    }

    public void moveX(int x){
        this.x += x;
    }

    public void moveY(int y){
        this.y += y;
    }

    public void collectCoin(){
        hasCoin = false;
    }

    public boolean containsCoin(){
        return hasCoin;
    }

    public boolean collision(){
        if(hasEnemy && hasProjectile){
            removeEnemy();
            removeProjectile();
            return true;
        }
        return false;
    }
    public void drawMe(Graphics g){

        if(element == 1){
            //draw water tile
            g.drawImage(waterTile, x * 20, y * 20, null);
        }
        else if(element == 2){
            if(!hasCoin){
                g.drawImage(landTile, x * 20, y * 20, null);
            }
            else if(hasCoin){
                g.drawImage(treasureTile, x * 20, y * 20, null);
            }
        }

        if(hasEnemy){
            if(element == 1){
                g.drawImage(waterEnemy, (x * 20), (y * 20) + 5, null);
            }
            if(element == 2){
                g.drawImage(landEnemy, (x * 20), (y * 20) + 5, null);
            }
        }
        
        if(hasProjectile){
            g.setColor(Color.black);
            g.fillOval((x * 20) + 5, (y * 20) + 5, 10, 10);
        }


    }
    
}
