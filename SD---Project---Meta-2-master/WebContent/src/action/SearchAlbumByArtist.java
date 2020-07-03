package action;

import com.opensymphony.xwork2.ActionSupport;
import model.Bean;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;


public class SearchAlbumByArtist extends ActionSupport implements SessionAware{
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    private String artistName="";

    public String execute() throws Exception {
        if((this.artistName != null && !artistName.equals(""))){
            String response = this.getBean().showAlbumByArtist(this.artistName);
            String[] respSplit = response.split(";");
            switch(respSplit[0]){
                case "type|albumDatabaseEmpty":
                    return "failed";
                case "type|noMatchesFound":
                    return "failed";
                case "type|showArtistAlbumsComplete":
                    String[] albumParts = respSplit[1].split("\\|");
                    String[] albums = albumParts[1].split(",");
                    session.put("artist",this.artistName);
                    session.put("albums",albums);
                    return "worked";
                default:
                    return "rip";
            }
        }
        return "rip";
    }

    public Bean getBean(){
        if(!session.containsKey("Bean"))
            this.setBean(new Bean());
        return (Bean) session.get("Bean");
    }

    public void setBean(Bean bean) {
        this.session.put("Bean", bean);
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }
}
