/**
 * the King
 */
public class KingAlien extends Alien{
    /**
     * the time of absorb player
     */
    int absorbedTimes = 0;

    /**
     * to make sure kingAlien raise successfully
     */
    private boolean release=false;
    /**
     * judge if the kingAlien goes up to release aliens
     */
    private boolean goUp=false;

    KingAlien(GameArena panel){
        super(panel.screenSize.width,panel.screenSize.height/3,panel,1);
        setHeight(400);
        setWidth(300);
        setSpeedY(0);
        setHp(1000);
    }

    public boolean getRelease(){ return  release; }

    @Override
    public void move(GameArena panel, GamePlayer player) {
        String num = String.valueOf(panel.time % 30 / 10);
        if(release){
            this.setSource("img/characters/Boss1" + num + ".png");
        }
        this.setSource("img/characters/Boss0" + num + ".png");
        if (panel.time%3==0) {
            setX((int) (getX()+getSpeedX()));
        }
        setY((int) (getY() + getSpeedY()));

        double distance = Math.pow(getX() - player.getX(),2) + Math.pow(getY() - player.getY(),2);
        if (distance<= 50000 & absorbedTimes == 0){
            absorbedTimes = 100;
        }
        if(absorbedTimes >0){
            int toX = player.getX() + (getX() - player.getX())/10;
            int toY = player.getY() + (getY() - player.getY())/10;
            player.setX(toX);
            player.setY(toY);
            panel.player.setHoldStrength(0);
            absorbedTimes--;
        }
        if(absorbedTimes == 2){
            absorbedTimes = -50; }
        if(absorbedTimes <0){
            absorbedTimes++; }

        setImg(0, 0);
        if (getHpImg().getWidth() > this.getHp()) {
            setImgDec(); }
        kingUp(panel);
        if(Math.abs(this.getX()-panel.tower.getX())<20){
            if(Math.abs(this.getY()-panel.tower.getY())>200){
                setSpeedY(2);
            }
            else {
                setSpeedY(0);
            }
            setSpeedX(0);
        }

    }
    //make KingAlien go up to release aliens
    private void kingUp(GameArena panel){
        if(panel.time%250==0){
            goUp=true;
            setSpeedX(0);
            setSpeedY(-2);
            if(this.getY()<panel.screenSize.height/12){
                this.setSpeedY(0);
            }
        }
        if(((panel.time%250)%30==0)&(panel.time%250!=0)){
            release=true;
            setSpeedY(0);
        }
        if(((panel.time%250)%90==0)&(panel.time%250!=0)){
            release=false;
            setSpeedY(2);
            if(this.getY()>panel.screenSize.height/3){
                this.setSpeedY(0);
            }
        }
        if(((panel.time%250)%110==0)&(panel.time%250!=0)){
            goUp=false;
        }
        else if (!goUp){
            setSpeedX(-1);
            setSpeedY(0);}
    }
}
