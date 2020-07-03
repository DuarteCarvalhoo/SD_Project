package action;

import com.github.scribejava.core.model.Token;
import com.github.scribejava.core.model.Verifier;
import com.github.scribejava.core.oauth.OAuthService;
import model.Bean;
import org.apache.struts2.interceptor.SessionAware;
import rest.DropBoxRestClient;
import java.util.Map;

public class DropboxAuthRedirect extends DropBoxRestClient implements SessionAware {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    private String code;

    public String execute() throws Exception {
        setCode(code);
        if(code==null){
            return "rip";
        }
        else{
            OAuthService service = createService();
            setCode(code);
            Verifier verifier = new Verifier(code);
            Token accessToken = service.getAccessToken(null,verifier);
            String targetMail = getCurrentAccountMail(service,accessToken);
            if(session.get("username")==null){
                String email = getCurrentAccountMail(service,accessToken);
                String response = this.getBean().checkKnownEmail(email);
                String[] responseSplit = response.split(";");
                switch(responseSplit[0]){
                    case "type|accountExists":
                        String[] user = responseSplit[1].split("\\|");
                        session.put("username",user[1]);
                        return "success";
                    case "type|accountDoesNotExist":
                        return "failedLogin";
                    default:
                        return "rip";
                }
            }
            else{
                String response = this.getBean().saveDropboxInfo(accessToken.getToken(),session.get("username").toString(),targetMail);
                String[] respSplit = response.split(";");
                switch(respSplit[0]){
                    case "type|authenticationComplete":
                        return "success";
                    case "type|authenticationFailed":
                        return "failed";
                    default:
                        return "rip";
                }
            }
        }
    }

    public void setCode(String code){
        this.code= code;
    }

    public Bean getBean(){
        if(!session.containsKey("Bean"))
            this.setBean(new Bean());
        return (Bean) session.get("Bean");
    }

    public void setBean(Bean bean) {
        this.session.put("Bean", bean);
    }
    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }
}
