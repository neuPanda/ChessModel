package edu.neumont.chessModel.Game;

import edu.neumont.chessModel.Board.Location;
import edu.neumont.chessModel.Movement.Move;
import edu.neumont.chessModel.Piece.ChessPiece;

public interface IGameListener {
	public void movePiece(Move move, boolean capturePiece);
	public void placePiece(ChessPiece piece, Location location);
}