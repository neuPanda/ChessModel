package edu.neumont.chessModel.piece;

import java.util.Enumeration;

import edu.neumont.chessModel.board.ChessBoard;
import edu.neumont.chessModel.board.Location;
import edu.neumont.chessModel.game.Team;
import edu.neumont.chessModel.movement.MoveEnumeration;

public class Queen extends ChessPiece {

	public static final String NAME = "Queen";
	private static final int WORTH = 9;
	
	public Queen(Team team) {
		super(team, WORTH);
	}

	public String getName() {
		return NAME;
	}
	
	public Enumeration<Location> getLegalMoves(ChessBoard board) {
		MoveEnumeration moves = new MoveEnumeration(board, this.getLocation(board));
		moves.addDiags();
		moves.addPerps();
		return moves;
	}
	
}
