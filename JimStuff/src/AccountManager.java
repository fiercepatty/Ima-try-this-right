
import java.util.HashMap;
import java.util.Map;

public class AccountManager {
    static int userID = 1000;
    static int adminID = 1;
    static AccountManager instance = new AccountManager();
    
    
    boolean value;
    int num;
    String other;
    byte[] starter;

    public enum Error{
        SUCCESS, DUPLICATEACCOUNT, BADEMAIL
    }
    
    class User
    {
        String username;    //email address
        String password;
        int Identification;
        byte[] Avatar;
        protected boolean loggedIn;
      
        User(String username, String password, int user ){
            this.username = username;
            this.password = password;
            this.Identification = user;
            this.Avatar = null;
            loggedIn = false;
            
        }
        String getUsername()
        {
            return this.username;
        }
        String getPassword()
        {
            return this.password;
        }
        int getID()
        {
            return this.Identification;
        }
        void setID(int ID)
        {
            this.Identification = ID;
        }
        void setAvatar(byte[] newavatar)
        {
            this.Avatar = newavatar;
        }
        byte[] getAvatar()
        {
            return this.Avatar;
        }
        public boolean getLoggedIn()
        {
            return loggedIn;
        }
        public void setLoggedIn(boolean status)
        {
            loggedIn = status;
        }
    }
    protected static Map<String, User> users = new HashMap<String,User>();
    
    
    static boolean login(String username, String password) {
        if(users.isEmpty()){
            return false;
        }
        if(!users.get(username).getLoggedIn())
        {
            users.get(username).setLoggedIn(true);
        }
        else{
            return false;
        }
        return (users.get(username).getPassword().equals(password));
    }
    
    static boolean logout(String username)
    {
        if(users.get(username).getLoggedIn()==true)
        {
            users.get(username).setLoggedIn(false);
            return true;
        }
        return false;
    }
    
    
    void clear(){
        //clear out the account database
        users.clear();
    }
    
    static boolean verifyUser(String username, String password)
    {
        return (users.get(username) != null);
       
    }

    Error addUser(String emailAddress, String password) {
        // Returns true if user could be added; false if not (ex: Duplicate or invalid username)
        String[] tmp = emailAddress.split("@");
        if( tmp.length != 2 )               //no @ at all
            return Error.BADEMAIL;
        if( tmp[0].length() == 0 )      //@bob ->  [ "", "bob" ]
            return Error.BADEMAIL;
        if( tmp[1].length() == 0 )      // bob@ -> [ "bob", "" ]
            return Error.BADEMAIL;   
        if( users.containsKey(emailAddress))
            return Error.DUPLICATEACCOUNT;
        else{
            users.put(emailAddress, new User( emailAddress, password, AccountManager.userID));
            AccountManager.userID ++;
            return Error.SUCCESS;
        }
    }

    int getUID(String username) {
        this.num = 0;
        users.entrySet().forEach((mapElement) -> {
            String key = (String)mapElement.getKey(); 
            User okey = (User) mapElement.getValue();
            if (key.equals(username)){
                int marks = okey.getID();
                this.num =marks;
            }
        });
        if (this.num == 0)
        {
            return -1;
        }
        else
        {
            return this.num;
        }
    }

    String getEmail(int userID) {
        this.other = null;
        users.entrySet().forEach((mapElement) -> {
            String key = (String)mapElement.getKey(); 
            User okey = (User) mapElement.getValue();
            if (okey.getID() == userID)
            {
                String marks = okey.getUsername();
                this.other =marks;
            }
        });
        if (this.other == null)
        {
            return null;
        }
        else
        {
            return this.other;
        }
    }

    boolean isAdmin(int userID) 
    {
        //If userID is  less than 1000 than admin
        return this.getUID(this.getEmail(userID)) < 1000;
    }

    boolean setAdmin(int userID, boolean isAdmin) 
    {
        this.num = 0;
        users.entrySet().forEach((mapElement) -> {
            String key = (String)mapElement.getKey(); 
            User okey = (User) mapElement.getValue();
            if (okey.getID() == userID && isAdmin == true){
                okey.setID(AccountManager.adminID);
                AccountManager.adminID ++;
            }
        });
        return this.isAdmin(userID);
    }

    byte[] getAvatar(int userID)
    {
        this.starter = null;
        users.entrySet().forEach((mapElement) -> {
            String key = (String)mapElement.getKey(); 
            User okey = (User) mapElement.getValue();
            if (okey.getID() == userID)
            {
                byte[] marks = okey.getAvatar();
                this.starter =marks;
            }
        });
        if (this.starter == null)
        {
            return null;
        }
        else
        {
            return this.starter;
        }
    }

    boolean setAvatar(int userID, byte[] img) {
        this.value = false;
        users.entrySet().forEach((mapElement) -> {
            String key = (String)mapElement.getKey(); 
            User okey = (User) mapElement.getValue();
            if (okey.getID() == userID)
            {
                okey.setAvatar(img);
                this.value =true;
            }
        });
        return this.value != false; // Sets a user's avatar. Returns true for OK, false if not.
    }
}
