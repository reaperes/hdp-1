import gnu.io.SerialPort;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.sql.Timestamp;
import java.util.Date;

public class WiGem implements Runnable {
	private SerialPort port;
	private byte[] query;
	private byte recvData[];
	
	private BufferedOutputStream bufferedOutputStream;
	private BufferedInputStream bufferedInputStream;
	
	private DB dbHandler;
	
	public WiGem() {
		try {
			port = WiGemSerialPort.getSerialPort(2000);
			query = WiGemQuery.getQuery(WiGemQuery.POWER);
			recvData = new byte[100];
			bufferedOutputStream = new BufferedOutputStream(port.getOutputStream());
			bufferedInputStream = new BufferedInputStream(port.getInputStream());
			dbHandler = DB.getInstance();
		} catch (Exception e) {
			System.out.println("WiGem Construction failed.");
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		try {
			while (true) {
				// send query
				bufferedOutputStream.write(query);
				bufferedOutputStream.flush();
				
				Thread.sleep(200);
				
				// receive data
				bufferedInputStream.read(recvData);
	
				// get power from receive data
				byte[] powerArray = new byte[4];
				for (int i=3; i<7; i++)
					powerArray[i-3] = recvData[i];
				Float power = IEEE_754.toFloat(powerArray);

				// insert record to mysql
				dbHandler.insertPower(new Timestamp(new Date().getTime()), power);
				System.out.println("current power : " + String.valueOf(power) + " watts");
				
	//			Thread.sleep(600000); // wait for 10 mins.
				Thread.sleep(1500); // wait for 10 seconds.
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbHandler.close();
			if (port != null) {
				port.close();
				System.out.println("Port closed successfully");
			}
		}
	}

	public static void main(String argc[]) {
		Thread t = new Thread(new WiGem(), "EMU Thread");
		t.start();
	}
}
