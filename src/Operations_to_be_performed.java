//Provides the implementation of menu of options of operations and function calls of that particular operation

import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Logger;

public class Operations_to_be_performed
{
	String first_Name,last_Name,status,phone,specilization,address,location,record_id,field_name,field_value;
	String [] courses_Registered={""};
	String [] count;
	String username;
	Date date;

	Boolean success;
	Scanner sc=new Scanner(System.in);
	 Logger	logger = Logger.getLogger(Logger.class.getName());

	int choice;
	void do_the_following(ServerInterface object,String usr) throws RemoteException  //Defines the function to be performed by server manager
	{
		logger.setUseParentHandlers(false);
		username=usr;
		object.user_details(username);

					while(true) {
						System.out.println("Choose your option:\n1.Create student record.\n2.Create teacher record.\n3.Edit the record\n4.Get record count\n5.Display records \n6.Exit");
						System.out.print("Enter your choice:");
						choice = sc.nextInt();
						sc.nextLine();

						switch (choice) {
							case 1: {
								System.out.println("Enter the Student first name:");
								first_Name = sc.nextLine();
								System.out.println("Enter the Student last name:");
								last_Name = sc.nextLine();
								System.out.println("Enter the courses registered:");
								String result = sc.nextLine();
								courses_Registered = result.split("\\s");
								System.out.println("Enter the Student status:");
								status = sc.nextLine();
								success = object.createSRecord(first_Name, last_Name, courses_Registered, status, new Date(System.currentTimeMillis()));
								if (success) {
									logger.info("Student record created with fields" + first_Name + " " + last_Name + " " + Arrays.toString(courses_Registered) + " " + status);
									System.out.println("Student record created with fields" + first_Name + " " + last_Name + " " + Arrays.toString(courses_Registered) + " " + status);

								}
								else {
									logger.info("Student record creation failed");
									System.out.println("Student record creation failed");
								}
								break;
							}
							case 2: {
								System.out.println("Enter the Teacher first name:");
								first_Name = sc.nextLine();
								System.out.println("Enter the Teacher last name:");
								last_Name = sc.nextLine();
								System.out.println("Enter the address:");
								address = sc.nextLine();
								System.out.println("Enter the phone no:");
								phone = sc.nextLine();
								System.out.println("Enter the specification:");
								specilization = sc.nextLine();
								System.out.println("Enter the location:");
								location = sc.nextLine();
								success = object.createTRecord(first_Name, last_Name, address, phone, specilization, location);
								if (success) {
									logger.info("Teacher record created with fields" + first_Name + " " + last_Name + " " + address + " " + phone + " " + specilization + " " + location);
									System.out.println("Teacher record created with fields" + first_Name + " " + last_Name + " " + address + " " + phone + " " + specilization + " " + location);

								}else {
									logger.info("Teacher record creation failed");
									System.out.println("Teacher record creation failed");

								}
								break;
							}
							case 3: {
								System.out.println("Enter the Record id[SR/TR0000N]");
								record_id = sc.nextLine();
								if (record_id.substring(0, 2).equals("TR")) {
									System.out.println("Enter the field name to edit:[address,phone,location]");
									field_name = sc.nextLine();
									System.out.println("Enter the  field's value:");
									field_value = sc.nextLine();
									success = object.edit(record_id, field_name, field_value);
									if (success) {
										logger.info("Teacher record edited");
										System.out.println("Teacher record edited");

									}
									else {
										logger.info("Edit operation failed");
										System.out.println("Edit operation failed");
									}

								} else {
									System.out.println("Enter the field name to edit:[courses_Registered,status]");
									field_name = sc.nextLine();
									if (field_name.equals("courses_Registered")) {
										System.out.println("Enter the  courses :");
										String result = sc.nextLine();
										courses_Registered = result.split(" ");

										success = object.edit(record_id, field_name, courses_Registered);
										if (success) {
											logger.info("Student record edited");
											System.out.println("Student record edited");
										}
										else {
											logger.info("Edit operation failed");
											System.out.println("Edit operation failed");
										}
									} else if(field_name.equals("status")){
										System.out.println("Enter the  status value:[active/deactive]");
										field_value = sc.nextLine();
										success = object.edit(record_id, field_name, field_value);
										if (success) {
											logger.info("Status edited");
											System.out.println("Status edited");
											}
										else {
											logger.info("Edit operation failed");
											System.out.println("Status edited");

										}
									}
								}
								break;

							}

							case 4: {
								count = object.getRecordCount();
								System.out.println(count[0]+count[1]+count[2]);
								if (success)
								{
									logger.info("Get record count method executed ");
									System.out.println("Get record count method executed");

								}
								else {
									logger.info("Get record count failed");
									System.out.println("Get record count failed");

								}
								break;
							}
							case 5: {
										String records=object.display();
										System.out.println(records);
									logger.info("Display function performed");

								break;
							}
							case 6:
								System.exit(0);
						}
					}
}
	}


