package action;
import com.opensymphony.xwork2.ActionSupport;
import model.Bean;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Arrays;
import java.util.Map;

public class SearchAlbumByName extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    private String albumName="";

    public String execute() throws Exception {
        if((this.albumName != null && !albumName.equals(""))){
            String response = this.getBean().showAlbumByName(this.albumName);
            String[] respSplit = response.split(";");
            switch(respSplit[0]){
                case "type|albumDatabaseEmpty":
                    return "failed";
                case "type|noMatchesFound":
                    return "failed";
                case "type|partialSearchAlbumComplete":
                    String[] results = respSplit[1].split("\\|");
                    String[] res = results[1].split(",");
                    session.put("albums",res);
                    return "workedP";
                case "type|notPartialSearchAlbumComplete":
                    String[] nameParts = respSplit[1].split("\\|");
                    String[] artistParts = respSplit[2].split("\\|");
                    String[] descParts = respSplit[3].split("\\|");
                    String[] genreParts = respSplit[5].split("\\|");
                    String[] scoreParts = respSplit[6].split("\\|");
                    String[] lengthParts = respSplit[4].split("\\|");
                    String[] criticParts = respSplit[7].split("\\|");
                    String[] critics = criticParts[1].split("!");
                    String[] musicsParts = respSplit[8].split("\\|");
                    String[] musics = musicsParts[1].split(",");
                    String[] publisherParts = respSplit[9].split("\\|");

                    session.put("name",nameParts[1]);
                    session.put("length",lengthParts[1]);
                    session.put("artist",artistParts[1]);
                    session.put("score",scoreParts[1]);
                    session.put("description",descParts[1]);
                    session.put("genre",genreParts[1]);
                    session.put("critics",critics);
                    session.put("musics",musics);
                    session.put("publisher",publisherParts[1]);
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


    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }
}
