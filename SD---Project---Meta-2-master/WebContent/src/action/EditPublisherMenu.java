package action;

import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Token;
import com.github.scribejava.core.oauth.OAuthService;
import com.opensymphony.xwork2.ActionSupport;
import model.Bean;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

public class EditPublisherMenu extends ActionSupport implements SessionAware{
    private String publisherName="";
    Map<String, Object> session;
    public String execute() throws Exception {
        if((this.publisherName != null && !publisherName.equals(""))) {
            session.put("publisherChange", this.publisherName);
            return "worked";
        }
        return "failed";
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
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
