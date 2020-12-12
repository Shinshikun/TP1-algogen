public class Cellule {

	private int x;
	private int y;
	
	private int typeOccupation;
	
	final static int LIBRE = 0;
	final static int REINE = 1;
	final static int MENACEE = 2;
	
	public Cellule(int x, int y) {
		this.setX(x);
		this.setY(y);
		this.setTypeOccupation(LIBRE);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getTypeOccupation() {
		return typeOccupation;
	}

	public void setTypeOccupation(int typeOccupation) {
		this.typeOccupation = typeOccupation;
	}
	
}
