public class Login {
    private String email;
    private String password;
    
    Login(){}
    Login(String email, String password){
        this.email = email;
        this.password = password;
    }
    String getemail(){
        return email;
    }
    String getpassword(){
        return password;
    }
    void setemail(String email) {
        this.email = email;
    }
    void setpassword(String password) {
        this.password = password;
    }
    public boolean checkPassword(String inputPassword){
        return this.password.equals(inputPassword);
    }
    
}
