package net.todd.games.boardgame.astar;

// TODO javadoc
public interface IAStarContext {
	public abstract int getHeight();
	public abstract int getWidth();
	
	public abstract double g_factor(Point point);
	public abstract double h(Point from, Point to);
	
	public abstract boolean traversable(Point point);
	
	public abstract boolean allowDiagonalMovements();
}
