import java.io.*;

class Caesar{
	
	static BufferedReader br = new BufferedReader (new InputStreamReader(System.in));

	public static void main (String args[]){
		
		Caesar cipher = new Caesar();
		String plaintext = cipher.InputPlainText();
		int key = cipher.InputKey();
		String ciphertext = cipher.Encrypt(plaintext,key);
		cipher.OutputCipherText(ciphertext);

	}

	private String Encrypt (String plaintext, int key){
		
		String ciphertext = "";
		plaintext = plaintext.toUpperCase();

		for(int i=0; i<plaintext.length(); i++)
			ciphertext+=Character.toString((char)((((((int)(plaintext.charAt(i)))-65)+key)%26)+65));
		
		return ciphertext;
	}

	public String InputPlainText() throws IOException{

		System.out.println("\nEnter the Plaintext");
		String plaintext = br.readLine();
		return plaintext;
	}

	private int InputKey() throws IOException{

		System.out.println("Enter the key : The Additive Summand");
		int k = Integer.parseInt(br.readLine());
		return k;

	}

	public void OutputCipherText(String ciphertext){

		System.out.println("The Encrypted Message is: " + ciphertext + "\n");
		
	}
}