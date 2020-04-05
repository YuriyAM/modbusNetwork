package modbus_client;
import de.re.easymodbus.modbusclient.*;

public class conSlaveLogic {
	public static void main(String[] args)
	{
		ModbusClient modbusClient = new ModbusClient();
		try
		{
            modbusClient.setipAddress("127.0.0.1");
            modbusClient.setPort(1522);
            modbusClient.Connect();
            System.out.println("Connected");
            Thread.sleep(3000);
            modbusClient.Disconnect();
            System.out.println("Disconnected");
            Thread.sleep(3000);

            modbusClient.setipAddress("127.0.0.1");
            modbusClient.setPort(1522);
            modbusClient.Connect();
            System.out.println("Connected");
            Thread.sleep(3000);
            modbusClient.Disconnect();
            System.out.println("Disconnected");
            Thread.sleep(3000);
		}
		catch (Exception e)
		{		
            System.out.println(e.getMessage());
		}	
	}
}