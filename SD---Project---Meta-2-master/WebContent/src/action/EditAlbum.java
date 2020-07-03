package action;

import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Token;
import com.github.scribejava.core.oauth.OAuthService;
import com.opensymphony.xwork2.ActionSupport;
import model.Bean;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

public class EditAlbum extends ActionSupport implements SessionAware{
    private String albumGenre="";
    private String albumDescription="";
    Map<String, Object> session;
    public String execute() throws Exception {
        if((this.albumGenre != null && !albumGenre.equals("") && (this.albumDescription != null && !albumDescription.equals("")))) {
            String response = this.getBean().editAlbumGenre(session.get("albumChange").toString(),this.albumGenre);
            if(response.equals("worked")){
                String resp = this.getBean().editAlbumDescription(session.get("albumChange").toString(),this.albumDescription);
                return "worked";
            }
            else if(response.equals("false")){
                return "failed";
            }
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

    public void setAlbumGenre(String albumGenre) {
        this.albumGenre = albumGenre;
    }

    public void setAlbumDescription(String albumDescription) {
        this.albumDescription = albumDescription;
    }
}
