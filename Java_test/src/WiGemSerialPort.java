import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;

import java.util.Enumeration;


public class WiGemSerialPort {
	public static SerialPort getSerialPort(int timeout) {
		Enumeration enumeration = CommPortIdentifier.getPortIdentifiers();
		CommPortIdentifier commPortIdentifier = null;
		SerialPort serialPort = null;

		int count = 0;
		while (enumeration.hasMoreElements()) {
			commPortIdentifier = (CommPortIdentifier) enumeration.nextElement();
			count++;
		}

		// if 2 more port search then return
		if (count != 1) {
			System.out.println("Error: " + String.valueOf(count) + " CommPort has founded. ");
			return null;
		}

		// check if it is owned
		if (commPortIdentifier.isCurrentlyOwned()) {
			System.out.print("Error: " + String.valueOf(count) + " CommPort has founded. ");
			System.out.println("But Port is currently in use");
			return null;
		}

		// check if it is RS-232 communication
		try {
			CommPort commPort = commPortIdentifier.open(commPortIdentifier.getName(), timeout);
			if (commPort instanceof SerialPort) {
				serialPort = (SerialPort) commPort;
				serialPort.setSerialPortParams(38400, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE); // only use for WiGem
				System.out.println("Port opens successfully.");
			}
		} catch (PortInUseException e) {
			System.out.println("Error: RS-232 Communication Port has founded. ");
			System.out.println("But it is in Use.");
		} catch (Exception e) {
			System.out.print("Error: " + String.valueOf(count) + " CommPort has founded.");
			System.out.println("But Port is not RS-232 Communication.");
			e.printStackTrace();
		}
		return serialPort;
	}
}
