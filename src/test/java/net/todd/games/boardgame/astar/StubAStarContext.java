package net.todd.games.boardgame.astar;

// TODO javadoc
public class StubAStarContext implements IAStarContext {
	private final boolean allowDiagonal;
	private final Point highEstimatePoint;
	private final static int[][] traversableSquares = new int[][] {
		{ 0,0,0,0,0,0,0,1,0,0 },
		{ 0,0,0,0,0,1,0,1,0,0 },
		{ 0,0,0,0,0,1,0,0,0,0 },
		{ 0,0,0,0,0,1,0,0,0,0 },
		{ 0,0,0,0,0,1,0,0,0,0 },
		{ 0,1,1,1,1,0,0,0,0,0 },
		{ 0,0,0,0,0,0,0,0,0,0 },
		{ 1,1,0,0,0,0,0,0,0,0 },
		{ 0,0,0,0,0,0,0,0,0,1 },
		{ 0,0,0,0,0,0,0,0,1,0 }
	};
	
	public StubAStarContext() {
		this(false);
	}

	public StubAStarContext(boolean allowDiagonal) {
		this.allowDiagonal = allowDiagonal;
		this.highEstimatePoint = new Point(-1, -1);
	}
	
	public StubAStarContext(Point highEstimatePoint) {
		this.allowDiagonal = false;
		this.highEstimatePoint = highEstimatePoint;
	}

	public boolean allowDiagonalMovements() {
		return allowDiagonal;
	}

	public double g_factor(Point point) {
		return 1;
	}

	public double h(Point from, Point to) {
		double estimate = Math.sqrt(Math.pow(Math.abs(from.getX() - to.getX()), 2) + Math.pow(Math.abs(from.getY() - to.getY()), 2));
		
		if(highEstimatePoint.equals(from)) {
			estimate *= 10;
		}
		
		return estimate;
	}

	public int getHeight() {
		return 10;
	}
	
	public int getWidth() {
		return 10;
	}

	public boolean isTraversable(Point point) {
		return traversableSquares[point.getX()][point.getY()] == 0;
	}

}
