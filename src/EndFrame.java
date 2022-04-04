
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Implement end page
 */
public class EndFrame {
    /**
     *  buttons implemented by JLabel
     */
    JLabel again, backToMenu, exit, endBackground;
    /**
     * ranking table used JTable
     */
    JTable table;
    private ArrayList<String> playerScore = new ArrayList<>();
    /**
     * create a series of components of end page
     * @param panel remove GameArena
     * @param window add units on MainFrame
     * @throws IOException if file reading failed
     */
    public EndFrame(GameArena panel, MainFrame window) throws IOException {
        window.bgm.close();
        window.bgm.play("bgm/Win.wav", true);
        try {
            BufferedWriter out;
            out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("log/history.txt", true)));
            String str = (window.male ? window.player1 : window.player2) + "," + panel.time + "," + panel.score + "\n";
            out.write(str);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //The following is three buttons implemented by JLabels
        again = new JLabel("Try Again");
        ImageIcon againImg = new ImageIcon("img/menu/again.png");
        againImg.setImage(againImg.getImage().getScaledInstance(50, 50,
                Image.SCALE_DEFAULT));
        again.setFont(new Font(null, Font.BOLD, 22));
        again.setEnabled(false);
        again.setIcon(againImg);
        again.setBounds((int)(0.45138*window.screenSize.width), (int)(0.57083*window.screenSize.height), 200, 60);
        again.addMouseListener(new MouseAdapter() {
            @Override
            /**
             * when again is clicked, players switch from player 1 to player 2
             */
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                new Music().play("sound/click.wav", false);
                panel.setEnd(false);
                window.tryAgainFlag = true;
                window.remove(panel);
                window.repaint();
                window.changeS = 2;
            }

            @Override
            /**
             * when mouse enters button, button will become larger. When mouse exits, button will back to normal
             */
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                again.setEnabled(true);
                ImageIcon againImg = new ImageIcon("img/menu/again.png");
                again.setFont(new Font(null, Font.BOLD, 24));
                againImg.setImage(againImg.getImage().getScaledInstance(60, 60,
                        Image.SCALE_DEFAULT));
                again.setIcon(againImg);
                panel.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                again.setEnabled(false);
                again.setFont(new Font(null, Font.BOLD, 22));
                againImg.setImage(againImg.getImage().getScaledInstance(50, 50,
                        Image.SCALE_DEFAULT));
                again.setIcon(againImg);
                panel.repaint();
            }
        });

        ImageIcon menuImg = new ImageIcon("img/menu/backToMenu.png");
        backToMenu = new JLabel("Back To Menu");
        backToMenu.setFont(new Font(null, Font.BOLD, 22));
        menuImg.setImage(menuImg.getImage().getScaledInstance(50, 50,
                Image.SCALE_DEFAULT));
        backToMenu.setIcon(menuImg);
        backToMenu.setEnabled(false);
        backToMenu.setBounds((int)(0.45138*window.screenSize.width), (int)(0.69083*window.screenSize.height), 250, 60);//add if you need that

        backToMenu.addMouseListener(new MouseAdapter() {
            @Override
            /**
             * when backToMenu is clicked,players will get back to menu
             */
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                window.bgm.close();
                window.bgm.play("bgm/Menu.wav", true);
                new Music().play("sound/click.wav", false);
                window.remove(panel);
                window.backToMenuFlag = true;
                panel.setEnd(false);
            }

            @Override
            /**
             * when mouse enters button, button will become larger.
             */
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                backToMenu.setEnabled(true);
                ImageIcon menuImg = new ImageIcon("img/menu/backToMenu.png");
                menuImg.setImage(menuImg.getImage().getScaledInstance(60, 60,
                        Image.SCALE_DEFAULT));
                backToMenu.setFont(new Font(null, Font.BOLD, 24));
                backToMenu.setIcon(menuImg);
                panel.repaint();
            }

            @Override
            /**
             * When mouse exits, button will back to normal
             */
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                backToMenu.setEnabled(false);
                menuImg.setImage(menuImg.getImage().getScaledInstance(50, 50,
                        Image.SCALE_DEFAULT));
                backToMenu.setFont(new Font(null, Font.BOLD, 22));
                backToMenu.setIcon(menuImg);
                panel.repaint();
            }
        });

        exit = new JLabel("exit");
        ImageIcon exitImg = new ImageIcon("img/menu/cancel.png");
        exitImg.setImage(exitImg.getImage().getScaledInstance(50, 50,
                Image.SCALE_DEFAULT));
        exit.setIcon(exitImg);
        exit.setFont(new Font(null, Font.BOLD, 22));
        exit.setEnabled(false);
        exit.setBounds((int)(0.45138*window.screenSize.width), (int)(0.81083*window.screenSize.height), 150, 60);
        exit.addMouseListener(new MouseAdapter() {
            @Override
            /**
             *  when exit is clicked, execution will stop
             */
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                new Music().play("sound/click.wav", false);
                System.exit(0);
            }

            @Override
            /**
             * when mouse enters button, button will become larger.
             */
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                exit.setEnabled(true);
                ImageIcon exitImg = new ImageIcon("img/menu/cancel.png");
                exitImg.setImage(exitImg.getImage().getScaledInstance(60, 60,
                        Image.SCALE_DEFAULT));
                exit.setFont(new Font(null, Font.BOLD, 24));
                exit.setIcon(exitImg);
                panel.repaint();
            }

            @Override
            /**
             * When mouse exits, button will back to normal
             */
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                exit.setEnabled(false);
                exitImg.setImage(exitImg.getImage().getScaledInstance(50, 50,
                        Image.SCALE_DEFAULT));
                exit.setFont(new Font(null, Font.BOLD, 22));
                exit.setIcon(exitImg);
                panel.repaint();
            }
        });

        Object[] columnNames = {"PLAYERS", "TIME", "SCORE"};

        //bis.read(buffer);

        InputStream in;
        InputStreamReader ir;
        BufferedReader br;
        //player's name and score would be stored in a text file named "history"
        in=new BufferedInputStream(new FileInputStream("log/history.txt"));
        ir=new InputStreamReader(in,"utf-8");
        br= new BufferedReader(ir);

        String line="";
        while((line=br.readLine())!=null){
            playerScore.add(line);
        }
        br.close();
        ir.close();
        in.close();
        Object[][] rowData = {
                {"Player", "Time", "Score"},
                {0,0,0},
                {0,0,0},
        };
        //if there is more than two player's information stored in "history",then read the top two player's information
        if(playerScore.size() > 2) {
            rowData[1][0] = playerScore.get(playerScore.size() - 2).split(",")[0];
            rowData[1][1] = playerScore.get(playerScore.size() - 2).split(",")[1];
            rowData[1][2] = playerScore.get(playerScore.size() - 2).split(",")[2];
            rowData[2][0] = playerScore.get(playerScore.size() - 1).split(",")[0];
            rowData[2][1] = playerScore.get(playerScore.size() - 1).split(",")[1];
            rowData[2][2] = playerScore.get(playerScore.size() - 1).split(",")[2];
        }
        if(playerScore.size() == 1) {
            rowData[1][0] = playerScore.get(playerScore.size() - 1).split(",")[0];
            rowData[1][1] = playerScore.get(playerScore.size() - 1).split(",")[1];
            rowData[1][2] = playerScore.get(playerScore.size() - 1).split(",")[2];
        }


        table = new JTable(rowData, columnNames);
        ((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer())
                .setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
        table.setForeground(Color.ORANGE);
        table.setBackground(new Color(0, 0, 0, 0));
        table.getColumnModel().getColumn(0).setPreferredWidth(80);//set each column's width
        table.setFont(new Font(null, Font.BOLD, 30));
        table.setShowGrid(false);
        table.setRowHeight(50);
        table.setEnabled(false);

        table.setBounds((int)(0.45138*window.screenSize.width), (int)(0.2*window.screenSize.height), 400, 200);

        ImageIcon endBackgroundImg = new ImageIcon("img/menu/backgroundLabel.png");
        endBackgroundImg.setImage(endBackgroundImg.getImage().getScaledInstance(600, (int) (1.5*window.screenSize.height),
                Image.SCALE_DEFAULT));
        endBackground = new JLabel(endBackgroundImg);
        endBackground.setBounds((int)(0.35138*window.screenSize.width), (int)(-0.25*window.screenSize.height), 600, (int) (1.5*window.screenSize.height));
        panel.add(endBackground, 0);
        panel.add(exit, 0);
        panel.add(backToMenu, 0);
        panel.add(again, 0);
        panel.add(table, 0);

    }

    /**
     * when players quit end page, its components need to be deleted
     * @param panel EndFrame units will be removed from GameArena
     */
    public void quitEnd(GameArena panel) {
        panel.remove(exit);
        panel.remove(backToMenu);
        panel.remove(endBackground);
        panel.remove(again);
        panel.remove(table);
        panel.repaint();
    }

}
