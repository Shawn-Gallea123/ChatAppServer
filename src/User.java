
public class User {
	private String userName;
	private String passWord;
	
	public User(String uName, String pWord) {
		userName = uName;
		passWord = pWord;
	}
	
	public boolean checkMatch(String uName, String pWord) {
		return uName.equals(userName) && pWord.equals(passWord);
	}
	
	public String getUserName() {
		return userName;
	}
	
	
	
}
