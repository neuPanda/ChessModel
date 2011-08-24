package edu.neumont.chessModel.game;

import edu.neumont.chessModel.board.Location;
import edu.neumont.chessModel.movement.Move;
import edu.neumont.chessModel.piece.ChessPiece;

public interface IGameListener {
	public void movePiece(Move move, boolean capturePiece);
	public void placePiece(ChessPiece piece, Location location);
}