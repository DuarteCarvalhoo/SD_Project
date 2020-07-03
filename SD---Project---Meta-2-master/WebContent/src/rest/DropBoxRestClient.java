package rest;

import java.awt.event.ContainerEvent;
import java.util.Arrays;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.github.scribejava.apis.DropBoxApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.exceptions.OAuthException;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Token;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.model.Verifier;
import com.github.scribejava.core.oauth.OAuthService;

import uc.sd.apis.DropBoxApi2;


// Step 1: Create Dropbox Account

// Step 2: Create Application (https://www.dropbox.com/developers)

public class DropBoxRestClient {


	// Access codes #1: per application used to get access codes #2	
	private static final String API_APP_KEY = "ei8a3263rntvqx0";
	private static final String API_APP_SECRET = "a9xed0b1e4pxfwk";
	
	// Access codes #2: per user per application
	private static final String API_USER_TOKEN = "";

    public static String getAccessTokenSecret(Token accessToken) {
        return accessToken.getSecret();
    }

    public static Token getAccessToken(Scanner in, OAuthService service, Verifier verifier) {
        return service.getAccessToken(null, verifier);
    }

    public static String getAuthorizationUrl(OAuthService service) {
        return service.getAuthorizationUrl(null);
    }

    public static OAuthService createService() {
        return new ServiceBuilder()
        .provider(DropBoxApi2.class)
        .apiKey(API_APP_KEY)
        .apiSecret(API_APP_SECRET)
        .callback("http://localhost:8080/dropboxRedirect.action")
        .build();
    }

    public static String listSharedFiles(OAuthService service, Token accessToken, String finalString) {
        OAuthRequest request = new OAuthRequest(Verb.POST, "https://api.dropboxapi.com/2/sharing/list_received_files", service);
        request.addHeader("authorization", "Bearer " + accessToken.getToken());
        request.addHeader("Content-Type",  "application/json");
        request.addPayload("{}");

        Response response = request.send();
        System.out.println("Got it! Lets see what we found...");
        System.out.println("HTTP RESPONSE: =============");
        System.out.println(response.getCode());
        System.out.println(response.getBody());
        System.out.println("END RESPONSE ===============");

        JSONObject rj = (JSONObject) JSONValue.parse(response.getBody());
        JSONArray contents = (JSONArray) rj.get("entries");
        String cursor = (String) rj.get("cursor");
        for (int i=0; i<contents.size(); i++) {
            JSONObject item = (JSONObject) contents.get(i);
            String fPath = (String) item.get("preview_url");
            String path = (String) item.get("name");
            finalString+= "'" + path+"' preview in "+fPath+"<br>";
        }
        return finalString;
    }

    public static String listFiles(String folderPath, OAuthService service, Token accessToken, String finalString) {
		OAuthRequest request = new OAuthRequest(Verb.POST, "https://api.dropboxapi.com/2/files/list_folder", service);
		request.addHeader("authorization", "Bearer " + accessToken.getToken());
		request.addHeader("Content-Type",  "application/json");
		request.addPayload("{\n" +
				"    \"path\":\""+folderPath+"\",\n" +
				"    \"recursive\": true,\n" +
				"    \"include_media_info\": false,\n" +
				"    \"include_deleted\": false,\n" +
				"    \"include_has_explicit_shared_members\": false,\n" +
				"    \"include_mounted_folders\": true\n" +
				"}");

		Response response = request.send();
		System.out.println("Got it! Lets see what we found...");
		System.out.println("HTTP RESPONSE: =============");
		System.out.println(response.getCode());
		System.out.println(response.getBody());
		System.out.println("END RESPONSE ===============");

		JSONObject rj = (JSONObject) JSONValue.parse(response.getBody());
		JSONArray contents = (JSONArray) rj.get("entries");
		Boolean hasMore = (Boolean) rj.get("has_more");
		String cursor = (String) rj.get("cursor");
        for (int i=0; i<contents.size(); i++) {
            JSONObject item = (JSONObject) contents.get(i);
            String isFolder = (String) item.get(".tag");
            if(isFolder.equals("folder")){
                String fPath = (String) item.get("path_display");
                String path = (String) item.get("name");
            }
            else{
                String fPath = (String) item.get("path_display");
                String path = (String) item.get("name");
                finalString+= "'" + path+"' in "+fPath+"<br>";
            }
        }
		if(hasMore){
		    finalString = listAllFiles(service,accessToken,finalString,cursor);
        }

        finalString = listSharedFiles(service,accessToken,finalString);
		return finalString;
	}

    public static String listAllFiles(OAuthService service, Token accessToken, String finalString, String cursor) {
        OAuthRequest request = new OAuthRequest(Verb.POST, "https://api.dropboxapi.com/2/files/list_folder/continue", service);
        request.addHeader("authorization", "Bearer " + accessToken.getToken());
        request.addHeader("Content-Type",  "application/json");
        request.addPayload("{\"cursor\": \""+cursor+"\"}");

        Response response = request.send();
        System.out.println("Got it! Lets see what we found...");
        System.out.println("HTTP RESPONSE: =============");
        System.out.println(response.getCode());
        System.out.println(response.getBody());
        System.out.println("END RESPONSE ===============");

        JSONObject rj = (JSONObject) JSONValue.parse(response.getBody());
        JSONArray contents = (JSONArray) rj.get("entries");
        Boolean hasMore = (Boolean) rj.get("has_more");
        String cursor1 = (String) rj.get("cursor");
        for (int i=0; i<contents.size(); i++) {
            JSONObject item = (JSONObject) contents.get(i);
            String isFolder = (String) item.get(".tag");
            if(isFolder.equals("folder")){
                String fPath = (String) item.get("path_display");
                String path = (String) item.get("name");
            }
            else{
                String fPath = (String) item.get("path_display");
                String path = (String) item.get("name");
                finalString+= "'" + path+"' in "+fPath+"<br>";
            }
        }
        if(hasMore){
            finalString = listAllFiles(service,accessToken,finalString,cursor1);
        }
        return finalString;
    }




    public static void addFile(String path1, OAuthService service, Token accessToken) {
        OAuthRequest request = new OAuthRequest(Verb.POST, "https://content.dropboxapi.com/2/files/upload", service);
        request.addHeader("authorization", "Bearer " + accessToken.getToken());
        request.addHeader("Dropbox-API-Arg", "{\"path\":\""+path1+"\",\"mode\":{\".tag\":\"add\"},\"autorename\":true,\"mute\":false,\"strict_conflict\":false}");
        request.addHeader("Content-Type",  "application/octet-stream");
        Response response = request.send();
        System.out.println("Got it! Lets see what we found...");
        System.out.println("HTTP RESPONSE: =============");
        System.out.println(response.getCode());
        System.out.println(response.getBody());
        System.out.println("END RESPONSE ===============");
	}

	public static void downloadFile(String path1, OAuthService service, Token accessToken){
        OAuthRequest request = new OAuthRequest(Verb.POST, "https://content.dropboxapi.com/2/files/download", service);
        request.addHeader("authorization", "Bearer " + accessToken.getToken());
        request.addHeader("Dropbox-API-Arg",  "{\"path\":\""+path1+"\"}");
        request.addHeader("Content-Type","application/octet-stream");
        Response response = request.send();
        System.out.println("Got it! Lets see what we found...");
        System.out.println("HTTP RESPONSE: =============");
        System.out.println(response.getCode());
        System.out.println(response.getBody());
        System.out.println("END RESPONSE ===============");
    }

	public static String getFileMetadata(String filePath, OAuthService service, Token accessToken){
        OAuthRequest request = new OAuthRequest(Verb.POST, "https://api.dropboxapi.com/2/files/get_metadata", service);
        request.addHeader("authorization", "Bearer " + accessToken.getToken());
        request.addHeader("Content-Type",  "application/json");
        request.addPayload("{\"path\":\""+filePath+"\"}");

        Response response = request.send();
        System.out.println("Got it! Lets see what we found...");
        System.out.println("HTTP RESPONSE: =============");
        System.out.println(response.getCode());
        System.out.println(response.getBody());
        System.out.println("END RESPONSE ===============");

        JSONObject rj = (JSONObject) JSONValue.parse(response.getBody());
        return (String) rj.get("id");
    }

    public static String getCurrentAccountMail(OAuthService service, Token accessToken){
        OAuthRequest request = new OAuthRequest(Verb.POST, "https://api.dropboxapi.com/2/users/get_current_account",service);
        request.addHeader("authorization", "Bearer " + accessToken.getToken());
        request.addHeader("Content-Type",  "application/json");
        request.addPayload("null");

        Response response = request.send();
        System.out.println("Got it! Lets see what we found...");
        System.out.println("HTTP RESPONSE: =============");
        System.out.println(response.getCode());
        System.out.println(response.getBody());
        System.out.println("END RESPONSE ===============");

        JSONObject rj = (JSONObject) JSONValue.parse(response.getBody());
        return (String) rj.get("email");
    }


	public static void shareFile(String mail,String fileId ,OAuthService service, Token accessToken){
        OAuthRequest request = new OAuthRequest(Verb.POST, "https://api.dropboxapi.com/2/sharing/add_file_member", service);
        request.addHeader("authorization", "Bearer " + accessToken.getToken());
        request.addHeader("Content-Type",  "application/json");
        request.addPayload("{\"file\":\""+fileId+"\",\"members\":[{\".tag\":\"email\",\"email\":\""+mail+"\"}],\"access_level\":{\".tag\":\"viewer\"}}");


        Response response = request.send();
        System.out.println("Got it! Lets see what we found...");
        System.out.println("HTTP RESPONSE: =============");
        System.out.println(response.getCode());
        System.out.println(response.getBody());
        System.out.println("END RESPONSE ===============");
    }
}
