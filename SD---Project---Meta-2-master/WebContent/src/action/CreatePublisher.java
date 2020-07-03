package action;

import com.opensymphony.xwork2.ActionSupport;
import model.Bean;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

public class CreatePublisher extends ActionSupport implements SessionAware {
    private Map<String, Object> session;
    private static final long serialVersionUID = 4L;
    private String publisherName="";

    public String execute() throws Exception{
        if(this.publisherName!=null && !publisherName.equals("")){
            String response = this.getBean().createPublisher(this.publisherName);
            if(response.equals("failed")){
                return "failed";
            }
            else if(response.equals("worked")){
                return "worked";
            }
        }
        return "rip";
    }

    public Bean getBean(){
        if(!session.containsKey("Bean"))
            this.setBean(new Bean());
        return (Bean) session.get("Bean");
    }

    public void setBean(Bean bean) {this.session.put("Bean",bean);}

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }
}
