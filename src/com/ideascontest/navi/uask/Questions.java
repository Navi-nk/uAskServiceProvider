package com.ideascontest.navi.uask;

public class Questions {
	private String _Id;          
	private String _Text;
	private String _Datetime;
	private String _Category;
	private Boolean _Flag;
	private String _Answer;
	private String _Used_Id;
	private String _Ans_User_Id;
	private Integer _Number_Answers;
	
	public Questions(String Id,String Text,String Datetime,String Category,Boolean Flag,String Answer,String UserId,String answers_userid,Integer number_answers ) {
		// TODO Auto-generated constructor stub
		_Id=Id;
		_Text=Text;
		_Datetime=Datetime;
		_Category=Category;
		_Flag=Flag;
		_Answer=Answer;
		_Used_Id=UserId;
		_Ans_User_Id = answers_userid;
		_Number_Answers = number_answers;
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
	public String get_Category() {
		return _Category;
	}
	public void set_Category(String _Category) {
		this._Category = _Category;
	}
	public Boolean get_Flag() {
		return _Flag;
	}
	public void set_Flag(Boolean _Flag) {
		this._Flag = _Flag;
	}
	public String get_Answer() {
		return _Answer;
	}
	public void set_Answer_Id(String _Answer) {
		this._Answer = _Answer;
	}
	public String get_Used_Id() {
		return _Used_Id;
	}
	public void set_Used_Id(String _Used_Id) {
		this._Used_Id = _Used_Id;
	}
	public String get_Ans_User_Id() {
		return _Ans_User_Id;
	}
	public void set_Ans_User_Id(String _Ans_User_Id) {
		this._Ans_User_Id = _Ans_User_Id;
	}
	public Integer get_Number_Answers() {
		return _Number_Answers;
	}
	public void set_Number_Answers(Integer _Number_Answers) {
		this._Number_Answers = _Number_Answers;
	}

}
