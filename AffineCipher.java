import java.io.*;

class AffineCipher{
	
	BufferedReader br = new BufferedReader (new InputStreamReader(System.in));

	public static void main (String args[])throws IOException{
		
		AffineCipher cipher = new AffineCipher();
		String plaintext = cipher.InputPlainText();
		int key1 = cipher.InputKey(1);
		int key2 = cipher.InputKey(2);
		String ciphertext = cipher.Encrypt(plaintext,key1,key2);
		cipher.OutputCipherText(ciphertext);

	}

	private String Encrypt (String plaintext, int key1, int key2){
		
		String mulciphertext = "";
		String ciphertext="";
		plaintext = plaintext.toUpperCase();

		for(int i=0; i<plaintext.length(); i++){
			char current = plaintext.charAt(i);
			if(current!=' ')
				mulciphertext+=Character.toString((char)((((((int)(plaintext.charAt(i)))-65)*key1)%26)+65));
			else if(Character.isDigit(current))
				mulciphertext+=Character.toString((char)((((((int)(plaintext.charAt(i)))-48)*key1)%10)+48));
			else
				mulciphertext+=' ';
		}	
		for(int i=0; i<mulciphertext.length(); i++){
			char current = plaintext.charAt(i);
			if(current!=' ')
				ciphertext+=Character.toString((char)((((((int)(mulciphertext.charAt(i)))-65)+key2)%26)+65));
			else if(Character.isDigit(current))
				ciphertext+=Character.toString((char)((((((int)(mulciphertext.charAt(i)))-48)+key2)%10)+48));
			else
				ciphertext+=' ';
		}
		return ciphertext;
	}

	public String InputPlainText()throws IOException{

		System.out.println("\nEnter the Plaintext");
		String plaintext = br.readLine();
		return plaintext;
	}

	private int InputKey(int flag)throws IOException{

		if(flag==1){
			System.out.print("\nEnter first key : The Multiplicative key: ");
			int k = Integer.parseInt(br.readLine());
			return k;
		}
		
		else{
			System.out.print("\nEnter second key : The additive key: ");
			int k = Integer.parseInt(br.readLine());
			return k;
		}


	}

	public void OutputCipherText(String ciphertext){

		System.out.println("The Encrypted Message is: " + ciphertext + "\n");
		
	}
}
