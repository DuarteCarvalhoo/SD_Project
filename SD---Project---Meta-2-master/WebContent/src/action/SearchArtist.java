package action;

import com.opensymphony.xwork2.ActionSupport;
import model.Bean;
import org.apache.struts2.interceptor.SessionAware;

import java.util.ArrayList;
import java.util.Map;

public class SearchArtist extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private String artistName = "";
    private Map<String, Object> session;

    public String execute() throws Exception{ {
        if((this.artistName != null && !artistName.equals(""))){
            String response = this.getBean().showArtist(this.artistName);
            String[] respSplit = response.split(";");

            switch(respSplit[0]){
                case "type|artistDatabaseEmpty":
                    System.out.println("failed");
                    return "failed";
                case "type|noMatchesFound":
                    return "failed";
                case "type|partialSearchComplete":
                    String[] results = respSplit[1].split("\\|");
                    String[] res = results[1].split(",");
                    session.put("artists",res);
                    return "workedP";
                case "type|notPartialSearchComplete":
                    String[] nameParts = respSplit[1].split("\\|");
                    String[] descParts = respSplit[2].split("\\|");
                    String[] funcParts = respSplit[3].split("\\|");
                    String[] albumParts = respSplit[4].split("\\|");
                    String[] functionsN = funcParts[1].trim().split(",");
                    String[] albumN = albumParts[1].split(",");
                    session.put("name",nameParts[1]);
                    session.put("description",descParts[1]);
                    session.put("functions",functionsN);
                    session.put("albums", albumN);
                    return "worked";
                default:
                    return "rip";
            }
        }
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

    public void setArtistName(String artistName) {
        this.artistName=artistName;
    }
}
