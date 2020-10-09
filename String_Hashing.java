	//copy this code to your class file.
  // h[i] --> Gives Hash value till ith index(0 based)
	// subhash(i,j) --> gives hash value from [i+1] to [j]
  
	class StringHash
	{
     long mod=1000000000+7;
	   long h[];
	   long inv[]=new long[100006];
	   StringHash(String s)
		{
			int l=s.length();
			h=new long[l];
			long p=31;long tpow=31;
			h[0]=(s.charAt(0)-'a'+1)%mod;
			for(int i=1;i<l;i++)
			{
				char c=s.charAt(i);
				h[i]=(h[i-1] + (c-'a'+1)*tpow)%mod;
				tpow=(tpow*p)%mod;
			}
			inv[0]=1; inv[1]=expo(31,mod-2);
			   for (int i = 2; i <inv.length; i++) 
				   inv[i]=(inv[i-1]*inv[1])%mod;
		 }
	   long subhash(int l,int r)
		{
			return (  ((h[r]-h[l]+mod)%mod)  * inv[l+1]  )%mod;
		}
	}
