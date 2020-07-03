package action;
import com.opensymphony.xwork2.ActionSupport;
import jdk.nashorn.internal.ir.SetSplitState;
import model.Bean;
import org.apache.struts2.interceptor.SessionAware;

import java.rmi.RemoteException;
import java.util.Map;
public class CheckPermissions extends ActionSupport implements SessionAware{
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;

    public String execute() throws Exception{
        String response = this.getBean().isEditor(session.get("username").toString());
        String[] respSplit = response.split(";");
        System.out.println(respSplit[0]);
        switch(respSplit[0]){
            case "type|isEditor":
                return "success";
            case "type|notEditor":
                return "failed";
            default:
                return "rip";
        }
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
