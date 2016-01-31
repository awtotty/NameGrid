import java.util.*; 

public class NameGrid {

	private int cols, rows; 
	private final char EMPTY= '-';
	private int maxLoops = 2000; 
	private String[] availableWords;
	private char[][] grid; 
	
	public NameGrid() {
		cols = rows = 50;
		grid = new char[cols][rows]; 
		this.fillGrid(EMPTY); 
	}
	
	public NameGrid fillGrid(char fill) {
		for(int r = 0; r < cols; r++)
			for(int c = 0; c < rows; c++)
				grid[r][c] = fill;
		return this; 
	}
	
	public void computeNameGrid(int timePermitted, int spins) {
		int count = 0; 
		
	}
	
	public ArrayList<Integer[]> suggestCoord(String name) {
//		int count = 0; 
		ArrayList<Integer[]> coordList = new ArrayList<Integer[]>(); 
		int glc = -1; // What is glc???
		
		for(char letter : name.toCharArray()) { // for each letter in name
			glc += 1; 
			int rowc = 0; 
			for(char[] r : grid) { // for each row in grid
				rowc += 1; 
				int colc = 0; 
				for (char c : r) { // for each char in row
					if(letter == c)
						try { // suggest vertical placement 
							if(rowc - glc > 0) // make sure we're not suggesting a starting point 
								if((rowc - glc) + name.length() <= rows) // make sure it fits
									coordList.add(new Integer[] {	
																	colc, 
																	rowc - glc, 
																	1,
																	colc + (rowc - glc),
																	0
																}); 	
						}
						catch(IndexOutOfBoundsException e) {}
						try { // suggest horizontal placement
							if(colc - glc > 0) // make sure we're not suggesting a starting point
								if((colc - glc) + name.length() <= cols) // make sure it fits
									coordList.add(new Integer[] {
																	colc - glc, 
																	rowc, 
																	0, 
																	rowc + (colc - glc), 
																	0
																});
						}
						catch(IndexOutOfBoundsException e) {}
					
				}
			}
		}
		// Example: 
		// Sort list
		ArrayList<Integer[]> newCoordList = this.sortCoordList(coordList, name);
		return newCoordList; 
	}

	public ArrayList<Integer[]> sortCoordList(ArrayList<Integer[]> coordList, String name) {
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void print() {
		for(char[] r : grid)
		{
			for(char c : r)
				System.out.print(c + " ");
			System.out.println(); 
		}
			
	}
}
