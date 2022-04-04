import java.io.IOException;
/**
 * The main entrance of the program and the controller of the switching window
 * Its address is not known to other classes
 */
public class CMain {

    public static void main(String[] args) {
        try {
            Thread.sleep(5);
        } catch (InterruptedException ignore) {
        }
        MainFrame window = new MainFrame();//Create a subclass of JFrame. This JFrame will keep in all the software
        while (true) {//This loop will always running to wait for the massage of changing window
            while (window.changeS == 0) { //Anti deadlock buffer
                try {
                    Thread.sleep(0);
                } catch (InterruptedException ignore) {
                }

            }

            switch (window.changeS) {//change pages according to changeS
                case 1://Change back to mainframe
                    window.backToMenuFlag = false;
                    window.dynamicBackgroundFlag = true;//back to main menu, dynamic background will work again
                    window.createComponents();// add main menu's components
                    while (window.dynamicBackgroundFlag) {
                        window.repaint();
                    }
                    try {
                        Thread.sleep(0);
                    } catch (InterruptedException ignore) {
                    }
                    window.changeS = 0;
                    break;
                case 2:
                    //try again or start game
                    window.tryAgainFlag = false;
                    turnGame(window);
                    if (window.backToMenuFlag) {
                        window.changeS = 1;
                    } else if (window.tryAgainFlag) {
                        window.changeS = 2;
                    } else {
                        window.changeS = 0;
                    }

                    break;
                case 3:
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    new Help(window);//help page appears
                    window.dynamicBackgroundFlag = true;
                    window.removePanel();
                    window.createPanel();

                    while (window.dynamicBackgroundFlag) {
                        window.repaint();// keep dynamic background
                    }

                    window.changeS = 0;
                    break;
                case 4:
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    window.dynamicBackgroundFlag = true;
                    new Level(window);//level page appears
                    window.removePanel();
                    window.createPanel();//Reset the panel
                    while (window.dynamicBackgroundFlag) {
                        window.repaint();// keep dynamic background
                    }
                    window.changeS = 0;
                    break;
                default:
            }
        }
    }
    /**
     * @param window frame
     * Build GameArena on the incoming MainFrame(JFrame).
     */
    private static void turnGame(MainFrame window) {
        window.male = !window.male;//Change players when entering the game again
        GameArena panel = new GameArena(window);
        Score score = new Score();
        panel.add(score);
        while (true) {
            if (panel.pause()) {
                //when game has a pause, pause page appears
                Pause a = new Pause(panel, panel.player, window);
                while (panel.pause()) {
                    score.show(panel);
                    try {
                        Thread.sleep(0);
                    } catch (InterruptedException ignore) {
                    }
                }
                a.quit(panel);
                if (window.backToMenuFlag) {
                    break;
                }
            }
            if (panel.end()) {
                EndFrame b = null;
                try {
                    //when game comes to end, end page appears
                    b = new EndFrame(panel, window);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                while (panel.end()) {
                    try {
                        Thread.sleep(0);
                    } catch (InterruptedException ignore) {
                    }
                }
                b.quitEnd(panel);
                break;
            }
            //record players' scores
            score.show(panel);
            //when game's time exceeds 5000, game ends
            if (panel.time == 5000 & window.getDifficulty()!=3) {
                panel.end = true;
                score.show(panel,(int)panel.tower.getHp());
                panel.score += panel.tower.getHp() * window.getDifficulty();
            }
            if (panel.kingAlien!=null ){
                if(panel.kingAlien.getHp()<-50) {
                    panel.end = true;
                    score.show(panel, (int) panel.tower.getHp());
                    panel.score += panel.tower.getHp() * window.getDifficulty();
                }
            }
        }
    }
}
