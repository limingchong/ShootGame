import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Create level page
 */
public class Level implements MouseListener {
    /**
     * buttons implemented by JLabels
     */
    public JLabel back, easy, normal, hard,backgroundLabel;
    /**
     * these components will show on window
     */
    MainFrame window;
    /**
     * screen size(x,y)
     */
    Dimension screenSize;
    /**
     * ImageIcons to make JLabels
     */
    ImageIcon EX, EE, NE, HE, BK;

    /**
     * Create a series of components of level page
     * @param MFrame level units will be added on the MainFrame
     */
    public Level(MainFrame MFrame) {
        window = MFrame;
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        //The following is to create some buttons
        EX = new ImageIcon("img/menu/cancel.png");
        EX.setImage(EX.getImage().getScaledInstance(50, 50,
                Image.SCALE_DEFAULT));
        back = new JLabel("Back");
        back.setIcon(EX);
        back.setBounds((int)(0.625*screenSize.width), (int)(0.625*screenSize.height), 200, 100);
        back.setEnabled(false);
        back.setFont(new Font(null, Font.BOLD, 22));
        back.addMouseListener(this);
        window.add(back);

        EE = new ImageIcon("img/menu/easy.png");
        EE.setImage(EE.getImage().getScaledInstance(100, 100,
                Image.SCALE_DEFAULT));
        easy = new JLabel("Easy");
        easy.setIcon(EE);
        easy.setBounds((int)(0.3472*screenSize.width), (int)(0.275*screenSize.height), 200, 100);
        easy.setFont(new Font(null, Font.BOLD, 22));
        easy.addMouseListener(this);
        window.add(easy);

        NE = new ImageIcon("img/menu/normal.png");
        NE.setImage(NE.getImage().getScaledInstance(100, 100,
                Image.SCALE_DEFAULT));
        normal = new JLabel("Normal");
        normal.setIcon(NE);
        normal.setBounds((int)(0.3472*screenSize.width),  (int)(0.400*screenSize.height), 200, 100);
        normal.setFont(new Font(null, Font.BOLD, 22));
        normal.addMouseListener(this);
        window.add(normal);

        HE = new ImageIcon("img/menu/hard.png");
        HE.setImage(HE.getImage().getScaledInstance(100, 100,
                Image.SCALE_DEFAULT));
        hard = new JLabel("Hard");
        hard.setIcon(HE);
        hard.setBounds((int)(0.3472*screenSize.width), (int)(0.525*screenSize.height), 200, 100);
        hard.setFont(new Font(null, Font.BOLD, 22));
        hard.addMouseListener(this);
        window.add(hard);

        BK = new ImageIcon("img/menu/backgroundLabel.png");
        BK.setImage(BK.getImage().getScaledInstance((int)(0.48611*screenSize.width),(int)(0.9375*screenSize.height),Image.SCALE_DEFAULT));
        backgroundLabel = new JLabel(BK);
        backgroundLabel.setBounds((int)(0.2778*screenSize.width),0,(int)(0.48611*screenSize.width),(int)(0.9375*screenSize.height));
        window.add(backgroundLabel);
        //if current difficulty is not easy/normal/hard. easy/normal/hard is grey.
        // the button with original color shows current difficulty
        if(window.getDifficulty()==1){
            normal.setEnabled(false);
            hard.setEnabled(false);
        }
        else if(window.getDifficulty()==2){
            easy.setEnabled(false);
            hard.setEnabled(false);
        }
        else{
            easy.setEnabled(false);
            normal.setEnabled(false);
        }
        window.repaint();

    }





    @Override
    /**
     *  choose difficulty
     */
    public void mouseClicked(MouseEvent e) {
        if (e.getSource().equals(back)) {
            //back to main menu
            new Music().play("sound/click.wav", false);
            window.remove(back);
            window.remove(normal);
            window.remove(easy);
            window.remove(hard);
            window.remove(backgroundLabel);
            window.createComponents();
            window.changeS = 1;

        } else if (e.getSource().equals(easy)) {
            new Music().play("sound/click.wav", false);
            window.setDifficulty(1);
            easy.setEnabled(true);
            // other two buttons become grey
            normal.setEnabled(false);
            hard.setEnabled(false);
        } else if (e.getSource().equals(normal)) {
            new Music().play("sound/click.wav", false);
            window.setDifficulty(2);
            // other two buttons become grey
            easy.setEnabled(false);
            normal.setEnabled(true);
            hard.setEnabled(false);
        } else if (e.getSource().equals(hard)) {
            new Music().play("sound/click.wav", false);
            window.setDifficulty(3);
            // other two buttons become grey
            easy.setEnabled(false);
            normal.setEnabled(false);
            hard.setEnabled(true);
        }
        window.repaint();
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
     * similarly,when mouse enters button, button will become larger.
     */
    public void mouseEntered(MouseEvent e) {
        if (e.getSource().equals(back)) {
            back.setEnabled(true);
            EX.setImage(EX.getImage().getScaledInstance(60, 60,
                    Image.SCALE_DEFAULT));
            window.repaint();
        } else if (e.getSource().equals(easy)) {
            easy.setEnabled(true);
            EE.setImage(EE.getImage().getScaledInstance(120, 120,
                    Image.SCALE_DEFAULT));
            window.repaint();
        } else if (e.getSource().equals(normal)) {
            normal.setEnabled(true);
            NE.setImage(NE.getImage().getScaledInstance(120, 120,
                    Image.SCALE_DEFAULT));
            window.repaint();
        } else if (e.getSource().equals(hard)) {
            hard.setEnabled(true);
            HE.setImage(HE.getImage().getScaledInstance(120, 120,
                    Image.SCALE_DEFAULT));
            window.repaint();
        }
    }

    @Override
    /**
     * When mouse exits, button will back to normal
     */
    public void mouseExited(MouseEvent e) {
        if (e.getSource().equals(back)) {
            back.setEnabled(false);
        } else if (e.getSource().equals(easy)) {
            if(window.getDifficulty()!=1){
                easy.setEnabled(false);
            }
        } else if (e.getSource().equals(normal)) {
            if(window.getDifficulty()!=2){
                normal.setEnabled(false);
            }
        } else if (e.getSource().equals(hard)) {
            if(window.getDifficulty()!=3){
                hard.setEnabled(false);
            }
        }
    }
}