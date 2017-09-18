import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Created by kamal on 6/5/2017.
 */

    public class Login
    {
        private String username = "";                                            			 //username of server manager
        private String password = "";                                                        //password of server manager
        private String record = "";
        private boolean flag = false;

        public  String user_login() {
            Logger logger;                                                                //Reference to Logger class is generated
            Handler fileHandler;
            SimpleFormatter plainText;
            Scanner s;

            try {//login details fetched from login.txt file
                BufferedReader br = new BufferedReader(new FileReader("D:/JAVA/DSassg1/Login.txt"));
                s = new Scanner(System.in);
                System.out.println("Enter your username: ");
                username = s.nextLine();
                System.out.println("Enter your password: ");
                password = s.nextLine();

                Scanner scan;


                while ((record = br.readLine()) != null) {                    //records fetched line by line

                    String[] split = record.split(" ");

                    if (username.equals(split[0]) && password.equals(split[1])) //username and password compared with data fetched
                    {
                        System.out.println(" LOGIN SUCCESSFULL");
                        flag = true;
                        logger = Logger.getLogger(Logger.class.getName());
                        logger.setUseParentHandlers(false);
                        File f = new File("D:/JAVA/assignment1/" + username + ".txt");
                        if (!f.exists())
                            f.createNewFile();

                        fileHandler = new FileHandler("D:/JAVA/assignment1/" + username + ".txt", true);//File for writting log is opened in append mode
                        plainText = new SimpleFormatter();
                        fileHandler.setFormatter(plainText);
                        logger.addHandler(fileHandler);
                        logger.info("User " + username + "logged in");

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (flag == false) {
                System.out.println("LOGIN UNSUCCESSFULL!!!! ");
                System.out.println("Try again: ");
                user_login();                                                    //if login fails,user reenter his details
            }
            return username;
        }


    }


