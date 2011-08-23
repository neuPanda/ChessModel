package edu.neumont.chessModel.piece;

import java.util.Enumeration;

import edu.neumont.chessModel.board.ChessBoard;
import edu.neumont.chessModel.board.Location;
import edu.neumont.chessModel.game.Team;
import edu.neumont.chessModel.movement.Adjustment;
import edu.neumont.chessModel.movement.MoveEnumeration;

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
