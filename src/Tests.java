 

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;



class Tests {
	@Test
	/**
	 * Tests the init2  function
	 */ 
	public void testInit2() {
		//Test 1:
		int[][]arr1= {{1,2,3},{4,5,6},{7,8,9}}; 
		Map map1 = new Map(arr1);
		map1.init(arr1);  //Making the deep copy.
		assertArrayEquals(arr1,map1.getMap());

		//Test 2:
		int[][]arr2= {{22,21,20},{19,18,17}};
		Map map2=new Map(arr2);
		map1.init(arr2);  //Making the deep copy.
		assertArrayEquals(arr2,map2.getMap());

	}

	@Test 
	/**
	 * Tests the init1 function
	 */
	public void testInit1() {
		//Test1:
		int w1 = 3; 
		int h1 = 2;
		int v1 = 6;
		int[][] arr1 = {{6, 6, 6}, {6, 6, 6}};
		Map map1 = new Map(arr1);
		map1.init(w1, h1, v1);
		assertArrayEquals(arr1, map1.getMap());
		//Test 2:
		int w2=4;
		int h2=4;
		int v2=2;
		int[][]arr2= {{2,2,2,2},{2,2,2,2},{2,2,2,2},{2,2,2,2}};
		Map map2=new Map(arr2);
		map2.init( w2,h2,v2);
		assertArrayEquals(arr2,map2.getMap());
	}
	@Test
	/**
	 * 
	 *Tests the getMap function
	 */
	public void testGetMap(){
		//Test 1:
		int[][]array2= {{2,4,6},{8,10,12},{14,16,18}};
		Map map=new Map(array2);
		int[][]ansMap=map.getMap(); //Checking if the map is equal to the array2.
		assertArrayEquals(array2,ansMap);
		//Test 2:
		int array1[][]=new int[40][40];//Creating a 2D empty array.
		for(int i=0;i<40;i++) {
			for(int j=0;j<40;j++) { //Fills the places with an known value.
				array1[i][j]=-5;
			}
		}
		Map map2=new Map(array1);
		int[][]ansMap2=map2.getMap();//Checking if the map is equal to the array1.
		assertArrayEquals(array1,ansMap2);


	}

	@Test
	/**
	 * Tests the distance function:
	 */
	public void testDistance2D() {


		// create two pixel objects
		Index2D p1 = new Index2D(0, 0);
		Index2D p2 = new Index2D(3, 4);

		// test distance between two pixels
		double ansDistance = 5.0; //
		double theDistance = p1.distance2D(p2);
		assertEquals(ansDistance, theDistance);

	} 
	@Test
	/**
	 * Tests the getWidht function:
	 */
	public void testGetWidth() {
		// Test 1:
		int[][] array = {{1,2,3},{4,5,6},{7,8,9}};
		Map map1 = new Map(array);
		assertEquals(3, map1.getWidth());

		//Test 2:
		int[][]array2= {{2,4,6,8},{5,7,9,0}};
		Map map2 = new Map(array2);
		assertEquals(2, map2.getWidth());


	}
	@Test
	/**
	 * Tests the getHigh function:
	 */
	public void testGetHigh() {
		//Test:
		int[][]arr= {{2,3,4},{4,5,6}};
		Map map=new Map(arr);
		assertEquals(3,map.getHeight());
	}
	@Test
	/**
	 * Tests the getPixel function:
	 */
	public void testGetPixel() {
		//Test 1:
		int[][]arr= {{1,2,3},{4,5,6},{7,8,9}};
		Map map=new Map(arr);
		assertEquals(2,map.getPixel(0, 1));
		assertEquals(9,map.getPixel(2, 2));
		assertEquals(4,map.getPixel(1, 0));

	}
	@Test
	/**
	 * Tests the setPixel function:
	 */
	public void testSetPixel() {
		//Test 1:
		int[][]arr={{2,3,45,5},{4,53,65,3},{4,5,76,2},{2,4,5,6}};
		Map map=new Map(arr);
		map.setPixel(1, 2, 55);
		assertEquals(55,map.getPixel(1, 2));
		map.setPixel(1, 3, 50);
		assertEquals(50,map.getPixel(1, 3));
	}
	@Test
	/**
	 * Tests the isInside fucntion:
	 */
	public void testIsInside() {
		int[][]arr= {{1,2,3},{4,5,6},{7,8,9}};
		Map map=new Map(arr);
		//Test 1:
		Pixel2D pixel=new Index2D(0,2);
		boolean result1 =map.isInside(pixel);
		assertTrue(result1);
		//Test 2:
		Pixel2D pixel2=new Index2D(5,5);
		boolean result2=map.isInside(pixel2);
		assertFalse(result2);
	}
	@Test
	/**
	 * Tests the isCyclic function:
	 */
	public void testIsCyclic() {
		Map map=new Map(2,2,0);
		assertEquals(true, map.isCyclic());
	}
	@Test
	public void testFill() {
		// Create a map
		int[][] arr = {
				{1, 1, 1, 1,1},
				{1, 0, 0,0, 1},
				{1, 0, 1, 0,1},
				{1, 0, 0,0, 1},

		};
		Map map = new Map(arr);

		// Fill the map
		Pixel2D xy = new Index2D(1, 1); // Starting pixel
		int newColor = 2; // New color value
		int filledPixels = map.fill(xy, newColor);

		// Verify the filled pixels
		assertEquals(8, filledPixels); // Expected number of filled pixels

		// Verify the updated map
		int[][] expectedMap = {
				{1,1,1,1,1},
				{1,2,2,2,1} ,
				{1,2,1,2,1},
				{1,2,2,2,1},
				{1,1,1,1,1}  
		};
	}

	@Test
	/**
	 * Test the allDistance function:
	 */
	public void testAllDistance() {
	
		int[][] arr = { { 1, 0 }, { 1, 1 }, };
		Pixel2D p1 = new Index2D(0, 0);
		Pixel2D p2 = new Index2D(1, 0);
		Pixel2D p3 = new Index2D(0, 1);
		Pixel2D p4 = new Index2D(1, 1);

		Map map1 = new Map(arr);
		Map2D distances = map1.allDistance(p1, 0);
		map1.shortestPath(p1, p2, 0);
		map1.shortestPath(p1, p3, 0);
		map1.shortestPath(p1, p4, 0);

		assertEquals(0, distances.getPixel(p1.getX(), p1.getY()));
		assertEquals(1, distances.getPixel(p2.getX(), p2.getY()));
		assertEquals(-1, distances.getPixel(p3.getX(), p3.getY()));
		assertEquals(2, distances.getPixel(p4.getX(), p4.getY()));


	}
	/**
	 * Tests the shortesttPath function: 
	 */
	@Test
	public void testShortestPath() {
		//Test 1:
		int[][] arrMap = {
				{0, 0, 0},
				{0, 1, 0},
				{0, 0, 0}
		};
		Map map = new Map(arrMap);
		assertNotNull(map.getMap()); // Check if the map is not null

		Pixel2D p1 = new Index2D(0, 0); // Starting pixel
		Pixel2D p2 = new Index2D(2, 2); // Target pixel
		int obstacleColor = 1; // Color value representing obstacles

		Pixel2D[] path = map.shortestPath(p1, p2, obstacleColor);

		// Check if the computed path is not null and contains at least one element
		assertNotNull(path);
		assertTrue(path.length > 0);

		// Define the expected number of steps
		int numOfSteps = 3;

		// Check if the number of steps in the computed path matches the expected number of steps
		assertEquals(numOfSteps, path.length);
		//tests for 10*10 map.
		int[][] mapHuge = {
				{0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
				{1, -1, -1, -1, 2, 0, 0, 0, 0, 0},
				{0, -1, -5, -1, 0, 0, 0, 0, 0, 0},
				{0, -1, -1, -1, 0, 0, 0, 0, 0, 0},
				{0, 2, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
		};
		Map2D map100 = new Map(mapHuge);
		Map2D map100Cyclic = new Map(mapHuge);
		map100.setCyclic(true);
		map100Cyclic.setCyclic(false);
		Index2D p100Start = new Index2D(0, 0);
		Index2D p100End = new Index2D(9, 9);

		Pixel2D[] resHugeMap1 = map100.shortestPath(p100Start, p100End, -1);
		Pixel2D[] resHugeMap2 = map100Cyclic.shortestPath(p100Start, p100End, -1);
		// Expected points for the first huge map (cyclic)
		Index2D[] expected1 = {
				new Index2D(0, 0),
				new Index2D(9, 0),
				new Index2D(9, 9)
		};
		// Expected points for the second huge map (non-cyclic)
		Index2D[] expected2 = {
				new Index2D(0, 0),   new Index2D(0, 0), new Index2D(0, 0),  new Index2D(0, 0),
				new Index2D(0, 0),  new Index2D(0, 0), new Index2D(0, 0),new Index2D(0, 0),
				new Index2D(0, 0), new Index2D(0, 0),  new Index2D(0, 0), new Index2D(0, 0),
				new Index2D(0, 0),new Index2D(0, 0),new Index2D(0, 0),new Index2D(0, 0),
				new Index2D(0, 0), new Index2D(9, 0), new Index2D(9, 9)
		};
		// Assertions for the first huge map (cyclic)
		for (int i = 0; i < resHugeMap1.length; i++) {
			assertEquals(resHugeMap1[i], expected1[i]);
		}
		// Assertions for the second huge map (non-cyclic)
		for (int i = 0; i < resHugeMap2.length; i++) {
			assertEquals(resHugeMap2[i], expected2[i]);
		}


	}
	@Test
	public void testGetValidNeighbors() {
		int[][] arrMap = {
				{0, 0, 0},
				{0, 1, 0},
				{0, 0, 0}
		};
		Map map=new Map(arrMap);
		Pixel2D c=new Index2D(1,1); //The current pixel(that is the center of the map).
		int obstacleColor = 1; // Color value representing obstacles in the map.

		Pixel2D[] validNeighbors = map.getValidNeighbors(c, obstacleColor);

		// Define the expected valid neighbors
		Pixel2D[] expectedNeighbors = {
				new Index2D(0, 1), // Left neighbor
				new Index2D(2, 1), // Right neighbor
				new Index2D(1, 0), // Top neighbor
				new Index2D(1, 2)  // Bottom neighbor

		};
		// Check if the computed valid neighbors match the expected valid neighbors
		assertArrayEquals(expectedNeighbors, validNeighbors);

	}
	@Test
	public void testIsValidNeighbor() {
		int[][] arrMap = {
				{0, 0, 0},
				{0, 1, 0},
				{0, 0, 0}
		};
		Map map = new Map(arrMap);

		Pixel2D neighbor = new Index2D(1, 1); // Neighbor pixel with color 0
		int obstacleColor = 1; // Color value representing obstacles

		boolean isValid = map.isValidNeighbor(neighbor, obstacleColor);

		assertFalse(isValid); // The neighbor pixel color is not the obstacle color
	}
	/*
	 * Tests the RaggedArray function:
	 */
	@Test
	void testRaggedArray() {
		int[][] arr1 = {
				{1, 2, 3},
				{4, 5},
				{6, 7, 8, 9}
		};
		assertTrue(Map.raggedArray(arr1)); // The array is ragged

		int[][] arr2 = {
				{1, 2, 3},
				{4, 5, 6},
				{7, 8, 9}
		};
		assertFalse(Map.raggedArray(arr2)); // The array is not ragged

		int[][] arr3 = {
				{},
				{},
				{}
		};
		assertTrue(Map.raggedArray(arr3)); // The array is not ragged
	}
	/*
	 * Tests the comparePixels function:
	 */
	@Test
	public void testComparePixels() {
		Pixel2D p1=new Index2D(5,5);
		Pixel2D p2=new Index2D(5,5);
		Pixel2D p3=new Index2D(5,56);
		
		assertFalse(Ex3Algo.comparePixels(p2, p3));
		assertTrue(Ex3Algo.comparePixels(p2, p1));


	}
	
}









