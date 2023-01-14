import java.util.*;

public class Projectile implements Runnable{
    int x, y, direction, startX, startY;
    HashMap<Location, Tile> tempMap;

    boolean shot = false;

    public Projectile(int x, int y){
        this.x = x;
        this.y = y;
        this.startX = x;
        this.startY = y;
    }

    public void getInfo(int direction, int x, int y){
        this.direction = direction;
        this.x = x;
        this.y = y;
    }

    public void getMap(HashMap temp){
        this.tempMap = temp;
    }

    public void shoot(){
        this.shot = true;
    }

    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }

    public void run(){
        while(true){

            try{
                Thread.sleep(100);
            }
            catch(InterruptedException ex){
                Thread.currentThread().interrupt();
            }

            if(this.shot){

                tempMap.get(new Location(x, y)).addProjectile();

                for(int i = 0; i < 5; i++){

                    try{
                        Thread.sleep(100);
                    }
                    catch(InterruptedException ex){
                        Thread.currentThread().interrupt();
                    }

                    Tile current = tempMap.get(new Location(x, y));
                    Tile left = tempMap.get(new Location(x - 1, y));
                    Tile right = tempMap.get(new Location(x + 1, y));
                    Tile up = tempMap.get(new Location(x, y - 1));
                    Tile down = tempMap.get(new Location(x, y + 1));

                    if(current.containsEnemy() && current.containsProjectile()){
                        this.shot = false;
                        tempMap.get(new Location(x, y)).removeEnemy();
                        current.removeProjectile();
                        if(left != null){
                            left.removeProjectile();
                        }
                        if(right != null){
                            right.removeProjectile();
                        }
                        if(up != null){
                            up.removeProjectile();
                        }
                        if(down != null){
                            down.removeProjectile();
                        }
                    }


                    if(direction == 1){//move right
                        if(right == null){
                            this.shot = false;
                            current.removeProjectile();
                        }
                        else if(current != null && right != null){
                            tempMap.get(new Location(x + 1, y)).addProjectile();
                            tempMap.get(new Location(x, y)).removeProjectile();
                            x++;
                        }
                    }
                    else if(direction == 2){//move left
                        if(left == null){
                            this.shot = false;
                            current.removeProjectile();
                        }
                        else if(current != null && !current.containsEnemy()){
                            tempMap.get(new Location(x - 1, y)).addProjectile();
                            tempMap.get(new Location(x, y)).removeProjectile();
                            x--;
                        }

                    }
                    else if(direction == 3){//move up
                        if(up == null){
                            this.shot = false;
                            current.removeProjectile();
                        }
                        else if(current != null && !current.containsEnemy()){
                            tempMap.get(new Location(x, y - 1)).addProjectile();
                            tempMap.get(new Location(x, y)).removeProjectile();
                            y--;
                        }
                    }
                    else{//move down
                        if(down == null){
                            this.shot = false;
                            current.removeProjectile();
                        }
                        else if(current != null && !current.containsEnemy()){
                            tempMap.get(new Location(x, y + 1)).addProjectile();
                            tempMap.get(new Location(x, y)).removeProjectile();
                            y++;
                        }
                    }


                    if(i==4 && current != null && left != null && right != null && down != null && up != null){
                        this.shot = false;
                        current.removeProjectile();
                        left.removeProjectile();
                        right.removeProjectile();
                        up.removeProjectile();
                        down.removeProjectile();
                        x = startX;
                        y = startY;
                    }
                }
            }

        }
    }
}
