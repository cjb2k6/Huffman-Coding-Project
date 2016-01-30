/**
 * 
 */
package huffman;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * A class for the Huffman Tree object.
 * 
 * @author Christopher Barnett
 *
 */
public class HuffmanTree {
	/**
	 * A class for the Huffman Tree Node object.
	 */
	private class HuffTreeNode implements Comparable<HuffTreeNode>{
		private HuffTreeNode leftChild;
		private HuffTreeNode rightChild;
		private String character;
		private int freq;
		
		/**
		 * Default constructor for the Huffman Tree Node
		 */
		public HuffTreeNode(){
			this.leftChild = null;
			this.rightChild = null;
			this.character = null;
			this.freq = 0;
		}
		
		/**
		 * Parameterized constructor for the Huffman Tree Node
		 * @param character - the character for the node
		 * @param freq - the frequency the character appears in the source file
		 */
		public HuffTreeNode(String character, int freq){
			this.leftChild = null;
			this.rightChild = null;
			this.character = character;
			this.freq = freq;
		}

		/**
		 * @return the leftChild
		 */
		public HuffTreeNode getLeftChild() {
			return leftChild;
		}

		/**
		 * @param leftChild the leftChild to set
		 */
		public void setLeftChild(HuffTreeNode leftChild) {
			this.leftChild = leftChild;
		}

		/**
		 * @return the rightChild
		 */
		public HuffTreeNode getRightChild() {
			return rightChild;
		}

		/**
		 * @param rightChild the rightChild to set
		 */
		public void setRightChild(HuffTreeNode rightChild) {
			this.rightChild = rightChild;
		}

		/**
		 * @return the character
		 */
		public String getCharacter() {
			return character;
		}

		/**
		 * @return the freq
		 */
		public int getFreq() {
			return freq;
		}

		/**
		 * @param freq the freq to set
		 */
		public void setFreq(int freq) {
			this.freq = freq;
		}

		@Override
		public int compareTo(HuffTreeNode n) {
			if(n.getFreq() > this.freq){
				return -1;
			}
			if(n.getFreq() < this.freq){
				return 1;
			}
			return 0;
		}
		/**
		 * Returns true if node is a leaf node
		 * @return isLeaf
		 */
		public boolean isLeaf(){
			if(this.leftChild == null && this.rightChild == null){
				return true;
			}
			return false;
		}
		
		/**
		 * Returns a string representation of the node.
		 * Used to populate the dot graphs
		 * @return the string
		 */
		
		public String toString(){
			if(!this.isLeaf()){
				if(this.freq == 0){
					return this.character;
				}
				return "" + this.freq;
			}
			if(this.character == "\u0000" || ((byte) this.character.charAt(0)) == 0){
				if(this.freq == 0){
					return "EOF";
				}
				return "EOF:" + this.freq;
			}
			if(this.character == "\r" || this.character == "\u0013"){
				if(this.freq == 0){
					return "Carriage Return";
				}
				return "Carriage Return:" + this.freq;
			}
			if(this.character == "\"" || this.character == "\\u0022"){
				if(this.freq == 0){
					return "DblQuote";
				}
				return "DblQuote:" + this.freq;
			}
			if(this.freq == 0){
				return "" + this.character;
			}
			return "" + this.character + ":" + this.freq;
		}
	}
	//-------------------------------------------------------------
	private HuffTreeNode root;
	private LinkedList<String> inputFileChars = null;
	
	/**
	 * Default constructor for the Huffman Tree object
	 */
	public HuffmanTree() {
		this.root = null;
	}

	/**
	 * @return the root
	 */
	public HuffTreeNode getRoot() {
		return root;
	}

	/**
	 * @param root the root to set
	 */
	public void setRoot(HuffTreeNode root) {
		this.root = root;
	}
	
	/**
	 * @return the inputFileChars
	 */
	public LinkedList<String> getInputFileChars() {
		return inputFileChars;
	}

	/**
	 * Method to build the tree from an input text file.
	 * @param inputFile - a string representing the name of the input file
	 */
	public void buildTree(String inputFile){
		ArrayList<HuffTreeNode> h = read(inputFile);
		int n = h.size();
		PriorityQueue<HuffTreeNode> q = new PriorityQueue<HuffTreeNode>(h);
		
		for(int i = 1; i < n; i++){
			HuffTreeNode z = new HuffTreeNode();
			HuffTreeNode x = q.poll();
			HuffTreeNode y = q.poll();
			
			z.setLeftChild(x);
			z.setRightChild(y);
			z.setFreq(x.getFreq() + y.getFreq());
			
			q.add(z);	
		}
		root = q.poll();
	}
	
	/**
	 * Method to build the tree from a priority queue of CharCodes
	 * @param p - a priority queue of CharCodes
	 */
	public void buildTree(PriorityQueue<CharCode> p){
		CharCode c;
		HuffTreeNode curr;
		PriorityQueue<CharCode> q = new PriorityQueue<CharCode>();
		q.addAll(p);
		
		root = new HuffTreeNode("root", 0);
		curr = root;
		while(q.size() > 0){
			c = q.poll();
			String s = Integer.toBinaryString(c.getCode());
			//Pad right side with zeroes until lengths match
			while(s.length() < c.getCodeLength()){
				s = "0" + s;
			}
			//Traverse Tree
			int i;
			for(i = 0; i < s.length() - 1; i++){
				if(s.charAt(i) == "0".charAt(0)){
					if(curr.getLeftChild() == null){
						if(curr.getCharacter() == "root"){
							curr.setLeftChild(new HuffTreeNode("0", 0));
						}else{
							curr.setLeftChild(new HuffTreeNode(curr.getCharacter() + "0", 0));
						}
					}
					curr = curr.getLeftChild();
				}else{
					if(curr.getRightChild() == null){
						if(curr.getCharacter() == "root"){
							curr.setRightChild(new HuffTreeNode("1", 0));
						}else{
							curr.setRightChild(new HuffTreeNode(curr.getCharacter() + "1", 0));
						}
					}
					curr = curr.getRightChild();
				}
			}
			if(s.charAt(i) == "0".charAt(0)){
				curr.setLeftChild(new HuffTreeNode(c.getCharacter(), 0));
				curr = root;
			}else{
				curr.setRightChild(new HuffTreeNode(c.getCharacter(), 0));
				curr = root;
			}
		}
	}
	
	/**
	 * Method to read the characters from the input file
	 * and put them into a hash table.
	 * @param fileName - a string representing the input file name
	 */
	public ArrayList<HuffTreeNode> read(String fileName){
		ArrayList<HuffTreeNode> h = new ArrayList<HuffTreeNode>();
		Hashtable<String, Integer> table = new Hashtable<String, Integer>();
		String line = "";
		String character = "";
		boolean firstLine = true;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			inputFileChars = new LinkedList<String>();
			while((line = reader.readLine()) != null){
				if(firstLine){
					firstLine = false;
				}else{
					character = "\r";
					inputFileChars.add(character);
					if(table.containsKey(character)){
						int freq = table.get(character);
						table.remove(character);
						table.put(character, freq + 1);
					}else{
						table.put(character, 1);
					}
				}
				for(int i = 0; i < line.length(); i++){
					character = "" + line.charAt(i);
					inputFileChars.add(character);
					if(table.containsKey(character)){
						int freq = table.get(character);
						table.remove(character);
						table.put(character, freq + 1);
					}else{
						table.put(character, 1);
					}
				}
			}
			reader.close();
			Object[] alphabet = table.keySet().toArray();
			
			for(int i = 0; i < alphabet.length; i++){
				String s = alphabet[i].toString();
				h.add(new HuffTreeNode(s, table.get(s)));
			}
			h.add(new HuffTreeNode("\u0000", 1));
			inputFileChars.add("\u0000");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return h;
	}
	
	/**
	 * Method to produce a canonical Huffman Tree
	 * @param binaryOutputFile - a string representing the binary output file name
	 * @param infile - a LinkedList of strings representing the input file
	 */
	public HuffmanTree canonize(String binaryOutputFile, LinkedList<String> infile){
		HuffmanTree h = new HuffmanTree();
		PriorityQueue<CharCode> codes = new PriorityQueue<CharCode>();
		
		canonExpand(root, codes, 0);
		
		PriorityQueue<CharCode> newCodes = generateCodes(codes);
		
		h.buildTree(newCodes);
		
		h.writeBinaryOutFile(binaryOutputFile, infile, newCodes);
		return h;
		
	}
	
	/**
	 * Method to generate codes based on the tree, a canonical tree
	 * should be used
	 * @param codes - a priority queue of CharCodes
	 * @return the new priority queue with codes
	 */
	public PriorityQueue<CharCode> generateCodes(PriorityQueue<CharCode> codes){
		PriorityQueue<CharCode> newCodes = new PriorityQueue<CharCode>();
		byte currentCode = 0b00000000;
		int currentLength;
		CharCode c;
		
		//Get first code
		c = codes.poll();
		currentLength = c.getCodeLength();
		
		c.setCode(currentCode);
		newCodes.add(c);
		//Get the rest of the codes
		while(codes.size() > 0){
			c = codes.poll();
			if(c.getCodeLength() == currentLength){
				currentCode++;
				c.setCode(currentCode);
				newCodes.add(c);
			}else{
				currentLength = c.getCodeLength();
				currentCode++;
				currentCode = (byte) (currentCode >> 1);
				c.setCode(currentCode);
				newCodes.add(c);
			}
			
		}
		return newCodes;
	}
	
	/**
	 * Method for recursive calls to generate the canonical tree
	 * @param curr - the current node of the tree traversal
	 * @param codes - the current queue of codes
	 * @param length - the length of the current code - AKA the current depth of the tree traversal
	 */
	private void canonExpand(HuffTreeNode curr, PriorityQueue<CharCode> codes, int length){
		if(!curr.isLeaf()){
			if(curr.getLeftChild() != null){
				canonExpand(curr.getLeftChild(), codes, length + 1);
			}
			if(curr.getRightChild() != null){
				canonExpand(curr.getRightChild(), codes, length + 1);
			}
		}else{
			codes.add(new CharCode(curr.getCharacter(), length));
		}
	}
	
	/**
	 * Method to write the binary output file
	 * @param file - a string representing the output file name
	 * @param infile - a LinkedList representing the input file characters
	 * @param q - a priority queue of CharCodes
	 */
	public void writeBinaryOutFile(String file, LinkedList<String> infile, PriorityQueue<CharCode> q){
		try {
			FileOutputStream outFile = new FileOutputStream(file);
			ArrayList<Byte> bytes = new ArrayList<Byte>(1024);
			Hashtable<String, CharCode> c = new Hashtable<String, CharCode>();
			String currentByte = "";
			
			//Start header
			bytes.add((byte)q.size());
			
			//Build the table and rest of header
			while(q.size() > 0){
				CharCode cc = q.poll();
				c.put(cc.getCharacter(), cc);
				
				bytes.add((byte) cc.getCharacter().charAt(0));
				bytes.add((byte)cc.getCodeLength());
			}
			
			//Process the input
			while(infile.size() > 0){
				String inputChar = infile.poll();
				if(inputChar == "\u0000"){
					while(currentByte.length() < 8){
						currentByte += "0";
					}
					bytes.add((byte) Integer.parseInt(currentByte, 2));
				}else{
					//Get the code for the character as a binary string
					String s = Integer.toBinaryString(c.get(inputChar).getCode());
					//Pad with 0s if necessary
					while(s.length() < c.get(inputChar).getCodeLength()){
						s = "0" + s;
					}
					//Add code to bytes
					for(int i = 0; i < s.length(); i++){
						if(currentByte.length() >= 8){
							bytes.add((byte) Integer.parseInt(currentByte, 2));
							currentByte = "";
						}
						currentByte += "" + s.charAt(i);
					}
				}
			}
			byte[] buffer = new byte[bytes.size()];
			for(int i = 0; i < bytes.size(); i++){
				buffer[i] = bytes.get(i);
			}
			outFile.write(buffer);
			outFile.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Method to read the bytes from the binary input file, build the tree from its header,
	 * and write the output file
	 * @param inputFile - a string representing the binary input file name
	 * @param outputFile - a string representing the output file name
	 */
	
	public void readBinaryInFile(String inputFile, String outputFile){
		try {
			FileInputStream inFile = new FileInputStream(inputFile);
			PriorityQueue<CharCode> q = new PriorityQueue<CharCode>();
			PriorityQueue<CharCode> newq = new PriorityQueue<CharCode>();
			byte[] buffer = new byte[1000000];
			int currByte = 0;
			inFile.read(buffer);
			
			//Read the header
			int numChars = (int) buffer[currByte];
			currByte++;
			//Build queue of CharCodes
			for(int i = 0; i < numChars; i++){
				String c = "" + (char) buffer[currByte];
				currByte++;
				int length = buffer[currByte];
				currByte++;
				q.add(new CharCode(c, length));
			}
			newq.addAll(q);
			buildTree(generateCodes(newq));
			
			//Build a code reference table
			Hashtable<String, String> table = new Hashtable<String, String>();
			int maxLength = -1;
			while(q.size() > 0){
				CharCode c = q.poll();
				String key = Integer.toBinaryString(c.getCode());
				while(key.length() < c.getCodeLength()){
					key = "0" + key;
				}
				table.put(key, c.getCharacter());
				if(maxLength == -1){
					maxLength = c.getCodeLength();
				}
			}
			//Write file
			System.out.println("Starting to decode...");
			BufferedWriter output = new BufferedWriter(new FileWriter(outputFile));
			String currCode = "";
			String outputStr = "";
			boolean finished = false;
			while(!finished && currByte < buffer.length){
				String s = Integer.toBinaryString(buffer[currByte] & 0xff);
				currByte++;
				//Pad with zeroes if necessary
				while(s.length() < 8){
					s = "0" + s;
				}
				for(int i = 0; i < s.length(); i++){
					currCode = currCode + s.charAt(i);
					if(table.containsKey(currCode)){
						if(currCode.length() == maxLength && Integer.parseInt(currCode) == 0){
							finished = true;
							System.out.println("Done.");
							break;
						}else{
							outputStr += table.get(currCode);
							currCode = "";
						}
					}
				}
			}
			output.write(outputStr);
			output.close();
			inFile.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Method to produce the code for a dot graph representation of the tree
	 * @return a string representing the entire dot graph code representation
	 */
	public String generateDotGraph(){
		String s = "digraph{\n";
		s += dotGraphExpand(root);
		s += "}";
		return s;
	}
	
	/**
	 * Recursive method to traverse the tree to build the dot graph code
	 * @return a string with dot graph code for the current node
	 */
	private String dotGraphExpand(HuffTreeNode curr){
		String str = "";
		if(!curr.isLeaf()){
			if(curr.getLeftChild() != null){
				str += "\"" + curr + "\" -> \"" + curr.getLeftChild() + "\" [ label=\"0\" ];\n";
				str += dotGraphExpand(curr.getLeftChild());
			}
			if(curr.getRightChild() != null){
				str += "\"" + curr + "\" -> \"" + curr.getRightChild() + "\" [ label=\"1\" ];\n";
				str += dotGraphExpand(curr.getRightChild());
			}
		}
		
		return str;
	}
	
}
