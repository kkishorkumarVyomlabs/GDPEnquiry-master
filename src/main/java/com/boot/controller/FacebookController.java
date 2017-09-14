package com.boot.controller;

import java.util.Map;

import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.boot.model.JavaModel;
import com.boot.model.Parameters;
import com.boot.model.Response_Mdl;
import com.boot.model.Result;
import com.boot.bussiness.ApiAi;
import com.boot.bussiness.Bussiness;

@RestController
@RequestMapping("api/v1/")
public class FacebookController {
	@Autowired
	public Environment env;

	@RequestMapping(value = "getgdp", method = RequestMethod.POST)
	public Response_Mdl post(@RequestBody String inputJSON) throws Exception {
		System.out.println("Request recieved");
		String email="";
		String country="";
		String year="";
		String result="";
		JavaModel JM_Response=null;
		ApiAi apiresponse=null;
		Result rs=null;
		Parameters params=null;
		

		try
		{
			System.out.println("Request recieved");
			apiresponse = new ApiAi();
			System.out.println("responceBO : "+apiresponse.toString());
		}
		catch(Exception e){e.printStackTrace();}

		
		try
		{
			JM_Response = apiresponse.jsonToJava(inputJSON);
			System.out.println("JM_Response : "+JM_Response);
		}
		catch(Exception e){e.printStackTrace();}

        try
        {
			rs=JM_Response.getResult();
			System.out.println("rs :"+rs.toString());
			params=rs.getParameters();
			email=params.getEmail();
			country=params.getCountry();
			year=params.getYear();
			
			Bussiness b1= new Bussiness();
			result=b1.getData(email,country,year);
			System.out.println(result);
			
        }
        catch(Exception e){e.printStackTrace();}
	
			
			Response_Mdl res=new Response_Mdl();
			res.setSource("policyWS");
			res.setSpeech(result);
			res.setDisplayText(result);
			
	return res;
	}
	

	@RequestMapping(value = {"getgdp" }, method = RequestMethod.GET)
	public String search(@RequestParam Map<String, String> allRequestParams, ModelMap model) {

		System.out.println("GET Request Recieved...");
		return "Welcome User...!";
	}

}
