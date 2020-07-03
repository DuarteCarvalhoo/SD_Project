package action;

import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Token;
import com.github.scribejava.core.oauth.OAuthService;
import com.opensymphony.xwork2.ActionSupport;
import model.Bean;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

public class EditAlbumMenu extends ActionSupport implements SessionAware{
    private String albumName="";
    Map<String, Object> session;
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

                    session.put("albumChange",nameParts[1]);
                    session.put("length",lengthParts[1]);
                    session.put("artist",artistParts[1]);
                    session.put("score",scoreParts[1]);
                    session.put("descriptionChange",descParts[1]);
                    session.put("genreChange",genreParts[1]);
                    session.put("critics",critics);
                    session.put("musics",musics);
                    session.put("publisher",publisherParts[1]);
                    return "worked";
                default:
                    return "rip";
            }
        }
        return "failed";
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
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
