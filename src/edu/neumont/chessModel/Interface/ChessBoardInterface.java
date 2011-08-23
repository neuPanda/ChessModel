package edu.neumont.chessModel.Interface;

import edu.neumont.chessModel.Board.ChessBoard.IListener;
import edu.neumont.chessModel.Board.Location;
import edu.neumont.chessModel.Movement.Move;
import edu.neumont.chessModel.Piece.ChessPiece;

public interface ChessBoardInterface {

	/**
	 * @param listener
	 * @see edu.neumont.chessModel.ChessBoard#AddListener(edu.neumont.chessModel.ChessBoard.IListener)
	 */
	public abstract void AddListener(IListener listener);

	/**
	 * @param listener
	 * @see edu.neumont.chessModel.ChessBoard#RemoveListener(edu.neumont.chessModel.ChessBoard.IListener)
	 */
	public abstract void RemoveListener(IListener listener);

	/**
	 * @param obj
	 * @return
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public abstract boolean equals(Object obj);

	/**
	 * @return
	 * @see edu.neumont.chessModel.ChessBoard#getEnPassantablePawnLocation()
	 */
	public abstract Location getEnPassantablePawnLocation();

	/**
	 * @param location
	 * @return
	 * @see edu.neumont.chessModel.ChessBoard#getPiece(edu.neumont.chessModel.Location)
	 */
	public abstract ChessPiece getPiece(Location location);

	/**
	 * @return
	 * @see edu.neumont.chessModel.ChessBoard#hasEnPassantMove()
	 */
	public abstract boolean hasEnPassantMove();

	/**
	 * @param canEnPassant
	 * @param enPassantPawnLocation
	 * @see edu.neumont.chessModel.ChessBoard#hasEnPassantMove(boolean, edu.neumont.chessModel.Location)
	 */
	public abstract void hasEnPassantMove(boolean canEnPassant,
			Location enPassantPawnLocation);

	/**
	 * @param canEnPassant
	 * @see edu.neumont.chessModel.ChessBoard#hasEnPassantMove(boolean)
	 */
	public abstract void hasEnPassantMove(boolean canEnPassant);

	/**
	 * @param location
	 * @param isWhite
	 * @return
	 * @see edu.neumont.chessModel.ChessBoard#hasPiece(edu.neumont.chessModel.Location, boolean)
	 */
	public abstract boolean hasPiece(Location location, boolean isWhite);

	/**
	 * @param location
	 * @return
	 * @see edu.neumont.chessModel.ChessBoard#hasPiece(edu.neumont.chessModel.Location)
	 */
	public abstract boolean hasPiece(Location location);

	/**
	 * @return
	 * @see java.lang.Object#hashCode()
	 */
	public abstract int hashCode();

	/**
	 * @param location
	 * @return
	 * @see edu.neumont.chessModel.ChessBoard#isEnPassantableLocation(edu.neumont.chessModel.Location)
	 */
	public abstract boolean isEnPassantableLocation(Location location);

	/**
	 * @param move
	 * @see edu.neumont.chessModel.ChessBoard#makeMove(edu.neumont.chessModel.Move)
	 */
	public abstract void makeMove(Move move);

	/**
	 * @param piece
	 * @param location
	 * @see edu.neumont.chessModel.ChessBoard#placePiece(edu.neumont.chessModel.ChessPiece, edu.neumont.chessModel.Location)
	 */
	public abstract void placePiece(ChessPiece piece, Location location);

	/**
	 * @param start
	 * @param end
	 * @return
	 * @see edu.neumont.chessModel.ChessBoard#rangeIsEmpty(edu.neumont.chessModel.Location, edu.neumont.chessModel.Location)
	 */
	public abstract boolean rangeIsEmpty(Location start, Location end);

	/**
	 * @param piece
	 * @param location
	 * @see edu.neumont.chessModel.ChessBoard#removePiece(edu.neumont.chessModel.ChessPiece, edu.neumont.chessModel.Location)
	 */
	public abstract void removePiece(ChessPiece piece, Location location);

	/**
	 * @return
	 * @see java.lang.Object#toString()
	 */
	public abstract String toString();

	/**
	 * @param move
	 * @see edu.neumont.chessModel.ChessBoard#tryMove(edu.neumont.chessModel.Move)
	 */
	public abstract void tryMove(Move move);

	/**
	 * 
	 * @see edu.neumont.chessModel.ChessBoard#undoTriedMove()
	 */
	public abstract void undoTriedMove();
	
	/**
	 * 
	 * @param piece
	 * @return
	 * @see edu.neumont.chessModel.ChessBoard#getPieceLocation()
	 */
	public abstract Location getPieceLocation(ChessPiece piece);

}