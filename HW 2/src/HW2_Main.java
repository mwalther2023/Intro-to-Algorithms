import java.util.Random;

public class HW2_Main {
	public static void main(String[] args) {
		int[] arr = new int[20];
		Random r = new Random();
		for(int i = 0; i<20; i++) {
			arr[i] = r.nextInt();
		}
		
		for(int i =0; i<arr.length;i++) {
			System.out.print(arr[i] +" ");
		}	
		System.out.print("\n");
		int[] arr2 = arr;
		
		System.out.println("Insertion: ");
		Insertion(arr);
		for(int i =0; i<arr.length;i++) {
			System.out.print(arr[i] +" ");
		}
		System.out.print("\n");
		
		
		System.out.println("MergeSort: ");
		MergeSort(arr2,0,arr2.length-1);
		for(int i =0; i<arr2.length;i++) {
			System.out.print(arr2[i] +" ");
		}
		System.out.print("\n");
		
		
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
