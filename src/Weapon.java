/**
 * Weapon of the player
 */
public class Weapon extends Item {
    /**
     * This constructor creates an item to show weapon.
     * @param panel Display weapon in the panel.
     */
    public Weapon(GameArena panel) {
        super(0, 0, "img/weapons/weapon00.png", panel);
        this.setSize(100);
    }

    /**
     * This method changes the appearance of the weapon.
     * As the strength and angle change, so does the appearance of the weapon.
     * @param strength the greater the strength, the longer the string.
     * @param angle the bow rotates with the angle
     * @see ImageRotate
     */
    public void setWeapon(double strength, double angle){
        String a = String.valueOf((int)strength);
        String b = String.valueOf((int)(angle/(3.1415926/9)));
        this.setSource("img/weapons/weapon"+a+b+".png");
    }
}