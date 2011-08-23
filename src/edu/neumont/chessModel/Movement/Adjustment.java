package edu.neumont.chessModel.Movement;

import edu.neumont.chessModel.Board.Location;

public class Adjustment {
	private int row;
	private int column;
	
	public Adjustment(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	public Location getLocation(Location location) {
		return new Location(location.getRow()+row, location.getColumn() + column);
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}
}

