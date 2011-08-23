package edu.neumont.chessModel.piece;

import java.util.Enumeration;

import edu.neumont.chessModel.board.ChessBoard;
import edu.neumont.chessModel.board.Location;
import edu.neumont.chessModel.game.Team;
import edu.neumont.chessModel.movement.MoveEnumeration;

public class Rook extends ChessPiece {

	public static final String NAME = "Rook";
	private static final int WORTH = 5;
	
	public Rook(Team team) {
		super(team, WORTH);
	}

	
	public String getName() {
		return NAME;
	}

	public Enumeration<Location> getLegalMoves(ChessBoard board) {
		MoveEnumeration moves = new MoveEnumeration(board, this.getLocation(board));
		moves.addPerps();
		return moves;
	}
}
