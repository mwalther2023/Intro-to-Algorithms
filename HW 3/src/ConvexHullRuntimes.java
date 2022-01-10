import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class ConvexHullRuntimes {

	public static void main(String[] args) {
		
		Random r = new Random();
//		int n = (int)(Math.random()*100);
		int n = Math.abs(r.nextInt(25));
//		int[][] val = new int[10][Math.abs(n)];
//		Point[][] val = new Point[10][Math.abs(n)];
		ArrayList<ArrayList<Point>> set = new ArrayList<ArrayList<Point>>();
		ArrayList<Point> val = new ArrayList<Point>();
		
		System.out.println("N: "+n);
		System.out.println("Brute\tQuick");
		for(int i = 0; i<10; i++) {
			if(i >= 1) {
				n += Math.abs(r.nextInt(25));
//				System.out.println(n);
			}
			for(int j = 0; j<n; j++) {
//				val[i][j] = new Point(r.nextInt(),r.nextInt());
				val.add(new Point(r.nextInt(),r.nextInt()));
			}
			set.add(val);
			val = new ArrayList<Point>();
		}
		
		
		for(int j = 0; j<10; j++) {
			ArrayList<Point> temp = set.get(j);
			long startTime = System.currentTimeMillis();
			for(int i=0; i < 100000; i++){
				bruteHull(temp);
			}
			long endTime = System.currentTimeMillis();
			long startTime2 = System.currentTimeMillis();
			for(int i=0; i < 100000; i++){
				quickHull(temp);
			}
			long endTime2 = System.currentTimeMillis();
			System.out.println(((endTime-startTime)+"\t"+(endTime2-startTime2)));
		}
	}
	public static ArrayList<Point> quickHull(ArrayList<Point> points) {
        ArrayList<Point> convexHull = new ArrayList<Point>();
        if (points.size() < 3) {
        	convexHull = points;
        	return convexHull;
        }
        int minPoint = 0;
        int maxPoint = 0;
        int minX = points.get(0).x;
        int maxX = points.get(0).x;
        for (int i = 0; i < points.size(); i++) {
            if (points.get(i).x <= minX) {
                minX = points.get(i).x;
                minPoint = i;
            }
            if (points.get(i).x >= maxX) {
                maxX = points.get(i).x;
                maxPoint = i;
            }
        }
        Point A = points.get(minPoint);
        Point B = points.get(maxPoint);
        convexHull.add(A);
        convexHull.add(B);
        points.remove(A);
        points.remove(B);
 
        ArrayList<Point> lowerSet = new ArrayList<Point>();
        ArrayList<Point> upperSet = new ArrayList<Point>();
 
        for (int i = 0; i < points.size(); i++) {
            Point p = points.get(i);
            if (pointLocation(A, B, p) == -1)
            	lowerSet.add(p);
            else if (pointLocation(A, B, p) == 1)
            	upperSet.add(p);
        }
        hullSet(A, B, upperSet, convexHull);
        hullSet(B, A, lowerSet, convexHull);
 
        return convexHull;
    }
	public static int distance(Point A, Point B, Point C)
    {
        int ABx = B.x - A.x;
        int ABy = B.y - A.y;
        
        int num = Math.abs(ABx * (A.y - C.y) - ABy * (A.x - C.x));
        
        return num;
    }
	public static int pointLocation(Point A, Point B, Point P)
    {
        int cp1 = (B.x - A.x) * (P.y - A.y) - (B.y - A.y) * (P.x - A.x);
        if (cp1 > 0)
            return 1;
        else if (cp1 == 0)
            return 0;
        else
            return -1;
    }
	 public static void hullSet(Point A, Point B, ArrayList<Point> set, ArrayList<Point> hull) {
	        int insertPosition = hull.indexOf(B);
	        if (set.size() == 0)
	            return;
	        if (set.size() == 1) {
	            Point p = set.get(0);
	            set.remove(p);
	            hull.add(insertPosition, p);
	            return;
	        }
	        int dist = Integer.MIN_VALUE;
	        int furthestPoint = -1;
	        for (int i = 0; i < set.size(); i++)
	        {
	            Point p = set.get(i);
	            int distance = distance(A, B, p);
	            if (distance > dist)
	            {
	                dist = distance;
	                furthestPoint = i;
	            }
	        }
	        Point P = set.get(furthestPoint);
	        set.remove(furthestPoint);
	        hull.add(insertPosition, P);
	        
	        ArrayList<Point> leftSetAP = new ArrayList<Point>();
	        for (int i = 0; i < set.size(); i++)
	        {
	            Point M = set.get(i);
	            if (pointLocation(A, P, M) == 1)
	            {
	                leftSetAP.add(M);
	            }
	        }
	 
	        ArrayList<Point> leftSetPB = new ArrayList<Point>();
	        for (int i = 0; i < set.size(); i++)
	        {
	            Point M = set.get(i);
	            if (pointLocation(P, B, M) == 1)
	            {
	                leftSetPB.add(M);
	            }
	        }
	        hullSet(A, P, leftSetAP, hull);
	        hullSet(P, B, leftSetPB, hull);
	 
	    }
	public static ArrayList<Point> bruteHull(ArrayList<Point> S) {
		ArrayList<Point> CH = new ArrayList<Point>();
		for(int i = 0; i<S.size(); i++) {
				for(int j = 0; j<S.size(); j++) {
					if(!S.get(j).equals(S.get(i))) {
						int LeftTurnCount = 0;
						for(int k = 0; k<S.size(); k++) {
							if(!S.get(k).equals(S.get(i)) || !S.get(k).equals(S.get(j))) {
								if(LeftTurn(S.get(i),S.get(j),S.get(k))) {
									LeftTurnCount++;
								}
							}
						}
						
						if(LeftTurnCount == 0 || LeftTurnCount == S.size()-2) {
							if(!CH.contains(S.get(i))) {
								CH.add(S.get(i));
							}
							if(!CH.contains(S.get(j))) {
								CH.add(S.get(j));
							}
						}
						
					}
				}
		}
		
		return CH;
	}
	public static boolean LeftTurn(Point a, Point b, Point i) {
		
		Point vab = new Point();
		Point vbi = new Point();
		
		vab.x = b.x - a.x;
		vab.y = b.y - a.y;
		
		vbi.x = i.x - b.x;
		vbi.y = i.y - b.y;
		int crossProduct = (vab.x*vbi.y)-(vab.y*vbi.x);
		return crossProduct>0;
	}
}
