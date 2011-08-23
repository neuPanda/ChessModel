package edu.neumont.chessModel.Board;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

import edu.neumont.chessModel.Interface.ChessBoardInterface;
import edu.neumont.chessModel.Movement.Move;
import edu.neumont.chessModel.Piece.ChessPiece;

public class ChessBoard implements ChessBoardInterface{

	public static final int N_ROWS = 8;
	public static final int N_COLS = 8;
	
	public class MoveDescription {
		private Move move;
		private Team takenTeam;
		private ChessPiece takenPiece;
		private boolean hasMoved;
		
		public MoveDescription(Move move, Team takenTeam, ChessPiece takenPiece, boolean hasMoved) {
			this.move = move;
			this.takenTeam = takenTeam;
			this.takenPiece = takenPiece;
			this.hasMoved = hasMoved;
		}
		
		public Move getMove() {
			return move;
		}
		
		public Team getTakenTeam() {
			return takenTeam;
		}
		
		public ChessPiece getTakenPiece() {
			return takenPiece;
		}
		
		public boolean hasMoved() {
			return hasMoved;
		}
	}
	
	public interface IListener {
		public void movePiece(Move move, boolean capturePiece);
		public void placePiece(ChessPiece piece, Location location);
	}
	
	public static boolean isInBounds(Location location) {
		return (location.getRow() >= 0) && (location.getRow() < N_ROWS) && (location.getColumn() >= 0) && (location.getColumn() < N_COLS);
	}
	
	//private
	private BoardSquare grid[][] = new BoardSquare[N_ROWS][N_COLS];
	private Stack<MoveDescription> tryingMoves = new Stack<MoveDescription>();
	private ArrayList<IListener> listeners = new ArrayList<IListener>();
	private boolean hasEnPassantMove = false; // TODO EnPassant
	private Location enPassantPawnLocation; // saved location of pawn that can be captured EnPassant
											// TODO EnPassant
	public ChessBoard() {
		for (int i = 0; i < N_ROWS; i++) {
			for (int j = 0; j < N_COLS; j++) {
				grid[i][j] = new BoardSquare();
			}
		}
	}
	public boolean hasEnPassantMove() { // TODO EnPassant
		return hasEnPassantMove;
	}

	public void hasEnPassantMove(boolean canEnPassant, Location enPassantPawnLocation) { // TODO EnPassant
		hasEnPassantMove = canEnPassant;
		if(!hasEnPassantMove) enPassantablePawnLocation(null);
		else enPassantablePawnLocation(enPassantPawnLocation);
	}
	
	public void hasEnPassantMove(boolean canEnPassant) { // TODO EnPassant
		hasEnPassantMove(canEnPassant, null);
	}
	
	public Location getEnPassantablePawnLocation(){// TODO EnPassant
		return enPassantPawnLocation;
	}

	private void enPassantablePawnLocation(Location location) { // TODO EnPassant
		enPassantPawnLocation = location;
	}
	
	public boolean isEnPassantableLocation(Location location) { // TODO EnPassant
		return enPassantPawnLocation.equals(location);
	}

	public void AddListener(IListener listener) {
		listeners.add(listener);
	}
	
	public void RemoveListener(IListener listener) {
		listeners.remove(listener);
	}
	
	private void notifyListenersOfMove(Move move, boolean capturePiece) {
		for (Iterator<IListener> i = listeners.iterator(); i.hasNext(); ) {
			IListener listener = i.next();
			listener.movePiece(move, capturePiece);
		}
	}
	
	private void notifyListenersOfPlacement(ChessPiece piece, Location location) {
		for (Iterator<IListener> i = listeners.iterator(); i.hasNext(); ) {
			IListener listener = i.next();
			listener.placePiece(piece, location);
		}
	}
	
	private void putPiece(ChessPiece piece, Location location) {
		if (!grid[location.getRow()][location.getColumn()].isEmpty()) {
			throw new RuntimeException("Cannot place piece at a non-empty space");
		}
		grid[location.getRow()][location.getColumn()].putPiece(piece);
		//piece.setLocation(location);
	}
	
	public void placePiece(ChessPiece piece, Location location) {
		putPiece(piece, location);
		notifyListenersOfPlacement(piece, location);
	}

	public void removePiece(ChessPiece piece, Location location) { // TODO: pawn promotion
		removePiece(location);
		Team pieceTeam = piece.getTeam();
		pieceTeam.remove(piece);
	}

	public ChessPiece getPiece(Location location) {
		return grid[location.getRow()][location.getColumn()].getPiece();
	}
	
	public boolean hasPiece(Location location) {
		return getPiece(location) != null;
	}
	
	public boolean hasPiece(Location location, boolean isWhite) {
		ChessPiece piece = getPiece(location);
		return (piece != null) && (piece.getTeam().isWhite() == isWhite);
	}
	
	private ChessPiece removePiece(Location location) {
		
		ChessPiece piece = grid[location.getRow()][location.getColumn()].removePiece();
		//piece.setLocation(null);
		return piece;
	}
	
	private MoveDescription makeBasicMove(Move move) {
		ChessPiece capturedPiece = null;
		Team capturedTeam = null;
		boolean hasMoved = getPiece(move.getFrom()).hasMoved();
		if (hasPiece(move.getTo())) {
			capturedPiece = removePiece(move.getTo());
			capturedTeam = capturedPiece.getTeam();
			capturedTeam.remove(capturedPiece);
		}
		putPiece(removePiece(move.getFrom()), move.getTo());
		MoveDescription moveDescription = new MoveDescription(move, capturedTeam, capturedPiece, hasMoved);
		return moveDescription;
	}

	public void makeMove(Move move) {
		MoveDescription description = makeBasicMove(move);
		getPiece(move.getTo()).setHasMoved(true);
		notifyListenersOfMove(move, description.getTakenPiece() != null);
	}

	public void tryMove(Move move) {
		tryingMoves.push(makeBasicMove(move));
	}

	public void undoTriedMove() {
		MoveDescription moveDescription = tryingMoves.pop();
		Move move = moveDescription.getMove();
		putPiece(removePiece(move.getTo()), move.getFrom());
		getPiece(move.getFrom()).setHasMoved(moveDescription.hasMoved());
		if (moveDescription.getTakenPiece() != null) {
			putPiece(moveDescription.getTakenPiece(), move.getTo());
			moveDescription.getTakenTeam().add(moveDescription.getTakenPiece());
		}
	}
	
	//Checks to see if all of the spots between columns are empty on the same row, inclusively.
	public boolean rangeIsEmpty(Location start, Location end){
		boolean hasPiece = false;
		int x = 0;
		if(isInBounds(start) && isInBounds(end)){
			if(end.getColumn()>start.getColumn()){
				while(start.getColumn()+x <= end.getColumn() && hasPiece == false){
					hasPiece = hasPiece(new Location(start.getRow(), start.getColumn()+x));
					x++;
				}
			}else if(start.getColumn() > end.getColumn()){
				while(end.getColumn()+x <= start.getColumn() && hasPiece == false){
					hasPiece =hasPiece(new Location(end.getRow(), end.getColumn()+x));
					x++;
				} 
			}else{
				hasPiece = hasPiece(new Location(start.getRow(), start.getColumn()));
			}
		}
		return !hasPiece;
	}

	public Location getPieceLocation(ChessPiece piece) 
	{
		for (int i = 0; i < grid.length; i++){
			for (int j = 0; j < grid[i].length; j++) 
			{
				if (grid[i][j].piece.equals(piece))
				{
					return new Location(i, j);
				}
			}
		}
		return null;
	}
	
}
