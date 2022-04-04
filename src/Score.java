import javax.swing.*;
import java.awt.*;

/**
 * a label to show score
 */
public class Score extends JLabel {
    public void show(GameArena panel){
        if(panel.window.getDifficulty()!=3) {
            setText("time: " + panel.time + " / 5000 \n score: " + panel.score);
        }else{
            setText("time: " + panel.time + " / INF \n score: " + panel.score);
        }
        setForeground(Color.BLUE);
        setHorizontalAlignment(0);
        setBounds(50,20,200,50);
        setBorder(BorderFactory.createLoweredBevelBorder());
        setBackground(new Color(0,50,125,120));
        setVisible(true);
    }
    public void show(GameArena panel,int score){
        for(int i = 0;i<=score*panel.window.getDifficulty()/10;i++){
            if (panel.window.getDifficulty()!=3) {
                setText("time: " + panel.time + " / 5000 \n score: " + (panel.score + i * 10));
            }else{
                setText("time: " + panel.time + " / INF \n score: " + (panel.score + i * 10));
            }
            panel.waiter(5);
            if(i%30==0) {
                new Music().play("sound/upGrade.wav", false);
            }
        }
        if (panel.window.getDifficulty()!=3) {
            setText("time: " + panel.time + " / 5000 \n score: " + (panel.score + score * panel.window.getDifficulty()));
        }else {
            setText("time: " + panel.time + " / INF \n score: " + (panel.score + score * panel.window.getDifficulty()));
        }
    }
}
