/*
  Mo's Algorithm Implementation
*/

import java.io.*;
import java.util.*;

class Mosalgo
{
	public static void main(String[] args)throws Exception{ new Mosalgo().run();} 
	long mod=1000000000+7;
	int time;
	int[] l = new int[1000001]; static int r[] = new int[1000001], ID[] = new int[1000001];
	int VIS[];
	long res;
	int BLOCK_SIZE;
	int sparse[][];
	int ar[];
	int level[];
	ArrayList<Integer> adj[];
	void init(int n)
	{
		sparse = new int[19][n + 1];
		adj = new ArrayList[n + 1];
		ar = new int[n + 1];
		level = new int[n + 1];
		VIS = new int[n + 1];
	}
	void solve() throws Exception
	{
		int n = ni();
		int q = ni();
		init(n);
		for(int i = 1; i <= n; i++) 
			ar[i] = ni();
		
		for(int i = 0; i < adj.length; i++) adj[i] = new ArrayList<Integer>();
		for(int i = 1; i < n; i++)
		{
			int u = ni(); int v = ni();
			adj[u].add(v);
			adj[v].add(u);
		}
		
		time = 0;
		dfs(1, -1);
		
		BLOCK_SIZE = (int) Math.sqrt(time);
		int Q[][] = new int[q][4];
		int i = 0;
		while(q-- > 0)
		{
			int u = ni(); int v = ni();
			Q[i][0] = lca(u, v);
			if (l[u] > l[v]) 
			{
				int temp = u; u = v; v = temp;
			}
			if (Q[i][0] == u) 
			{
				Q[i][1] = l[u];
				Q[i][2] = l[v];
			}
			else 
			{
				Q[i][1] = r[u];
				Q[i][2] = l[v];
			}
			Q[i][3] = i;
			i++;
		}
		Arrays.sort(Q, new Comparator<int[]>() {
			@Override
			public int compare(int[] x, int[] y) {
				int block_x = (x[1] - 1) / (BLOCK_SIZE + 1);
			    int block_y = (y[1] - 1) / (BLOCK_SIZE + 1);
			    if(block_x != block_y)
			        return block_x - block_y;
			    return x[2] - y[2];
			}
		});
		
		compute(Q);
	}
	
	 void compute(int[][] Q) 
	 {
			int curL = Q[0][1], curR = Q[0][1] - 1;
			res = 0;
			int M = Q.length;
			long ans[] = new long[M];
			for (int i = 0; i < M; i++){
				while (curL < Q[i][1]) check(ID[curL++]);
				while (curL > Q[i][1]) check(ID[--curL]);
				while (curR < Q[i][2]) check(ID[++curR]);
				while (curR > Q[i][2]) check(ID[curR--]);
		 
				int u = ID[curL], v = ID[curR];
		 
				if (Q[i][0] != u && Q[i][0] != v) 
					check(Q[i][0]);
		 
				ans[Q[i][3]] = res;
		 
				if (Q[i][0] != u && Q[i][0] != v) 
					check(Q[i][0]);
			}
			
			for (int i = 0; i < M; i++) System.out.println(ans[i]);
		}
	 
     void check(int x) 
	{
		if(VIS[x] == 1)
		{
			res--;
		}
		else if(VIS[x] == 0)
		{
           res++;
		}
		VIS[x] ^= 1;
	}
	
	int lca(int u, int v){
		if (level[u] > level[v]) {
			int temp = u;
			u = v; v = temp;
		}
		for (int i = 18; i >= 0; i--)
			if (level[v] - (1 << i) >= level[u]) v = sparse[i][v];
		if (u == v) return u;
		for (int i = 18; i >= 0; i--){
			if (sparse[i][u] != sparse[i][v]){
				u = sparse[i][u];
				v = sparse[i][v];
			}
		}
		return sparse[0][u];
	}
	
	public void dfs(int u, int p)
	{
		l[u] = ++time; ID[time] = u;
		for (int i = 1; i < 19; i++) sparse[i][u] = sparse[i - 1][sparse[i - 1][u]];
		for (int i = 0; i < adj[u].size(); i++)
		{
			int v = adj[u].get(i);
			if (v == p) continue;
			level[v] = level[u] + 1;
			sparse[0][v] = u;
			dfs(v, u);
		}
		r[u] = ++time; ID[time] = u;
	}
	
	/*IMPLEMENTATION BY AMAN KOTIYAL, FAST INPUT OUTPUT & METHODS BELOW*/
	
	private byte[] buf=new byte[1024];
	private int index;
	private InputStream in;
	private int total;
	private SpaceCharFilter filter;
	PrintWriter out;
	
	int gcd(int a, int b) 
	{ 
		if (a == 0) 
			return b; 
		return gcd(b%a, a); 
	} 
	/* for (1/a)%mod = ( a^(mod-2) )%mod  ----> use expo to calc -->(a^(mod-2)) */
	long expo(long p,long q)  /*  (p^q)%mod   */
	{
		long z = 1;
		while (q>0) {
			if (q%2 == 1) {
				z = (z * p)%mod;
			}
			p = (p*p)%mod;
			q >>= 1;
		}
		return z;
	}
	void run()throws Exception
	{
		in=System.in; out = new PrintWriter(System.out);
		solve();
		out.flush();
	}
	private int scan()throws IOException
	{
		if(total<0)
			throw new InputMismatchException();
		if(index>=total)
		{
			index=0;
			total=in.read(buf);
			if(total<=0)
				return -1;
		}
		return buf[index++];
	}
	private int ni() throws IOException 
	{
		int c = scan();
		while (isSpaceChar(c))
			c = scan();
		int sgn = 1;
		if (c == '-') {
			sgn = -1;
			c = scan();
		}
		int res = 0;
		do {
			if (c < '0' || c > '9')
				throw new InputMismatchException();
			res *= 10;
			res += c - '0';
			c = scan();
		} while (!isSpaceChar(c));
		return res * sgn;
	}
	private long nl() throws IOException 
	{
		long num = 0;
		int b;
		boolean minus = false;
		while ((b = scan()) != -1 && !((b >= '0' && b <= '9') || b == '-'))
			;
		if (b == '-') {
			minus = true;
			b = scan();
		}
		
		while (true) {
			if (b >= '0' && b <= '9') {
				num = num * 10 + (b - '0');
			} else {
				return minus ? -num : num;
			}
			b = scan();
		}
	}
	private double nd() throws IOException{
		return Double.parseDouble(ns());
	}
	private String ns() throws IOException {
		int c = scan();
		while (isSpaceChar(c))
			c = scan();
		StringBuilder res = new StringBuilder();
		do {
			if (Character.isValidCodePoint(c))
				res.appendCodePoint(c);
			c = scan();
		} while (!isSpaceChar(c));
		return res.toString();
	}
	private String nss() throws IOException
	{
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		return br.readLine();
	}
	private char nc() throws IOException 
	{
		int c = scan();
		while (isSpaceChar(c))
			c = scan();
		return (char) c;
	}
	private boolean isWhiteSpace(int n)
	{
		if(n==' '||n=='\n'||n=='\r'||n=='\t'||n==-1)
			return true;
		return false;
	}
	private boolean isSpaceChar(int c) {
		if (filter != null)
			return filter.isSpaceChar(c);
		return isWhiteSpace(c);
	}
	private interface SpaceCharFilter {
		public boolean isSpaceChar(int ch);
	}
}
