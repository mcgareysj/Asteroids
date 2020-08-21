package AsteroidsGame;

public class Point {
	
	public double x;
	public double y;
	
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * This constructor makes a point on the circle of the passed in radius.
	 * The method is designed to allow a random value to be passed in for angle,
	 * and an offsetting factor to be passed in with angleOffset, effectively giving
	 * the caller control over where on the circle the random point could possibly end up
	 * @param radius the radius of the circle
	 * @param angle the angle from the x axis, usually a random number
	 * @param angleOffset a fixed angle to start the range of the random point
	 */
	public Point(double radius, double angle, double angleOffset) {
		x = radius*Math.cos(angle + angleOffset);
		y = radius*Math.sin(angle + angleOffset);
	}
	
	public void updatePoint(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public void RotateAroundCenter(int degrees, Point origin) {
		double newX = this.x - origin.x;
		double newY = this.y - origin.y;
		double radians = Math.toRadians(degrees);
		
		double tempX = (newX*Math.cos(radians) - newY*Math.sin(radians)) + origin.x;
		newY = (newX*Math.sin(radians) + newY*Math.cos(radians)) + origin.y;
		newX = tempX;
		this.x = newX;
		this.y = newY;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Point other = (Point) obj;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		return true;
	}

	public double getDistance(Point other) {
		return (Math.sqrt(Math.pow(x-other.x, 2) + Math.pow(y-other.y, 2)));
	}
	
	public int myHash() {
		return ((int)((this.x / 100) + 5) + (int)((this.y / 100) + 5)*10);
	}
	
	public double distanceFromOrigin() {
		return (Math.sqrt(x*x + y*y));
	}
	
	public double dotProduct(Point other) {
		return(this.x*other.x + this.y*other.y);
	}
	
	public void makeIntoUnitVector() {
		double dist = distanceFromOrigin();
		this.x /= dist;
		this.y /= dist;
	}
}
