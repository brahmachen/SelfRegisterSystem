package beans;

import java.io.Serializable;

public class User implements Serializable{
	public String sno;
	public String password;
	public String newpassword;

	public User(){}
	public User(String sno, String password, String newpassword) {
		this.sno = sno;
		this.password = password;
		this.newpassword = newpassword;
	}

	public String getSno() {
		return sno;
	}
	public void setSno(String sno) {
		this.sno = sno;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNewpassword() {
		return newpassword;
	}
	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}

	@Override
	public String toString() {
		return "sno:"+sno+" password:"+password+" newpassword:"+newpassword;
	}
}
