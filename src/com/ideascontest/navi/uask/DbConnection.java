package com.ideascontest.navi.uask;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
public class DbConnection {
	private static String dbClass = "com.mysql.jdbc.Driver";
    private static String dbName = "uask";
    private static String dbUrl = "jdbc:mysql://localhost:3306/"+dbName;
    private static String dbUser = "root";
    private static String dbPwd = "Password@123";
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
            String query = "INSERT into users(name, email, password,faculty) values('"+usrObject.get_name()+ "',"+"'"
                    + usrObject.get_email() + "','" + usrObject.get_pwd() + "','" + usrObject.get_faculty() + "')";
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
        String query = " SELECT q.Question_Id,q.Question_Text,q.Question_Datetime,q.Question_Category,q.Question_Flag,IFNULL(a.Answer_text,0),q.Used_Id,a.answer_userid FROM questions q LEFT OUTER JOIN answers a ON q.Question_Id=a.Question_Id AND q.Answer_Id=a.Answer_Id ORDER BY q.Question_Datetime DESC";
        System.out.println(query);
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            Questions q = new Questions(Integer.toString(rs.getInt(1)), rs.getString(2), rs.getDate(3).toString(), rs.getString(4), rs.getBoolean(5), rs.getString(6), Integer.toString(rs.getInt(7)),Integer.toString(rs.getInt(8)));
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
        //System.out.println(query);
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            Answers a = new Answers(Integer.toString(rs.getInt(1)), rs.getString(2), rs.getDate(3).toString(), Integer.toString(rs.getInt(4)),Integer.toString(rs.getInt(5)));
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
        String query = "INSERT into questions(Question_Text,Question_Datetime,Question_Category,Question_Flag,Answer_Id,Used_Id) values('"+ qObj.get_Text() + "'," + "sysdate()" + ",'" + qObj.get_Category() + "'," + qObj.get_Flag() + "," + qObj.get_Answer() + "," + qObj.get_Used_Id() + ")";
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
        String query = "INSERT into answers(Answer_text,Answer_DateTime,Question_Id,Answer_UserID) values('"+ ansObj.get_Text() + "'," + "sysdate()" + "," + ansObj.get_Question_Id() + "," + ansObj.get_Answered_UserID() + ")";
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
    String query = "UPDATE QUESTIONS SET answer_id = LAST_INSERT_ID() WHERE question_id ="+ quesId;
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


}


