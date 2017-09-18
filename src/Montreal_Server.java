//Provides the implementation of Montreal Server

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Montreal_Server
{
	public static void main(String args[]) throws IOException
		{
			final Logger logger;                            	//Refrence to Logger class is generated
			Handler fileHandler;
		    SimpleFormatter plainText;
			logger = Logger.getLogger(Logger.class.getName());
			logger.setUseParentHandlers(false);
			fileHandler = new FileHandler("D:/JAVA/DSassg1/Montreallog.txt",true);//File for writting log is opened in append mode
			plainText = new SimpleFormatter();
			fileHandler.setFormatter(plainText);
			logger.addHandler(fileHandler);

		Server_Imp ms=new Server_Imp("MTL");
	
		try {
			Registry registry=LocateRegistry.createRegistry(ms.get_rmi_port());
			registry.bind("Mtl", ms);											//Reference of object ms is created in registry
			System.out.println("Montreal Server statred...");
			logger.info("Montreal Server started ");                       //information to logger is written
		} catch (AlreadyBoundException e) 
		{
			e.printStackTrace();
		}
			logger.info("UDP Server started at port"+ms.get_udp_port());

			System.out.println("UDP Server started at port"+ms.get_udp_port());
			ms.UDPServer(ms.get_udp_port());									//UDP Server is started



		}
	
	}

