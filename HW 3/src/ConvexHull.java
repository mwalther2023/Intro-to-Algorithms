import java.awt.Color;
import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import edu.princeton.cs.introcs.StdDraw;

public class ConvexHull {
	public static void main(String[] args) {
		
		StdDraw.setCanvasSize(700,700);
		StdDraw.setXscale(-1, 51);
		StdDraw.setYscale(-1, 51);
		StdDraw.setPenColor(StdDraw.RED);
		StdDraw.setPenRadius(0.005);
		
		Random r = new Random();
		int n = 50;
		ArrayList<Point> set = new ArrayList<Point>();
		for(int i = 0; i<n; i++) {
			set.add(new Point(r.nextInt(n), r.nextInt(n)));
			StdDraw.filledCircle(set.get(i).x, set.get(i).y, .5);
		}
		
		
		ArrayList<Point> brute = bruteHull(set);
		ArrayList<Point> quick = quickHull(set);
		
		//Draw QuickHull Points
		StdDraw.setPenColor(StdDraw.BLUE);
//		System.out.println("QuickHull: " + quick.size());
		for (int i = 0; i < quick.size(); i++) {
//            System.out.print("(" + quick.get(i).x + ", " + quick.get(i).y + ") | ");
            StdDraw.filledCircle(quick.get(i).x, quick.get(i).y, .5);
            if(i>0) {
            	StdDraw.line(quick.get(i).x, quick.get(i).y, quick.get(i-1).x, quick.get(i-1).y);
            }
		}
		StdDraw.line(quick.get(quick.size()-1).x, quick.get(quick.size()-1).y, quick.get(0).x, quick.get(0).y);
		
		
		//Draw BruteForce Points
		StdDraw.setPenColor(StdDraw.GREEN);
//		System.out.println("\nBruteHull: " + brute.size());
		for (int i = 0; i < brute.size(); i++) {
//            System.out.print("(" + brute.get(i).x + ", " + brute.get(i).y + ") | ");
            StdDraw.filledCircle(brute.get(i).x, brute.get(i).y, .5);
            if(i>0) {
//            	StdDraw.line(brute.get(i).x, brute.get(i).y, brute.get(i-1).x, brute.get(i-1).y);
            }
		}
//		StdDraw.line(brute.get(brute.size()-1).x, brute.get(brute.size()-1).y, brute.get(0).x, brute.get(0).y);
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
