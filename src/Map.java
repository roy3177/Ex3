

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * This class represents a 2D map as a "screen" or a raster matrix or maze over integers.
 * @author boaz.benmoshe
 *
 */
public class Map implements Map2D {
	private int[][] _map;
	private boolean _cyclicFlag = true;
 
	/**
	 * Constructs a w*h 2D raster map with an init value v.
	 * @param w
	 * @param h
	 * @param v
	 */
	public Map(int w, int h, int v) {init(w,h, v);}
	/**
	 * Constructs a square map (size*size).
	 * @param size
	 */
	public Map(int size) {this(size,size, 0);}

	/**
	 * Constructs a map from a given 2D array.
	 * @param data
	 */
	public Map(int[][] data) {
		init(data);
	}

	@Override
	//Initializes a 2D array of doubles with height,width,and the initial value:
	public void init(int w, int h, int v) {
		if(w<=0 || h<=0) {	//If the width or the height are not positive.
			throw new IllegalArgumentException(" The width and height are need to be positive.");
		}
		else {
			int[][]arr=new int[h][w];
			for (int i = 0; i < h; i++) {
				for (int j = 0; j < w; j++) {
					arr[i][j] = v; //Sets all the values of the 2D array  to the initial value.
				}
			}
			this._map = arr;
		}
	}

	@Override
	public void init(int[][] arr) {
		if(arr==null || arr.length==0 || arr[0].length==0) { //If the array is empty or null or ragged 2D array.
			throw new RuntimeException("The array can't be a null or empty");
		}
		else {		
			int rMap=arr.length; //The rows of the map.
			int cMap=arr[0].length; //The columns of the map.
			this._map=new int[rMap][cMap];//Initializes the _map with the same sizes of the array "arr".

			for(int i=0;i<rMap;i++) {
				if(arr[i].length!=cMap) { //If all the rows don't have the same length.
					throw new RuntimeException("The array can't be a ragged 2D array");
				}
				else {
					for (int j = 0; j < cMap; j++) {
						this._map[i][j] = arr[i][j]; //Giving the array _map a deep copy of the array arr.

					}
				}
			}	
		}
	}
	@Override
	public int[][] getMap() {
		//Compute a deep copy of the underline 2D matrix.
		int numberRows=_map.length;
		int numberColumns=_map[0].length;

		int[][]ans=new int[numberRows][numberColumns];

		//Using loops to copy the values of _map matrix  to the ans matrix:
		for (int i = 0; i < numberRows; i++) {
			for (int j = 0; j < numberColumns; j++) {
				ans[i][j] = _map[i][j]; 
			}
		}
		return ans; //Return the deep copy of the underline matrix.
	}
	@Override
	//We looking for the number of the columns in the 2D array _map:
	public int getHeight() {
		if(_map==null&& _map.length==0&&_map[0].length==0) { //If the array is null or empty.
			return 0;
		}
		return _map[0].length;
	}
	@Override
	//We looking for the number of the rows in the 2D array _map:
	public int getWidth() {
		return _map.length;
	}
	@Override
	//We return the [x][y](int) value value of the map[x][y].
	public int getPixel(int x, int y) {
		if( x < 0 || x >= _map.length || y < 0 || y >= _map[0].length) {
			throw new IndexOutOfBoundsException("Invalid coordinates: (" + x + ", " + y + ")");
		}
		return (int) _map[x][y];
	}
	@Override
	public int getPixel(Pixel2D p) {
		int x = p.getX();
		int y = p.getY();
		if( x < 0 || x >= _map.length || y < 0 || y >= _map[0].length) {
			throw new IndexOutOfBoundsException("Invalid coordinates: (" + x + ", " + y + ")");
		}
		//System.out.println("x,y for get pixel are: " +x+", "+ y);
		return (int) _map[x][y];
	}
	@Override
	//We have the v value,that came at the coordinate [x][y].
	public void setPixel(int x, int y, int v) {
		_map[x][y]=v;
	}
	@Override
	public void setPixel(Pixel2D p, int v) {
		int x = p.getX();
		int y = p.getY();
		if( x < 0 || x >= _map.length || y < 0 || y >= _map[0].length) {
			throw new IndexOutOfBoundsException("Invalid coordinates: (" + x + ", " + y + ")");
		}
		_map[p.getY()][p.getX()] = v;

	}
	@Override
	/** 
	 * Fills this map with the new color (new_v) starting from p.
	 * https://en.wikipedia.org/wiki/Flood_fill
	 */
	public int fill(Pixel2D xy, int new_v) {
		int ans = 0; // The  initial number of pixels that were filled.
		int old_v = getPixel(xy); // the original color value at p(xy).
		boolean[][] visit = new boolean[getHeight()][getWidth()]; // Following after the pixels that I visited.

		Queue<Pixel2D> thor = new LinkedList<>(); // Use a queue for Breadth-First Search (BFS) traversal.

		thor.offer(xy); // Adding the starting pixel to the queue "thor".
		visit[xy.getY()][xy.getX()] = true; // Mark it as visited.

		while (!thor.isEmpty()) {
			Pixel2D current = thor.poll(); // Get the next pixel from the thor.
			setPixel(current, new_v); // Fill it with the new color value.
			ans++; // Increase the number of the filled pixels.

			// Looking for the neighbors of the current pixel:
			Pixel2D[] neighbors = new Pixel2D[4];
			neighbors[0] = new Index2D(current.getX() - 1, current.getY()); // Left neighbor
			neighbors[1] = new Index2D(current.getX() + 1, current.getY()); // Right neighbor
			neighbors[2] = new Index2D(current.getX(), current.getY() - 1); // Top neighbor
			neighbors[3] = new Index2D(current.getX(), current.getY() + 1); // Bottom neighbor
			for (int i = 0; i < neighbors.length; i++) {
				Pixel2D neighbor = neighbors[i];
				if (isInside(neighbor) && !visit[neighbor.getY()][neighbor.getX()] && getPixel(neighbor) == old_v) {
					thor.offer(neighbor); // add the neighbor to the queue
					visit[neighbor.getY()][neighbor.getX()] = true; // mark it as visited
				}
			}
		}
		return ans;
	}


	@Override
	/**
	 * BFS like shortest the computation based on iterative raster implementation of BFS, see:
	 * https://en.wikipedia.org/wiki/Breadth-first_search
	 */
	public Pixel2D[] shortestPath(Pixel2D p1, Pixel2D p2, int obsColor) {
		Pixel2D[] ans;  // What we return in the end.
		int[][] temporaryMap = this.getMap(); //Define a temporary map,that  calling our current map.
		Map2D calculatingPath = new Map(temporaryMap); 
		//Checking if the map is cyclic:
		if(this.isCyclic()) {
			calculatingPath.setCyclic(true); //Put the cyclic property to the calcPath.
		}
		else {
			calculatingPath.setCyclic(false);
		}
		//Calculates the distances from p1 to all other positions. .
		//Using the allDistance method on the calculatingPath,p1 is the start position and the obsColor is the the pixel of the obstacle.
		calculatingPath = calculatingPath.allDistance(p1, obsColor);


		if (calculatingPath.getPixel(p2) == -1 || calculatingPath.getPixel(p1) == -1 || calculatingPath.getPixel(p2) == -5 ) {
			//Checking invalid values.
			return null;
		}
		//Taking a method from the Ex3Algo and taking an 1 cell array:
		if(Ex3Algo.comparePixels(p1, p2)) {
			ans = new Pixel2D[1];
			//Case 1:
			ans[0] = p1;
			return ans;
		}
		ans = new Pixel2D[calculatingPath.getPixel(p2) +1 ];
		ans[0] = p1;
		//Case 2:
		if( (p1.distance2D(p2)) < 1.2 ) {
			ans[1] = p2;
			return ans;
		}
		//Made a linked list and add p2:
		List<Pixel2D> curPixel = new ArrayList<>();
		curPixel.add(p2);
		//It initializes bestNeighbor and smallestNeighbor with p2+new list neighbors:
		Pixel2D bestNeighbor = p2;
		List<Pixel2D> neighbors;

		Pixel2D smallestNeighbor = p2;

		for (int i = 1; i <= ans.length-1; i++) {
			//It retrieves the neighbors of bestNeighbor by calling the getNeighbors method:
			neighbors = getNeighbors(bestNeighbor, calculatingPath.isCyclic());
			//The filtered neighbors are stored in validNeighbors:
			List<Pixel2D> isValidNeighbors = new ArrayList<>();
			//If it is not a cyclic map:
			if (!(calculatingPath.isCyclic())) {
				for (Pixel2D neighbor : neighbors) {
					if (calculatingPath.isInside(neighbor) && calculatingPath.getPixel(neighbor) != -1) {
						//The filtered neighbors are stored in validNeighbors:
						isValidNeighbors.add(neighbor);
					}
				}
				//Assigned back to the neighbors:
				neighbors = isValidNeighbors;
			}
			//if it is cyclic map:
			if ((calculatingPath.isCyclic())) {
				//It enter to another loop that iterates through the neighbors:
				for (int k = 0 ; k < neighbors.size() ; k++) {
					//What happened if a neighbor is not inside the map:
					if( ! (calculatingPath.isInside(neighbors.get(k))) ) {
						Pixel2D newNeighbor = new Index2D(((Index2D) neighbors.get(k)).Call(neighbors.get(k),calculatingPath.getWidth(),calculatingPath.getHeight()));
						neighbors.set(k, newNeighbor) ;
					}
					//If neighbor is an obstacle(-1):
					if (calculatingPath.getPixel(neighbors.get(k)) == -1) {
						neighbors.remove(k);
						k--;
					}

				}
			}
			//It iterates through the remaining neighbors:
			for (Pixel2D neighbor : neighbors) {
				//Looking for the smallest distance value(getPixel):
				if (calculatingPath.getPixel(neighbor) < calculatingPath.getPixel(smallestNeighbor) && calculatingPath.getPixel(neighbor) != -1 ) {
					smallestNeighbor = neighbor;
				}
			}
			//It assigns smallestNeighbor to the corresponding element of ans:
			ans[ans.length -1 - i] = smallestNeighbor;
			bestNeighbor = smallestNeighbor;
		}

		//The bestNeighbor is the p2(end position):
		ans[ans.length -1] = p2;


		return ans;
	}
	@Override
	public boolean isInside(Pixel2D p) {
		int x=p.getX();
		int y=p.getY();

		if (x < 0 || x >= _map.length|| y<0 || y >= _map[0].length) {
			return false; //If the Pixel2D p is outside in this map.
		}
		else {
			return true;
		}
	}

	@Override
	public boolean isCyclic() {
		return this._cyclicFlag;
	}
	@Override
	public void setCyclic(boolean cy) {
		this._cyclicFlag=cy;
	}
	public Map2D allDistance(Pixel2D start, int obsColor) {
		Map2D result = null;  //The result of the function.
		int[][] mapDis = null; 

		if(_map != null && _map.length > 0 ){
			//It assigns the width of _map to  and the height of the _map:
			int width = _map.length;
			int height = _map[0].length;
			//Creating a new int mapDis with the same dimension of the _map:
			mapDis = new int[width][height]; 
			Pixel2D temporaryPoint = start;//The start pixel.

			//draw in mapDistances obstacle :
			for(int x = 0 ; x < this.getWidth() ; x++) {
				for(int y = 0 ; y < this.getHeight() ; y++) {
					//Checks if the current coordinates match the start pixel's coordinates: 
					if(x == start.getX() && y == start.getY()) {
						//It sets the corresponding cell in mapDis to 0:
						mapDis[x][y] = 0;
						continue;
					}
					//If the value in _map at position matches obsColor:
					if(_map[x][y] == obsColor) {
						mapDis[x][y] = -1; //add the obstacle to mapDis set as -1(the obs value).
					}
					else
						//It sets the corresponding cell in mapDis to -2(empty cell).
						mapDis[x][y]=-2;
				}
			}
			//Make a queue,like the fill method:
			Queue<Pixel2D> thor = new LinkedList<>();
			thor.offer(temporaryPoint);
			//Checking what is goes to be if the queue is not empty:
			while (!thor.isEmpty()) {
				//It retrieves and removes the head of the queue, assigning it to current:
				Pixel2D current = thor.poll();
				int currentX = current.getX();
				int currentY = current.getY();
				int currentDis = mapDis[currentX][currentY];
				
				//Obtains the list of neighbors of the current pixel using the getNeighbors method:
				List<Pixel2D> neighbors = getNeighbors(current, this.isCyclic());
				//If the map is not cyclic, it iterates over each neighbor in the neighbors list:
				if(!this.isCyclic()) {
					for (Pixel2D neighbor : neighbors) {
						int neighborX = neighbor.getX();
						int neighborY = neighbor.getY();
						//1.Checking if the neighbor is in the limits of the map.
						//2.the cell in the mapDis is cell that not visited in the past:
						if (isInside(neighbor) && mapDis[neighborX][neighborY] == -2) {
							mapDis[neighborX][neighborY] = currentDis + 1;
							//Add the neighbor to the queue:
							thor.offer(neighbor);
						}
					}
				}
				//If the map is cyclic:
				if(this.isCyclic()) {
					for (Pixel2D neighbor : neighbors) {
						//For each neighbor, it creates a new Index2D object by calling the Call method:
						neighbor = new Index2D(((Index2D) neighbor).Call(neighbor,getWidth(),getHeight()));
						int neighborX = neighbor.getX();
						int neighborY = neighbor.getY();
						//Like  before:
						if (mapDis[neighborX][neighborY] == -2) {
							mapDis[neighborX][neighborY] = currentDis + 1;
							thor.offer(neighbor);
						}
					}	
				}
			}
		}
		result = new Map(mapDis);

		return result;//Return the final allDistance.
	}
	//Function that calculate of the array is ragged:
	public static boolean raggedArray(int[][]arr) {
		int w=arr.length;
		for(int i=0;i<arr.length;i++) {
			if(arr[i].length != w) {
				return true;
			}
		}
		return false;
	}
	//Function that check and returning the valid neighboring pixels of a given current pixel:
	Pixel2D[] getValidNeighbors(Pixel2D current, int obsColor) {
		//The coordinates of the current pixel:
		int x = current.getX();
		int y = current.getY();
		List<Pixel2D> validNeighbors = new ArrayList<>();//Empty list to store  the valid neighboring pixels.

		// Check the left neighbor
		Pixel2D leftNeighbor = new Index2D((x - 1 + getWidth()) % getWidth(), y);
		if (isValidNeighbor(leftNeighbor, obsColor)) {
			validNeighbors.add(leftNeighbor);
		}

		// Check the right neighbor
		Pixel2D rightNeighbor = new Index2D((x + 1) % getWidth(), y);
		if (isValidNeighbor(rightNeighbor, obsColor)) {
			validNeighbors.add(rightNeighbor);
		}

		// Check the top neighbor
		Pixel2D topNeighbor = new Index2D(x, (y - 1 + getHeight()) % getHeight());
		if (isValidNeighbor(topNeighbor, obsColor)) {
			validNeighbors.add(topNeighbor);
		}

		// Check the bottom neighbor
		Pixel2D bottomNeighbor = new Index2D(x, (y + 1) % getHeight());
		if (isValidNeighbor(bottomNeighbor, obsColor)) {
			validNeighbors.add(bottomNeighbor);
		}

		return validNeighbors.toArray(new Pixel2D[0]);
	}

	public boolean isValidNeighbor(Pixel2D neighbor, int obsColor) {
		int neighborColor = getPixel(neighbor);
		return neighborColor != obsColor;
	}

	public List<Pixel2D> getNeighbors(Pixel2D pixel, boolean cyclicFlag) {
		int x = pixel.getX();
		int y = pixel.getY();

		List<Pixel2D> neighbors = new ArrayList<>();
		neighbors.add(new Index2D(x, y + 1)); // Add the pixel to the right
		neighbors.add(new Index2D(x, y - 1)); // Add the pixel to the left
		neighbors.add(new Index2D(x + 1, y)); // Add the pixel below
		neighbors.add(new Index2D(x - 1, y)); // Add the pixel above

		// Check if the neighbors are out of bounds
		for (int i = 0; i < neighbors.size(); i++) {
			Pixel2D neighbor = neighbors.get(i);

			// If the neighbor is outside the map and cyclicFlag is true, wrap around to the opposite side of the map
			if (!isInside(neighbor) && cyclicFlag) {
				neighbor = new Index2D(((Index2D) pixel).Call(neighbor, this.getWidth(), this.getHeight()));
				neighbors.set(i, neighbor);
			}
			// If the neighbor is outside the map and cyclicFlag is false, remove the neighbor from the list
			else if (!isInside(neighbor) && !cyclicFlag) {
				neighbors.remove(i);
				i--;
			}
		}
		return neighbors;
	}
}
