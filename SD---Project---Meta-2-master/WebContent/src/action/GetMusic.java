package action;

import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Token;
import com.github.scribejava.core.oauth.OAuthService;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

public class GetMusic{
    private String url,dbfilePath;
    public String execute() throws Exception {
        String[] urll = this.dbfilePath.split(":");
        if(urll[0].equals("https")){
            url=this.dbfilePath;
            return "redirect";
        }
        else{
            String[] pathParts = dbfilePath.split("/");
            String folder = "";
            String file = pathParts[pathParts.length-1];
            for(int i=0;i<pathParts.length-1;i++){
                if(i==pathParts.length-1){
                    folder+=pathParts[i];
                }
                else{
                    folder+=pathParts[i];
                    folder+="/";
                }
            }
            url = "https://www.dropbox.com/home"+folder+"?preview="+file;
            return "redirect";
        }
        }

    public String getUrl(){
        return url;
    }

    public void setUrl (String url){
        this.url = url;
    }

    public void setDbfilePath(String dbfilePath) {
        this.dbfilePath = dbfilePath;
    }

}
