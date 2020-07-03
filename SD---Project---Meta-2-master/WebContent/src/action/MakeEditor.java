package action;
import com.opensymphony.xwork2.ActionSupport;
import model.Bean;
import org.apache.struts2.interceptor.SessionAware;

import java.rmi.RemoteException;
import java.util.Map;


public class MakeEditor extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    private String targetName="";

    public String execute(){
        if((this.targetName != null && !targetName.equals(""))){
                String response = this.getBean().makeEditor(this.targetName);
                String[] respSplit = response.split(";");
                switch(respSplit[0]){
                    case "type|makingEditorFail":
                        return "failed";
                    case "type|makingEditorComplete":
                        return "worked";
                    default:
                        return "rip";
                }
        }
        return "rip";
    }


    public void setTargetName(String targetName) {
        this.targetName = targetName;
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
