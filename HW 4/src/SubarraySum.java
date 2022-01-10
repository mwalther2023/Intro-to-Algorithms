
public class SubarraySum {
	public static void main(String[] args) {
		int[] arr = new int[25];
		for(int i = 0; i<25; i++) {
			arr[i] = (int)(Math.random()*20);
		}
		
		int[] b = brute(arr);
		int[] d = maxSubArraySum(arr,0,arr.length-1);
		int[] k = Kadane(arr);
		System.out.println("Brute: ("+b[0]+", "+b[1]+") = "+b[2]);
		System.out.println("Divide & Conquer: ("+d[0]+", "+d[1]+") = "+d[2]);
		System.out.println("Kadane: ("+k[0]+", "+k[1]+") = "+k[2]);
	}
	
	public static int[] brute(int[] arr) {
		int max = 0;
		int start = 0;
		int end = 0;
		int[] out = new int[3];
		
		int n = arr.length;
		for(int i = 0; i < n; i++) {
		    int sum = 0;
		    for (int j = i; j < n; j++) {
		        sum += arr[j];
		        if (sum > max) {
		            max = sum;
		        	start = i;
		        	end = j;
		        }
	        }
		}
		
		out[0] = start;
		out[1] = end;
		out[2] = max;
		return out;
	}
	public static int[] maxSubArraySum(int arr[], int l, int h) {

		if(h == l) {
			int[] out = new int[3];
			out[0] = l;
			out[1] = h;
			out[2] = arr[l];
			return out;
		}
		else {
			int m = (l+h)/2;
			int[] leftSum = maxSubArraySum(arr,l,m);
			int[] rightSum = maxSubArraySum(arr,m+1,h);
			int[] crossSum = maxCrossingSum(arr,l,m,h);
			if(leftSum[2] > rightSum[2] && leftSum[2] > crossSum[2]) {
				return leftSum;
			}
			else if(rightSum[2] >= leftSum[2] && rightSum[2] >= crossSum[2]) {
				return rightSum;
			}
			else {
				return crossSum;
			}
		}
		
    }
	public static int[] maxCrossingSum(int arr[], int l, int m, int h) {
		int sum = 0;
		int lStart = m;
		int lEnd = 0;
		int left_sum = Integer.MIN_VALUE;
		for (int i = m; i >= l; i--) {
			sum = sum + arr[i];
			if (sum > left_sum) {
				left_sum = sum;
				lEnd = i;
			}
		}
		
		sum = 0;
		int rStart = m+1;
		int rEnd = 0;
		int right_sum = Integer.MIN_VALUE;
		for (int i = m + 1; i <= h; i++) {
			sum = sum + arr[i];
			if (sum > right_sum) {
				right_sum = sum;
				rEnd = i;
			}
		}
		
		int[] out = new int[3];
		if(left_sum > right_sum) {
			if(left_sum > left_sum + right_sum) {
				out[0] = lEnd;
				out[1] = lStart;
				out[2] = left_sum;
				return out;
			}
		}
		else if(right_sum > left_sum) {
			if(right_sum > left_sum + right_sum) {
				out[0] = rStart; 
				out[1] = rEnd;
				out[2] = right_sum;
				return out;
			}
		}
		
		out[0] = lEnd;
		out[1] = rEnd;
		out[2] = left_sum+right_sum;
		return out;
		
	}
	
	public static int[] Kadane(int a[]) {
        int maxSum = Integer.MIN_VALUE;
        int futureSum = 0;
        int start = 0;
        int end = 0;
        int s = 0;
        int e = 0;
        int[] out = new int[3];
        
        for (int i = 0; i < a.length; i++)
        {
        	futureSum += a[i];
            if (futureSum > maxSum) {
            	maxSum = futureSum;
                start = s;
                end = i;
            }
            if (futureSum < 0) {
            	futureSum = 0;
                s = i+1;
            }
//            futureSum = Math.max(a[i], a[i]+futureSum);
//            if(futureSum > maxSum) {
//            	maxSum = futureSum;
//            	start = s;
//            	end = i;
//            }
//            else {
//            	s = i+1;
//            }
        }

        
        out[0] = start;
        out[1] = end;
        out[2] = maxSum;
        return out;
    }
}
