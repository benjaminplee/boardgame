package net.todd.games.boardgame.astar;

import java.util.List;
// TODO javadoc
public interface IPathFinder {

	public abstract List<Point> findPath(Point start, Point end);

}