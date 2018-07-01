import java.util.ArrayList;

public class User {
	private String userName;
	private String passWord;
	private ArrayList<Message> messages;
	
	public User(String uName, String pWord) {
		userName = uName;
		passWord = pWord;
		messages = new ArrayList<Message>();
	}
	
	public boolean checkMatch(String uName, String pWord) {
		return uName.equals(userName) && pWord.equals(passWord);
	}
	
	public String getUserName() {
		return userName;
	}
	
	public ArrayList<Message> getMessages() {
		return messages;
	}
	
	
	
}
