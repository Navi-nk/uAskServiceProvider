package com.ideascontest.navi.uask;

	import java.util.ArrayList;

import javax.ws.rs.GET;
	import javax.ws.rs.Path;
	import javax.ws.rs.Produces;
	import javax.ws.rs.QueryParam;
	import javax.ws.rs.core.MediaType;
	//Path: http://localhost:port/<appln-folder-name>/login
	@Path("/login")
	public class Login {
	    // HTTP Get Method
	    @GET
	    // Path: http://localhost/<appln-folder-name>/login/dologin
	    @Path("/dologin")
	    // Produces JSON as response
	    @Produces(MediaType.APPLICATION_JSON) 
	    // Query parameters are parameters: http://localhost/<appln-folder-name>/login/dologin?username=abc&password=xyz
	    public String doLogin(@QueryParam("username") String uname, @QueryParam("password") String pwd){
	        String response = "";
	        ArrayList<Users> users = null;
	        if(checkCredentials(uname, pwd)){
	        	try {
					users = DbConnection.getUsers(uname);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            response = Utilities.constructJSON("login",true,users);
	        }else{
	            response = Utilities.constructJSON("login", false, "Incorrect Email or Password");
	        }
	    return response;        
	    }
	 
	    /**
	     * Method to check whether the entered credential is valid
	     * 
	     * @param uname
	     * @param pwd
	     * @return
	     */
	    private boolean checkCredentials(String uname, String pwd){
	        System.out.println("Inside checkCredentials");
	        boolean result = false;
	        if(Utilities.isNotNull(uname) && Utilities.isNotNull(pwd)){
	            try {
	                result = DbConnection.checkLogin(uname, pwd);
	                //System.out.println("Inside checkCredentials try "+result);
	            } catch (Exception e) {
	                // TODO Auto-generated catch block
	                System.out.println("Inside checkCredentials catch");
	                result = false;
	            }
	        }else{
	            //System.out.println("Inside checkCredentials else");
	            result = false;
	        }
	 
	        return result;
	    }
	 
	}

