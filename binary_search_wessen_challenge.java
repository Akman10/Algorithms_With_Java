



import java.io.*;
import java.util.*;

class ques1 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter wr = new PrintWriter(System.out);
         String s[] = br.readLine().split(" ");
        int N = Integer.parseInt(s[0]);
        int K = Integer.parseInt(s[1]);
         String[] arr_constraints_array = br.readLine().split(" ");
         int[] a = new int[N];
         for(int i_constraints_array = 0; i_constraints_array < arr_constraints_array.length; i_constraints_array++)
         {
         	a[i_constraints_array] = Integer.parseInt(arr_constraints_array[i_constraints_array]);
         }

         int out_ = Solve(N, K, a);
         System.out.println(out_);

         wr.close();
         br.close();
    }
    static int Solve(int N, int K, int[] a){
       // Write your code here
       long max=0;
        int cnt[]=new int[N];
        int num[]=new int[N];
        int num2[]=new int[N];
        for(int i=0;i<N;i++)
        {
            cnt[i]=(i==0)?0:cnt[i-1];
            if(a[i]%2==1)
            cnt[i]++;
            int tem=a[i];
            while(tem>0&&tem%2==0)
            {
            	num[i]++;
            	tem=tem/2;
            }
            num2[i]=num[i];
            if(i!=0)
            	num[i]+=num[i-1];
        }
        for(int i=0;i<N;i++) //odd to even
        {
        	int x;
        	if(a[i]%2==1)
             x= binary(cnt,cnt[i]+K-1);
        	else
        		   x= binary(cnt,cnt[i]+K);
            if(x<0)
            {
            	x=x+1;
            	x=Math.abs(x)-1;
            }
           //  System.out.print(x+" ");
            long tem= x+1 - i;
            max=Math.max(tem,max);
        }
        
        for(int i=0;i<N;i++) //even to odd
        {
        	int x;
        	if(a[i]%2==1) 
        		x=   binary(num,num[i]+K);
        	else
        	 x= binary(num,num[i]+K-num2[i]);
        	
            if(x<0)
            {
            	x=x+1;
            	x=Math.abs(x)-1;
            }
          //   System.out.print(x+" ");
            long tem= x+1 - i;
            max=Math.max(tem,max);
        }
        
        return (int)max;
    }

   static int binary(int ar[],int key)// s->starting index | e->ending index + 1
	{
    		int s=0; int e=ar.length;
		int fn=0;
		int xx=0;
	    while (s !=e)
	    {
	        int mid = s+e>>1;
	        if(ar[mid]==key)
	        	{
	        	fn=1;
	        	xx=mid;
	        	}
	        if (ar[mid] <=key)
	        {
	           s=mid+1;;
	        }
	        else
	        {
	           e=mid;
	        }
	    }
	    if(fn==1)
	    	return xx;
	    else
	    	return -(s+1);
	}
}
