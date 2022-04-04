import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * A series of units show how to play.
 */
public class Help implements MouseListener {
    /**
     * buttons implemented by JLabels
     */
    JLabel back, description, helpWindow;
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
    ImageIcon EX, DES, WIN;

    /**
     * create help page's components
     * @param MFrame is the frame of MainFrame
     */
    public Help(MainFrame MFrame) {
        window = MFrame;
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();//get whole screen's size
        //The following is to set several buttons
        EX = new ImageIcon("img/menu/cancel.png");
        EX.setImage(EX.getImage().getScaledInstance(50, 50,
                Image.SCALE_DEFAULT));
        back = new JLabel("Back");
        back.setIcon(EX);
        back.setBounds((int) (0.65972 * screenSize.width), (int) (0.8510416 * screenSize.height), 200, 100);
        back.setEnabled(false);
        back.setFont(new Font(null, Font.BOLD, 22));
        back.addMouseListener(this);
        window.add(back);

        DES = new ImageIcon("img/menu/helpDescription.png");
        DES.setImage(DES.getImage().getScaledInstance((int) (0.3472 * screenSize.width), (int) (0.630833 * screenSize.height), Image.SCALE_DEFAULT));
        description = new JLabel();
        description.setIcon(DES);
        description.setBounds((int) (0.3472 * screenSize.width), (int) (0.334375 * screenSize.height), (int) (0.3472 * screenSize.width), (int) (0.630833 * screenSize.height));
        window.add(description);

        WIN = new ImageIcon("img/menu/backgroundLabel.png");
        WIN.setImage(WIN.getImage().getScaledInstance((int) (0.48611 * screenSize.width), (int) (1.15 * screenSize.height),
                Image.SCALE_DEFAULT));
        helpWindow = new JLabel();
        helpWindow.setIcon(WIN);
        helpWindow.setBounds((int) (0.2778 * screenSize.width), 20, (int) (0.48611 * screenSize.width), (int) (1.15 * screenSize.height));
        window.add(helpWindow);

        window.repaint();
    }

    @Override
    /**
     *  when back is clicked, players will be back to main menu
     */
    public void mouseClicked(MouseEvent e) {
        if (e.getSource().equals(back)) {
            new Music().play("sound/click.wav", false);
            window.changeS = 1;
            window.remove(back);
            window.remove(description);
            window.remove(helpWindow);
            window.createComponents();
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
     * //similarly,when mouse enters button, button will become larger. When mouse exits, button will back to normal
     */
    public void mouseEntered(MouseEvent e) {
        if (e.getSource().equals(back)) {
            back.setEnabled(true);
            EX.setImage(EX.getImage().getScaledInstance(60, 60,
                    Image.SCALE_DEFAULT));
            window.repaint();
        }
    }

    @Override
    /**
     * when mouse exits "back", "back" will back to grey again
     */
    public void mouseExited(MouseEvent e) {
        if (e.getSource().equals(back)) {
            back.setEnabled(false);
        }
    }
}