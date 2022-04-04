import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * create Pause page
 */
public class Pause {
    /**
     * JLabels to make buttons
     */
    JLabel upGrade, backGame, white, moreArrows, jumpMore, levelSetting, easy, backTo, normal, hard, morePower,backMenu;
    /**
     * screen size(x,y)
     */
    public Dimension screenSize;

    /**
     * create Pause page's components and achieve player's upgrade
     * @param panel  an instance of GameArena, pause page's components will be added to panel
     * @param player In pause page, player could get upgrade
     * @param window an instance of MainFrame. Pause page needs to get difficulty that stored in window
     */
    public Pause(GameArena panel, GamePlayer player, MainFrame window) {
        screenSize = window.screenSize;
        //The following are several buttons using JLabel
        ImageIcon EE = new ImageIcon("img/menu/easy.png");
        EE.setImage(EE.getImage().getScaledInstance(50, 50,
                Image.SCALE_DEFAULT));
        easy = new JLabel("Easy");
        easy.setIcon(EE);
        easy.setBounds((int) (0.45138 * screenSize.width), (int) (0.270 * screenSize.height), 200, 100);
        //if current difficulty is not easy, easy button is grey
        if (window.getDifficulty() != 1) {
            easy.setEnabled(false);
        }
        easy.setFont(new Font(null, Font.BOLD, 22));
        easy.addMouseListener(new MouseAdapter() {
            @Override
            //when easy is clicked, difficulty will set to easy
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                new Music().play("sound/click.wav", false);
                window.setDifficulty(1);
                normal.setEnabled(false);
                hard.setEnabled(false);
            }

            @Override
            /**
             * similarly,when mouse enters button, button will become larger. When mouse exits, button will back to normal
             */
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                easy.setEnabled(true);
                EE.setImage(EE.getImage().getScaledInstance(60, 60,
                        Image.SCALE_DEFAULT));
                easy.setIcon(EE);
                easy.setFont(new Font(null, Font.BOLD, 24));
                panel.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                if (window.getDifficulty() != 1) {
                    easy.setEnabled(false);
                }
            }
        });

        ImageIcon NE = new ImageIcon("img/menu/normal.png");
        NE.setImage(NE.getImage().getScaledInstance(50, 50,
                Image.SCALE_DEFAULT));
        normal = new JLabel("Normal");
        normal.setIcon(NE);
        normal.setBounds((int) (0.45138 * screenSize.width), (int) (0.3525 * screenSize.height), 200, 100);
        if (window.getDifficulty() != 2) {
            normal.setEnabled(false);
        }
        normal.setFont(new Font(null, Font.BOLD, 22));
        normal.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                new Music().play("sound/click.wav", false);
                window.setDifficulty(2);// set difficulty to normal
                easy.setEnabled(false);// other two buttons will become grey
                hard.setEnabled(false);
            }

            @Override
            //similarly,when mouse enters button, button will become larger. When mouse exits, button will back to normal
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                normal.setEnabled(true);
                NE.setImage(NE.getImage().getScaledInstance(60, 60,
                        Image.SCALE_DEFAULT));
                normal.setIcon(NE);
                normal.setFont(new Font(null, Font.BOLD, 24));
                panel.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                if (window.getDifficulty() != 2) {
                    normal.setEnabled(false);
                }
            }
        });


        ImageIcon HE = new ImageIcon("img/menu/hard.png");
        HE.setImage(HE.getImage().getScaledInstance(50, 50,
                Image.SCALE_DEFAULT));
        hard = new JLabel("Hard");
        hard.setIcon(HE);
        hard.setBounds((int) (0.45138 * screenSize.width), (int) (0.44667 * screenSize.height), 200, 100);
        if (window.getDifficulty() != 3) {
            hard.setEnabled(false);
        }
        hard.setFont(new Font(null, Font.BOLD, 22));
        hard.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                new Music().play("sound/click.wav", false);
                window.setDifficulty(3);
                easy.setEnabled(false);
                normal.setEnabled(false);
            }

            @Override
            //similarly,when mouse enters button, button will become larger. When mouse exits, button will back to normal
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                hard.setEnabled(true);
                HE.setImage(HE.getImage().getScaledInstance(60, 60,
                        Image.SCALE_DEFAULT));
                hard.setIcon(HE);
                hard.setFont(new Font(null, Font.BOLD, 24));
                panel.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                if (window.getDifficulty() != 3) {
                    hard.setEnabled(false);
                }
            }
        });

        ImageIcon backToImg = new ImageIcon("img/menu/goBack.png");
        backToImg.setImage(backToImg.getImage().getScaledInstance(50, 50,
                Image.SCALE_DEFAULT));
        backTo = new JLabel("Go Back");
        backTo.setFont(new Font(null, Font.BOLD, 22));
        backTo.setIcon(backToImg);
        backTo.setEnabled(false);
        backTo.setBounds((int) (0.45138 * screenSize.width), (int) (0.55083 * screenSize.height), backToImg.getIconWidth() + 120, backToImg.getIconHeight() + 50);
        backTo.addMouseListener(new MouseAdapter() {
            @Override
            //when backTo is clicked, players will back to initial pause page from level setting page
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                new Music().play("sound/click.wav", false);
                panel.remove(easy);
                panel.remove(normal);
                panel.remove(hard);
                panel.remove(backTo);
                panel.remove(moreArrows);
                panel.remove(jumpMore);
                panel.remove(morePower);
                panel.remove(backMenu);
                panel.add(upGrade, 0);
                panel.add(backGame, 0);
                panel.add(levelSetting, 0);
                panel.add(backMenu, 0);

            }

            @Override
            //similarly,when mouse enters button, button will become larger. When mouse exits, button will back to normal
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                backTo.setEnabled(true);
                backToImg.setImage(backToImg.getImage().getScaledInstance(60, 60,
                        Image.SCALE_DEFAULT));
                backTo.setIcon(backToImg);
                backTo.setFont(new Font(null, Font.BOLD, 24));
                panel.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                backTo.setEnabled(false);
                backToImg.setImage(backToImg.getImage().getScaledInstance(50, 50,
                        Image.SCALE_DEFAULT));
                backTo.setIcon(backToImg);
                backTo.setFont(new Font(null, Font.BOLD, 22));
            }
        });

        ImageIcon levelImg = new ImageIcon("img/menu/levelPick.png");
        levelImg.setImage(levelImg.getImage().getScaledInstance(50, 50,
                Image.SCALE_DEFAULT));
        levelSetting = new JLabel("Level Settings");
        levelSetting.setFont(new Font(null, Font.BOLD, 22));
        levelSetting.setIcon(levelImg);
        levelSetting.setBounds((int) (0.45138 * screenSize.width), (int) (0.4167 * screenSize.height), levelImg.getIconWidth() + 200, levelImg.getIconHeight() + 10);

        // players could also change difficulty in the pause page
        levelSetting.addMouseListener(new MouseAdapter() {
            @Override
            //when levelSetting is clicked, players will enter level-setting page
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                new Music().play("sound/click.wav", false);
                panel.remove(levelSetting);
                panel.remove(backGame);
                panel.remove(upGrade);
                panel.remove(backMenu);
                panel.add(easy, 0);
                panel.add(normal, 0);
                panel.add(hard, 0);
                panel.add(backTo, 0);
                panel.repaint();
            }

            @Override
            //similarly,when mouse enters button, button will become larger. When mouse exits, button will back to normal
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                levelSetting.setEnabled(true);
                levelImg.setImage(levelImg.getImage().getScaledInstance(60, 60,
                        Image.SCALE_DEFAULT));
                levelSetting.setIcon(levelImg);
                levelSetting.setFont(new Font(null, Font.BOLD, 24));
                panel.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                levelImg.setImage(levelImg.getImage().getScaledInstance(50, 50,
                        Image.SCALE_DEFAULT));
                levelSetting.setIcon(levelImg);
                levelSetting.setFont(new Font(null, Font.BOLD, 22));
                panel.repaint();
            }
        });

        ImageIcon upImg = new ImageIcon("img/menu/upgrade.png");
        upImg.setImage(upImg.getImage().getScaledInstance(50, 50,
                Image.SCALE_DEFAULT));
        upGrade = new JLabel("Upgrade!");
        upGrade.setFont(new Font(null, Font.BOLD, 22));
        upGrade.setIcon(upImg);
        upGrade.setBounds((int) (0.45138 * screenSize.width), (int) (0.3083 * screenSize.height), upImg.getIconWidth() + 120, upImg.getIconHeight() + 10);


        upGrade.addMouseListener(new MouseAdapter() {
            @Override
            //when upGrade is clicked, players will enter upgrade page
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                new Music().play("sound/click.wav", false);
                panel.remove(upGrade);
                panel.remove(levelSetting);
                panel.remove(backGame);
                panel.remove(backMenu);
                panel.add(backTo, 0);
                panel.add(moreArrows, 0);
                panel.add(jumpMore, 0);
                panel.add(morePower, 0);
                panel.repaint();


            }

            @Override
            //similarly,when mouse enters button, button will become larger. When mouse exits, button will back to normal
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                upImg.setImage(upImg.getImage().getScaledInstance(60, 60,
                        Image.SCALE_DEFAULT));
                upGrade.setIcon(upImg);
                upGrade.setFont(new Font(null, Font.BOLD, 24));

                panel.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);

                upImg.setImage(upImg.getImage().getScaledInstance(50, 50,
                        Image.SCALE_DEFAULT));
                upGrade.setIcon(upImg);
                upGrade.setFont(new Font(null, Font.BOLD, 22));
                panel.repaint();
            }
        });

        ImageIcon menuImg = new ImageIcon("img/menu/backToMenu.png");
        backMenu = new JLabel("Back To Menu");
        backMenu.setFont(new Font(null, Font.BOLD, 22));
        menuImg.setImage(menuImg.getImage().getScaledInstance(50, 50,
                Image.SCALE_DEFAULT));
        backMenu.setIcon(menuImg);

        backMenu.setBounds((int) (0.45138 * window.screenSize.width), (int) (0.6083 * window.screenSize.height), 250, upImg.getIconHeight() + 10);//add if you need that
        //((int) (0.45138 * screenSize.width), (int) (0.52083 * screenSize.height), upImg.getIconWidth() + 200, upImg.getIconHeight() + 10);
        backMenu.addMouseListener(new MouseAdapter() {
            @Override
            // when backToMenu is clicked,players will get back to menu
            //when mouse enters button, button will become larger. When mouse exits, button will back to normal
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                new Music().play("sound/click.wav", false);
                window.remove(panel);
                window.backToMenuFlag = true;
                panel.setPause(false);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                ImageIcon menuImg = new ImageIcon("img/menu/backToMenu.png");
                menuImg.setImage(menuImg.getImage().getScaledInstance(60, 60,
                        Image.SCALE_DEFAULT));
                backMenu.setFont(new Font(null, Font.BOLD, 24));
                backMenu.setIcon(menuImg);
                panel.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                menuImg.setImage(menuImg.getImage().getScaledInstance(50, 50,
                        Image.SCALE_DEFAULT));
                backMenu.setFont(new Font(null, Font.BOLD, 22));
                backMenu.setIcon(menuImg);
                panel.repaint();
            }
        });

        ImageIcon backImg = new ImageIcon("img/menu/back.png");
        backImg.setImage(backImg.getImage().getScaledInstance(50, 50,
                Image.SCALE_DEFAULT));
        backGame = new JLabel("Back to Game");
        backGame.setFont(new Font(null, Font.BOLD, 22));
        backGame.setIcon(backImg);
        backGame.setBounds((int) (0.45138 * screenSize.width), (int) (0.52083 * screenSize.height), upImg.getIconWidth() + 200, upImg.getIconHeight() + 10);

        backGame.addMouseListener(new MouseAdapter() {
            @Override
            //when back is clicked, players could get back to game
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                new Music().play("sound/click.wav", false);
                panel.setPause(false);
            }

            @Override
            //similarly,when mouse enters button, button will become larger. When mouse exits, button will back to normal
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                backImg.setImage(backImg.getImage().getScaledInstance(60, 60,
                        Image.SCALE_DEFAULT));
                backGame.setIcon(backImg);
                backGame.setFont(new Font(null, Font.BOLD, 24));
                panel.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                backImg.setImage(backImg.getImage().getScaledInstance(50, 50,
                        Image.SCALE_DEFAULT));
                backGame.setIcon(backImg);
                backGame.setFont(new Font(null, Font.BOLD, 22));
                panel.repaint();
            }
        });
        // some upgrade buttons
        ImageIcon arrowsImg = new ImageIcon("img/menu/moreArrows.png");
        arrowsImg.setImage(arrowsImg.getImage().getScaledInstance(50, 50,
                Image.SCALE_DEFAULT));
        moreArrows = new JLabel("More Arrows!");
        moreArrows.setFont(new Font(null, Font.BOLD, 22));
        moreArrows.setIcon(arrowsImg);
        moreArrows.setBounds((int) (0.45138 * screenSize.width), (int) (0.305 * screenSize.height), arrowsImg.getIconWidth() + 200, arrowsImg.getIconHeight() + 10);
        moreArrows.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                //if score>=10 and players have not upgraded this, players could shoot more arrows by pressing "A"
                if (panel.score >= 10 & panel.quinticShootCold == 0) {
                    new Music().play("sound/upGrade.wav", false);
                    panel.quinticShootCold = 100;
                    moreArrows.setEnabled(true);
                    panel.score -= 10;
                } else if (panel.score < 10) {
                    // if not enough score
                    JOptionPane.showMessageDialog(null, "not Enough score", "OOPS!", JOptionPane.ERROR_MESSAGE);
                }


            }

            @Override
            //similarly,when mouse enters button, button will become larger. When mouse exits, button will back to normal
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                arrowsImg.setImage(arrowsImg.getImage().getScaledInstance(60, 60,
                        Image.SCALE_DEFAULT));
                moreArrows.setIcon(arrowsImg);
                moreArrows.setFont(new Font(null, Font.BOLD, 24));
                moreArrows.setEnabled(true);
                panel.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                arrowsImg.setImage(arrowsImg.getImage().getScaledInstance(50, 50,
                        Image.SCALE_DEFAULT));
                moreArrows.setIcon(arrowsImg);
                moreArrows.setFont(new Font(null, Font.BOLD, 22));
                if (panel.quinticShootCold == 0) {
                    moreArrows.setEnabled(false);
                }
                panel.repaint();
            }
        });

        ImageIcon jumpImg = new ImageIcon("img/menu/jump.png");
        jumpImg.setImage(jumpImg.getImage().getScaledInstance(50, 50,
                Image.SCALE_DEFAULT));
        jumpMore = new JLabel("Jump Higher!");
        jumpMore.setFont(new Font(null, Font.BOLD, 22));
        jumpMore.setIcon(jumpImg);
        jumpMore.setBounds((int) (0.45138 * screenSize.width), (int) (0.385 * screenSize.height), jumpImg.getIconWidth() + 200, jumpImg.getIconHeight() + 20);
        jumpMore.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                //if score>=10 and players have not upgraded this, players could jump higher
                if (panel.score >= 10 & !panel.jumpHigher) {
                    new Music().play("sound/upGrade.wav", false);
                    panel.jumpHigher = true;
                    panel.score -= 10;
                    jumpMore.setEnabled(true);
                } else if (panel.score < 10) {
                    JOptionPane.showMessageDialog(null, "not Enough score", "OOPS!", JOptionPane.ERROR_MESSAGE);
                }

            }

            @Override
            //similarly,when mouse enters button, button will become larger. When mouse exits, button will back to normal
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                jumpImg.setImage(jumpImg.getImage().getScaledInstance(60, 60,
                        Image.SCALE_DEFAULT));
                jumpMore.setIcon(jumpImg);
                jumpMore.setFont(new Font(null, Font.BOLD, 24));
                jumpMore.setEnabled(true);
                panel.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                jumpImg.setImage(jumpImg.getImage().getScaledInstance(50, 50,
                        Image.SCALE_DEFAULT));
                jumpMore.setIcon(jumpImg);
                jumpMore.setFont(new Font(null, Font.BOLD, 22));
                if (!panel.jumpHigher) {
                    jumpMore.setEnabled(false);
                }
                panel.repaint();
            }
        });

        ImageIcon rampageImg = new ImageIcon("img/menu/rampage.png");
        rampageImg.setImage(rampageImg.getImage().getScaledInstance(50, 50,
                Image.SCALE_DEFAULT));
        morePower = new JLabel("Rampage Mode!");
        morePower.setFont(new Font(null, Font.BOLD, 22));
        morePower.setIcon(rampageImg);
        morePower.setBounds((int) (0.45138 * screenSize.width), (int) (0.465 * screenSize.height), jumpImg.getIconWidth() + 200, jumpImg.getIconHeight() + 10);
        morePower.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                //if score>=10 and players have not upgraded this, players could become rampage(higher acceleration and damage)
                if (panel.score >= 10) {
                    panel.upGradeTime = panel.time;
                    panel.score -= 10;
                    new Music().play("sound/upGrade.wav", false);
                    player.setStrong(3);
                    player.setAcceleration(0.5);
                    morePower.setEnabled(true);
                } else {
                    JOptionPane.showMessageDialog(null, "not Enough score", "OOPS!", JOptionPane.ERROR_MESSAGE);
                }

            }

            @Override
            //similarly,when mouse enters button, button will become larger. When mouse exits, button will back to normal
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                rampageImg.setImage(rampageImg.getImage().getScaledInstance(60, 60,
                        Image.SCALE_DEFAULT));
                morePower.setIcon(rampageImg);
                morePower.setFont(new Font(null, Font.BOLD, 24));
                morePower.setEnabled(true);
                panel.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                rampageImg.setImage(rampageImg.getImage().getScaledInstance(50, 50,
                        Image.SCALE_DEFAULT));
                morePower.setIcon(rampageImg);
                morePower.setFont(new Font(null, Font.BOLD, 22));
                if (!panel.rampageMode) {
                    morePower.setEnabled(false);
                }
                panel.repaint();
            }
        });


        ImageIcon whiteGround = new ImageIcon("img/menu/backgroundLabel.png");
        whiteGround.setImage(whiteGround.getImage().getScaledInstance((int) (0.2778 * screenSize.width), (int) (0.745 * screenSize.height),
                Image.SCALE_DEFAULT));
        white = new JLabel(whiteGround);
        white.setBounds((int) (0.38194 * screenSize.width), (int) (0.09416 * screenSize.height), whiteGround.getIconWidth(), whiteGround.getIconHeight());
        //if players have not upgraded these skills, their buttons are grey
        if (!panel.jumpHigher) {
            jumpMore.setEnabled(false);
        }
        if (panel.quinticShootCold == 0) {
            moreArrows.setEnabled(false);
        }
        if (!panel.rampageMode) {
            morePower.setEnabled(false);
        }

        panel.add(white, 0);
        panel.add(upGrade, 0);
        panel.add(levelSetting, 0);
        panel.add(backGame, 0);
        panel.add(backMenu, 0);


        panel.repaint();

    }
    /**
     * when pause page is closed, pause page's components will be removed
     * @param panel is game's panel
     */
    public void quit(GameArena panel) {
        panel.remove(backGame);
        panel.remove(white);
        panel.remove(upGrade);
        panel.remove(moreArrows);
        panel.remove(jumpMore);
        panel.remove(levelSetting);
        panel.remove(morePower);
        panel.remove(backTo);
        panel.remove(easy);
        panel.remove(normal);
        panel.remove(hard);
        panel.remove(backMenu);
        panel.repaint();
    }
}
