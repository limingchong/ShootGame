import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Things occurs on this panel, items show on this panel when playing.
 */
public class GameArena extends JPanel {
    /**
     * The height player stop move down.
     */
    public int resetGround;

    /**
     * Store picture-items here.
     */
    private ArrayList<Item> items = new ArrayList<>();

    /**
     * Store all keys that pressed and not released.
     */
    private ArrayList<KeyEvent> pressed = new ArrayList<>();

    /**
     * Store all items that need to be deleted.
     */
    private ArrayList<Item> removeItems = new ArrayList<>();

    /**
     * Store index of alien that enable to throw rocks.
     */
    private ArrayList<int[]> indexCase = new ArrayList<>();

    /**
     * Whether the game is currently paused.
     */
    private boolean pause = false;

    /**
     * Whether the player has launched an arrow.
     */
    public boolean shoot = false;

    /**
     * A player currently displayed in the game.
     */
    public GamePlayer player;

    /**
     * The current time of the game (the number of frames displayed).
     */
    public int time = 0;

    /**
     * Whether the player has been contacted by alien.
     */
    public boolean contact;

    /**
     * A tower currently displayed in the game.
     */
    public Tower tower;

    /**
     * A random number generated.
     */
    Random random;

    /**
     * The current window of the game.
     */
    MainFrame window;

    /**
     * The current number of alien in the game.
     */
    private int alienCount = 0;

    /**
     * The current background of the game.
     */
    private Item background;

    /**
     * The number of aliens have been occurred.
     */
    private int alienAccumulate;

    /**
     * The score of the current player of the game.
     */
    public int score = 0;
    public Dimension screenSize;

    /**
     * The extra-skill of rampage mode.
     */
    public boolean rampageMode = false;

    /**
     * The extra-skill of jump higher.
     */
    public boolean jumpHigher = false;

    /**
     * The time of rampage mode opened.
     */
    public int upGradeTime = 0;

    /**
     * When quintic shoot cold is 100, player can use the extra-skill.
     */
    public int quinticShootCold = 0;

    /**
     * The current king alien instance of the game.
     */
    public KingAlien kingAlien;

    /**
     * The current index of king alien.
     */
    private int kingIndex;

    /**
     * Whether the game is currently over.
     */
    public boolean end = false;

    /**
     * shaking time
     */
    public int shaking = 0;

    /**
     * @param window new GameArena add to the window.
     */
    public GameArena(MainFrame window) {
        window.bgm.close();
        if (window.getDifficulty()==3){
            window.bgm.play("bgm/Hard.wav",true);
        }else {
            window.bgm.play("bgm/Easy.wav",true);
        }
        this.window = window;
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width, screenSize.height);

        background = new Item(screenSize.width / 2, screenSize.height / 2, null, this);
        background.setWidth(screenSize.width);
        background.setHeight(screenSize.height);
        tower = new Tower(this);
        player = new GamePlayer(this);

        window.add(this);
        this.setLayout(null);

        /**
         * Listen to the key released.
         */
        window.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {

                int i = 0;
                while (i < pressed.size()) {
                    if (e.getKeyCode() == pressed.get(i).getKeyCode()) {
                        pressed.remove(i); // Error are always reported here, but it can be run
                        i = -1;
                    }
                    // remove the key have been released from 'pressed'.
                    i++;
                }
                if (e.getKeyCode() == KeyEvent.VK_P) {
                    pause = !pause;
                }
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    System.exit(0);
                }
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    shoot = true;
                }
                if ((e.getKeyCode() == KeyEvent.VK_A) & (quinticShootCold == 100)) {
                    quinticShoot();
                    quinticShootCold = 1;
                }
                if (e.getKeyCode() == KeyEvent.VK_SHIFT & jumpHigher) {
                    if (player.jump == 20) {
                        player.jump = 10;
                    } else {
                        player.jump = 20;
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_1) {
                    score+=100; // cheat!
                }
                if (e.getKeyCode() == KeyEvent.VK_2) {
                    king(); // cheat!
                }
                if (e.getKeyCode() == KeyEvent.VK_3) {
                    end = !end; // cheat!
                }
            }
        });


        /**
         * Listen to the key pressed, then add them to 'pressed'.
         */
        window.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int exist = 0;
                for (KeyEvent p : pressed) {
                    if (p.getKeyCode() == e.getKeyCode()) {
                        exist = 1;
                        break;
                    }
                }
                // Do not store the same keys to reduce memory usage.
                if (exist == 0) {
                    pressed.add(e);
                }
            }
        });

        window.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new Music().play("sound/click.wav", false);
            }
        });

    }

    /**
     * After killed all kids of the king, create the king and its guards.
     */
    public void king() {
        kingAlien = new KingAlien(this);
        new Alien(kingAlien.getX() - window.getWidth() / 10, window.getHeight() / 5 + kingAlien.getY(), this, 6);
        new Alien(kingAlien.getX() + window.getWidth() / 10, -window.getHeight() / 5 + kingAlien.getY(), this, 6);
        new Alien(kingAlien.getX() + window.getWidth() / 10, window.getHeight() / 5 + kingAlien.getY(), this, 6);
        new Alien(kingAlien.getX() - window.getWidth() / 10, -window.getHeight() / 5 + kingAlien.getY(), this, 6);
        new Alien(kingAlien.getX(), -window.getHeight() / 5 + kingAlien.getY(), this, 6);
        new Alien(kingAlien.getX(), window.getHeight() / 5 + kingAlien.getY(), this, 6);
        new Alien(kingAlien.getX() - window.getWidth() / 10, kingAlien.getY(), this, 6);
        new Alien(kingAlien.getX() + window.getWidth() / 10, kingAlien.getY(), this, 6);
    }

    /**
     * Create aliens in easy mode.
     */
    public void easyAlien() {
        alienCount++;
        alienAccumulate++;
        random = new Random();
        new Alien(random.nextInt(screenSize.width - 100), random.nextInt(2 * screenSize.height / 5) + 100, this, 1);
    }

    /**
     * Create aliens in normal mode.
     */
    public void normalAlien() {
        alienCount++;
        alienAccumulate++;
        random = new Random();
        new Alien(screenSize.width - 100, random.nextInt(2 * screenSize.height / 5) + 100, this, 2);
    }

    /**
     * Create aliens in hard mode.
     */
    public void hardAlien() {
        alienCount += 4;
        alienAccumulate += 4;
        random = new Random();
        new Alien((int) (0.8 * screenSize.width + random.nextInt(screenSize.width / 5)), random.nextInt(screenSize.height / 3) + 100, this, 2);
        new Alien(screenSize.width / 5 + random.nextInt(4 * screenSize.width / 5), random.nextInt(screenSize.height / 3) + 200, this, 3);
        new Alien((int) (0.8 * screenSize.width + random.nextInt(screenSize.width / 5)), 3 * screenSize.height / 5, this, 4);
        new Alien(screenSize.width / 5 + random.nextInt(4 * screenSize.width / 5), screenSize.height / 3, this, 5);
    }

    /**
     * always repaints, objects need painted are from ArraryList 'items'.
     *
     * @param g paint on the GameArena.
     */
    public void paint(Graphics g) {
        super.paint(g);
        contact =false;
        if (!pause & !end) {
            moveArrow(time, player);
            updateItem();
            waiter();
        }
        if(!contact & !pause & !end){
            player.move(this);
        }
        if (shaking > 0) {
            if (shaking > 3) {
                background.setY(background.getY() - 6);
            }
            background.setY(background.getY() + 3);
            shaking--;
        } else {
            background.setY(screenSize.height / 2);
        }
        g.setColor(Color.BLACK);
        for (Item item : items) {
            int x = item.getX() - (item.getWidth()) / 2;
            int y = item.getY() - (item.getHeight()) / 2;
            // Each frame draws the tail of the arrow launched by the player according to
            // the launching direction.
            if (item instanceof Arrow) {
                double width = ((Arrow) item).getLastX() - item.getX();
                double height = -((Arrow) item).getLastY() + item.getY();
                double alfa = Math.atan(width / height);
                int dy = (int) (50 * Math.cos(alfa));
                int dx = -(int) (50 * Math.sin(alfa));
                if (height < 0) {
                    dy = -dy;
                    dx = -dx;
                }
                Graphics2D g2 = (Graphics2D) g;
                g2.setStroke(new BasicStroke(3));
                int colorStrength = 2 * (int) ((Arrow) item).getSpeed();
                if (colorStrength > 254) {
                    colorStrength = 254;
                }
                g2.setColor(new Color(255, 140, 0, colorStrength));
                int originX = ((Arrow) item).getLastX() - dx;
                int originY = ((Arrow) item).getLastY() - dy;
                g2.drawLine(originX, originY, item.getX(), item.getY()); // draw arrow
            }
            // Update the picture of the bomb dropped by king alien every 10 frames (produce
            // animation effect).
            if (item instanceof Rock && ((Rock) item).getMode() == 1) {
                String num = String.valueOf(time % 20 / 10);
                item.setSource("img/characters/boom" + num + ".png");
            }
            // Each frame draws the laser emitted by alien according to the emission
            // direction.
            if (item instanceof Rock && ((Rock) item).getMode() == 0) {
                int width = item.getX() - 100;
                int height = -item.getY() + getHeight() - 250;
                double alfa;
                if (height != 0) {
                    alfa = Math.atan(width / height);
                } else {
                    alfa = 0;
                }
                int dy = (int) (88 * Math.cos(alfa));
                int dx = -(int) (88 * Math.sin(alfa));
                if (height < 0) {
                    dy = -dy;
                    dx = -dx;
                }
                int originX = item.getX() - dx;
                int originY = item.getY() - dy;
                Graphics2D g2 = (Graphics2D) g;
                g2.setStroke(new BasicStroke(5));
                g2.setColor(new Color(128, 0, 128, 100));
                g2.drawLine(originX, originY, item.getX(), item.getY());
            }
            g.setFont(new Font(null, Font.BOLD, 16));
            g.setColor(Color.ORANGE);
            if (window.male) {
                g.drawString(window.player1, player.getX() - 32, player.getY() - 30);
            } else {
                g.drawString(window.player2, player.getX() - 32, player.getY() - 30);
            }
            if (contact) {
                g.setColor(new Color(215, 16, 46));
                g.drawString("frozen", player.getX(), player.getY() - 40);
            }

            String source = item.getSource();
            g.drawImage(new ImageIcon(source).getImage(), x, y, item.getWidth(), item.getHeight(), this);

        }
        if (quinticShootCold < 100 & quinticShootCold >= 1) {
            quinticShootCold++;
        }
        g.setColor(Color.BLUE);
        g.fillRect(player.getX() - 32, player.getY() - 70, quinticShootCold, 10);
        if (upGradeTime != 0 & time - upGradeTime < 600) {
            g.setColor(Color.YELLOW);
            g.fillRect(player.getX() - 32, player.getY() - 90, 100 - (time - upGradeTime) / 6, 10);
        }
        g.setColor(Color.GREEN);
        g.drawString("Tower's life: " + (int) tower.getHp() + "/1000", tower.getX() / 2, tower.getY() - tower.getHeight() / 2);
        super.paintComponents(g);
        repaint();
    }

    /**
     * All items can be updated (every frame will be called).
     */
    public void updateItem() {
        if (time - upGradeTime == 600 & upGradeTime != 0) {
            player.setStrong(1);
            player.setAcceleration(0.1);
        }
        random = new Random();
        int backIndex = time % 40 / 10;
        switch (window.getDifficulty()) {
            // alienCount: the number of aliens on the screen;
            // alienAccumulate: the number of aliens have been created;
            // time: every 100 period have a peak of create rate.

            // Easy mode, generating easy aliens randomly in the range.
            case 1:
                background.setSource("img/background/easy" + backIndex + ".png");
                if ((time % 100 + 5 * random.nextFloat() * (20 - alienCount) + random.nextFloat() * (60 - alienAccumulate)) > 200 | alienCount == 0) {
                    easyAlien();
                }
                break;

            // Normal mode, randomly generate normal aliens on the right side of the screen.
            case 2:
                background.setSource("img/background/normal" + backIndex + ".png");
                if ((time % 100 + 5 * random.nextFloat() * (15 - alienCount) + random.nextFloat() * (50 - alienAccumulate)) > 200 | alienCount == 0) {
                    normalAlien();
                }
                break;

            // Difficult mode, generate kingAlien and its children.
            case 3:
                background.setSource("img/background/hard" + backIndex + ".png");
                if ((time % 100 + 5 * random.nextFloat() * (10 - alienCount) + 3 * random.nextFloat() * (40 - alienAccumulate)) > 200 & alienAccumulate < 20) {
                    hardAlien();
                }
                if (alienCount == 0 & kingAlien == null & time > 100) {
                    king();
                    window.bgm.close();
                    window.bgm.play("bgm/Boss.wav", true);
                }
                if (kingAlien != null) {
                    if (kingAlien.getRelease() & (time % 280) == 0 & alienCount < 5) {
                        Alien slimer = new Alien(kingAlien.getX() + random.nextInt(200) + 20, kingAlien.getY() - (random.nextInt(200) + 20), this, 3);
                        slimer.setSpeedX(20);
                        slimer.setSpeedY(10);
                        new Alien((kingAlien.getX() + random.nextInt(200) + 50), kingAlien.getY() + 50, this, 5);
                        new Alien((kingAlien.getX() - random.nextInt(200) - 50), kingAlien.getY() + 50, this, 5);
                    }
                }
                break;
        }
        player.setGroundHeight(resetGround);
        resetGround = getHeight() / 3;
        for (Item item : items) {

            if (item instanceof Arrow) {
                ((Arrow) item).update(time);
                // When the arrow fired by the player is off the screen, delete the arrow.
                if (item.getY() > this.getHeight() | item.getX() > this.getWidth()) {
                    removeItems.add(item);
                }
                for (Item item2 : items) {
                    if (item2 instanceof Alien) {
                        // When the arrow fired by the player hits alien, delete the arrow, alien will
                        // be attacked and play the sound effect, and the player will gain points.
                        if (((Alien) item2).getMoveMode() == 4 && ((Math.abs(item2.getX() - (item.getX() - 50)) < 50
                                & Math.abs(item2.getY() - (item.getY() - 75)) < 75) || Math.abs(item2.getX() - (item.getX() + 50)) < 50
                                & Math.abs(item2.getY() - (item.getY() + 75)) < 75)) {
                            score += (int) (1.07226722 * Math.exp((((Arrow) item).getSpeed() * 0.06977531))) * window.getDifficulty();
                            removeItems.add(item);
                            new Music().play("sound/hit.wav", false);
                            ((Alien) item2).Attacked(((Arrow) item).getSpeed());
                        } else if (item2 instanceof KingAlien) {
                            if ((Math.pow((item2.getX()) - item.getX(), 2) - (7396 - 0.6582 * Math.pow((item2.getY()) - item.getY(), 2)) <= 0)) {
                                removeItems.add(item);
                                new Music().play("sound/hit.wav", false);
                                ((Alien) item2).Attacked(((Arrow) item).getSpeed());
                            }
                        } else if (Math.abs(item2.getX() - item.getX()) < 25 & Math.abs(item2.getY() - item.getY()) < 25) {
                            score += (int) (1.07226722 * Math.exp((((Arrow) item).getSpeed() * 0.06977531)));
                            removeItems.add(item);
                            new Music().play("sound/hit.wav", false);
                            ((Alien) item2).Attacked(((Arrow) item).getSpeed());
                        }
                    }

                }
            }

            if (item instanceof Rock) {
                // When the rock fired by the alien is off the screen, delete the rock.
                ((Rock) item).update(this);
                if (item.getY() > this.getHeight() | item.getX() > this.getWidth()) {
                    removeItems.add(item);
                }
                // When the rock launched by alien hits the tower, delete the rock and the tower
                // will be attacked to play the sound effect.
                for (Item item2 : items) {
                    if ((item2 instanceof Tower) & Math.abs(item2.getX() - item.getX()) < 150
                            & Math.abs(item2.getY() - item.getY()) < 100) {
                        removeItems.add(item);
                        new Music().play("sound/hit.wav", false);
                        ((Tower) item2).towerAttacked(this);
                    }
                }
            }

            if (item instanceof Alien) {
                float hp = ((Alien) item).getHp();
                // Alien can only move when it's alive.
                if (hp > 0) {
                    ((Alien) item).move(this, player);
                }
                // After the death of alien, there are 50 frames for the boom effect.
                if (hp <= 0) {
                    item.setSource("img/characters/boom.png");
                    removeItems.add(((Alien) item).getHpImg());
                    ((Alien) item).setHp(((Alien) item).getHp() - 1);
                }
                if (hp <= -50) {
                    removeItems.add(item);
                    alienCount--;
                }

                if ((player.getY() <= item.getY() - 20) & (Math.abs(player.getX() - item.getX()) < 30)
                        & (getHeight() - item.getY() + 50 > resetGround)) {
                    resetGround = getHeight() - item.getY() + 50;
                }

                // Record the index of the alien that needs to launch rock.
                if (((Alien) item).getMoveMode() == 2) {
                    int n = items.indexOf(item);
                    int nx[] = new int[1];
                    nx[0] = n;
                    indexCase.add(nx);
                }
                if (((Alien) item).getMoveMode() == 4 & item.getSpeedX() == 0) {
                    if (time % 50 == 0 | time % 50 == 1 | time % 50 == 2 | time % 50 == 3 | time % 50 == 4) {
                        item.setSource("img/characters/dinosaur_attack1.png");
                        tower.towerAttacked(this);
                    } else {
                        item.setSource("img/characters/dinosaur_attack0.png");
                    }
                }
            }
            if (item instanceof KingAlien) {
                kingIndex = items.indexOf(item);
                int n = items.indexOf(item);
                int nx[] = new int[1];
                nx[0] = n;
                indexCase.add(nx);
                ((KingAlien) item).move(this, player);
            }
            if (item instanceof Tower) {
                float hp = ((Tower) item).getHp();
                item.setSource("img/characters/tower.png");
                if (hp <= 0) {
                    end = true;
                    removeItems.add(item);
                    break;
                }
            }

            if (item instanceof Dot) {
                removeItems.add(item);
            }
        }

        if (time % 200 == 0) {
            for (int i = 0; i < indexCase.size(); i++) {
                new Rock(((Alien) items.get(indexCase.get(i)[0])), this);
            }
        }
        player.setGroundHeight(resetGround);
        indexCase.clear();
        if (random.nextInt(30) == 1 && kingIndex > 0) {
            if (Math.abs(kingAlien.getX() - tower.getX()) < 20) {
                Rock boom = new Rock((Alien) items.get(kingIndex), this);
                boom.setSource("img/characters/boom0.png");
                boom.setSize(80);
                boom.setMode(1);
            }
        }
        kingIndex = 0;
        for (Item item : removeItems) {
            items.remove(item);
        }
        removeItems.clear();

        player.trajectory(this);
    }

    /**
     * @return tell other objects what key is being pressed.
     */
    public ArrayList getKeyList() {
        return pressed;
    }

    /**
     * unit time: 10 millis.
     */
    public void waiter() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        time++;
    }

    /**
     * @param time Pause for a specific length of time.
     */
    public void waiter(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean pause() {
        return pause;
    }

    public boolean end() {
        return end;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    public void setEnd(boolean end) {
        this.end = end;
    }

    /**
     * @param time   Get the current time.
     * @param player Tell the position of player where the arrow started.
     */
    public void moveArrow(int time, GamePlayer player) {
        if (shoot) {
            items.add(new Arrow(player, this, time));
            new Music().play("sound/shoot.wav", false);
            shoot = false;
        }
    }

    /**
     * @param item add item to the ArrayList, so that the item will be painted.
     * @see Item
     */
    public void addItem(Item item) {
        items.add(item);
    }

    /**
     * Trigger skill, 5 arrows in a time.
     */
    public void quinticShoot() {
        new Arrow(player, this, time, 11, -19);
        new Arrow(player, this, time, 13, -17);
        new Arrow(player, this, time, 15, -15);
        new Arrow(player, this, time, 17, -13);
        new Arrow(player, this, time, 19, -11);

        new Music().play("sound/shoot.wav", false);
        waiter(5);
        new Music().play("sound/shoot.wav", false);
        waiter(5);
        new Music().play("sound/shoot.wav", false);
        waiter(5);
        new Music().play("sound/shoot.wav", false);
        waiter(5);
        new Music().play("sound/shoot.wav", false);
    }

    /**
     * Make the player can't move.
     * @param contact It is true when the player contacts a Slimer.
     */
    public void setContact(boolean contact) {
        this.contact = contact;
    }
}