package com.ideascontest.navi.uask;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
//Path: http://localhost:port/<appln-folder-name>/signup
@Path("/signup")
public class Signup {
	// HTTP Get Method
    @GET
    // Path: http://host:8080/<appln-folder-name>/signup/doregister
    @Path("/doregister")
    // Produces JSON as response
    @Produces(MediaType.APPLICATION_JSON) 
    // Query parameters are parameters: http://host/<appln-folder-name>/signup/doregister?username=abc&email=abc@xyz&password=mnp&faculty=123
    public String doRegister(@QueryParam("username") String uname, @QueryParam("email") String email,@QueryParam("password") String pwd, @QueryParam("faculty") String faculty){
        String response = "";
        Users usrObject = new Users(uname, email, pwd, faculty);
        try {
			if(DbConnection.insertUser(usrObject)){
			    response = Utilities.constructJSON("register",true);
			}else{
			    response = Utilities.constructJSON("register", false, "Invalid information");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    return response;        
    }
}
