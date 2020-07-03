package action;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

public class MenuPrincipal extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;

    public String execute() throws Exception{
        setSession(session);
        return "success";
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }
}
