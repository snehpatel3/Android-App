package BackendStuff;

public class Users {
	private String userid; 
	private String password; 
	private String pos; 
	
	public Users(String username, String pass, String position){
		this.userid = username; 
		this.password = pass;
		this.pos = position;
	}
	
	public String getUsername(){
		return this.userid;
	}
	public String getPassword(){
		return this.password;
	}
	
	public String getPosition(){
		return this.pos;
	}
	
}
