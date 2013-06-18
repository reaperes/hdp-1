
public class Hex {
	private static char[] hexChar = {
		'0', '1', '2', '3',
		'4', '5', '6', '7',
		'8', '9', 'a', 'b',
		'c', 'd', 'e', 'f'};
	
	/**
	 * Convert a byte array to a hex string
	 * 
	 * @param hex array of bytes to convert to string
	 * @return hex representation, two chars per byte.
	 */
	public static String toString(byte[] hex) {
		StringBuilder sb = new StringBuilder(hex.length * 2);
		
		for (int i=0; i<hex.length; i++) {
			sb.append(hexChar[(hex[i] & 0xf0) >>> 4]);
			sb.append(hexChar[hex[i]&0x0f]);
		}
		return sb.toString();
	}
	
	public static String toString(byte hex) {
		StringBuilder sb = new StringBuilder(2);
		sb.append(hexChar[(hex & 0xf0) >>> 4]);
		sb.append(hexChar[hex&0x0f]);
		return sb.toString();
	}
	
	/**
	 * Convert a byte array to a int value
	 * 
	 * @param hex array of bytes to convert to string
	 * @return hex representation, two chars per byte.
	 */	
	public static int toInt(byte[] hex) {
		int ret = 0;
		
		for (int i=0; i<hex.length; i++)
			ret += hex[i] * (Math.pow(2, i));
		
		return ret;
	}	
}
