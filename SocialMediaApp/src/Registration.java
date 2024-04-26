import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registration {
	private static int userid;
    private String name;
    private String email;
    private String password;
    private String picture; 
    private String bio;
    
    public Registration(String name, String email, String password) {
    	userid++;
        System.out.println("Hello, Welcome to our project \nLets make your account");
        System.out.println("\nUsername and Password Format\n\nUsernames cannot\n1)Have special Characters\n2)Be less than 5 letters\n3)Be more than 5 letters");
        if(checkName(name) == false) {
        	System.out.println("Error! Invalid username format");
       this.name = "default_user" + userid;
        }
        else {
        	this.name=name;
        }
        
        this.email = email;
        	this.password= password;
        
    }
    
    // beginning of setters
  
    public void editName(String x) {
        this.name = x;
    }
    
    public void editEmail(String y) {
        this.email = y;
    }
    
    public void editPassword(String k) {
        this.password = k;
    }
    
    // beginning of getters
    
    public String seeName() {
        return name;
    }
    
    public String seeEmail() {
        return email;
    }
    
    public String seePassword() {
        return password;
    }
    
    // Error handling for name
    public boolean checkName(String username) {
    	int x = username.length();
    	if (x <5 || x > 15) 
    	{
    		return false;
    	}
    	
    	Pattern pattern = Pattern.compile("^(?=.*[a-zA=Z0-9])[a-zA-Z0-9_]+$");
    	Matcher matcher = pattern.matcher(username);
    	return matcher.matches();
    }
    
    
   
}
