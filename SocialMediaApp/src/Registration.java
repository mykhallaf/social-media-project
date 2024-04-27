import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Scanner;
import java.util.Objects;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;


public class Registration {
		private static int userid;
		private int nametrials= 3;
		private int passtrials= 3;
	    private String name;
	    private String email;
	    private String password;
	    private String picture; 
	    private String bio;
	    
	    public Registration (String name, String email, String password) {
	    	userid++;
	        System.out.println("\nUsername and Password Format\n\nUsernames cannot\n⚫ Have special Characters\n⚫ Be less than 5 letters\n\nPasswords Must Contain at least\n⚫ 8 Characters\n⚫ 1 Capital Letter\n⚫ Numbers and Letters\n⚫ 1 Special Character ");
	        while(checkName(name)== false && nametrials !=0) {
	        	System.out.println("Invalid username format\nPlease try again\n\n " + nametrials +" trials left");
	        	Scanner x = new Scanner(System.in);
	        	nametrials--;
	        	String y = x.nextLine();
	        	name = y;
	        	if(nametrials == 0 && checkName(name)== false ) {
	        		this.name= "default_user" + userid;
		        	System.out.println("Your username has been set to " + this.name +" try to change it later\n\n");
	        	}
	        	
	        }
	        if(checkName(name)== true) {
	        	
	        	this.name=name;
	        }
	        
	        this.email = email;
	        
	        while(valPass(password) == false && passtrials !=0) {
	        	System.out.println("Invalid password format\nPlease try again\n\n " + passtrials + " trials left\n");
	        	Scanner x = new Scanner(System.in);
	        	String k = x.nextLine();
	        	passtrials--;
	        	password = k;
	        	if(passtrials==0) {
	        		this.password = "Password123@";
		        	System.out.println("Your password has been set to " + this.password + " try to change it as soon as possible");
	        	}
	        }
	        if(valPass(password) == true) {
	        	this.password=password;
	        	
	        }
	        
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
	    
	    // Error Handling for Password
	    
	    public boolean valPass(String pass) {
	    	int x = pass.length();
	    if(x > 7) {
	    	if(checkPassword(pass)) {
	    		return true;
	    	}
	    	else {
	    		return false;
	    	}
	    }
	    	else {return false;}
	    }
	    
	   public boolean checkPassword(String pass) {
		   boolean hasNum= false; boolean hasCap= false; boolean hasLow = false; char c;
		   int x = pass.length();
		   for (int i =0;i<x;i++) {
			   c = pass.charAt(i);
			   if(Character.isUpperCase(c)) {
				   hasCap = true;
			   }
			   else if(Character.isDigit(c)) {
				   hasNum = true;
			   }
			   else if(Character.isLowerCase(c)) {
				   hasLow = true;
			   }
		   }
		   if (hasNum && hasLow && hasCap) {
			   return true;
		   }
		   return false;
	   }
	   
	}
