package action;

import com.opensymphony.xwork2.ActionSupport;
import model.Bean;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

public class EditArtistMenu extends ActionSupport implements SessionAware {
    private String artistName=null;
    Map<String, Object> session;
    public String execute() throws Exception {
        if((this.artistName != null && !artistName.equals(""))) {
            String response = this.getBean().showArtist(this.artistName);
            String[] respSplit = response.split(";");
            String[] nameParts = respSplit[1].split("\\|");
            String[] descParts = respSplit[2].split("\\|");
            String[] funcParts = respSplit[3].split("\\|");
            String[] albumParts = respSplit[4].split("\\|");
            String[] functionsN = funcParts[1].trim().split(",");
            String[] albumN = albumParts[1].split(",");

            session.put("descriptionChange",descParts[1]);
            session.put("artistChange", this.artistName);
            return "worked";
        }
        else{
            return "failed";
        }
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
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