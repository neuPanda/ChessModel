package edu.neumont.chessModel.piece;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;

import edu.neumont.chessModel.board.ChessBoard;
import edu.neumont.chessModel.board.Location;
import edu.neumont.chessModel.game.Team;

public class Pawn extends ChessPiece {

	public static final String NAME = "Pawn";
	private static final int WORTH = 1;
	
	public Pawn(Team team) {
		super(team, WORTH);
	}
	
	public String getName() {
		return NAME;
	}
	
	public Location nextStep(ChessBoard board) {
		int step = team.isWhite()? 1: -1;
		return new Location(this.getLocation(board).getRow()+step, this.getLocation(board).getColumn());
	}

	public Location longStep(ChessBoard board) {
		int step = team.isWhite()? 2: -2;
		return new Location(this.getLocation(board).getRow()+step, this.getLocation(board).getColumn());
	}

	public Location strikeLeft(ChessBoard board) {
		int step;
		int left;
		if (team.isWhite()) {
			step = 1;
			left = -1;
		} else {
			step = -1;
			left = 1;
		}
		return new Location(this.getLocation(board).getRow()+step, this.getLocation(board).getColumn()+left);
	}

	public Location strikeRight(ChessBoard board) {
		int step;
		int right;
		if (team.isWhite()) {
			step = 1;
			right = 1;
		} else {
			step = -1;
			right = -1;
		}
		return new Location(this.getLocation(board).getRow()+step, this.getLocation(board).getColumn()+right);
	}

	public Enumeration<Location> getLegalMoves(ChessBoard board) {
		return new PawnMoves(board, this);
	}
	
	public class PawnMoves implements Enumeration<Location> {

		private Iterator<Location> iter;
		
		public PawnMoves(ChessBoard board, Pawn pawn) {
			
			ArrayList<Location> moves = new ArrayList<Location>();
			boolean pawnIsWhite = pawn.getTeam().isWhite();
			
			if (ChessBoard.isInBounds(pawn.longStep(board)) &&
			   (!pawn.hasMoved()) &&
			   (!board.hasPiece(pawn.nextStep(board))) && 
			   (!board.hasPiece(pawn.longStep(board)))) {
					moves.add(pawn.longStep(board));
			}
			if (ChessBoard.isInBounds(pawn.nextStep(board)) && !board.hasPiece(pawn.nextStep(board))) {
				moves.add(pawn.nextStep(board));
			}
			if (ChessBoard.isInBounds(pawn.strikeLeft(board)) && board.hasPiece(pawn.strikeLeft(board), !pawnIsWhite)) {
				moves.add(pawn.strikeLeft(board));
			}
			if (ChessBoard.isInBounds(pawn.strikeRight(board)) && board.hasPiece(pawn.strikeRight(board), !pawnIsWhite)) {
				moves.add(pawn.strikeRight(board));
			}
			iter = moves.iterator();
		}
		
		@Override
		public boolean hasMoreElements() {
			return iter.hasNext();
		}

		@Override
		public Location nextElement() {
			return iter.next();
		}
		
	}
}
