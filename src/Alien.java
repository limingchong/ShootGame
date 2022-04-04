/**
 * Enemies.
 */
public class Alien extends Item {
    /**
     * blood value
     */
    private float hp = 100;
    /**
     * blood image
     */
    private Item hpImg;
    /**
     * attack value
     */
    private float damage;
    /**
     * type of alien
     */
    private int moveMode;
    /**
     * size of alien image
     */
    private int size = 60;

    /**
     * create an item to show alien
     * @param x initial x position
     * @param y initial y position
     * @param panel paint on panel
     * @param moveMode alien type
     */
    public Alien(int x, int y, GameArena panel, int moveMode) {
        super(x, y, null, panel);
        hpImg = new Item(x, y - 50, "img/characters/blood.png", panel); // create a blood image
        hpImg.setWidth(100);
        hpImg.setHeight(5);
        this.moveMode = moveMode;
        super.setSize(60);
        if (moveMode == 2) { // horizontal move alien
            setSpeedX(-1);
        }
        if (moveMode == 4) { // dinosaur
            setHp(200);
            setSpeedX(-1);
            hpImg.setWidth(200);
        }
        if (this instanceof KingAlien) {
            setHp(400);
            hpImg.setWidth(400);
        }
    }

    public float getHp() {
        return hp;
    }

    public Item getHpImg() {
        return hpImg;
    }

    public int getMoveMode() {
        return moveMode;
    }

    public void setHp(float hp) {
        this.hp = hp;
    }

    public void setImgDec() {
        hpImg.setWidth(hpImg.getWidth() - 1);
    }

    /**
     * make the image of blood on center
     * @param xOffset x offset
     * @param yOffset y offset
     */
    public void setImg(int xOffset, int yOffset) {
        int x = this.getX();
        int y = this.getY();
        hpImg.setY(y - 50 + yOffset);
        hpImg.setX(x - 50 + hpImg.getWidth() / 2 + xOffset);
    }

    public void Attacked(float dmg) {
        // after being attacked
        damage += dmg;
        hp -= dmg;
        if (this instanceof KingAlien) {
            this.setSource("img/characters/Boss_attacked.png");
        } else {
            switch (this.moveMode) {
                // attacked image
                case 1:
                    this.setSource("img/characters/alien_attacked.png");
                    break;
                case 2:
                    this.setSource("img/characters/alien_attacked.png");
                    break;
                case 3:
                    this.setSource("img/characters/slame_attacked.png");
                    break;
                case 4:
                    this.setSource("img/characters/dinosaur_attacked.png");
                    break;
                case 5:
                    this.setSource("img/characters/alien_pink_attacked.png");
                    break;
            }
        }
    }

    public void decImg() {
        hp -= 5;
    }

    /**
     * this method will be run every time automatically.
     * @param panel move on panel
     * @param player find the position of player
     */
    public void move(GameArena panel, GamePlayer player) {

        switch (this.moveMode) {
            // static
            case 1:
                String num = String.valueOf(panel.time % 30 / 10);
                this.setSource("img/characters/alien" + num + ".png");
                this.setSpeedX(0);
                this.setSpeedY(0);
                setImg(0, 0);
                if (getHpImg().getWidth() > hp) {
                    setImgDec();
                }
                break;

            // horizontal move
            case 2:
                num = String.valueOf(panel.time % 30 / 10);
                this.setSource("img/characters/alien" + num + ".png");
                this.setX((int) (this.getX() + this.getSpeedX()));
                setImg(0, 0);
                if (getHpImg().getWidth() > hp) {
                    setImgDec();
                }
                if (this.getX() <= 100) {
                    setSpeedX(0);
                }
                break;

            // Slimer: get close to player and hold player
            case 3:
                num = String.valueOf(panel.time % 20 / 10);
                this.setSource("img/characters/Slimer" + num + ".png");
                double alfa;
                int distanceX = getX() - panel.player.getX();
                int distanceY = -getY() + panel.player.getY();
                if (distanceY != 0) {
                    alfa = Math.atan(distanceX / distanceY);
                } else {
                    alfa = 0;
                }
                if (getSpeedX() > 2) {
                    setSpeedX(getSpeedX() - 0.2);
                } else {
                    setSpeedX(2);
                }
                if (getSpeedY() > 2) {
                    setSpeedY(getSpeedY() - 0.2);
                } else {
                    setSpeedY(2);
                }
                int dy = (int) (getSpeedY() * Math.cos(alfa));
                int dx = -(int) (getSpeedX() * Math.sin(alfa));

                if (distanceY < 0) {
                    dy = -dy;
                    dx = -dx;
                }
                if (distanceX * getWidth() < 0) {
                    setWidth(-getWidth());
                }
                int toX = getX() + dx;
                int toY = Math.max(getY() + dy, 100);
                this.setX(toX);
                this.setY(toY);
                if (Math.abs(getX() - player.getX()) < 15 & player.getY()-getY() < 15) {
                    panel.setContact(true);
                    this.setSize(size--);
                    this.decImg(); // slower
                    if (hp <= 0) {
                        this.setSize(60);
                    }
                }
                setImg(0, 0);
                if (getHpImg().getWidth() > hp) {
                    setImgDec();
                }
                break;

            // Dinosuar: high blood value alien
            case 4:
                if (getSpeedX() < 0) {
                    num = String.valueOf((panel.time % 40 / 10) + 1);
                    setSource("img/characters/dinosaur" + num + ".png");
                }
                this.setHeight(400);
                this.setWidth(-250);
                this.setX((int) (this.getX() + this.getSpeedX()));
                setImg(0, 0);
                if (getHpImg().getWidth() > hp) {
                    setImgDec();
                }
                if (this.getX() <= 100) {
                    setSpeedX(0);
                    setSource("img/characters/dinosaur_attack0.png");
                }
                break;

            // horizontal move: boom on the ground
            case 5:
                num = String.valueOf(panel.time % 30 / 10);
                this.setSource("img/characters/alien_pink" + num + ".png");
                this.setSize(100);
                this.getHpImg().setX(this.getX() - 50);
                if (this.getX() > panel.getWidth()) {
                    this.setX(this.getX() - 1);
                } else {
                    this.setY(this.getY() + 8);
                }
                if (this.getY() >= (0.5 * panel.screenSize.height)) {
                    this.setSpeedY(0);
                    this.setHp(this.getHp() - 5);
                    if (Math.abs(this.getX() - player.getX()) < 100 & Math.abs(this.getY() - player.getY()) < 50) {
                        panel.player.setSpeedX(panel.player.getSpeedX() + (1000 - Math.abs(getX() - panel.player.getX()))
                                * (getX() - panel.player.getX() > 0 ? -10 : 10));
                        panel.player.setSpeedY(panel.player.getSpeedY() + (1000 - Math.abs(getY() - panel.player.getY())));
                    }
                }
                setImg(-20, 0);
                if (getHpImg().getWidth() > hp) {
                    setImgDec();
                }
                break;

            // guards of King alien
            case 6:
                num = String.valueOf(panel.time % 30 / 10);
                this.setSource("img/characters/alien" + num + ".png");
                if (panel.time % 3 == 0) {
                    this.setX((int) (this.getX() + 1.5 * panel.kingAlien.getSpeedX()));
                }
                this.setY((int) (this.getY() + panel.kingAlien.getSpeedY()));
                setImg(0, 0);
                if (getHpImg().getWidth() > hp) {
                    setImgDec();
                }
                if ((panel.tower.getX() == (this.getX() - 20))) {
                    setSpeedX(0);
                    setSpeedY(0);
                }
                break;

        }
    }

}
