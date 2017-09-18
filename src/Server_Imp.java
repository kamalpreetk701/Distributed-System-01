import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.logging.Logger;

public class Server_Imp extends UnicastRemoteObject implements ServerInterface
{
	int rmi_port=0;														//rmi port of server
	int udp_port=0;														//udp port of server
	String  server_name="",username="";
	DatagramSocket ds;													//socket
	DatagramPacket data_packet_request_1,data_packet_request_2;			//packets to send request
	DatagramPacket data_packet_reply_1,data_packet_reply_2;				//packets to recieve request

	Boolean success_flag=false;											//flag indicating success or failure of a function
	String record_id="";
	static int student_count=0;											//student and teacher count intialized to zero
	static int teacher_count=0;
	String count_result[]={"0","0","0"};								//String array to store count of all three servers

	byte [] byte_array_request=new byte[1000];							//byte array to send request
	byte [] byte_array_reply=new byte[1000];							//byte array to recieve reply


	Logger logger = Logger.getLogger(Logger.class.getName());			//instance of Logger class


	HashMap<Character,ArrayList<Record>> map=new HashMap<Character,ArrayList<Record>>();	//hashmap of type character as key and arraylist as value

	protected Server_Imp() throws RemoteException						//default constructor
		{
		super();
		}


	Server_Imp(String x) throws RemoteException						  //Constructor overloaded
	{
	server_name=x;
	if(x.equals("MTL"))
	{
		rmi_port=2964;
		udp_port=5434;
	}
	else if(x.equals("LVL"))
	{
		rmi_port=2965;
		udp_port=5439;
	}
	else if(x.equals("DDO"))
	{
		rmi_port=2966;
		udp_port=5436;
	}
}

int get_rmi_port()
{
	return rmi_port;
}

int get_udp_port()
{
return udp_port;	
}


	private void insert(Record record,char first_letter)				//inserts the new recordinto arraylist and that arraylist into hashmap
	{
		if(map.containsKey(first_letter))
		{
			ArrayList<Record>old_arraylist=new ArrayList<Record>();
			old_arraylist=map.get(first_letter);

			ArrayList<Record>new_arraylist=new ArrayList<Record>();
			new_arraylist.addAll(0,old_arraylist);
			new_arraylist.add(record);
			map.remove(first_letter);
			map.put(first_letter,new_arraylist);
		}
		else
		{
			ArrayList<Record>new_arraylist=new ArrayList<Record>();
			new_arraylist.add(record);
			map.put(first_letter,new_arraylist);
		}

	}

	private int total_no_of_records()							//returns the total number of records on server
	{
		return (student_count+teacher_count);
	}


	private String [] send_and_recieve_packets(int port1,int port2)		//invoked server sends the count request to other two servers and recieve their reply
	{
		String result[]={"",""};
		try {

			InetAddress host=InetAddress.getLocalHost();
			byte_array_request="count".getBytes();
			ds = new DatagramSocket();
			data_packet_request_1 = new DatagramPacket(byte_array_request, byte_array_request.length, host, port1);//first packet is sent to first serve(port no1)
			this.ds.send(data_packet_request_1);
			data_packet_reply_1 = new DatagramPacket(byte_array_reply, byte_array_reply.length);
			this.ds.receive(data_packet_reply_1);

			ds = new DatagramSocket();
			data_packet_request_2 = new DatagramPacket(byte_array_request, byte_array_request.length, host, port2);
			ds.send(data_packet_request_2);																			//second packet send to second server
			data_packet_reply_2= new DatagramPacket(byte_array_reply, byte_array_reply.length);
			this.ds.receive(data_packet_reply_2);

			result[0] = new String(data_packet_reply_1.getData());
			result[1] = new String(data_packet_reply_2.getData());
		}
		catch(Exception e)
		{

		}

		return result;
	}
	public void user_details(String usr)
	{
		this.username=usr;
		System.out.println(username);
	}						//gets the username of manager logged in
	public Boolean createTRecord(String f_name,String l_name,String addr,String number,String spec,String loc)
	{																					//creates the teacher record
		synchronized (this) {
			teacher_count++;
			String formatted = String.format("%05d", teacher_count);
			record_id = "TR" + formatted;
		}
	Record r=new Record(record_id,f_name,l_name,addr,number,spec,loc);

	char first=l_name.charAt(0);
	insert(r,first);												//calls insert method to insert the new record	success_flag=true;
	logger.info("Teacher record created by "+username);
	return success_flag;
	}
	
	
	public Boolean createSRecord(String f_name,String l_name,String [] courses,String status,Date dat)
	{																			//creates student record

		synchronized (this) {
			student_count++;
			String formatted = String.format("%05d", student_count);
			record_id = "SR" + formatted;
		}
		Record r=new Record(record_id,f_name,l_name,courses,status,dat);
			
		char first=l_name.charAt(0);
		
		insert(r,first);												//calls insert method to insert the new record
		success_flag=true;
		logger.info("Student record created by "+username);
		return success_flag;
		}
	
	
		public String display()											//displays all the records on a server
		{
			StringBuffer str=new StringBuffer();
			String result="";
			for(Entry<Character, ArrayList<Record>> entry : map.entrySet()) {
				str.append("["+entry.getKey()+"]");
				for(Record x : entry.getValue())
				{
					str.append((x));
					str.append("\n");
				}
			}
			result=str.toString();
			logger.info("Display function performed by "+username);
			return result;
		}




	public String [] getRecordCount()				//returns the total number of records on all the servers
	{

		try
		{
		    int record_count=this.total_no_of_records();

		count_result[0]=this.server_name+" "+Integer.toString(record_count);
   		 String [] result={""};
		switch(server_name)
		{
			case "MTL": {

                result = send_and_recieve_packets(5436, 5439); //calls send_and_recieve_packets method to recieve count from other two servers
				count_result[1]=" LVL:"+result[0];
				count_result[2]=" DDO:"+result[1];
                break;
            }

			case "LVL":
			result=	send_and_recieve_packets(5434,5436);
				count_result[1]=" MTL:"+result[0];
				count_result[2]=" DDO:"+result[1];
				break;

			case "DDO":
			result=send_and_recieve_packets(5434,5439);
				count_result[1]=" MTL:"+result[0];
				count_result[2]=" LVL:"+result[1];
				break;
		}
			logger.info("gerRecordCount function performed by user "+username+"  "+count_result[0]+count_result[1]+count_result[2]);
		}
		catch(Exception e)
		{}
        return count_result;
	}


	public Boolean edit(String id,String field_name,String value)			//edit the record with given record id
	{

		for (Entry<Character, ArrayList<Record>> entry : map.entrySet()) {
			boolean flag = false;
			for (Record x : entry.getValue()) {
				if (id.equals(x.record_id)) {
					String sub = (x.record_id).substring(0, 2);
					if (sub.equals("TR")) {                                                //edit the record of teacher

						if (field_name.equals("address")) {
							x.address = value;
							success_flag = true;
							logger.info("Address is edited for record id:" + record_id + " by " + username);

						} else if (field_name.equals("phone")) {
							x.phone = value;
							success_flag = true;
							logger.info("Phone no is edited for record id:" + record_id + "by " + username);
						} else if (field_name.equals("location")) {
							if (value.equals("mtl") || value.equals("lvl") || value.equals("ddo")) {
								x.location = value;
								success_flag = true;
								logger.info("Location is edited for record id:" + record_id + " by " + username);
							} else {
								logger.info("Operation failed:invalid location");
								success_flag = false;
							}
						}
					}else                                                //edit the record of student
						{
							if (field_name.equals("status")) {

								if (value.equals("active") || value.equals("deactive")) {
									x.status = value;
									x.date = new Date(System.currentTimeMillis());
									success_flag = true;
									logger.info("Status is edited for record id:" + record_id + " by " + username);

								} else {
									success_flag = false;
									logger.info("Status edit failed by " + username);
								}
							} else {
								success_flag = false;
							}
						}

				}

			}

		}
		return success_flag;
	}
	

	public Boolean edit(String id,String field_name,String []s)			//edit the courses registered by student
	{
		for (Entry<Character, ArrayList<Record>> entry : map.entrySet())
		{
	        for(Record x : entry.getValue())
	        {
	        	if(id.equals(x.record_id))
	        	{
	        		if(field_name.equals("courses_Registered"))
	     		{
	        			x.courses_Registered=s;
	        			success_flag=true;
					logger.info("Courses registered for student id:"+record_id+"edited by "+username);
				}
			}
			else
				success_flag=false;
	        }
		}
			return success_flag;
	}


	public void UDPServer(int udpport) throws IOException						//starts the udp server on given port
	{	ds=new DatagramSocket(udpport);
		while(true)
		{
			byte[] a=new byte[1000];
			DatagramPacket data_packet=new DatagramPacket(a,a.length);
			ds.receive(data_packet);

			String data = new String(data_packet.getData());

			if(data.trim().equals("count"))
			{
				int count=this.total_no_of_records();

			byte [] b=(count+"").getBytes();
			DatagramPacket data_packet_respone=new DatagramPacket(b,b.length,data_packet.getAddress(),data_packet.getPort());
			ds.send(data_packet_respone);
			}

	}
	}
		

}
		






