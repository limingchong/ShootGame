import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URL;

/**
 * Create main menu with an instance of JFrame, and this frame will never change in this program
 */
public class MainFrame extends JFrame implements MouseListener, KeyListener {
    /**
     * buttons implemented by JLabels
     */
    JLabel start, help, exit, level, players;
    /**
     * for players to input their name
     */
    JTextField userField1, userField2;
    /**
     * background panel
     */
    MainPanel panel;
    /**
     * get screen size(x,y)
     */
    public Dimension screenSize;
    /**
     * The speed at which the background rolls
     */
    int backGroundSpeed = 0;
    /**
     * Control a series of pictures to implement dynamic title
     */
    double titleIndex = 1;
    /**
     *  Control dynamicBackground
     */
    public boolean dynamicBackgroundFlag = true;
    /**
     * when it is true, players will get back to menu from game
     */
    public boolean backToMenuFlag = false;
    /**
     * when it is true, players will try again(see more in CMain)
     */
    public boolean tryAgainFlag = false;
    /**
     * when it is true, game will start(see more in CMain)
     */
    boolean startGame = false;
    /**
     * play background music
     */
    public Music bgm;
    /**
     * ImageIcons used to make JLabels
     */
    ImageIcon LE, ST, HE, EX, PL;
    /**
     * Store players' name
     */
    public String player1 = "Player1", player2 = "Player2";
    /**
     * change flag used to control page(see more in CMain)
     */
    public int changeS = 0;
    /**
     * Used to switch players
     */
    public boolean male = false;
    /**
     * Implement keyboard control menu
     */
    int focusIndex = 0;
    /**
     * store game's difficulty and initial difficulty is 1
     */
    int difficulty = 1;

    /**
     * Create a series of components in main menu
     */
    public MainFrame() {

        bgm = new Music();
        bgm.play("bgm/Menu.wav", true);
        URL classUrl = this.getClass().getResource("");
        Image imageCursor = Toolkit.getDefaultToolkit().getImage(classUrl); // Make the mouse transparent
        setCursor(Toolkit.getDefaultToolkit().createCustomCursor(imageCursor, new Point(0, 0), "cursor"));
        setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon("img/menu/mouse.png").getImage(), new Point(0, 0), null));
        // give an icon to mouse

        setUndecorated(true);
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();//Get screen size in order to make Mainframe cover the whole screen
        setSize(screenSize.width, screenSize.height);

        PL = new ImageIcon("img/menu/players.png");
        PL.setImage(PL.getImage().getScaledInstance((int) (0.178 * screenSize.width), (int) (0.267 * screenSize.height),
                Image.SCALE_DEFAULT));//modify image's size
        players = new JLabel(PL);
        players.setBounds((int) (0.5125 * screenSize.width), (int) (0.25 * screenSize.height), (int) (0.178 * screenSize.width), (int) (0.267 * screenSize.height));
        this.add(players);// add it to the frame

        userField1 = new JTextField();//used to set player's name
        userField1.setText("Player1");//Set initial value
        userField1.setForeground(Color.WHITE);
        userField1.setHorizontalAlignment(0);
        userField1.setBounds((int) (0.5125 * screenSize.width), (int) (0.5 * screenSize.height), (int) (0.0694 * screenSize.width), (int) (0.03125 * screenSize.height));
        userField1.setBorder(BorderFactory.createLoweredBevelBorder());
        userField1.setBackground(new Color(0, 0, 128, 128));//set color
        //userField1.setOpaque(false);
        this.add(userField1);
        userField1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if(e.getKeyCode()==KeyEvent.VK_ENTER)
                {
                    panel.requestFocus();
                }
            }
        });

        userField2 = new JTextField();//same as userField1
        userField2.setText("Player2");
        userField2.setForeground(Color.WHITE);
        userField2.setHorizontalAlignment(0);
        userField2.setBounds((int) (0.61225 * screenSize.width), (int) (0.5 * screenSize.height), (int) (0.0694 * screenSize.width), (int) (0.03125 * screenSize.height));
        userField2.setBorder(BorderFactory.createLoweredBevelBorder());
        userField2.setBackground(new Color(128, 0, 0, 128));
        //userField2.setOpaque(false);
        this.add(userField2);
        userField2.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if(e.getKeyCode()==KeyEvent.VK_ENTER)
                {
                    panel.requestFocus();
                }
            }
        });
        //The following are four buttons using JLabel
        ST = new ImageIcon("img/menu/bowman.png");
        ST.setImage(ST.getImage().getScaledInstance(50, 50,
                Image.SCALE_DEFAULT));
        start = new JLabel("Start");// set a start button using JLabel
        start.setIcon(ST);// set a image for this label
        start.setFont(new Font(null, Font.BOLD, 22));//set word's front and size
        start.setBounds((int) (0.25 * screenSize.width), (int) (0.225 * screenSize.height), 200, 100);//put it in suitable position
        start.setEnabled(false);//make label grey in order to achieve the dynamic effect of button
        start.addMouseListener(this);// when click this button, game will start
        this.add(start);

        HE = new ImageIcon("img/menu/help.png");
        HE.setImage(HE.getImage().getScaledInstance(50, 50,
                Image.SCALE_DEFAULT));
        help = new JLabel("Help");
        help.setIcon(HE);
        help.setBounds((int) (0.25 * screenSize.width), (int) (0.325 * screenSize.height), 200, 100);
        help.setEnabled(false);
        help.addMouseListener(this);
        help.setFont(new Font(null, Font.BOLD, 22));
        this.add(help);

        LE = new ImageIcon("img/menu/pick.png");
        LE.setImage(LE.getImage().getScaledInstance(50, 50,
                Image.SCALE_DEFAULT));
        level = new JLabel("Level");
        level.setIcon(LE);
        level.setBounds((int) (0.25 * screenSize.width), (int) (0.425 * screenSize.height), 200, 100);
        level.setEnabled(false);
        level.addMouseListener(this);
        level.setFont(new Font(null, Font.BOLD, 22));
        this.add(level);

        EX = new ImageIcon("img/menu/cancel.png");
        EX.setImage(EX.getImage().getScaledInstance(50, 50,
                Image.SCALE_DEFAULT));
        exit = new JLabel("Exit Game");
        exit.setIcon(EX);
        exit.setBounds((int) (0.25 * screenSize.width), (int) (0.525 * screenSize.height), 200, 100);
        exit.setEnabled(false);
        exit.setFont(new Font(null, Font.BOLD, 22));
        exit.addMouseListener(this);
        this.add(exit);
        ImageIcon arrow = new ImageIcon("img/menu/arrow.png");
        arrow.setImage(arrow.getImage().getScaledInstance((int) (0.052083 * screenSize.width), (int) (0.078125 * screenSize.height), Image.SCALE_DEFAULT));

        panel = new MainPanel();
        this.add(panel);


        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setIconImage(new ImageIcon("img/icons/logo.png").getImage());//use a image to make logo (Picture of taskbar window)
        this.setVisible(true);
        this.setTitle("Shoot!");


        panel.addKeyListener(this);
        panel.setVisible(true);
        panel.setFocusable(true);
        panel.requestFocus();
        while (dynamicBackgroundFlag) {
            repaint();//use while loop to achieve dynamic background
        }
    }

    @Override
    /**
     * Apart from using mouse to click button, we could also use up or down to select button
     */
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_DOWN:
                focusIndex++;
                break;
            case KeyEvent.VK_UP:
                if (focusIndex == 1) {
                    focusIndex = focusIndex - 2;
                } else {
                    focusIndex--;
                }
                break;

        }
        if (focusIndex == 5) {
            focusIndex = 1;
        }
        if (focusIndex == -1) {
            focusIndex = 4;
        }
        //The following is about how to show what button you are on through making button larger
        if (focusIndex == 1) {
            exit.setEnabled(false);
            help.setEnabled(false);
            start.setEnabled(true);
            ST.setImage(ST.getImage().getScaledInstance(60, 60,
                    Image.SCALE_DEFAULT));//make image larger
            start.setIcon(ST);
            start.setFont(new Font(null, Font.BOLD, 24));//make letters larger
            repaint();
        } else if (focusIndex == 2) {
            start.setEnabled(false);
            level.setEnabled(false);
            help.setEnabled(true);
            HE.setImage(HE.getImage().getScaledInstance(60, 60,
                    Image.SCALE_DEFAULT));
            help.setIcon(HE);
            help.setFont(new Font(null, Font.BOLD, 24));

            repaint();
        } else if (focusIndex == 3) {
            help.setEnabled(false);
            exit.setEnabled(false);
            level.setEnabled(true);
            LE.setImage(LE.getImage().getScaledInstance(60, 60,
                    Image.SCALE_DEFAULT));
            level.setIcon(LE);
            level.setFont(new Font(null, Font.BOLD, 24));
            repaint();
        } else if (focusIndex == 4) {
            level.setEnabled(false);
            start.setEnabled(false);
            exit.setEnabled(true);
            EX.setImage(EX.getImage().getScaledInstance(60, 60,
                    Image.SCALE_DEFAULT));
            exit.setIcon(EX);
            exit.setFont(new Font(null, Font.BOLD, 24));
            repaint();
        }
        //when enter is pressed, different button will lead players to next different page
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            switch (focusIndex) {
                case 1: {
                    new Music().play("sound/click.wav", false);
                    removeComponents();
                    repaint();
                    dynamicBackgroundFlag = false;
                    startGame = true;// when start is pressed, game will start
                    this.requestFocus();// this frame get focus, because key listener which controls players is on mainframe
                    changeS = 2;
                    System.out.println("start");
                    break;
                }
                case 2: {
                    new Music().play("sound/click.wav", false);
                    dynamicBackgroundFlag = false;
                    this.requestFocus();
                    changeS = 3;
                    removeComponents();
                    repaint();
                    break;
                }
                case 3: {
                    new Music().play("sound/click.wav", false);
                    dynamicBackgroundFlag = false;
                    this.requestFocus();
                    changeS = 4;
                    removeComponents();
                    repaint();
                    break;
                }
                case 4: {
                    new Music().play("sound/click.wav", false);
                    System.exit(0);
                    break;
                }
            }
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }


    /**
     * inner class used to implement dynamic background
     */
    class MainPanel extends JPanel {
        Image background, TL;

        public MainPanel() {
            try {
                background = ImageIO.read(new File("img/menu/background.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * use two identical images, change their positionX to implement rolling
         * this method will be called automatically by MainFrame
         * @param g paint on the Main Frame
         */
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            if (dynamicBackgroundFlag) {
                backGroundSpeed -= 1;
                titleIndex += 0.25;
                if (titleIndex >= 10) {
                    titleIndex = 1;
                }
            }
            try {
                TL = ImageIO.read(new File("img/title/title" + (int) titleIndex + ".png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            // The following is how to achieve dynamic background
            // Scroll two identical pictures back and forth
            g.drawImage(background, backGroundSpeed, 0, screenSize.width, screenSize.height, null);
            g.drawImage(background, backGroundSpeed + screenSize.width, 0, screenSize.width, screenSize.height, null);
            g.drawImage(TL, screenSize.width / 2 - 200, screenSize.height / 100 - 100, 400, 400, null);
            if (backGroundSpeed <= -screenSize.width) {
                backGroundSpeed = 0;
            }

        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource().equals(start)) {
            new Music().play("sound/click.wav", false);
            player1=userField1.getText();
            player2=userField2.getText();
            removeALLComponents();
            startGame = true;
            dynamicBackgroundFlag = false;//stop scrolling background pictures
            this.requestFocus();
            changeS = 2;
            start.setEnabled(false);
            repaint();

        } else if (e.getSource().equals(exit)) {
            new Music().play("sound/click.wav", false);
            System.exit(0);
        } else if (e.getSource().equals(level)) {
            new Music().play("sound/click.wav", false);
            dynamicBackgroundFlag = false;
            this.requestFocus();
            changeS = 4;// when level button is clicked, players will enter level-choosing page
            level.setEnabled(false);
            removeComponents();
            repaint();
        } else if (e.getSource().equals(help)) {
            new Music().play("sound/click.wav", false);
            dynamicBackgroundFlag = false;
            this.requestFocus();
            changeS = 3;// when help button is clicked,players will enter help page
            help.setEnabled(false);
            removeComponents();
            repaint();
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    /**
     * when mouse enters buttons, button's image and letter will become larger to show dynamic effect
     */
    public void mouseEntered(MouseEvent e) {
        if (e.getSource().equals(start)) {
            start.setEnabled(true);
            ST.setImage(ST.getImage().getScaledInstance(60, 60,
                    Image.SCALE_DEFAULT));//make image larger
            start.setIcon(ST);
            start.setFont(new Font(null, Font.BOLD, 24));//make letters larger
            repaint();

        } else if (e.getSource().equals(help)) {
            help.setEnabled(true);
            HE.setImage(HE.getImage().getScaledInstance(60, 60,
                    Image.SCALE_DEFAULT));
            help.setIcon(HE);
            help.setFont(new Font(null, Font.BOLD, 24));
            repaint();
        } else if (e.getSource().equals(level)) {
            level.setEnabled(true);
            LE.setImage(LE.getImage().getScaledInstance(60, 60,
                    Image.SCALE_DEFAULT));
            level.setIcon(LE);
            level.setFont(new Font(null, Font.BOLD, 24));
            repaint();
        } else if (e.getSource().equals(exit)) {
            exit.setEnabled(true);
            EX.setImage(EX.getImage().getScaledInstance(60, 60,
                    Image.SCALE_DEFAULT));
            exit.setIcon(EX);
            exit.setFont(new Font(null, Font.BOLD, 24));
            repaint();
        }
    }

    @Override
    /**
     * when mouse exits buttons, button will be back to original appearance
     */
    public void mouseExited(MouseEvent e) {
        if (e.getSource().equals(start)) {
            start.setEnabled(false);
        } else if (e.getSource().equals(help)) {
            help.setEnabled(false);
        } else if (e.getSource().equals(exit)) {
            exit.setEnabled(false);
        } else if (e.getSource().equals(level)) {
            level.setEnabled(false);
        }
    }

    /**
     * get game's difficulty
     * @return game's difficulty: 1:easy,2:normal,3:hard
     *
     */
    public int getDifficulty() {
        return difficulty;// difficulty is stored in MainFrame
    }


    /**
     * change game's difficulty
     * @param difficulty  game's difficulty
     */
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * when players enter game,main menu's components including background panel would be deleted from frame
     */
    public void removeALLComponents() {
        this.remove(start);
        this.remove(level);
        this.remove(exit);
        this.remove(help);
        this.remove(userField1);
        this.remove(userField2);
        this.remove(players);
        this.remove(panel);
    }

    /**
     * when players enter level or help page,apart from background panel main menu's components would be deleted from frame
     */
    public void removeComponents() {
        this.remove(start);
        this.remove(level);
        this.remove(exit);
        this.remove(help);
        this.remove(userField1);
        this.remove(userField2);
        this.remove(players);
    }
    /**
     * when players back to main menu from level or help page,apart from background panel main menu's components would be recreated
     */
    public void createComponents() {
        this.add(start);
        this.add(level);
        this.add(exit);
        this.add(help);
        this.add(userField1);
        this.add(userField2);
        this.add(players);
        this.add(panel);
        panel.requestFocus();
    }
    /**
     * create background panel again
     */
    public void createPanel() {
        this.add(panel);
    }
    /**
     * remove background panel
     */
    public void removePanel() {
        this.remove(panel);
    }
}