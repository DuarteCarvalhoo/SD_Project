package action;

import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Token;
import com.github.scribejava.core.oauth.OAuthService;
import com.opensymphony.xwork2.ActionSupport;
import model.Bean;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

public class EditMusic extends ActionSupport implements SessionAware{
    private String musicTitle="";
    Map<String, Object> session;
    public String execute() throws Exception {
        if((this.musicTitle != null && !musicTitle.equals(""))) {
            String response = this.getBean().editMusic(musicTitle,session.get("musicChange").toString());
            if(response.equals("worked")){
                return "worked";
            }
            else if(response.equals("false")){
                return "failed";
            }
        }
        return "rip";
    }

    public void setMusicTitle(String musicTitle) {
        this.musicTitle = musicTitle;
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
