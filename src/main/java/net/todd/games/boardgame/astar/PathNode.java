package net.todd.games.boardgame.astar;


class PathNode implements Comparable<PathNode> {
	public PathNode parent;
	public Point location;
	public double g;
	public double h;
	
	public PathNode(PathNode parent, Point location, double g, double h) {
		this.parent = parent;
		this.location = location;
		this.g = g;
		this.h = h;
	}

	public double f() {
		return g + h;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((location == null) ? 0 : location.hashCode());
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
		PathNode other = (PathNode) obj;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		return true;
	}

	public int compareTo(PathNode other) {
		return (int)(f() * 100.0 - other.f() * 100.0);
	}
}