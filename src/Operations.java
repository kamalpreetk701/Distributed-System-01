/**
 * Created by kamal on 6/5/2017.
 */
public class Operations {

    void Operations1(ServerInterface object,String username) {
        try {
            System.out.println("inside operations");
            object.user_details(username );
            object.createSRecord("first_name1", "last_name1", new String[]{"course1", "course2"}, "active", new java.util.Date(System.currentTimeMillis()));
            object.createSRecord("first_name2", "last_name2", new String[]{"course1", "course2", "course3"}, "active", new java.util.Date(System.currentTimeMillis()));
           // object.createSRecord("first_name3", "last_name3", new String[]{"course2", "course3"}, "active", new java.util.Date(System.currentTimeMillis()));
            object.createTRecord("t-first_name1", "last_name1", "#12 ,Vancouver", "+1 514 333 2435", "Physics", "Mtl");
            object.createTRecord("t-first_name2", "t-last_name2", "#123 ,Toronto", "+1 514 222 6754", "Chemistry", "DDo");
          //  object.createTRecord("t-first_name3", "t-last_name3", "#506,Montreal", "+1 514 222 6754", "Chemistry", "DDo");
            String result=object.display();
            System.out.println(result);
        } catch (Exception e) {

        }
    }
}


