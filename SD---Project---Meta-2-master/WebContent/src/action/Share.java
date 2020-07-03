package action;

import com.github.scribejava.core.model.Token;
import com.github.scribejava.core.oauth.OAuthService;
import model.Bean;
import org.apache.struts2.interceptor.SessionAware;
import rest.DropBoxRestClient;
import java.util.Map;

public class Share extends DropBoxRestClient implements SessionAware {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    private String filePath="";
    private String targetUser="";

    public String execute() {

        try {
            if((this.filePath != null && !filePath.equals("")) && (this.targetUser != null && !targetUser.equals(""))){
                OAuthService service = createService();
                String response = this.getBean().getDropboxInfo(this.targetUser);
                String[] respSplit = response.split(";");
                switch(respSplit[0]){
                    case "type|getInfoFailed":
                        return "failed";
                    case "type|getInfoComplete":
                        String[] mailParts = respSplit[2].split("\\|");
                        if(mailParts[1].equals("null")){
                            return "failed";
                        }
                        String resp = this.getBean().getDropboxInfo(session.get("username").toString());
                        String[] respSpli = resp.split(";");
                        String[] tokenParts = respSpli[1].split("\\|");
                        Token accessToken = new Token(tokenParts[1], "");
                        String fileId = getFileMetadata(this.filePath, service, accessToken);
                        shareFile(mailParts[1], fileId, service, accessToken);
                        return "worked";
                    default:
                        return "rip";
                }
            }
            return "rip";
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "rip";
    }

    public Bean getBean() {
        if (!session.containsKey("Bean"))
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

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setTargetUser(String targetUser) {
        this.targetUser = targetUser;
    }
}