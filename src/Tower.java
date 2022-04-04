/**
 * Tower is a subclass of Item.
 */
public class Tower extends Item {
    /**
     * towerHP represents the health of tower.
     */
    private float towerHP = 1000;
    /**
     * This constructor creates an item to show tower.
     * Sets height and width of tower.
     * @param panel Display tower in the panel.
     */
    public Tower(GameArena panel) {
        super(100, panel.window.getHeight() - 350, "img/characters/tower.png", panel);
        setHeight(300);
        setWidth(200);
    }
    /**
     * This method sets tower's health.
     * @param towerHP Tower's health.
     */
    public void towerHP(float towerHP) {
        this.towerHP = towerHP;
    }
    /**
     * This method gets tower's health.
     * @return Return the health of tower.
     */
    public float getHp() {
        return towerHP;
    }
    /**
     * This method shows the attack of tower.
     * When tower is attacked, change tower's image and reduce health by 5.
     * @param panel Display the attack effect in the panel.
     */
    public void towerAttacked(GameArena panel) {
        this.setSource("img/characters/towerAttacked.png");
        towerHP -= 5;
        setY(panel.window.getHeight() - 200 - getHeight()/2);
    }
}