/**
 * 
 */
package huffman;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Method to decode a text file using Huffman's algorithm.
 * Produces a text file representation of the input file.
 * Optionally, output a dot graph representation of the canonical tree using the -c argument
 * Usage: java huffman.Encode [-c CANONICAL_TREE_FILE] SOURCEFILE TARGETFILE
 * @author Christopher Barnett
 */
public class Decode {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String sourceFile = "";
		String targetFile = "";
		String canonicalTreeFile = "";
		//Process the command line arguments
				for(int i = 0; i < args.length; i++){
					if(args[i].equals("-c")){
						i++;
						canonicalTreeFile = args[i];
					}else{
						sourceFile= args[i];
						i++;
						targetFile = args[i];
					}
				}
		HuffmanTree canonicalTree = new HuffmanTree();
		canonicalTree.readBinaryInFile(sourceFile, targetFile);
		
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
