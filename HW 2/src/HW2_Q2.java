import java.util.Random;

public class HW2_Q2 {
	public static void main(String[] args) {
		Random r = new Random();
		int n = (int)(Math.random()*100);
		int[][] val = new int[10][Math.abs(n)];
		
		
		System.out.println("N: "+n);
		
		for(int i = 0; i<10; i++) {
			for(int j = 0; j<n; j++) {
				val[i][j] = r.nextInt();
			}
		}

		for(int j = 0; j<10; j++) {
			long startTime = System.currentTimeMillis();
			for(int i=0; i < 100000000; i++){ //10000000
				Insertion(val[j]);
//				MergeSort(val[j],0,n-1);
			}
			long endTime = System.currentTimeMillis();
			System.out.println(((endTime-startTime)));
		}

	}
	public static int[] Insertion(int[] a){
		int key = 0;
		for(int i = 1; i<a.length;i++) {
			key = a[i];
			int j = i-1;
			
			while(j>=0 && a[j]>key) {
				a[j+1] = a[j];
				j -= 1;
			}
			a[j+1] = key;
		}
		return a;
	}
	public static int[] Merge(int[] arr,int p, int q, int r){
		int n1 = q-p+1;
		int n2 = r-q;
		int[] L = new int[n1];
		int[] R = new int[n2];
		
		for(int i = 0; i<n1; i++) {
			L[i] = arr[p+i];
		}
		for(int j = 0; j<n2; j++) {
			R[j] = arr[q+j+1];
		}
		int i = 0;
		int j = 0;
		
		int k = p;
		while(i<n1 && j<n2) {
//		for(int k = p; k<r; k++) {
			if(L[i] <= R[j]) {
				arr[k] = L[i];
				i++;
			}
			else {
				arr[k] = R[j];
				j++;
			}
			k++;
		}
		while (i < n1) {
	        arr[k] = L[i];
	        i++;
	        k++;
	    }
		while (j < n2) {
	        arr[k] = R[j];
	        j++;
	        k++;
	    }
		return arr;
	}
	public static int[] MergeSort(int[] arr, int p, int r) {
		if(p == r) {
			return arr;
		}
		else {
			int q = (p+r)/2;
			MergeSort(arr,p,q);
			MergeSort(arr,q+1,r);
			Merge(arr,p,q,r);
		}
		return arr;
	}
}
