package edu.neumont.chessModel.Piece;

import java.util.Enumeration;

import edu.neumont.chessModel.Board.ChessBoard;
import edu.neumont.chessModel.Board.Location;
import edu.neumont.chessModel.Game.Team;
import edu.neumont.chessModel.Movement.Move;

public abstract class ChessPiece {

	protected Team team;
	protected boolean hasMoved = false;
	protected int worth;
	
	public ChessPiece(Team team, int worth) {
		this.team = team;
		this.worth = worth;
	}	
	public Location getLocation(ChessBoard board) {
		return board.getPieceLocation(this);
	}
	
	public boolean hasMoved() {
		return hasMoved;
	}
	
	public void setHasMoved(boolean hasMoved) {
		this.hasMoved = hasMoved;
	}
	
	public String getName() {
		return null;
	}
	
	public Team getTeam() {
		return team;
	}
	
	public abstract Enumeration<Location> getLegalMoves(ChessBoard board);
	
	public boolean canAttack(ChessBoard board, Location target) {
		boolean attacks = false;
		for (Enumeration<Location> e = getLegalMoves(board); !attacks && e.hasMoreElements(); ) {
			Location location = e.nextElement();
			attacks = target.equals(location);
		}
		return attacks;
	}
	
	public boolean isLegalMove(ChessBoard board, Move move) {
		boolean isValid = false;
		
		for (Enumeration<Location> e = getLegalMoves(board); !isValid && e.hasMoreElements(); ) {
			Location there = e.nextElement();
			isValid = there.equals(move.getTo());
		}
		return isValid;
	}
	
	public int getValue() {
		return worth;
	}
}
