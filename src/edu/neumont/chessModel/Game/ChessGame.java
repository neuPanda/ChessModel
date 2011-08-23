package edu.neumont.chessModel.Game;

import edu.neumont.chessModel.Board.ChessBoard;
import edu.neumont.chessModel.Board.Location;
import edu.neumont.chessModel.Board.Team;
import edu.neumont.chessModel.Board.Team.Color;
import edu.neumont.chessModel.Interface.ChessBoardInterface;
import edu.neumont.chessModel.Interface.ICheckChecker;
import edu.neumont.chessModel.Movement.Move;
import edu.neumont.chessModel.Piece.*;

public class ChessGame implements ICheckChecker
{
	ChessBoardInterface board;
	Team lightTeam;
	Team darkTeam;
	Team currentTeam;
	
	public ChessGame() 
	{
		board = new ChessBoard();
		lightTeam = new Team(Color.LIGHT,board, this);
		darkTeam = new Team(Color.DARK,board, this);
		currentTeam = lightTeam;
	}
	
	private void changeTeam()
	{
		currentTeam = (currentTeam.equals(lightTeam))?darkTeam:lightTeam;
	}
	
	public Team getCurrentTeam() {
		return currentTeam;
	}
	
	public void setCurrentTeam(Team currentTeam) {
		this.currentTeam = currentTeam;
	}
	
	public void initalizeBoard(Team lightTeam, Team darkTeam) {
		
		lightTeam.setupBoard();
		darkTeam.setupBoard();
	}
	
	public void placePiece(ChessPiece piece, Location location) 
	{
		board.placePiece(piece, location);
	}
	
	public void makeMove(Move move) {
		if (!currentTeam.hasPiece(board.getPiece(move.getFrom())))
		{
			throw new RuntimeException("Moving piece for wrong team");
		}
		board.makeMove(move);
		changeTeam();
	}

	@Override	
	public boolean isInCheck(Team team) {
	Location kingsLocation = team.getKing().getLocation(board);
	Team attackingTeam = (team.isWhite()) ? darkTeam : lightTeam;
	boolean inCheckTemp = attackingTeam.canAttack(board, kingsLocation);
	if (!inCheckTemp) {
		isCastlingNotInCheck(team, kingsLocation, attackingTeam,
				inCheckTemp);
	} else {
		team.getKing().setInCheckLeft(inCheckTemp);
		team.getKing().setInCheckRight(inCheckTemp);
	}
	return inCheckTemp;
	}
	
	private void isCastlingNotInCheck(Team team, Location kingsLocation,
			Team attackingTeam, boolean inCheckTemp) {
		King king = team.getKing();
		king.setInCheckLeft(attackingTeam.canAttack(board, new Location(kingsLocation.getRow(),
				kingsLocation.getColumn() - 1)));
		king.setInCheckRight(attackingTeam.canAttack(board, new Location(kingsLocation.getRow(),
				kingsLocation.getColumn() + 1)));
	}

}
