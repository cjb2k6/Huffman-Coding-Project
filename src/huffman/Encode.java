/**
 * 
 */
package huffman;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Method to encode a text file using Huffman's algorithm.
 * Produces a binary file representation of the input file.
 * Optionally, output a dot graph representation of the huffman tree or canonical tree using the -h and/or -c arguments
 * Usage: java huffman.Encode [-h HUFFMAN_TREE_FILE] [-c CANONICAL_TREE_FILE] SOURCEFILE TARGETFILE
 * @author Christopher Barnett
 */
public class Encode {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String sourceFile = "";
		String targetFile = "";
		String huffmanTreeFile = "";
		String canonicalTreeFile = "";
		//Process the command line arguments
				for(int i = 0; i < args.length; i++){
					if(args[i].equals("-h")){
						i++;
						huffmanTreeFile = args[i];
					}else if(args[i].equals("-c")){
						i++;
						canonicalTreeFile = args[i];
					}else{
						sourceFile= args[i];
						i++;
						targetFile = args[i];
					}
				}
		//Initialize the tree
		HuffmanTree huffmanTree = new HuffmanTree();
		huffmanTree.buildTree(sourceFile);
		HuffmanTree canonicalTree = huffmanTree.canonize(targetFile, huffmanTree.getInputFileChars());
		System.out.println("Finished Encoding");
		
		if(huffmanTreeFile != ""){
			writeDotGraph(huffmanTreeFile, huffmanTree.generateDotGraph());
		}
		
		if(canonicalTreeFile != ""){
			writeDotGraph(canonicalTreeFile, canonicalTree.generateDotGraph());
		}
	}
	/**
	 * @param file - the name of the file to write the dot graph to
	 * @param dotGraph - a string containing all of the dot graph code
	 */
	public static void writeDotGraph(String file, String dotGraph){
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(dotGraph);
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
