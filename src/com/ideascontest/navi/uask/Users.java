package com.ideascontest.navi.uask;

public class Users {
	private String _name;
	private String _email;
	private String _pwd;
	private String _faculty;

	public Users(String name,String email, String pwd, String faculty) {
		// TODO Auto-generated constructor stub
		set_name(name);
		set_email(email);
		set_pwd(pwd);
		set_faculty(faculty);
	}

	public String get_name() {
		return _name;
	}

	public void set_name(String _name) {
		this._name = _name;
	}

	public String get_email() {
		return _email;
	}

	public void set_email(String _email) {
		this._email = _email;
	}

	public String get_pwd() {
		return _pwd;
	}

	public void set_pwd(String _pwd) {
		this._pwd = _pwd;
	}

	public String get_faculty() {
		return _faculty;
	}

	public void set_faculty(String _faculty) {
		this._faculty = _faculty;
	}
	
	

}
