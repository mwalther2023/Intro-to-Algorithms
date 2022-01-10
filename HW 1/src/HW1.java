import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class HW1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] n = new int[100];
		for(int i = 1; i<101;i++) {
			n[i-1] = i;
		}
		int[] num = new int[100];
		for(int i = 0; i<100; i++) {
			num[i] = 4900+i;
		}
		int[] fib = new int[42];
		File f = new File("fib.txt");
		Scanner scan;
		try {
			scan = new Scanner(f);
			for(int i = 0; i<36;i++) {
				fib[i] = scan.nextInt();
			}
			scan.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Question 1");
		for(int x = 0; x<100; x++) {
			long startTime = System.currentTimeMillis();
			for(int i=0; i < 1000; i++){
				Q1aN(n,7,x+4500);
//				Q2aInt(fib[x],fib[x+1]);
			}
			long endTime = System.currentTimeMillis();
			long startTime2 = System.currentTimeMillis();
			for(int i=0; i < 1000; i++){
				Q1aH(n,7,x+4500);
//				Q2aEuc(fib[x],fib[x+1]);
			}
			long endTime2 = System.currentTimeMillis();
			System.out.print((endTime-startTime)/1000.0);
			System.out.print("\t"+(endTime2-startTime2)/1000.0+"\n");
		}
		System.out.println("Question 2");
		for(int x = 0; x<35;x++) {
			long startTime = System.currentTimeMillis();
			for(int i=0; i < 1000; i++){
				Q2aInt(fib[x],fib[x+1]);
			}
			long endTime = System.currentTimeMillis();
			long startTime2 = System.currentTimeMillis();
			for(int i=0; i < 1000; i++){
				Q2aEuc(fib[x],fib[x+1]);
			}
			long endTime2 = System.currentTimeMillis();
			System.out.print((endTime-startTime)/1000.0);
			System.out.print("\t"+(endTime2-startTime2)/1000.0+"\n");
		}
		
		System.out.println("Question test cases: ");
		int[] t = new int[2];
		t[0] = 2;
		t[1] = 2;
		System.out.println(Q1aN(t,3,2));
		System.out.println(Q1aH(t,3,2));
		
		System.out.println(Q2aInt(60,24));
		System.out.println(Q2aEuc(60,24));
		
		System.out.println(Q3a(2,10));
		System.out.println(Q3b(2,10));
	}
	public static int Q1aN(int[] a, int x, int n) {
		int out = 0;
		int y = 1;
		for(int i = 0; i<n; i++) {
			for(int j = 0; j<i;j++) {
				y*=x;
			}
//			if(i>0) {
//				y *=x;
//				out += a*y;
//			}
//			else if(i ==0) {
//				out += a;
//			}

//			System.out.println("Test: "+y);
//			out += a[i]*y;
			out += a[0]*y;
			y = 1;
		}
		return out;
	}
	public static int Q1aH(int[] a, int x, int n) {
		int out = a[0];
		for(int i = 1; i<n; i++) {
			out = out*x + a[0];
		}
//		if(n == 0) {
//			return a[0];
//		}
//		else {
//			out += x*(Q1aH(a,x,n-1));
//		}
		
		return out;
	}
	
	public static int Q2aInt(int m, int n) {
		int t = Math.min(m, n);
		for(int i = t; i>0;i--) {
			if(m%i == 0 && n%i == 0) {
				return i;
			}
		}
		return 1;
	}
	public static int Q2aEuc(int m, int n) {
		if(n == 0) {
			return m;
		}
		int r = m%n;
		return Q2aEuc(n,r);
	}

	public static int Q3a(int x, int n) {
		int out = 1;
		for(int i = 0; i<n;i++) {
			out *= x;
		}
		
		return out;
	}
	public static int Q3b(int x, int n) {
		int out = 0;
		if(n%2 == 0) {
			out = x * Q3b(x,n-1);
		}
		else if(n>1 && n%2 == 1) {
			out = x * Q3b(x,n-1);
		}
		else {
			return x;
		}
		return out;
	}

}
