package edu.neumont.chessModel.Piece;

import java.util.Enumeration;

import edu.neumont.chessModel.Board.ChessBoard;
import edu.neumont.chessModel.Board.Location;
import edu.neumont.chessModel.Game.Team;
import edu.neumont.chessModel.Movement.Adjustment;
import edu.neumont.chessModel.Movement.MoveEnumeration;

public class Knight extends ChessPiece {

	public static final String NAME = "Knight";
	private static final int WORTH = 3;

	private static Adjustment[] knightAdjustments = {
		new Adjustment(2,1),
		new Adjustment(2,-1),
		new Adjustment(-2,1),
		new Adjustment(-2,-1),
		new Adjustment(1,2),
		new Adjustment(-1,2),
		new Adjustment(1,-2),
		new Adjustment(-1,-2)
	};
	

	public Knight(Team team) {
		super(team, WORTH);
	}
	
	public String getName() {
		return NAME;
	}
	
	public Enumeration<Location> getLegalMoves(ChessBoard board) {
		MoveEnumeration moves = new MoveEnumeration(board, this.getLocation(board));
		moves.addAdjustments(knightAdjustments);
		return moves;
	}
}
