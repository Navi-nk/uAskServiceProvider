package com.ideascontest.navi.uask;

import java.util.ArrayList;
import java.util.HashMap;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.server.model.ParamQualifier;

//Path: http://localhost:port/<appln-folder-name>/qfeed
@Path("/qfeed")
public class QuestionFeed {

	// HTTP Get Method
	@GET
	// Path: http://localhost/<appln-folder-name>/qfeed/getfeed
	@Path("/getfeed")
	// Produces JSON as response
	@Produces(MediaType.APPLICATION_JSON) 
	// Query parameters are parameters: http://localhost/<appln-folder-name>/qfeed/getfeed
	public String getQuestions(){
		String response = "";
		ArrayList<Questions> qFeed = new ArrayList<Questions>();
		try {
			qFeed = DbConnection.getAllQuestions();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(!qFeed.isEmpty()){
			response = Utilities.constructJSON(qFeed);
		}else{
			response = Utilities.constructJSON("feed", false, "Feed not available");
		}
		return response;        
	}

	// HTTP Get Method
	@GET
	// Path: http://localhost/<appln-folder-name>/getans
	@Path("/getans")
	// Produces JSON as response
	@Produces(MediaType.APPLICATION_JSON) 
	// Query parameters are parameters: http://localhost/<appln-folder-name>/getans?
	public String getAnswers(@QueryParam("question") String qID){
		String response = "";
		System.out.println("Inside getAnswer:input="+qID);
		ArrayList<Answers> qAns = new ArrayList<Answers>();
		try {
			qAns = DbConnection.getAllAnswers(qID);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(!qAns.isEmpty()){
			response = Utilities.constructJSON(qAns);
		}else{
			response = Utilities.constructJSON("Answers", false, "Answers Not Available");
		}
		return response;        
	}

	// HTTP Get Method
	@GET
	// Path: http://localhost/<appln-folder-name>/qfeed/askques
	@Path("/askques")
	// Produces JSON as response
	@Produces(MediaType.APPLICATION_JSON) 
	// Query parameters are parameters: http://localhost/<appln-folder-name>/qfeed/ansques?question=&category=&flag=&userId=
	public String askQuestions(@QueryParam("question") String qText, @QueryParam("category") String qCat, @QueryParam("flag") boolean qFlag,@QueryParam("userId") String uId){
		String response = "";
		Questions question = new Questions(null, qText, null, qCat, qFlag, "0", uId,"0", 0);

		try {
			if(DbConnection.insertQuestionDetails(question)){
				response = Utilities.constructJSON("query",true);
			}else{
				response = Utilities.constructJSON("query", false, "Invalid information/unexpected exception");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   return response;        
	}

	@GET
	@Path("/ansques")
	@Produces({"application/json"})
	public String ansQuestions(@QueryParam("answer") String ansText, @QueryParam("questionId") String qId, @QueryParam("userId") String uId)
	{
		String response = "";
		Answers answer = new Answers(null, ansText, null, qId, uId);
		try {
			if (DbConnection.insertAnswerDetails(answer)) {
				response = Utilities.constructJSON("answer", true);
				HashMap<String, String> emailDetailsMap = 
						DbConnection.getEmailUserDetails(qId);
				SendEmail.composeEmail(
						(String)emailDetailsMap.get("email"), (String)emailDetailsMap.get("question"), ansText); // Modified by Diva = Send Email
			} else {
				response = Utilities.constructJSON("answer", false, "Invalid information/unexpected exception");
			}
		}
		catch (Exception e) {
			e.printStackTrace(); }
		return response;
	}

	// HTTP Get Method
	@GET
	// Path: http://localhost/<appln-folder-name>/qfeed/getcatfeed
	@Path("/getcatfeed")
	// Produces JSON as response
	@Produces(MediaType.APPLICATION_JSON) 
	// Query parameters are parameters: http://localhost/<appln-folder-name>/qfeed/getcatfeed?category=
	public String getCategoryQuestions(@QueryParam("category") String catText){
		String response = "";
		System.out.println("Category choosen:"+catText);
		ArrayList<Questions> qFeed = new ArrayList<Questions>();
		try {
			qFeed = DbConnection.getAllCategoryQuestions(catText);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(!qFeed.isEmpty()){
			response = Utilities.constructJSON(qFeed);
		}else{
			response = Utilities.constructJSON("feed", false, "Feed not available");
		}
		return response;        
	}

	// HTTP Get Method
	@GET
	// Path: http://localhost/<appln-folder-name>/qfeed/getuserques
	@Path("/getuserques")
	// Produces JSON as response
	@Produces(MediaType.APPLICATION_JSON) 
	// Query parameters are parameters: http://localhost/<appln-folder-name>/qfeed/getuserques?userId=
	public String getQuestionsFromUser(@QueryParam("userId") String user){
		String response = "";
		System.out.println("userId:"+user);
		ArrayList<Questions> qFeed = new ArrayList<Questions>();
		try {
			qFeed = DbConnection.getAllQuestionsFromUser(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(!qFeed.isEmpty()){
			response = Utilities.constructJSON(qFeed);
		}else{
			response = Utilities.constructJSON("feed", false, "Feed not available");
		}
		return response;        
	}

	// HTTP Get Method
	@GET
	// Path: http://localhost/<appln-folder-name>/qfeed/getuserqa
	@Path("/getuserqa")
	// Produces JSON as response
	@Produces(MediaType.APPLICATION_JSON) 
	// Query parameters are parameters: http://localhost/<appln-folder-name>/qfeed/getuserqa?userId=
	public String getQuestionsAnsweredByUser(@QueryParam("userId") String user){
		String response = "";
		System.out.println("userId:"+user);
		ArrayList<Questions> qFeed = new ArrayList<Questions>();
		try {
			qFeed = DbConnection.getAllQuestionsAnsweredbyUser(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(!qFeed.isEmpty()){
			response = Utilities.constructJSON(qFeed);
		}else{
			response = Utilities.constructJSON("feed", false, "Feed not available");
		}
		return response;        
	}

	// HTTP Get Method
	@GET
	// Path: http://localhost/<appln-folder-name>/qfeed/getprivatefeed
	@Path("/getprivatefeed")
	// Produces JSON as response
	@Produces(MediaType.APPLICATION_JSON) 
	// Query parameters are parameters: http://localhost/<appln-folder-name>/qfeed/getprivatefeed?faculty=
	public String getallPrivateQuestions(@QueryParam("faculty") String faculty){
		String response = "";
		System.out.println("user faculty:"+faculty);
		ArrayList<Questions> qFeed = new ArrayList<Questions>();
		try {
			qFeed = DbConnection.getAllPrivateQuestions(faculty);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(!qFeed.isEmpty()){
			response = Utilities.constructJSON(qFeed);
		}else{
			response = Utilities.constructJSON("feed", false, "Feed not available");
		}
		return response;        
	}
}
