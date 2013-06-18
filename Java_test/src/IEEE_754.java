

public class IEEE_754 {
	public static float toFloat(byte[] data) {
		// convert to String
		StringBuilder sb = new StringBuilder(8);
		for (int i=0; i<data.length; i++) {
			sb.append(Hex.toString(data[i]));
		}
		
		// convert to Float
		Integer bits = Integer.valueOf(new String(sb), 16).intValue();
		return Float.intBitsToFloat(bits);
	}
}
