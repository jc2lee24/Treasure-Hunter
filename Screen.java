import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;


public class Screen extends JPanel implements KeyListener{
    HashMap<Location, Tile> gameMap;
    
    private int rand1;
    private int rand2;
    private int rand3;
    private boolean landOrWater; //true for land, false for water
    private boolean containsEnemy = false;

    Player p;
    Timer t;
    Thread th1;
    Thread projThread;
    Projectile proj;
    ArrayList<Thread> threads;
    ArrayList<Enemy> enemies;

    private int playerXCoord;
    private int playerYCoord;

    private int score = 0;
    private int coinsRemaining = 0;
    private int time;
    private int enemyCount = 0;;
    private int lives = 3;

    private boolean gameWon = false;
    private boolean gameLost = false;
    private boolean start = false;

    private int changedX = 0;
    private int changedY = 0;

    private int direction = 1; //1 for left 2 right 3 up 4 down

    private boolean shot = false;

    public Screen(){

        setFocusable(true);
        setLayout(null);
        addKeyListener(this);

        gameMap = new HashMap<Location, Tile>();

        int element = 0;
        boolean coin = false;
        enemies = new ArrayList<Enemy>();
        threads = new ArrayList<Thread>();

        for(int r = 0; r < 100; r++){
            for(int c = 0; c < 100; c++){

                rand1 = (int)(Math.random() * 100); //prob of making it random
                rand2 = (int)(Math.random() * 100); //prob of copying /random water or land
                rand3 = (int)(Math.random() * 1000); //prob of enemy spawning


                if(r == 0 && c == 0){
                    element = 1;
                }
                else if(r > 0 && c == 0){
                    if(rand1 <= 10){
                        if(rand2 < 92){
                            element = 1;
                        }
                        else if(rand2 >= 92){
                            element = 2;
                        }
                    }
                    else if(rand1 > 10){
                            element = gameMap.get(new Location(r-1, c)).getElement();
                    }
                }

                else if(r > 0 && c > 0){
                    if(rand1 <= 10){
                        if(rand2 < 92){
                            element = 1;
                        }
                        else if(rand2 >= 92){
                            element = 2;
                        }
                    }
                    else if(rand1 > 10){
                        if(rand2 < 50){
                            element = gameMap.get(new Location(r-1, c)).getElement();
                        }
                        else if(rand2 >= 50){
                            element = gameMap.get(new Location(r, c-1)).getElement();
                        }
                    }
                }

                int coinNum = (int)(Math.random() * 100);
                coin = false;
                if(coinNum > 97 && element == 2){
                    coin = true;
                    coinsRemaining++;
                }

                if(rand3 >= 997){
                    enemies.add(new Enemy(r, c, element));
                    threads.add(new Thread(enemies.get(enemyCount)));
                    containsEnemy = true;
                    enemyCount++;
                }
                else{
                    containsEnemy = false;
                }
                
                gameMap.put(new Location(r, c), new Tile(element, containsEnemy, r, c, coin, false));
            }
        }

        p = new Player(400, 300);

        playerXCoord = 400/20;
        playerYCoord = 300/20;

        t = new Timer(240);
        th1 = new Thread(t);

        proj = new Projectile(playerXCoord, playerYCoord);
        projThread = new Thread(proj);
    }

    public void animate(){
        while(!gameWon && !gameLost){
            repaint();

            time = t.getTime();

            if(time == 0){
                gameLost = true;
            }

            if(lives == 0){
                gameLost = true;
            }  

            for(int i = 0; i < enemies.size(); i++){
                enemies.get(i).getMap(gameMap);
            }

            if(gameMap.get(new Location(proj.getX(), proj.getY())).containsEnemy()){
                for(int i = 0; i < enemies.size(); i++){
                    if( enemies.get(i).getX() == proj.getX() && enemies.get(i).getY() == proj.getY() ){
                        enemies.remove(enemies.get(i));
                    }
                }
            }
        }
    }

    public Dimension getPreferredSize(){
        return new Dimension(800,600);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        for(int r = 0; r < 100; r++){
            for(int c = 0; c < 100; c++){
                gameMap.get(new Location(r, c)).drawMe(g);
            }
        }
        if(landOrWater){
            p.drawLand(g);
        }
        else{
            p.drawWater(g);
        }

        g.setColor(Color.DARK_GRAY);
        g.drawString("Z TO SHOOT", 10, 590);

        if(start){
            g.drawString("Coins remaining: " + coinsRemaining, 10, 15);
            g.drawString("Score: " + score, 730, 15);
            g.drawString("Time remaining: " + time, 150, 15);
            g.drawString("Lives: " + lives, 660, 15);
        }

        if(!start){
            g.drawString("SPACE TO START", 320, 250);
        }

        if(gameWon){
            g.drawString("YOU WIN", 350, 250);
        }
        if(gameLost){
            g.drawString("YOU LOST", 350, 250);
        }

    }

    public void keyTyped(KeyEvent e){}

    public void keyPressed(KeyEvent e){

        if(start){
            if(e.getKeyCode() == 37){//right
                for(int r = 0; r < 100; r++){
                    for(int c = 0; c < 100; c++){
                        gameMap.get(new Location(r, c)).moveX(1);
                    }
                }
                changedX++;
                playerXCoord--;
                direction = 2;
            }
            else if(e.getKeyCode() == 39){//left
                for(int r = 0; r < 100; r++){
                    for(int c = 0; c < 100; c++){
                        gameMap.get(new Location(r, c)).moveX(-1);
                    }
                }
                changedX--;
                playerXCoord++;
                direction = 1;
            }
            else if(e.getKeyCode() == 38){//up
                for(int r = 0; r < 100; r++){
                    for(int c = 0; c < 100; c++){
                        gameMap.get(new Location(r, c)).moveY(1);
                    }
                }
                changedY++;
                playerYCoord--;
                direction = 3;
            }
            else if(e.getKeyCode() == 40){//down
                for(int r = 0; r < 100; r++){
                    for(int c = 0; c < 100; c++){
                        gameMap.get(new Location(r, c)).moveY(-1);
                    }
                }
                changedY--;
                playerYCoord++;
                direction = 4;
            }

            proj.getMap(gameMap);

            if(e.getKeyCode() == 90 && !shot){//shoot projectile
                shot = true;
                proj.getInfo(direction, playerXCoord, playerYCoord);
                proj.shoot();
            }

            if(gameMap.get(new Location(playerXCoord, playerYCoord)).getElement() == 1){
                landOrWater = false;
            }
            else{
                landOrWater = true;
            }

            if(gameMap.get(new Location(playerXCoord, playerYCoord)).containsCoin()){
                score += 10;
                coinsRemaining--;
                gameMap.get(new Location(playerXCoord, playerYCoord)).collectCoin();
            }        

            if(coinsRemaining == 0){
                gameWon = true;
            }

            if(gameMap.get(new Location(playerXCoord, playerYCoord)).containsEnemy()){
                lives--;
                for(int r = 0; r < 100; r++){
                    for(int c = 0; c < 100; c++){
                        gameMap.get(new Location(r, c)).moveX(-changedX);
                        gameMap.get(new Location(r, c)).moveY(-changedY);
                        playerXCoord = 400/20;
                        playerYCoord = 300/20;
                    }
                }
                changedX = 0;
                changedY = 0;
            }

            if(proj.getX() == playerXCoord && proj.getY() == playerYCoord){
                shot = false;
            }
        }
        if(!start){
            if(e.getKeyCode() == 32){
                start = true;
                th1.start();
                for(int i = 0; i < enemyCount; i++){
                    threads.get(i).start();
                }
                projThread.start();
            }
        }
    }

    public void keyReleased(KeyEvent e){}
 
}
