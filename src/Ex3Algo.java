import java.awt.*;

import exe.ex3.game.Game;
import exe.ex3.game.GhostCL;
import exe.ex3.game.PacManAlgo;
import exe.ex3.game.PacmanGame;

/**
 * This is the major algorithmic class for Ex3 - the PacMan game:
 *
 * This code is a very simple example (random-walk algorithm).
 * Your task is to implement (here) your PacMan algorithm.
 */
public class Ex3Algo implements PacManAlgo{
	private int _count;
	public Ex3Algo() {_count=0;}
	@Override
	/**
	 *  Add a short description for the algorithm as a String.
	 */
	public String getInfo() {
		return null;
	}
	@Override
	/**
	 * This ia the main method - that you should design, implement and test.
	 */
	/**********************207302340****************************
	 *In this Ex3Algo class,which is implements the PacManAlgo interface,
	 *which defines the algorithm for controlling the PacMan game.
	 *The move method is the main algorithmic method that determines the next move for PacMan.
	 *The algorithm begins by checking the current position of the game and looks for relevant
	 *information such as the game board, PacMan's position,and the positions and statuses of the ghosts.
	 *It then performs certain actions based on specific conditions,for example, if a ghost is edible and
	 *close to PacMan,the algorithm tries to move PacMan towards the ghost to eat it.
	 *If there is no specific condition met, the algorithm generates a random direction for PacMan to move. 
	 *The algorithm also includes helper methods for printing the game board and ghost information,
	 *converting positions from strings to coordinates, generating a random direction for PacMan,
	 *finding the closest food pixel, determining the direction from one position to another, 
	 *comparing pixels, and calculating the direction for PacMan to move towards eating ghosts. 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	
	
	
	
	public int move(PacmanGame game) {
		if(_count==0 || _count==300) {
			int code = 0;
			int[][] board = game.getGame(0);
			printBoard(board);
			int blue = Game.getIntColor(Color.BLUE, code);
			int pink = Game.getIntColor(Color.PINK, code);
			int black = Game.getIntColor(Color.BLACK, code);
			int green = Game.getIntColor(Color.GREEN, code);
			System.out.println("Blue=" + blue + ", Pink=" + pink + ", Black=" + black + ", Green=" + green);
			String pos = game.getPos(code).toString();
			System.out.println("Pacman coordinate: "+pos);
			GhostCL[] ghosts = game.getGhosts(code);
			printGhosts(ghosts);
	 		int up = Game.UP, left = Game.LEFT, down = Game.DOWN, right = Game.RIGHT;
			//Using the method of taking the string to index.
			Index2D pacmanPos = fromStringToIndex(pos);
			
			Map map=new Map(board); 
			//Checks if the first ghost is currently edible and active:
			//The remainTimeAsEatable(0) method returns the
			//remaining time for which the ghost can be eaten
			if(ghosts[0].remainTimeAsEatable(0)>0.5 &&ghosts[0].getStatus() == 1) {
				if (ghosts[0].remainTimeAsEatable(0)<0.2){
					return game.DOWN;
				}
				//Checks if the pixel on the game board at Pacman's
				//current position has the color green:
				if (board[pacmanPos.getY()][pacmanPos.getX()] == green) {
					return eatingGhosts(map,pacmanPos);
				}
				
			}
		}
		
		_count++;
		int dir = randomDir(game);
		
		return dir;
	}
	/*
	 * This function is responsible for printing the board
	 */
	private static void printBoard(int[][] b) {
		for(int y =0;y<b[0].length;y++){
			for(int x =0;x<b.length;x++){
				int v = b[x][y];
				System.out.print(v+"\t");
			}
			System.out.println();
		}
	}
	/*
	 * This function prints information about the ghosts
	 */
	private static void printGhosts(GhostCL[] gs) {
		for(int i=0;i<gs.length;i++){
			GhostCL g = gs[i];
			System.out.println(i+") status: "+g.getStatus()+",  type: "+g.getType()+",  pos: "+g.getPos(0)+",  time: "+g.remainTimeAsEatable(0));
		}
	}
	/*
	 * This is a helper method that converts a string representation of a position
	 *  into an Index2D object. It splits the string using the comma (",") delimiter and 
	 *  parses the two substrings into integer values to create a new Index2D object.
	 */
	public static Index2D fromStringToIndex(String string) {
		Index2D ans = new Index2D();
		String[] splitting = string.split("\\,");//Using the split method.
		int currtX = Integer.parseInt(splitting[0]);
		int currY = Integer.parseInt(splitting[1]);
		ans = new Index2D(currtX, currY);
		return ans; //Return that as a Index2D.
	}
	/*
	 * This function generates a random direction for Pacman to move
	 * This function uses map analysis and pathfinding to select
	 *  a random direction for Pacman to move towards a nearby pink pixel.
	 */
	private static int randomDir(PacmanGame game) {

		int[][] board = game.getGame(0);
		 //Create a Map object using the game board.:
		Map map = new Map(board);

		String p = game.getPos(0);//Get the current position of Pacman as a string.
		String[] parts = p.split(",");//Using the split method.
		//Parse the elements into an integers:
		int x = Integer.parseInt(parts[0]);
		int y = Integer.parseInt(parts[1]);
		//Create a Pixel2D object representing the current position of Pacman:
		Pixel2D position = new Index2D(x,y);
		//Calculate the distance map from the current position using the allDistance method:
		Map2D allDisMap = map.allDistance(position, 1);
		//Finding the closest food pieces,using the foodPic method:
		Pixel2D pink = foodPic(position, map, allDisMap);
		//Using the shortestPath method to find the shortest path of 
		//the current position to the closest food pixel:
		Pixel2D[] path = map.shortestPath(position, pink, 1);
		int dir = findDir(position, path[1]); 
		return dir;

	}
	/*
	 *his function iterates over each pixel in the map and finds the
	 *closest food pixel to the given position pose. It uses the allDistanceMap
	 *to check the distance to each food pixel and updates the minimum distance
	 *and coordinates accordingly. Finally, it returns the coordinates
	 *of the closest food pixel.
	 */
	private static Pixel2D foodPic(Pixel2D pose, Map map, Map2D allDistanceMap){
		//Initialize the variable to the maximum possible integer value:
		int minDistanceToFood  = Integer.MAX_VALUE;
		Pixel2D closestFoodPixel = new Index2D(0,0);
		//Get the height and the width of the map and iterates in double loop on them:
		int width = map.getWidth();
		int heigth = map.getHeight();
		for(int i=0; i<width; i++){
			for(int j=0; j<heigth; j++){
				//Check if the current pixel(i,j) is a food pixel: 
				if( map.getPixel(i,j)== 3 || map.getPixel(i,j)== 5){
					
					if( allDistanceMap.getPixel(i,j) != -1){
						//: Get the distance value from allDistanceMap for
						//the current pixel and assign it to the variable currDist
						int currDist = allDistanceMap.getPixel(i,j);
						if (currDist < minDistanceToFood ){
							//Update the minimum distance to currDist:
							minDistanceToFood  = currDist;
							closestFoodPixel = new Index2D(i,j);
						}
				}} 
			}
		} 
		return closestFoodPixel;// Return the coordinates of the closest food pixel
	}
	/*
	 * The findDir function is used to determine the direction from a given position
	 *  to a specified destination in a 2D coordinate system.
	 */
	private static int findDir(Pixel2D pose, Pixel2D dest){
		//Checking coordinates if the x values are equals:
		if (pose.getX() == dest.getX()){
			if (pose.getY()+1 == dest.getY()){
				return Game.UP;
			}
			if (pose.getY()-1 == dest.getY()){
				return Game.DOWN;
			}
			if (pose.getY() > dest.getY()){
				return Game.UP;
			}
			if (pose.getY() < dest.getY()){
				return Game.DOWN;
			}
		}
		//Checking coordinates if the y values are equals:
		else if (pose.getY() == dest.getY()){
			if (pose.getX()+1 == dest.getX()){
				return Game.RIGHT;
			}
			if (pose.getX()-1 == dest.getX()){
				return Game.LEFT;
			}
			if (pose.getX() > dest.getX()){
				return Game.RIGHT;
			}
			if (pose.getX() < dest.getX()){
				return Game.LEFT;
			}
		}
		//The direction could not be determined:
		return -1;
	}
	/*
	 *  This function compares two Pixel2D objects and determines if they represent the same pixel:
	 */
	public static boolean comparePixels(Pixel2D pixelnum1, Pixel2D pixelnum2) {
		boolean result =false;
		if(pixelnum1.getX() == pixelnum2.getX() && pixelnum1.getY() == pixelnum2.getY()) {
			result = true;
		}
		return result;
	}
	/*
	 *This function determines the direction for Pacman to move in order to eat the ghosts.
	 *It takes a Map object and Pacman's current position
	 *this function calculates the next position (move) for Pacman to move towards
	 *in order to eat the ghosts.
	 *It takes into account the map topology (cyclic or not) and determines the
	 *direction for Pacman .
	 */
	public static int eatingGhosts(Map map1,Pixel2D pac) {
		//Create a new Pixel2D object,represents a specific position on the map:
		Pixel2D p=new Index2D(11,14);
		//Using the shortestPath method:
		Pixel2D[]pWay=map1.shortestPath(p, pac, 1);
		Pixel2D move=pWay[1];//The next position of moving towards.
		
		//Checking if the map is not cyclic:
		if(!map1.isCyclic()) {
			//Option 1:
			if(move.getX()==pac.getX()) {
				if(move.getY()<pac.getY()) {
					return Game.DOWN;
				}
				else {
					return Game.UP;
				}
			}
				//Option 2:
				else {
					if(move.getX()<pac.getX()) {
						return Game.LEFT;
					}
					else {
						return Game.RIGHT;
					}
				}
		}
		else {
			if(move.getX()==pac.getX()) {
				if(move.getY()<pac.getY()) {
					if(move.getY()==0 && pac.getY()==map1.getHeight()-1) {
						return Game.UP;
					}
					else {
						return Game.DOWN;
					}
				}
				else {
					if(move.getY()==map1.getHeight()-1 && pac.getY()==0) {
						return Game.DOWN;
					}
					else {
						return Game.UP;
					}
				}	
			}
			else {
				if(move.getX()<pac.getX()) {
					if(move.getX()==0 && pac.getX()==map1.getWidth()-1) {
						return Game.RIGHT;
					}
					else {
						return Game.LEFT;
					}
				}
				else {
					if(move.getX()==map1.getWidth()-1 &&pac.getX()==0) {
						return Game.LEFT;
					}
					else {
						return Game.RIGHT;
					}
				}
			}
		}			
					
					
				
			
		
	}
	 
		


}