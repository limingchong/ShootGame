/**
 * All items extends from this.
 */
public class Item {
    /**
     * The abscissa of the object's current position.
     */
    private int x;

    /**
     * The ordinate of the object's current position.
     */
    private int y;

    /**
     * The current picture path of the object.
     */
    private String source;

    /**
     * The width of the object's current picture.
     */
    private int width;

    /**
     * The height of the object's current picture.
     */
    private int height;

    /**
     * The velocity of the object in the X direction.
     */
    private double speedX;

    /**
     * The velocity of the object in the Y direction.
     */
    private double speedY;

    /**
     * The force received by an object.
     */
    private double forceR = 1.5;

    /**
     * Constructor, can define the initial location of the instance, image path, and
     * display on the screen.
     * @param x Item initial position
     * @param y Item initial position
     * @param source The picture address of item.
     * @param panel The picture showed on the panel.
     */
    public Item(int x, int y, String source, GameArena panel) {
        this.x = x;
        this.y = y;
        this.source = source;
        this.width = 64;
        this.height = 64;
        panel.addItem(this);
    }

    /**
     * Change the position of the object.
     * @param y only change y.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Change the position of the object.
     * @param x only change x.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     *
     * @return Get the position of the object.
     */
    public int getY() {
        return y;
    }

    /**
     *
     * @return Get the position of the object.
     */
    public int getX() {
        return x;
    }

    /**
     *
     * @return Get the image path of the object.
     */
    public String getSource() {
        return source;
    }

    /**
     *
     * @param length Change the image height of the object.
     */
    public void setHeight(int length) {
        this.height = length;
    }

    /**
     *
     * @return Get the image height of the object.
     */
    public int getHeight() {
        return height;
    }

    /**
     *
     * @param width Change the image width of the object.
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     *
     * @return Gets the image width of the object.
     */
    public int getWidth() {
        return width;
    }

    /**
     *
     * @param speedX Change the speed of the object.
     */
    public void setSpeedX(double speedX) {
        this.speedX = speedX;
    }

    /**
     *
     * @param speedY Change the speed of the object.
     */
    public void setSpeedY(double speedY) {
        this.speedY = speedY;
    }

    /**
     *
     * @return Gets the speed of the object.
     */
    public double getSpeedX() {
        return speedX;
    }

    /**
     *
     * @return Gets the speed of the object.
     */
    public double getSpeedY() {
        return speedY;
    }

    /**
     *
     * @return Gets the resistance of the object.
     */
    public double getForceR() {
        return forceR;
    }

    /**
     *
     * @param source Change the picture path of the object.
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     *
     * @param size Change the image size of the object.
     */
    public void setSize(int size) {
        this.width = size;
        this.height = size;
    }

}