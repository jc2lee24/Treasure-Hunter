public class Timer implements Runnable{
    int time;

    public Timer(int time){
        this.time = time;
    }

    public void run(){
        for(int i = time; i >= 0; i--){
            try{
                Thread.sleep(1000);
                }
            catch(InterruptedException ex){
                Thread.currentThread().interrupt();
            }
            time = i;
        }
                    
    }

    public int getTime(){
        return time;
    }
}
