package edu.neumont.chessModel.Game;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;

import edu.neumont.chessModel.Board.ChessBoard;
import edu.neumont.chessModel.Board.Location;
import edu.neumont.chessModel.Movement.Move;
import edu.neumont.chessModel.Piece.Bishop;
import edu.neumont.chessModel.Piece.ChessPiece;
import edu.neumont.chessModel.Piece.King;
import edu.neumont.chessModel.Piece.Knight;
import edu.neumont.chessModel.Piece.Pawn;
import edu.neumont.chessModel.Piece.Queen;
import edu.neumont.chessModel.Piece.Rook;

public class Team {

	public enum Color {
		LIGHT, DARK
	}

	protected Color color;
	private King king;
	protected ChessBoard board;
	private ICheckChecker checkChecker;
	private ArrayList<ChessPiece> workingPieces = new ArrayList<ChessPiece>();
	private ArrayList<ChessPiece> takenPieces = new ArrayList<ChessPiece>();
	private char mainRow;
	private char pawnRow;


	public Team(Color color, ChessBoard board,
			ICheckChecker checkChecker) {
		this.color = color;
		this.board = board;
		this.checkChecker = checkChecker;
		this.king = new King(this);
		takenPieces.add(king);
		takenPieces.add(new Queen(this));
		takenPieces.add(new Rook(this));
		takenPieces.add(new Rook(this));
		takenPieces.add(new Bishop(this));
		takenPieces.add(new Bishop(this));
		takenPieces.add(new Knight(this));
		takenPieces.add(new Knight(this));
		for (int i = 0; i < 8; i++) {
			takenPieces.add(new Pawn(this));
		}
		if (this.isWhite()) {
			mainRow = '1';
			pawnRow = '2';
		} else {
			mainRow = '8';
			pawnRow = '7';
		}
	}

	
	public Color getColor() {
		return color;
	}
	
	public ArrayList<ChessPiece> getWorkingPieces() {
		return workingPieces;
	}

	public void setupBoard() {
		placePiece(Rook.class, new Location(mainRow, 'a'));
		placePiece(Knight.class, new Location(mainRow, 'b'));
		placePiece(Bishop.class, new Location(mainRow, 'c'));
		placePiece(Queen.class, new Location(mainRow, 'd'));
		placePiece(King.class, new Location(mainRow, 'e'));
		placePiece(Bishop.class, new Location(mainRow, 'f'));
		placePiece(Knight.class, new Location(mainRow, 'g'));
		placePiece(Rook.class, new Location(mainRow, 'h'));
		for (int i = 0; i < 8; i++) {
			placePiece(Pawn.class,
					new Location(pawnRow, (char) ('a' + i)));
		}
	}
	
	@SuppressWarnings("rawtypes")
	private void placePiece(Class pieceClass, Location location) {
		ChessPiece piece = findTakenPiece(pieceClass);
		if (piece == null)
			throw new RuntimeException("Piece not found");
		add(piece);
		board.placePiece(piece, location);
	}

	@SuppressWarnings("rawtypes")
	private ChessPiece findTakenPiece(Class pieceClass) {
		ChessPiece piece = null;
		for (Iterator<ChessPiece> i = takenPieces.iterator(); piece == null && i.hasNext();) {
			ChessPiece tempPiece = i.next();
			if(tempPiece.getClass()==pieceClass)
				piece = tempPiece;
		}
		return piece;
	}

	public void add(ChessPiece piece) {
		if (!takenPieces.remove(piece))
			throw new RuntimeException("piece not found");
		workingPieces.add(piece);
	}

	public void remove(ChessPiece piece) {
		if (!workingPieces.remove(piece))
			throw new RuntimeException("piece not found");
		takenPieces.add(piece);
	}

	public boolean isWhite() {
		return color == Color.LIGHT;
	}

	public King getKing() {
		return king;
	}

	public boolean canAttack(ChessBoard board, Location target) {
		boolean attacks = false;
		for (Iterator<ChessPiece> i = workingPieces.iterator(); !attacks
				&& i.hasNext();) {
			ChessPiece piece = i.next();
			attacks = piece.canAttack(board, target);
		}
		return attacks;
	}

	public boolean hasPiece(ChessPiece piece) {
		return workingPieces.contains(piece);
	}

	public int getPieceCount() {
		return workingPieces.size();
	}

	public Iterator<Move> getMoves(ChessBoard board) {
		ArrayList<Move> moves = new ArrayList<Move>();
		for (Iterator<ChessPiece> i = workingPieces.iterator(); i.hasNext();) {
			ChessPiece piece = i.next();
			for (Enumeration<Location> e = piece.getLegalMoves(board); e
					.hasMoreElements();) {
				Location to = e.nextElement();
				Move move = new Move(piece.getLocation(board), to);
				moves.add(move);
			}
		}
		return moves.iterator();
	}

	public boolean isLegalMove(Move move) {
		ChessPiece movingPiece = board.getPiece(move.getFrom());
		Team movingTeam = movingPiece.getTeam();
		return (movingTeam == this) && movingPiece.isLegalMove(board, move)
				&& !causesCheckmate(move);
	}

	private boolean causesCheckmate(Move move) {
		board.tryMove(move);
		boolean result = checkChecker.isInCheck(this);
		board.undoTriedMove();

		return result;
	}

}
