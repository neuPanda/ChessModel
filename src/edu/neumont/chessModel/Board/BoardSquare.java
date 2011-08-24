package edu.neumont.chessModel.Board;

import edu.neumont.chessModel.Piece.ChessPiece;


public class BoardSquare {
	ChessPiece piece = null;
	
	public boolean isEmpty() {
		return piece == null;
	}
	
	public void putPiece(ChessPiece piece) {
		this.piece = piece;
	}
	public ChessPiece getPiece() {
		return piece;
	}
	
	public ChessPiece removePiece() {
		ChessPiece temp = piece;
		piece = null;
		return temp;
	}
}
