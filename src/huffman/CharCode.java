/**
 * 
 */
package huffman;

/**
 * This is a class for the CharCode object which stores information about
 * each character, such as its code and code length.
 * 
 * @author Christopher Barnett
 *
 */
public class CharCode implements Comparable<CharCode> {
	private String character;
	private byte code;
	private int codeLength;
	/**
	 * @param character
	 * @param code
	 * @param codeLength
	 */
	public CharCode(String character, int codeLength) {
		super();
		this.character = character;
		this.code = -1;
		this.codeLength = codeLength;
	}
	/**
	 * @return the character
	 */
	public String getCharacter() {
		return character;
	}
	/**
	 * @param character the character to set
	 */
	public void setCharacter(String character) {
		this.character = character;
	}
	/**
	 * @return the code
	 */
	public byte getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(byte code) {
		this.code = code;
	}
	/**
	 * @return the codeLength
	 */
	public int getCodeLength() {
		return codeLength;
	}
	/**
	 * @param codeLength the codeLength to set
	 */
	public void setCodeLength(int codeLength) {
		this.codeLength = codeLength;
	}
	@Override
	public int compareTo(CharCode c) {
		if(this.codeLength > c.getCodeLength()){
			return -1;
		}
		if(this.codeLength < c.getCodeLength()){
			return 1;
		}
		if(this.codeLength == c.getCodeLength()){
			return this.character.compareTo(c.getCharacter());
		}
		System.err.println("Something went wrong comparing CharCodes.");
		return 0;
	}
	
	/**
	 * Overrides the default toString method
	 * @return a string representation of the CharCode object
	 */
	public String toString(){
		return character + " Code: " + Integer.toBinaryString(code) + " Length: " + codeLength;
	}
}
