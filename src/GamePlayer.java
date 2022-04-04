import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * Player is the hero of game.
 */
public class GamePlayer extends Item {

    /**
     *
     * the max value of forceX.
     */
    private double acceleration = 0.1;
    /**
     * as a parameter multiply on hold strength.
     */
    private double strong = 1;
    /**
     * increase or decrease when press left or right.
     */
    private double forceX;
    /**
     * increase when press up and then decrease automatically by gravity.
     */
    private double forceY = 0.5;
    /**
     * increase when keep pressing on space, and reset to 0 after released.
     */
    private double holdStrength = 0;
    /**
     * increase or decrease when press w or s.
     */
    private double holdAngle = 0.7;
    /**
     * when player on the height of groundHeight, reset speedY as 0.
     */
    private int groundHeight = 50;
    /**
     * the index of player image to be showed.
     */
    private double currentSourceIndex = 1;
    /**
     * after jumped every time, there is a time to wait for the another
     * jump when jumpCold decrease to 0.
     */
    private int jumpCold = 0;
    /**
     * The initial upward velocity of a jump.
     */
    public int jump = 10;
    /**
     * check if player pressing on space.
     */
    boolean hold = false;
    /**
     * secondHold check if the player holds the space too long,
     * causing the string to fall off the bow, and has not release.
     */
    boolean secondHold = false;
    /**
     * give player a object of weapon.
     */
    private Weapon weapon;

    GamePlayer(GameArena panel) {
        super(20, panel.getHeight() - 200, "img/characters/Player0.png", panel);
        weapon = new Weapon(panel);
        this.setSpeedX(0);
        this.setSpeedY(0);
    }

    public void move(GameArena panel) {
        if(holdAngle<0){
            holdAngle = 0.011;
        }
        if(holdAngle>3.141){
            holdAngle = 3.14;
        }

        ArrayList<KeyEvent> pressed = panel.getKeyList();
        if (pressed.contains(KeyEvent.VK_RIGHT) | !pressed.contains(KeyEvent.VK_LEFT)) {
            forceX = 0;
        }

        hold = false;
        for (KeyEvent e : pressed) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_RIGHT:
                    if (forceX < acceleration*5) {
                        forceX += acceleration;
                        currentSourceIndex += 0.2;
                        this.setWidth(64);
                    }
                    break;
                case KeyEvent.VK_LEFT:
                    if (forceX > -acceleration*5) {
                        forceX -= acceleration;
                        currentSourceIndex -= 0.2;
                        this.setWidth(-64);
                    }
                    break;
                case KeyEvent.VK_UP:
                    if ((this.getY() >= panel.getHeight() - groundHeight) & (jumpCold == 0)) {
                        this.setSpeedY(-jump);
                        new Music().play("sound/jump1.wav",false);
                        jumpCold = 50;
                    }
                    break;
                case KeyEvent.VK_SPACE:
                    holdStrength = holdStrength + strong * Math.exp(-holdStrength) / 10;
                    hold = true;
                    break;
                case KeyEvent.VK_W:
                    if((holdAngle < 1.5707963 * 2)&&(holdAngle > 0)) {
                        if(holdAngle <= 1.5707963)
                        {
                            holdAngle += 0.01;
                        }else if(holdAngle >= 1.5707963)
                        {
                            holdAngle -= 0.01;
                        }
                    }
                    break;
                case KeyEvent.VK_S:
                    if((holdAngle < 1.5707963 * 2)&&(holdAngle > 0)) {
                        if(holdAngle <= 1.5707963)
                        {
                            holdAngle -= 0.01;
                        }else if(holdAngle >= 1.5707963)
                        {
                            holdAngle += 0.01;
                        }
                    }
                    break;
            }
        }

        if ((getWidth()< 0 & holdAngle < 1.5707963)||(getWidth() > 0 & holdAngle > 1.5707963)) {
            holdAngle = 2*1.5707963-holdAngle;
        }

        if (!hold) {
            holdStrength = 0;
            secondHold = false;
        }

        if (secondHold) {
            holdStrength = 0;
        }
        if (holdStrength >= strong*3) {
            secondHold = true;
        }

        moveX(panel,forceX);
        moveY(panel);

        if ((int) currentSourceIndex == -1) {
            currentSourceIndex += 4;
        }
        if ((int) currentSourceIndex == 4) {
            currentSourceIndex -= 4;
        }
        if(panel.window.male) {
            setSource("img/characters/Player"+(int)currentSourceIndex+".png");
        }else {
            setSource("img/characters/PlayerF"+(int)currentSourceIndex+".png");
        }
        jumpCold--;
        if(jumpCold<0){
            jumpCold = 0;
        }

    }

    /**
     *
     * @param panel move on the panel.
     * @param forceX Unit length of movement.
     */
    public void moveX(GameArena panel,double forceX) {

        if (holdAngle<1.5707963){
            weapon.setX(getX()+20);
        }else{
            weapon.setX(getX()-20);
        }
        weapon.setY(getY()-10);
        weapon.setWeapon(holdStrength,holdAngle);

        if (forceX == 0) { // to static
            if (this.getSpeedX() > 0) {
                this.setSpeedX(this.getSpeedX() - this.getForceR());
                if (this.getSpeedX() < 0) {
                    this.setSpeedX(0);
                }
            }
            if (this.getSpeedX() < 0) {
                this.setSpeedX(this.getSpeedX() + this.getForceR());
                if (this.getSpeedX() > 0) {
                    this.setSpeedX(0);
                }
            }
        }

        this.setSpeedX(this.getSpeedX() + forceX);
        // add speed

        //speed limit
        if (this.getSpeedX() >= acceleration*80) {
            this.setSpeedX(acceleration*80);
        }
        if (this.getSpeedX() <= -acceleration*80) {
            this.setSpeedX(-acceleration*80);
        }

        // collision
        if (this.getSpeedX() > 0 & this.getX() >= panel.getWidth() | this.getSpeedX() < 0 & this.getX() <= 0) {
            this.setSpeedX(-this.getSpeedX() / 2);
        }

        this.setX((int) (this.getX() + this.getSpeedX())); //add move
    }

    /**
     *
     * @param panel move on the panel.
     */
    public void moveY(GameArena panel) {
        this.setSpeedY(this.getSpeedY() + forceY);
        if (this.getSpeedY() > 0 & this.getY() >= panel.getHeight() - groundHeight) { // collision ground
            this.setSpeedY(0);
            this.setY(panel.getHeight() - groundHeight);
        }
        this.setY((int) (this.getY() + this.getSpeedY()));
    }

    public double getHoldStrength() {
        return holdStrength;
    }

    public void setHoldStrength(double holdStrength) {
        this.holdStrength = holdStrength;
    }

    public double getHoldAngle() {
        return holdAngle;
    }

    /**
     * this will be called when player keep pressing space.
     * @param panel Create a trajectory for the arrow on panel.
     */
    public void trajectory(GameArena panel) {
        if (holdStrength * holdAngle > 0) {
            int x = this.getX();
            double strength = holdStrength * 10;
            double gravity = -0.5;
            double SX = strength * Math.cos(holdAngle);
            double SY = -strength * Math.sin(holdAngle);
            int time = (int) (2 * SY / gravity);
            time += (int)Math.sqrt(-2*(panel.getHeight()-getY())/gravity);
            for (int i = 0; i <= time; i++) {
                Dot newDot = new Dot(panel, (int) (x + SX * i), (int) (gravity * i * i / 2 - SY * i) - this.getY() + panel.getHeight());
                newDot.setY(panel.getHeight() - newDot.getY());
            }
        }
    }

    public void setGroundHeight(int groundHeight) {
        this.groundHeight = groundHeight;
    }

    public void setStrong(double strong) {
        this.strong = strong;
    }

    public void setAcceleration(double acceleration) {
        this.acceleration = acceleration;
    }
}

