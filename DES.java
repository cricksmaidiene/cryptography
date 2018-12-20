import java.io.*;
import java.util.*;

class DES
{
	static Scanner sc = new Scanner(System.in);
	
	public static void main (String args[]) throws IOException
	{
		
		int text[] = new int [64];
		int temp64[] = new int [64];
		int temp32[] = new int [32];
		int temp48[] = new int[48];
		int L[] = new int [32];
		int R[] = new int [32];

		int key[]= new int[64];
		int roundKeys[][] = new int[16][48];

		DES obj = new DES();

		obj.Input(key, 1);
		obj.KeyGen(key, temp48, roundKeys);

		obj.Input(text, 0);
		obj.Permute(1,text,temp64); 
		obj.Split(temp64,L,R); //works fine till here
		
		for(int round=0;round<16;round++)
		{	
			obj.Mixer(L,R,temp32,temp48,roundKeys,round); 
			// temp48 initially has same value as 16th roundkey
			if(round!=15)
				obj.Swapper(L,R);
		}
		obj.Combine(L,R,temp64);
		obj.Permute(2,temp64,text);
		obj.Output(text);
	}	

	public void Output(int arr[])
	{
		int l = arr.length;
		String last="";
		System.out.print("The Ciphertext is: ");
		
		for(int i=0;i<l;i++)
			last+=Integer.toString(arr[i]);
		
		for(int i=0;i<l;i+=4){
			String temp = last.substring(i,i+4);
			int dec = Integer.parseInt(temp,2);
			temp = Integer.toString(dec,16);
			temp=temp.toUpperCase();
			System.out.print(temp);
		}
		System.out.println();

	}
	public void KeyGen(int key64[], int temp48[], int roundKeys[][])
	{
		/* Declaring some temporary data structures to manage the inner computations */
		int key56[]= new int[56];
		int leftKey[] = new int[28];
		int rightKey[] = new int[28];

		/* Call Parity Drop Permutation */
		Permute(5, key64, key56);

		Split(key56, leftKey, rightKey);

		for(int round=0; round<16; round++)
		{
			ShiftLeft(leftKey, shiftTable[round]);
			ShiftLeft(rightKey, shiftTable[round]);
			
			/* Combine 28, 28 to 56 bits */
			Combine(leftKey, rightKey, key56);
			/* Call Key Compression Permutation */
			Permute(6,key56,temp48);
			
			for(int j=0; j<48;j++)
				roundKeys[round][j] = temp48[j];
				/* Copy the current key to the key holding structure */
		}

	}

	public void ShiftLeft(int key28[], int x)
	{
		for(int i=0; i<x; i++)
		{
			int temp = key28[0];
			for(int j=1; j<28; j++)
				key28[j-1] = key28[j];
			key28[27] = temp;
		}
	}

	public void Combine(int l[], int r[], int text[])
	{
		/* Function can be called by KeyGen() to merge 28,28 to 56 or,
		   Can be called by main() to merge 32,32 to 64 bit input for final permutation */
		
		for(int i=0; i<text.length; i++)
		{
			if(i>=l.length)
				text[i]=r[i-(l.length)];
			else
				text[i]=l[i];
		}	
	}

	public void Input(int text[], int n)
	{
		/*The console accepts hexadecimal values, since all bit patterns can be 
		 * sufficiently encoded using the hexadecimal scheme */
		
		if(n==0)
		{	
			System.out.println("Enter Plaintext for encryption\n");
			String str = sc.next();
			System.out.println("The Plaintext is: "+str);
			StringToBitStream(str, text);
		}
		
		else
		{
			System.out.println("Enter KEY\n");
			String str = sc.next();
			System.out.println("The Key is: "+str);
			StringToBitStream(str, text);
		}	
	}
	
	public void StringToBitStream(String str, int text[])
	{
		
		for (int i=0; i<str.length();i++)
		{
			/* 
			 * First, get numeric value of HEX in the range 0 ≤ x ≤ 15
			 * Next, get bit pattern of the decimal equivalent of HEX value
			 * Min 1 bit, Max 4 bits. Therefore populate text[], 4 bits at a time
			*/
				
			int x = Character.getNumericValue(str.charAt(i));
			String bits = Integer.toBinaryString(x);
			int m = (4*i)+4;
			for(int j=bits.length();j>0;j--,m--)
				text[m-1]=Character.getNumericValue(bits.charAt(j-1));	
		}

	}

	public void Display(int text[], int n)
	{
		/*If n is 0, display message appropriate for Plaintext,
		* If n is 1, display message appropriate for Ciphertext*/
		
		if(n==0)
			System.out.println("\nThe Plaintext Is");
		else
			System.out.println("\nThe Ciphertext Is");
		for(int i=0;i<64;i++)
		{
			if(i%8==0)
				System.out.println();
			System.out.print(text[i]+" ");
		}	
		System.out.println("\n");
	}

	public void Update(int input[], int output[], int n)
	{
		/* Update is a copy function that behaves 
		 * uniquely for 32 bit and 64 bit values */

		if(n==64)
			for(int i=0;i<64;i++)
				output[i]=input[i]; 
			/* Copy the contents of the temporary text into primary text[]  */
		else if(n==32)
			for(int i=0;i<32;i++)
				output[i]=input[i];
			/* Reverse of the above case */
		else
			for(int i=0; i<48; i++)
				output[i] = input[i];	
	}

	public void Split(int text[], int l[], int r[])
	{
		/* Assign the L[] and R[] 'bit' arrays with
		 * each half of the current 'n' 'bit' text[] */

		for(int i=0; i<l.length;i++)
			l[i]=text[i];
		for(int i=l.length; i<text.length; i++)
			r[i-l.length]=text[i];

	}
	
	public void Swapper(int l[], int r[])
	{
		/* Copy the Contents of L[0-31] into a temporary variable
		 * Update the value of L[0-31] with R[32-63] by iteration
		 * Assign R[32-63] for each iteration with the value of the temp */

		for(int i=0;i<32;i++){
			int copier = 0;
			copier = l[i];
			l[i] = r[i];
			r[i] = copier;
		}

	}

	public void Mixer(int l[], int r[], int temp32[], int temp48[], int roundKeys[][], int round)
	{
		/* Mixer Function takes in: 
		- left 32 bits 
		- right 32 bits
		- Key Set for 16 rounds
		- Current round number
		- Two additional structures for computation
		
		Mixer Copies the value of R into a temporary variable, then
		DES Function is called passing data structures used for the computation	
		Finally, the output of the function is XOR'd with L bits
		New L is sent back to main() for swapping */
		
		/*if(round==2){
			int arr[]=new int[48];
			Output(l);
			Output(r);
			for(int i=0;i<48;i++)
				arr[i]=roundKeys[round][i];
			Output(arr);
		}*/
		Update(r,temp32,32); //Input of mixer is now copied to temp32
		DESFunction(temp32,temp48,roundKeys,round);
		ExclusiveOR(l,temp32);
	}

	public void DESFunction(int temp32[], int temp48[], int roundKeys[][], int round)
	{

		/* Expansion P Box is Called */
		Permute(3,temp32,temp48);
		/* The output 48 bits is XOR'd with 48 bit key for current round */
		ExclusiveOR(roundKeys,temp48,round);

		/* substitution tables are invoked to transform the 48 bit value to 32*/
		Substitute(temp48,temp32,round);

		/* Straight P Box */
		/* Copier variable is used for holding the output of the straight P box */
		int copier[]= new int[32];
		Permute(4,temp32,copier);
		Update(copier,temp32,32);

		/*Output of f available in temp32*/

	}

	/* Two XOR functions are included and implement overloading 
		according to the required situation */

	public void ExclusiveOR(int roundKeys[][], int temp48[], int round)
	{   
		//XOR Inside DES Function
		int copier[]= new int[48];
		for(int i=0;i<48;i++)
			copier[i]=temp48[i]^roundKeys[round][i];
		Update(copier,temp48,48);
	}

	public void ExclusiveOR(int l[], int r[])
	{
		//XOR inside MIXER - Whitener
		int copier[] = new int [48];
		for(int i=0; i<32;i++)
			copier[i]=l[i]^r[i];
		Update(copier,l,32);
	}

	public void Substitute(int temp48[], int temp32[], int round)
	{
		for(int i=0; i<8; i++)
		{
			/* 48 bit input of temp48 is taken in chunks of 6. 
			 * 8 '6 bits' are passed into the 8 SBoxes */

			int row = Integer.parseInt((Integer.toString(temp48[6*i]) + Integer.toString(temp48[6*i+5])), 2);
			String cols="";
			for(int j=1;j<5;j++)
				cols+= Integer.toString(temp48[6*i+j]);
			int col = Integer.parseInt(cols,2);

			int value = SboxValue(row, col, i);
			temp32[4*i] = value/8;
			value=value%8;
			temp32[4*i+1] = value /4;
			value = value%4;
			temp32[4*i+2] = value /2;
			value = value%2;
			temp32[4*i+3] = value;
		}
	}

	public int SboxValue(int row, int col, int i)
	{
		if(i==0)
			return Sbox1[row][col];
		else if(i==1)
			return Sbox2[row][col];
		else if(i==2)
			return Sbox3[row][col];
		else if(i==3)
			return Sbox4[row][col];
		else if(i==4)
			return Sbox5[row][col];
		else if(i==5)
			return Sbox6[row][col];
		else if(i==6)
			return Sbox7[row][col];
		else if(i==7)
			return Sbox8[row][col];
		else
			return 0;
			
	}

	public void Permute(int n, int input[], int output[])
	{	

		if(n==1)
		{
			//Apply Initital Permutation 
			for(int i=0;i<8;i++)
				for(int j=0;j<8;j++)
					output[(8*i)+j]=input[(Permutation1[i][j])-1];		
		}
		else if(n==2)
		{
			//Apply Final Permutation
			for(int i=0;i<8;i++)
				for(int j=0;j<8;j++)
					output[(8*i)+j]=input[(Permutation2[i][j])-1];		
		}
		else if(n==3)
		{
			//Apply Expansion Permutation
			for(int i=0;i<8;i++)
				for(int j=0;j<6;j++)
					output[(6*i)+j]=input[(PermutationExp[i][j])-1];
		}	
		else if(n==4)
		{
			//Apply Straight Permutation
			for(int i=0;i<4;i++)
				for(int j=0;j<8;j++)
					output[(8*i)+j]=input[(PermutationStraight[i][j])-1];	
		}
		else if(n==5)
		{
			//Apply Parity Drop Permutation for Key Generation
			for(int i=0;i<7;i++)
				for(int j=0;j<8;j++)
					output[(8*i)+j]=input[(ParityDrop[i][j])-1];
		}
		else
		{
			//Apply Key Compression Table for Key Generation
			for(int i=0;i<6;i++)
				for(int j=0;j<8;j++)
					output[(8*i)+j]=input[(KeyCompression[i][j])-1];
		}	
				
	}
	
	/* THE FOLLOWING PIECE OF CODE STORES THE STANDARD DES TABLES. 
	 * THE TABLES ARE AVAILABLE IN MEMORY THROUGH 2D VECTORS.
	 * THE FUNCTIONS IN THE CODE LOAD THE APPROPRIATE TABLE ON AN IF-THEN BASIS.
	 * THE DATA STRUCTURES ARE DECLARED STATIC TO BE ACCESSIBLE ANYWHERE IN THE PROGRAM */

	
	/* SHIFT TABLE IS 1 X 16 */

	static int shiftTable[]=
	{1,1,2,2,2,2,2,2,1,2,2,2,2,2,2,1};

	/* PARITY DROP TABLE IS 7 X 8 */

	static int ParityDrop[][]=
	{
		{57,49,41,33,25,17,9,1},
		{58,50,42,34,26,18,10,2},
		{59,51,43,35,27,19,11,3},
		{60,52,44,36,63,55,47,39},
		{31,23,15,7,62,54,46,38},
		{30,22,14,6,61,53,45,37},
		{29,21,13,5,28,20,12,4}
	};

	/* KEY COMPRESSION TABLE IS 6 X 8 */

	static int KeyCompression[][]=
	{
		{14,17,11,24,1,5,3,28},
		{15,6,21,10,23,19,12,4},
		{26,8,16,7,27,20,13,2},
		{41,52,31,37,47,55,30,40},
		{51,45,33,48,44,49,39,56},
		{34,53,46,42,50,36,29,32}
	};

	/* PERMUTATION TABLES ARE 8 X 8 */

	static int Permutation1[][]=
	{
		{58,50,42,34,26,18,10,2},
		{60,52,44,36,28,20,12,4},
		{62,54,46,38,30,22,14,6},
		{64,56,48,40,32,24,16,8},
		{57,49,41,33,25,17, 9,1},
		{59,51,43,35,27,19,11,3},
		{61,53,45,37,29,21,13,5},
		{63,55,47,39,31,23,15,7}
	};
	static int Permutation2[][]=
	{
		{40,8,48,16,56,24,64,32},
		{39,7,47,15,55,23,63,31},
		{38,6,46,14,54,22,62,30},
		{37,5,45,13,53,21,61,29},
		{36,4,44,12,52,20,60,28},
		{35,3,43,11,51,19,59,27},
		{34,2,42,10,50,18,58,26},
		{33,1,41, 9,49,17,57,25}
	};

	/*EXPANSION PERMUTATION IS 8 X 6 */

	static int PermutationExp[][]=
	{
		{32,1,2,3,4,5},
		{4,5,6,7,8,9},
		{8,9,10,11,12,13},
		{12,13,14,15,16,17},
		{16,17,18,19,20,21},
		{20,21,22,23,24,25},
		{24,25,26,27,28,29},
		{28,29,30,31,32,1}	
	};

	/*STRAIGHT PERMUTATION IS 4 X 8 */

	static int PermutationStraight[][]=
	{
		{16,7,20,21,29,12,28,17},
		{1,15,23,26,5,18,31,10},
		{2,8,24,14,32,27,3,9},
		{19,13,30,6,22,11,4,25}
	}; 

	/* S-BOXES ARE 16 X 4 */

	static int Sbox1[][]=
	{
		{14,4,13,1,2,15,11,8,3,10,6,12,5,9,0,7},
		{0,15,7,4,14,2,13,1,10,6,12,11,9,5,3,8},
		{4,1,14,8,13,6,2,11,15,12,9,7,3,10,5,0},
		{15,12,8,2,4,9,1,7,5,11,3,14,10,0,6,13}
	};
	static int Sbox2[][]=
	{
		{15,1,8,14,6,11,3,4,9,7,2,13,12,0,5,10},
		{3,13,4,7,15,2,8,14,12,0,1,10,6,9,11,5},
		{0,14,7,11,10,4,13,1,5,8,12,6,9,3,2,15},
		{13,8,10,1,3,15,4,2,11,6,7,12,0,5,14,9}
	};
	static int Sbox3[][]=
	{
		{10,0,9,14,6,3,15,5,1,13,12,7,11,4,2,8},
		{13,7,0,9,3,4,6,10,2,8,5,14,12,11,15,1},
		{13,6,4,9,8,15,3,0,11,1,2,12,5,10,14,7},
		{1,10,13,0,6,9,8,7,4,15,14,3,11,5,2,12}	
	};
	static int Sbox4[][]=
	{
		{7,13,14,3,0,6,9,10,1,2,8,5,11,12,4,15},
		{13,8,11,5,6,15,0,3,4,7,2,12,1,10,14,9},
		{10,6,9,0,12,11,7,13,15,1,3,14,5,2,8,4},
		{3,15,0,6,10,1,13,8,9,4,5,11,12,7,2,14}
	};
	static int Sbox5[][]=
	{
		{2,12,4,1,7,10,11,6,8,5,3,15,13,0,14,9},
		{14,11,2,12,4,7,13,1,5,0,15,10,3,9,8,6},
		{4,2,1,11,10,13,7,8,15,9,12,5,6,3,0,14},
		{11,8,12,7,1,14,2,13,6,15,0,9,10,4,5,3}
	};
	static int Sbox6[][]=
	{
		{12,1,10,15,9,2,6,8,0,13,3,4,14,7,5,11},
		{10,15,4,2,7,12,9,5,6,1,13,14,0,11,3,8},
		{9,14,15,5,2,8,12,3,7,0,4,10,1,3,11,6},
		{4,3,2,12,9,5,15,10,11,14,1,7,6,0,8,13}
	};
	static int Sbox7[][]=
	{
		{4,11,2,14,15,0,8,13,3,12,9,7,5,10,6,1},
		{13,0,11,7,4,9,1,10,14,3,5,12,2,15,8,6},
		{1,4,11,13,12,3,7,14,10,15,6,8,0,5,9,2},
		{6,11,13,8,1,4,10,7,9,5,0,15,14,2,3,12}
	};
	static int Sbox8[][]=
	{
		{13,2,8,4,6,15,11,1,10,9,3,14,5,0,12,7},
		{1,15,13,8,10,3,7,4,12,5,6,11,0,14,9,2},
		{7,11,4,1,9,12,14,2,0,6,10,13,15,3,5,8},
		{2,1,14,7,4,10,8,13,15,12,9,0,3,5,6,11}
	};	

}
	
