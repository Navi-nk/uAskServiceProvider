package com.ideascontest.navi.uask;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
public class DbConnection {
	
	
	// Modified by Diva = Cloud RDS
	private static String dbClass = "com.mysql.jdbc.Driver";
	private static String dbUrl = "jdbc:mysql://uaskdb.cet8xrcvhbvq.us-east-1.rds.amazonaws.com:3306/uask";
	private static String dbUser = "uaskdb";
	private static String dbPwd = "uaskdbcloud";
	
	@SuppressWarnings("finally")
	public static Connection createConnection() throws Exception {
		Connection con = null;
		try {
			Class.forName(dbClass);
			con = DriverManager.getConnection(dbUrl, dbUser, dbPwd);
		} catch (Exception e) {
			throw e;
		} finally {
			return con;
		}
	}
	/**
	 * Method to check whether uname and pwd combination are correct
	 * 
	 * @param uname
	 * @param pwd
	 * @return
	 * @throws Exception
	 */
	public static boolean checkLogin(String uname, String pwd) throws Exception {
		boolean isUserAvailable = false;
		Connection dbConn = null;
		try {
			try {
				dbConn = DbConnection.createConnection();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Statement stmt = dbConn.createStatement();
			String query = "SELECT name,email,faculty FROM users WHERE name = '" + uname
					+ "' AND password=" + "'" + pwd + "'";
			System.out.println(query);
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				System.out.println(rs.getString(1) + rs.getString(2) + rs.getString(3));
				isUserAvailable = true;
			}
		} catch (SQLException sqle) {
			throw sqle;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			if (dbConn != null) {
				dbConn.close();
			}
			throw e;
		} finally {
			if (dbConn != null) {
				dbConn.close();
			}
		}
		return isUserAvailable;
	}

	/**
	 * Method to insert user details to DB
	 * 
	 * @param usrObject
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public static boolean insertUser(Users usrObject) throws SQLException, Exception {
		boolean insertStatus = false;
		Connection dbConn = null;
		try {
			try {
				dbConn = DbConnection.createConnection();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Statement stmt = dbConn.createStatement();
			String query = "INSERT into users(name, email,faculty) values('"+usrObject.get_name()+ "',"+"'"
					+ usrObject.get_email() +  "','" + usrObject.get_faculty() + "')";
			System.out.println(query);
			int records = stmt.executeUpdate(query);
			//System.out.println(records);
			//When record is successfully inserted
			if (records > 0) {
				insertStatus = true;
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			//throw sqle;
		} catch (Exception e) {
			e.printStackTrace();
			// TODO Auto-generated catch block
			if (dbConn != null) {
				dbConn.close();
			}
			//throw e;
		} finally {
			if (dbConn != null) {
				dbConn.close();
			}
		}
		return insertStatus;
	}

	/**
	 * Method to get user data
	 * 
	 * @param 
	 * @return 
	 * @throws SQLException
	 * @throws Exception
	 */
	public static ArrayList<Users> getUsers(String userName) throws SQLException, Exception {

		ArrayList<Users> userlist = new ArrayList<Users>();

		Connection dbConn = null;
		try {
			try {
				dbConn = DbConnection.createConnection();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Statement stmt = dbConn.createStatement();

			String query = " SELECT name, email,faculty from users where name='"+userName+"'";
			System.out.println(query);
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				Users user= new Users(rs.getString(1), rs.getString(2), null, rs.getString(3));
				userlist.add(user);
			}
		} catch (SQLException sqle) {
			throw sqle;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			if (dbConn != null) {
				dbConn.close();
			}
			throw e;
		} finally {
			if (dbConn != null) {
				dbConn.close();
			}
		}

		return userlist;
	}


	/**
	 * Method to get all questions from DB
	 * 
	 * @param 
	 * @return 
	 * @throws SQLException
	 * @throws Exception
	 */
	public static ArrayList<Questions> getAllQuestions() throws SQLException, Exception {

		ArrayList<Questions> allQuestions = new ArrayList<Questions>();

		Connection dbConn = null;
		try {
			try {
				dbConn = DbConnection.createConnection();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Statement stmt = dbConn.createStatement();
			String query = " SELECT q.Question_Id,q.Question_Text,q.Question_Datetime,q.Question_Category,q.Question_Flag,IFNULL(a.Answer_text,0),q.Used_Id,a.answer_userid,qc.cnt FROM questions q LEFT OUTER JOIN answers a ON q.Question_Id=a.Question_Id AND q.Answer_Id=a.Answer_Id LEFT OUTER JOIN (select count(1) cnt,question_id from answers group by question_id) qc ON q.question_id=qc.question_id where q.question_flag=0 ORDER BY q.Question_Datetime DESC";
			System.out.println(query);
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				Questions q = new Questions(Integer.toString(rs.getInt(1)), rs.getString(2), rs.getDate(3).toString(), rs.getString(4), rs.getBoolean(5), rs.getString(6), rs.getString(7),rs.getString(8),rs.getInt(9));
				allQuestions.add(q);
			}
		} catch (SQLException sqle) {
			throw sqle;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			if (dbConn != null) {
				dbConn.close();
			}
			throw e;
		} finally {
			if (dbConn != null) {
				dbConn.close();
			}
		}

		return allQuestions;
	}

	/**
	 * Method to get all questions for a category from DB
	 * 
	 * @param 
	 * @return 
	 * @throws SQLException
	 * @throws Exception
	 */
	public static ArrayList<Questions> getAllCategoryQuestions(String category) throws SQLException, Exception {

		ArrayList<Questions> allQuestions = new ArrayList<Questions>();

		Connection dbConn = null;
		try {
			try {
				dbConn = DbConnection.createConnection();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Statement stmt = dbConn.createStatement();
			String query = " SELECT q.Question_Id,q.Question_Text,q.Question_Datetime,q.Question_Category,q.Question_Flag,IFNULL(a.Answer_text,0),q.Used_Id,a.answer_userid,qc.cnt FROM questions q LEFT OUTER JOIN answers a ON q.Question_Id=a.Question_Id AND q.Answer_Id=a.Answer_Id LEFT OUTER JOIN (select count(1) cnt,question_id from answers group by question_id) qc ON q.question_id=qc.question_id where q.question_category='"+category+"' and q.question_flag=0 ORDER BY q.Question_Datetime DESC";
			System.out.println(query);
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				Questions q = new Questions(Integer.toString(rs.getInt(1)), rs.getString(2), rs.getDate(3).toString(), rs.getString(4), rs.getBoolean(5), rs.getString(6), rs.getString(7),rs.getString(8),rs.getInt(9));
				allQuestions.add(q);
			}
		} catch (SQLException sqle) {
			throw sqle;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			if (dbConn != null) {
				dbConn.close();
			}
			throw e;
		} finally {
			if (dbConn != null) {
				dbConn.close();
			}
		}

		return allQuestions;
	}


	/**
	 * Method to get all questions from a user from DB
	 * 
	 * @param 
	 * @return 
	 * @throws SQLException
	 * @throws Exception
	 */
	public static ArrayList<Questions> getAllQuestionsFromUser(String user) throws SQLException, Exception {

		ArrayList<Questions> allQuestions = new ArrayList<Questions>();

		Connection dbConn = null;
		try {
			try {
				dbConn = DbConnection.createConnection();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Statement stmt = dbConn.createStatement();
			String query = " SELECT q.Question_Id,q.Question_Text,q.Question_Datetime,q.Question_Category,q.Question_Flag,IFNULL(a.Answer_text,0),q.Used_Id,a.answer_userid,qc.cnt FROM questions q LEFT OUTER JOIN answers a ON q.Question_Id=a.Question_Id AND q.Answer_Id=a.Answer_Id LEFT OUTER JOIN (select count(1) cnt,question_id from answers group by question_id) qc ON q.question_id=qc.question_id where q.Used_Id='"+user+"' ORDER BY q.Question_Datetime DESC";
			System.out.println(query);
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				Questions q = new Questions(Integer.toString(rs.getInt(1)), rs.getString(2), rs.getDate(3).toString(), rs.getString(4), rs.getBoolean(5), rs.getString(6), rs.getString(7),rs.getString(8),rs.getInt(9));
				allQuestions.add(q);
			}
		} catch (SQLException sqle) {
			throw sqle;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			if (dbConn != null) {
				dbConn.close();
			}
			throw e;
		} finally {
			if (dbConn != null) {
				dbConn.close();
			}
		}

		return allQuestions;
	}


	/**
	 * Method to get all questions answered by a user from DB
	 * 
	 * @param 
	 * @return 
	 * @throws SQLException
	 * @throws Exception
	 */
	public static ArrayList<Questions> getAllQuestionsAnsweredbyUser(String user) throws SQLException, Exception {

		ArrayList<Questions> allQuestions = new ArrayList<Questions>();

		Connection dbConn = null;
		try {
			try {
				dbConn = DbConnection.createConnection();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Statement stmt = dbConn.createStatement();
			String query = "SELECT q.Question_Id,q.Question_Text,q.Question_Datetime,q.Question_Category,q.Question_Flag,a.Answer_text,q.Used_Id,a.answer_userid,qc.cnt FROM questions q INNER JOIN answers a ON q.Question_Id=a.Question_Id INNER JOIN (select count(1) cnt,question_id from answers group by question_id) qc ON q.question_id=qc.question_id where a.answer_userId='"+user+"' ORDER BY q.Question_Datetime DESC";
			System.out.println(query);
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				Questions q = new Questions(Integer.toString(rs.getInt(1)), rs.getString(2), rs.getDate(3).toString(), rs.getString(4), rs.getBoolean(5), rs.getString(6), rs.getString(7),rs.getString(8),rs.getInt(9));
				allQuestions.add(q);
			}
		} catch (SQLException sqle) {
			throw sqle;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			if (dbConn != null) {
				dbConn.close();
			}
			throw e;
		} finally {
			if (dbConn != null) {
				dbConn.close();
			}
		}

		return allQuestions;
	}


	/**
	 * Method get all answers of a question to DB
	 * 
	 * @param question id
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */

	public static ArrayList<Answers> getAllAnswers(String qID) throws SQLException, Exception {

		ArrayList<Answers> allAnswers = new ArrayList<Answers>();

		Connection dbConn = null;
		try {
			try {
				dbConn = DbConnection.createConnection();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Statement stmt = dbConn.createStatement();
			String query = "SELECT Answer_Id,Answer_text,Answer_DateTime,Question_Id,Answer_UserID FROM answers WHERE question_id='"+ qID +"' ORDER BY Answer_DateTime DESC";
			System.out.println(query);
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				Answers a = new Answers(Integer.toString(rs.getInt(1)), rs.getString(2), rs.getDate(3).toString(), Integer.toString(rs.getInt(4)),rs.getString(5));
				allAnswers.add(a);
			}
		} catch (SQLException sqle) {
			throw sqle;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			if (dbConn != null) {
				dbConn.close();
			}
			throw e;
		} finally {
			if (dbConn != null) {
				dbConn.close();
			}
		}

		return allAnswers;
	}

	/**
	 * Method to insert questions to DB
	 * 
	 * @param usrObject
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public static boolean insertQuestionDetails(Questions qObj) throws SQLException, Exception {
		boolean insertStatus = false;
		Connection dbConn = null;
		try {
			try {
				dbConn = DbConnection.createConnection();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//use prepare stmt to handle special chars like '
			Statement stmt = dbConn.createStatement();
			String query = "INSERT into questions(Question_Text,Question_Datetime,Question_Category,Question_Flag,Answer_Id,Used_Id) values ('"+ qObj.get_Text() + "'," + "sysdate()" + ",'" + qObj.get_Category() + "'," + qObj.get_Flag() + ",'" + qObj.get_Answer() + "','" + qObj.get_Used_Id() + "')";
			System.out.println(query);
			int records = stmt.executeUpdate(query);
			//System.out.println(records);
			//When record is successfully inserted
			if (records > 0) {
				insertStatus = true;
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			//throw sqle;
		} catch (Exception e) {
			e.printStackTrace();
			// TODO Auto-generated catch block
			if (dbConn != null) {
				dbConn.close();
			}
			//throw e;
		} finally {
			if (dbConn != null) {
				dbConn.close();
			}
		}
		return insertStatus;
	}

	/**
	 * Method to insert answers to DB
	 * 
	 * @param ansObj
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public static boolean insertAnswerDetails(Answers ansObj) throws SQLException, Exception {
		boolean insertStatus = false;
		Connection dbConn = null;
		try {
			try {
				dbConn = DbConnection.createConnection();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Statement stmt = dbConn.createStatement();
			String query = "INSERT into answers(Answer_text,Answer_DateTime,Question_Id,Answer_UserID) values('"+ ansObj.get_Text() + "'," + "sysdate()" + "," + ansObj.get_Question_Id() + ",'" + ansObj.get_Answered_UserID() + "')";
			System.out.println(query);
			int records = stmt.executeUpdate(query);
			//System.out.println(records);
			//When record is successfully inserted
			if (records > 0) {
				insertStatus = true;
				insertStatus &= updateQuestiontable(dbConn, ansObj.get_Question_Id());
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			//throw sqle;
		} catch (Exception e) {
			e.printStackTrace();
			// TODO Auto-generated catch block
			if (dbConn != null) {
				dbConn.close();
			}
			//throw e;
		} finally {
			if (dbConn != null) {
				dbConn.close();
			}
		}
		return insertStatus;
	}

	private static boolean updateQuestiontable(Connection dbConn, String quesId) throws SQLException, Exception{
		// TODO Auto-generated method stub
		boolean updateStatus = false;
		Statement stmt = dbConn.createStatement();
		String query = "UPDATE questions SET answer_id = LAST_INSERT_ID() WHERE question_id ="+ quesId;
		System.out.println(query);
		try{
			int records = stmt.executeUpdate(query);
			//System.out.println(records);
			//When record is successfully inserted

			if (records > 0) {
				updateStatus = true;
			}
		}
		catch(SQLException sqle)
		{
			sqle.printStackTrace();
		}
		return updateStatus;
	}


	/**
	 * Method to get all private questions from DB
	 * 
	 * @param 
	 * @return 
	 * @throws SQLException
	 * @throws Exception
	 */
	public static ArrayList<Questions> getAllPrivateQuestions(String faculty) throws SQLException, Exception {

		ArrayList<Questions> allQuestions = new ArrayList<Questions>();

		Connection dbConn = null;
		try {
			try {
				dbConn = DbConnection.createConnection();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Statement stmt = dbConn.createStatement();
			String query = "SELECT q.Question_Id,q.Question_Text,q.Question_Datetime,q.Question_Category,q.Question_Flag,IFNULL(a.Answer_text,0),q.Used_Id,IFNULL(a.answer_userid,0),IFNULL(qc.cnt,0) FROM questions q LEFT OUTER JOIN answers a ON q.Question_Id=a.Question_Id AND q.Answer_Id=a.Answer_Id LEFT OUTER JOIN (select count(1) cnt,question_id from answers group by question_id) qc ON q.question_id=qc.question_id where q.question_flag=1 and q.used_id in (select trim(name) from users where faculty='"+faculty+"') ORDER BY q.Question_Datetime DESC";
			System.out.println(query);
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				Questions q = new Questions(Integer.toString(rs.getInt(1)), rs.getString(2), rs.getDate(3).toString(), rs.getString(4), rs.getBoolean(5), rs.getString(6), rs.getString(7),rs.getString(8),rs.getInt(9));
				allQuestions.add(q);
			}
		} catch (SQLException sqle) {
			throw sqle;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			if (dbConn != null) {
				dbConn.close();
			}
			throw e;
		} finally {
			if (dbConn != null) {
				dbConn.close();
			}
		}

		return allQuestions;
	}

	// Modified by Diva = Get Email of user to send mail
	public static HashMap<String, String> getEmailUserDetails(String questId)
			throws SQLException, Exception
	{
		Connection dbConn = null;
		String userId = "";
		String questionText = "";
		HashMap<String, String> emailDetailsMap = 
				new HashMap<String, String>();
		try
		{
			try {
				dbConn = createConnection();
			} catch (Exception e) {
				e.printStackTrace();
			}
			java.sql.Statement stmt = dbConn.createStatement();
			String query1 = "select Used_Id,Question_Text from questions where Question_Id='" + questId + "'";
			System.out.println(query1);
			ResultSet rs1 = stmt.executeQuery(query1);
			while (rs1.next()) {
				userId = rs1.getString(1);
				questionText = rs1.getString(2);
				emailDetailsMap.put("question", questionText);
			}

			if (userId != null) {
				String query2 = "select email from users where name='" + userId + "'";
				System.out.println(query2);
				ResultSet rs2 = stmt.executeQuery(query2);
				while (rs2.next()) {
					String email = rs2.getString(1);
					emailDetailsMap.put("email", email);
				}
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();

			if (dbConn != null) {
				dbConn.close();
			}
		}
		finally {
			if (dbConn != null) {
				dbConn.close();
			}
		}
		return emailDetailsMap;
	}

}


