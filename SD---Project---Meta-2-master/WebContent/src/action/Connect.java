package action;

import com.github.scribejava.core.model.Token;
import com.github.scribejava.core.oauth.OAuthService;
import model.Bean;
import org.apache.struts2.interceptor.SessionAware;
import rest.DropBoxRestClient;
import java.util.Map;

public class Connect extends DropBoxRestClient implements SessionAware{
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    private String dbfilePath="",musicName="";

    public String execute() {
        try {
            OAuthService service = createService();
            String response = this.getBean().getDropboxInfo(session.get("username").toString());
            String[] respSplit = response.split(";");
            switch (respSplit[0]) {
                case "type|getInfoFailed":
                    return "failed";
                case "type|getInfoComplete":
                    String[] tokenParts = respSplit[1].split("\\|");
                    Token accessToken = new Token(tokenParts[1], "");
                    String fileId = getFileMetadata(dbfilePath,service,accessToken);
                    String r = this.getBean().connectMusicFile(musicName,fileId);
                    switch(r){
                        case "type|connectionComplete":
                            return "success";
                        default:
                            return "rip";
                    }

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

    public void setDbfilePath(String dbfilePath) {
        this.dbfilePath = dbfilePath;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }
}
