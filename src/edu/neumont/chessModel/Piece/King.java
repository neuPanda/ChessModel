package edu.neumont.chessModel.Piece;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;

import edu.neumont.chessModel.Board.Location;
import edu.neumont.chessModel.Board.Team;
import edu.neumont.chessModel.Interface.ChessBoardInterface;
import edu.neumont.chessModel.Movement.Adjustment;
import edu.neumont.chessModel.Movement.MoveEnumeration;

public class King extends ChessPiece {

	public static final String NAME = "King";
	private static final int WORTH = 10000;
	private static Adjustment[] kingAdjustments = {
			new Adjustment(0,1),
			new Adjustment(1,1),
			new Adjustment(1,0),
			new Adjustment(1,-1),
			new Adjustment(0,-1),
			new Adjustment(-1,-1),
			new Adjustment(-1,0),
			new Adjustment(-1,1)
	};
	private boolean inCheckLeft;
	private boolean inCheckRight;
	
	public King(Team team) {
		super(team, WORTH);
	}
	
	public void setInCheckLeft(boolean inCheckLeft) {
		this.inCheckLeft = inCheckLeft;
	}


	public void setInCheckRight(boolean inCheckRight) {
		this.inCheckRight = inCheckRight;
	}

		
	
	public String getName() {
		return NAME;
	}
		
	public Enumeration<Location> getLegalMoves(ChessBoardInterface board) {
		return new KingMoves(board, this);
	}
	
	public class KingMoves implements Enumeration<Location>{
		 private Iterator<Location> iter;
		 
		 public KingMoves(ChessBoardInterface board, King king){
			  ArrayList<Location> kingMoves = new ArrayList<Location>();
			  MoveEnumeration moves = new MoveEnumeration(board, king.getLocation(board));
			  moves.addAdjustments(kingAdjustments);
			  while(moves.hasMoreElements())
				  kingMoves.add(moves.nextElement());
			  ChessPiece rook;
			  Location castleLocation;
			  if(!king.hasMoved() && !inCheckRight)
				{					
					rook = board.getPiece(new Location(king.getLocation(board).getRow(), king.getLocation(board).getColumn()+3));
					castleLocation = castleingLeftBlack_RightWhite(rook, board);
					if(castleLocation != null)
						kingMoves.add(castleLocation);
				}
			  if(!king.hasMoved() && !inCheckLeft)
				{
					rook = board.getPiece(new Location(king.getLocation(board).getRow(), king.getLocation(board).getColumn()-4));
					castleLocation = castleingLeftWhite_RightBlack(rook, board);
					if(castleLocation != null)
						kingMoves.add(castleLocation);
				}
				
			  iter = kingMoves.iterator();
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
	
	//this is castling left for black and right for white
	public Location castleingLeftBlack_RightWhite(ChessPiece rook, ChessBoardInterface board)
	{
		Location castleLocation = null;
		
		if(rook !=null && !rook.hasMoved &&
		   board.rangeIsEmpty(new Location(this.getLocation(board).getRow(),5), new Location(this.getLocation(board).getRow(),6)))
		{
			castleLocation =  new Location(this.getLocation(board).getRow(), this.getLocation(board).getColumn()+2);		   
		}
		return castleLocation;
	}
	
	//this is castling left for black and right for white
	public Location castleingLeftWhite_RightBlack(ChessPiece rook, ChessBoardInterface board)
	{
		Location castleLocation = null;
		
		if(rook !=null && !rook.hasMoved &&
		   board.rangeIsEmpty(new Location(this.getLocation(board).getRow(),1), new Location(this.getLocation(board).getRow(),3)))
		{
			castleLocation =  new Location(this.getLocation(board).getRow(), this.getLocation(board).getColumn()-2);		   
		}
		return castleLocation;
	}
	
	
	
	
	
}
