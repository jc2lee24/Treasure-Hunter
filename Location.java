public class Location {
    private int x, y, startX, startY;

    public Location(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public void moveX(int change){
        x += change;
    }

    public void moveY(int change){
        x += change;
    }

    @Override
    public boolean equals(Object o){
        Location l = (Location)(o);
        if(l.getX() == x && l.getY() == y){
            return true;
        }
        return false;
    }

    @Override
    public int hashCode(){
        return (x * 100) + y;
    }
}