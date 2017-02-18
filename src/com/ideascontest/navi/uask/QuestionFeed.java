package com.ideascontest.navi.uask;

import java.util.ArrayList;

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
    // Path: http://localhost/<appln-folder-name>/qfeed/getfeed
    @Path("/askques")
    // Produces JSON as response
    @Produces(MediaType.APPLICATION_JSON) 
    // Query parameters are parameters: http://localhost/<appln-folder-name>/qfeed/getfeed
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
    
	    // HTTP Get Method
	    @GET
	    // Path: http://localhost/<appln-folder-name>/qfeed/getfeed
	    @Path("/ansques")
	    // Produces JSON as response
	    @Produces(MediaType.APPLICATION_JSON) 
	    // Query parameters are parameters: http://localhost/<appln-folder-name>/qfeed/getfeed
	    public String ansQuestions(@QueryParam("answer") String ansText, @QueryParam("questionId") String qId,@QueryParam("userId") String uId){
	        String response = "";
	        Answers answer = new Answers(null, ansText, null, qId, uId);
	        try {
				if(DbConnection.insertAnswerDetails(answer)){
				    response = Utilities.constructJSON("answer",true);
				}else{
				    response = Utilities.constructJSON("answer", false, "Invalid information/unexpected exception");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}   return response;        

 }
    
}
