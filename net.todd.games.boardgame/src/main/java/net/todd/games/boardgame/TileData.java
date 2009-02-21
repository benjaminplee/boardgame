package net.todd.games.boardgame;

/**
 * TileData contains the size and color of the tile
 */
public class TileData {
	private float size;
	private float[] color;
	private float[] position;

	public float getSize() {
		return size;
	}

	public void setSize(float size) {
		this.size = size;
	}

	public float[] getColor() {
		return color;
	}

	public void setColor(float[] color) {
		this.color = color;
	}

	public float[] getPosition() {
		return position;
	}

	public void setPosition(float[] position) {
		this.position = position;
	}
}
