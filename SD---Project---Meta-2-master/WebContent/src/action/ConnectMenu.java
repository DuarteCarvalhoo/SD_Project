package action;

import com.github.scribejava.core.model.Token;
import com.github.scribejava.core.oauth.OAuthService;
import model.Bean;
import org.apache.struts2.interceptor.SessionAware;
import rest.DropBoxRestClient;
import java.util.Map;

public class ConnectMenu extends DropBoxRestClient implements SessionAware{
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;

    public String execute() {
        try {
            String files = "";
            OAuthService service = createService();
            String response = this.getBean().getDropboxInfo(session.get("username").toString());
            String[] respSplit = response.split(";");
            switch (respSplit[0]) {
                case "type|getInfoFailed":
                    return "failed";
                case "type|getInfoComplete":
                    String[] mailParts = respSplit[2].split("\\|");
                    if (mailParts[1].equals("null")) {
                        return "failed";
                    }
                    String resp = this.getBean().getDropboxInfo(session.get("username").toString());
                    String[] respSpli = resp.split(";");
                    String[] tokenParts = respSpli[1].split("\\|");
                    Token accessToken = new Token(tokenParts[1], "");
                    files = listFiles("", service, accessToken, files);
                    session.put("files",files);
                    return "success";
                default:
                    return "rip";
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "rip";
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
