package edu.neumont.chessModel.Piece;

import java.util.Enumeration;

import edu.neumont.chessModel.Board.Location;
import edu.neumont.chessModel.Board.Team;
import edu.neumont.chessModel.Interface.ChessBoardInterface;
import edu.neumont.chessModel.Movement.MoveEnumeration;

public class Bishop extends ChessPiece {

	public static final String NAME = "Bishop";
	private static final int WORTH = 3;
	

	public Bishop(Team team) {
		super(team, WORTH);
	}

	public String getName() {
		return NAME;
	}
	
	public Enumeration<Location> getLegalMoves(ChessBoardInterface board) {
		MoveEnumeration moves = new MoveEnumeration(board, this.getLocation(board));
		moves.addDiags();
		return moves;
	}
}
