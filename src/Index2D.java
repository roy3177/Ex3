

public class Index2D implements Pixel2D{
    private int _x, _y;
    public Index2D() {this(0,0);}
    public Index2D(int x, int y) {_x=x;_y=y;}
    public Index2D(Pixel2D t) {this(t.getX(), t.getY());}
    @Override
    public int getX() {
        return _x; 
    } 
    @Override
    public int getY() { 
        return _y;
    }
    //Computes the 2D distance between the pixel to the pixel2D t:
    public double distance2D(Pixel2D t) {
        if(t==null) {
            throw new RuntimeException("t is null");
        }
        	
        // Calculates the differences in the x and y coordinates between the current pixel  and the Pixel2D t.
       	double disX= this.getX()-t.getX(); 
       	double disY=this.getY()-t.getY();  
        	
       	double ans=	Math.sqrt((disY*disY)+(disX*disX));
        
       	return ans;
        
        
    }
    @Override
    public String toString() {
        return getX()+","+getY();
    }
    @Override
    /*
     * This function implements equivalence relation non-null object references,it's checking:
     * Reflexive:for any non-null reference value x,x.equals(x),should return true.
     * Symmetric:for any non-null reference value x and y,x.equals(y) return true if also y.equals(x).
     * Transitive:for any non-null reference value x and y and z,x.equals(y) and y.equals(z) return true if also x.equals(z).
     * For any non-null reference value x,x.equals(null) should return false.
     */
    
    public boolean equals(Object t) {
        boolean ans = false;
        if(t!=null &&  t instanceof Pixel2D) {
        	Pixel2D p=(Pixel2D)t;
        	ans=this.distance2D(p)==0;
        }
        return ans;
    }
    /*
     *This method is to handle cases where the x or y coordinates of P1
     * are outside the boundaries defined by the width and height parameters.
     */
    public Pixel2D Call(Pixel2D P1,int width , int height) { 
		if(P1.getX()<0) {
	  		P1 = new Index2D(width-1,P1.getY());
		}
		else if (P1.getX()==width) {
			P1 = new Index2D(0,P1.getY());
		}
		else if (P1.getY()<0) {
			P1 = new Index2D(P1.getX(),height-1);
		}
		else if(P1.getY()==height) {
			P1 = new Index2D(P1.getX(),0);
		}
		return P1;
	}
   
	
}
