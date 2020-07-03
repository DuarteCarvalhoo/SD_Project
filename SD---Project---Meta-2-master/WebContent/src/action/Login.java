package action;

import com.opensymphony.xwork2.ActionSupport;
import model.Bean;
import org.apache.struts2.interceptor.SessionAware;

import java.rmi.RemoteException;
import java.util.Map;

public class Login extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private String username = "", password = "";
    private Map<String, Object> session;

    public String execute() throws Exception {
        // any username is accepted without confirmation (should check using RMI)
        if((this.username != null && !username.equals("")) && (this.password !=null && !password.equals(""))) {
            this.getBean().setUsername(this.username);
            this.getBean().setPassword(this.password);
            session.put("username",username);
            String response = this.getBean().getUserMatchesPassword();
            String[] respSplit = response.split(";");
            switch(respSplit[0]){
                case "type|loginFail":
                    System.out.println("Entrou fail");
                    return "failed";
                case "type|loginComplete":
                    return "worked";
                default:
                    return "rip";
            }
        }
        return "rip";
    }

    public Bean getBean() throws RemoteException {
        if(!session.containsKey("Bean"))
            this.setBean(new Bean());
        return (Bean) session.get("Bean");
    }

    public void setBean(Bean bean) {
        this.session.put("Bean", bean);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public void setSession(Map<String, Object> session) { this.session = session; }
}
