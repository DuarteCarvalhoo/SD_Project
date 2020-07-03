package action;

import com.opensymphony.xwork2.ActionSupport;
import model.Bean;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

public class Register extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private String username = "", password = "";
    private Map<String,Object> session;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String execute() throws Exception {
        System.out.println(username + " + " + password);
        // any username is accepted without confirmation (should check using RMI)
        if((this.username != null && !username.equals("")) && (this.password !=null && !password.equals(""))) {
            this.getBean().setUsername(this.username);
            this.getBean().setPassword(this.password);
            String response = this.getBean().insertData(this.username,this.password);
            switch(response){
                case "usernameUsed":
                    return "usernameUsed";
                case "Success":
                    return SUCCESS;
                default:
                    return "rip";
            }
        }
        return "invalidCredentials";
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
