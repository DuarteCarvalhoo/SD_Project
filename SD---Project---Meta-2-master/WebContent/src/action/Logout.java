package action;

import com.opensymphony.xwork2.ActionSupport;
import model.Bean;
import org.apache.struts2.interceptor.SessionAware;

import java.rmi.RemoteException;
import java.util.Map;

public class Logout extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;

    public String execute() throws Exception {
        this.getBean().removeUserOnline(session.get("username").toString());
        session.put("username",null);
        return "success";
    }

    public Bean getBean() throws RemoteException {
        if(!session.containsKey("Bean"))
            this.setBean(new Bean());
        return (Bean) session.get("Bean");
    }

    public void setBean(Bean bean) {
        this.session.put("Bean", bean);
    }

    @Override
    public void setSession(Map<String, Object> session) { this.session = session; }
}
