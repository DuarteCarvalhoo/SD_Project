package action;

import com.opensymphony.xwork2.ActionSupport;
import model.Bean;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;


public class SearchMusicByName extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    private String musicName="";
    public String execute() throws Exception {
        if((this.musicName != null && !musicName.equals(""))){
            String response = this.getBean().showMusicByName(this.musicName);
            String[] respSplit = response.split(";");
            switch(respSplit[0]){
                case "type|musicDatabaseEmpty":
                    return "failed";
                case "type|noMatchesFound":
                    return "failed";
                case "type|partialSearchComplete":
                    String[] results = respSplit[1].split("\\|");
                    String[] res = results[1].split(",");
                    session.put("musics",res);
                    return "workedP";
                case "type|notPartialSearchComplete":
                    String[] nameParts = respSplit[1].split("\\|");
                    String[] artistParts = respSplit[2].split("\\|");
                    String[] composerParts = respSplit[3].split("\\|");
                    String[] songwriterParts = respSplit[4].split("\\|");
                    String[] albumParts = respSplit[5].split("\\|");
                    String[] lengthParts = respSplit[6].split("\\|");
                    session.put("title",nameParts[1]);
                    session.put("length",lengthParts[1]);
                    session.put("artist",artistParts[1]);
                    session.put("album",albumParts[1]);
                    session.put("composer",composerParts[1]);
                    session.put("songwriter",songwriterParts[1]);
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

    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }
}
