package com.ideascontest.navi.uask;

public class Answers {

	private String _Id;          
	private String _Text;
	private String _Datetime;
	private String _Question_Id;
	private String _Answered_UserID;
	
	public Answers(String Id,String Text,String Datetime,String QuestionId,String Answered_UserID) {
		// TODO Auto-generated constructor stub
		set_Id(Id);
		set_Text(Text);
		set_Datetime(Datetime);
		set_Question_Id(QuestionId);
		set_Answered_UserID(Answered_UserID);
	}

	public String get_Id() {
		return _Id;
	}

	public void set_Id(String _Id) {
		this._Id = _Id;
	}

	public String get_Text() {
		return _Text;
	}

	public void set_Text(String _Text) {
		this._Text = _Text;
	}

	public String get_Datetime() {
		return _Datetime;
	}

	public void set_Datetime(String _Datetime) {
		this._Datetime = _Datetime;
	}

	public String get_Question_Id() {
		return _Question_Id;
	}

	public void set_Question_Id(String _Question_Id) {
		this._Question_Id = _Question_Id;
	}

	public String get_Answered_UserID() {
		return _Answered_UserID;
	}

	public void set_Answered_UserID(String _Answered_UserID) {
		this._Answered_UserID = _Answered_UserID;
	}

}
