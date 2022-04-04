/**
 * Shot by player, from the bow.
 */
public class Arrow extends Item {
	int startTime;
	int startX;
	int startY;
	int lastX;
	int lastY;
	int panelHeight;
	private double speedX;
	private double speedY;

	Arrow(GamePlayer player, GameArena panel, int startTime) {
		super(player.getX(), player.getY(), "img/icons/Arrow.png", panel);
		this.setSize(10);
		this.startTime = startTime;

		panelHeight = panel.getHeight();
		startX = player.getX();
		startY = player.getY();
		speedX = 10 * player.getHoldStrength() * Math.cos(player.getHoldAngle());
		speedY = -10 * player.getHoldStrength() * Math.sin(player.getHoldAngle());
	}

	Arrow(GamePlayer player, GameArena panel, int startTime,double speedX,double speedY) {
		super(player.getX(), player.getY(), "img/icons/Arrow.png", panel);
		this.setSize(10);
		this.startTime = startTime;

		if(player.getWidth()<0){
			speedX = -speedX;
		}
		panelHeight = panel.getHeight();
		startX = player.getX();
		startY = player.getY();
		this.speedX = speedX;
		this.speedY = speedY;
	}

	public void update(int time) {
		lastX = (int) (startX + speedX * (time-1 - startTime));
		double gravity = -0.5;
		lastY = panelHeight - (int) (gravity * (time-1 - startTime) * (time-1 - startTime) / 2 - speedY * (time-1 - startTime)) + startY
				- panelHeight;
		setX((int) (startX + speedX * (time - startTime)));
		setY(panelHeight - (int) (gravity * (time - startTime) * (time - startTime) / 2 - speedY * (time - startTime)) + startY
				- panelHeight);
	}

	public float getSpeed() {
		int distance = (lastX - super.getX()) * (lastX - super.getX()) + (lastY - super.getY()) * (lastY - super.getY());
		if(distance > 1000)
		{
			distance = 1000;
		}
		return distance/10;
	}

	public int getLastX() {
		return lastX;
	}

	public int getLastY() {
		return lastY;
	}
}
