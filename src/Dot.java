/**
 * Dot is a subclass of Item.
 */
public class Dot extends Item{
    /**
     * This constructor creates an item to show the motion trail of arrow.
     * Sets the size of the dot.
     * @param panel paint on GameArena
     * @param x position of dot
     * @param y position of dot
     */
    Dot(GameArena panel,int x, int y){
        super(x,y,"img/icons/dot.png",panel);
        setSize(6);
    }
}
