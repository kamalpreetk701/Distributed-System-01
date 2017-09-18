//Run the client thread

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Manager {
	int count=0;
	String username="";

	 void test_case1()
	{
		for(int i=0;i<5;i++)
		{
			count++;
			String formatted = String.format("%05d", count);
			username="MTL"+formatted;
			System.out.println(username);
			Manager_Thread c = new Manager_Thread(username);            //run the new client thread
			c.start();
		}
	}
	void test_case2()
	{

		for(int i=0;i<5;i++)
		{
			count++;
			String formatted = String.format("%05d", count);
		//	u="SR"+formatted;
		//	Manager_Thread c = new Manager_Thread(i);            //run the new client thread
		//	c.start();
		}
	}

	public static void main(String arg[]) throws RemoteException, NotBoundException
	{
		Manager m=new Manager();
		m.test_case1();
	}

}
