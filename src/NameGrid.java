import java.util.*; 

public class NameGrid {

	private int cols, rows; 
	private final char EMPTY= '-';
	private int maxLoops = 2000; 
	private String[] wordList;
	private char[][] grid; 
	private ArrayList<Integer[]> coordList; 
	
	public NameGrid(int size, String[] words) {
		cols = rows = size;
		grid = new char[cols][rows]; 
		fillGrid(EMPTY); 
		wordList = words; 
	}
	
	public NameGrid fillGrid(char fill) {
		for(int r = 0; r < cols; r++)
			for(int c = 0; c < rows; c++)
				setCell(r, c, fill);
		return this; 
	}
	
	/**
	 * 
	 * @param timePermitted
	 * @param spins
	 */
	public void computeNameGrid(int timePermitted, int spins) {
		
	}
	
	/**
	 * Suggests a coordinate for a given word 
	 * @param word
	 * @return
	 */
	public ArrayList<Integer[]> suggestCoord(String word) {
//		int count = 0; 
		coordList = new ArrayList<Integer[]>(); 
		int glc = -1; // global count? 
		
		for(char letter : word.toCharArray()) { // for each letter in name
			glc++; 
			int rowc = 0; 
			for(char[] r : grid) { // for each row in grid
				rowc++; 
				int colc = 0; 
				for (char c : r) { // for each char in row
					colc++; 
					if(letter == c)
						try { // suggest vertical placement 
							if(rowc - glc > 0) // make sure we're not suggesting a starting point 
								if((rowc - glc) + word.length() <= rows) // make sure it fits
									coordList.add(new Integer[] {	 
																	rowc - glc,
																	colc, 
																	1,
																	colc + (rowc - glc),
																	0
																}); 	
						}catch(IndexOutOfBoundsException e) {}
						try { // suggest horizontal placement
							if(colc - glc > 0) // make sure we're not suggesting a starting point
								if((colc - glc) + word.length() <= cols) // make sure it fits
									coordList.add(new Integer[] {						
																	rowc,
																	colc - glc, 		 
																	0, 
																	rowc + (colc - glc), 
																	0
																});
						}catch(IndexOutOfBoundsException e) {}
					
				}
			}
		}
		// Sort list and return 
		return sortCoordList(coordList, word); 
	}
	
	/**
	 * Sorts a given coordinate list for a given word
	 * @param coordList
	 * @param word
	 * @return
	 */
	public ArrayList<Integer[]> sortCoordList(ArrayList<Integer[]> coordList, String word) {
		ArrayList<Integer[]> newCoordList = new ArrayList<Integer[]>(coordList.size()); 
		int row, col; 
		boolean vert; 
		
		for(Integer[] coord : coordList) {
			row = coord[0]; 
			col = coord[1]; 
			vert = coord[2] == 0 ? false : true;
			coord[3] = getFitScore(row, col, vert, word);
			if(coord[3] > 0) // only adds coords that fit
				newCoordList.add(coord);
		}
		// sort by score
		Collections.sort(newCoordList, new Comparator<Integer[]>(){
			public int compare(Integer[] a, Integer[] b) {
				return a[3].compareTo(b[3]); 
			}
		});
		return newCoordList; 
	}
	
	/**
	 * Returns a fit score for a given word. 0 is no fit, 1 is fit, 2+ means a cross
	 * @param row
	 * @param col
	 * @param vert
	 * @param word
	 * @return
	 */
	public int getFitScore(int row, int col, boolean vert, String word) {
		if(col < 0 || row < 0)
			return 0; 
		
		int count = 1, score = 1;
		char activeCell; 
		
		for(char letter : word.toCharArray()) {
			try {
				activeCell = getCell(row, col); 
			}
			catch(IndexOutOfBoundsException e) {return 0;}
			
			if(activeCell != letter && activeCell != EMPTY) 
				return 0; 
			
			if(activeCell == letter) // cross point
				score += 1; 
			
			if(vert) { // vertical 
				if(activeCell != letter) // not a cross point
					if(!cellEmpty(row, col+1)) // check to the right
						return 0; 
					if(!cellEmpty(row, col-1)) // check to the left
						return 0; 
				if(count == 1) // for first letter, check above
					if(!cellEmpty(row-1, col))
						return 0; 
				if(count == word.length()) // for last letter, check below
					if(!cellEmpty(row+1, col))
						return 0; 						
			}
			else { // horizontal 
				if(activeCell != letter) // not a cross point
					if(!cellEmpty(row+1, col)) // check above
						return 0; 
					if(!cellEmpty(row-1, col)) // check below
						return 0; 
				if(count == 1) // for first letter, check to the left
					if(!cellEmpty(row, col-1))
						return 0; 
				if(count == word.length()) // for last letter, check to the right
					if(!cellEmpty(row, col+1))
						return 0; 	
			}
			
			if(vert)
				row++;
			else
				col++; 
			count++; 
		}
		return score; 
	}
	
	/**
	 * Sets word in grid starting at coordinate r, c
	 * @param row
	 * @param col
	 * @param vert
	 * @param word
	 */
	public void setWord(int row, int col, boolean vert, String word) {
		for(char letter : word.toCharArray()) {
			setCell(row, col, letter);
			if(vert)
				row++; 
			else
				col++; 
		}
			
	}
	
	public void setCell(int row, int col, char fill) {
		grid[row][col] = fill;  
	}
	
	public char getCell(int row, int col) {
		return grid[row][col]; 
	}
	
	/**
	 * Checks if a cell is empty
	 * @param r
	 * @param c
	 * @return
	 */
	public boolean cellEmpty(int r, int c) {
		try {
			if(grid[r][c] == EMPTY)
				return true; 
		}
		catch(IndexOutOfBoundsException e) {}
		return false; 
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void display() {
		for(char[] r : grid)
		{
			for(char c : r)
				System.out.print(c + " ");
			System.out.println(); 
		}
			
	}
}
