
public class WiGemQuery {
	public static final int LOOPBACK	= 0;
	public static final int VOLTAGE		= LOOPBACK + 1;
	public static final int POWER		= LOOPBACK + 2;
	
	public static byte[] getQuery(int QUERY_STRING) {
		byte query[] = null;
		byte retQuery[] = null;
		
		switch (QUERY_STRING) {
		case LOOPBACK:
			// Loop back query means check if it is alive.
			// It returns same query.
			query = new byte[6];
			query[0] = (byte) 0x01; // slave address
			query[1] = (byte) 0x03; // function
			query[2] = (byte) 0x00; // starting address (Hi)
			query[3] = (byte) 0x00; // starting address (Lo)
			query[4] = (byte) 0x00; // bytes to read (Hi)
			query[5] = (byte) 0x00; // bytes to read (Lo)
//			query[6] = (byte) 0x45; // error check (Lo) - CRC generating
//			query[7] = (byte) 0xCA; // error check (Hi)
			break;
			
		case VOLTAGE:
			// it returns voltage from device.
			query = new byte[6];
			query[0] = (byte) 0x01; // slave address
			query[1] = (byte) 0x03; // function
			query[2] = (byte) 0x00; // starting address (Hi)
			query[3] = (byte) 0x2A; // starting address (Lo)
			query[4] = (byte) 0x00; // bytes to read (Hi)
			query[5] = (byte) 0x06; // bytes to read (Lo)
//			query[6] = (byte) 0xE4; // error check (Lo) - CRC generating
//			query[7] = (byte) 0x00; // error check (Hi)
			break;
			
		case POWER:
			// it returns active power from device.
			query = new byte[6];
			query[0] = (byte) 0x01; // slave address
			query[1] = (byte) 0x03; // function
			query[2] = (byte) 0x00; // starting address (Hi)
			query[3] = (byte) 0x30; // starting address (Lo)
			query[4] = (byte) 0x00; // bytes to read (Hi)
			query[5] = (byte) 0x06; // bytes to read (Lo)
//			query[6] = (byte) 0xC5; // error check (Lo) - CRC generating
//			query[7] = (byte) 0xC7; // error check (Hi)
			break;
		}
		
		// get CRC
		char fcs = CRC.generate(query);
		retQuery = new byte[query.length+2];
		for (int i=0; i<query.length; i++)
			retQuery[i] = query[i];
		retQuery[query.length] = (byte) fcs;
		retQuery[query.length+1] = (byte) (fcs >> 8);
		
		return retQuery;
	}
}
