package action;

import com.opensymphony.xwork2.ActionSupport;
import model.Bean;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

public class CreateMusic extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    private String musicName="",artistName="",albumName="",composerName="",songwriterName="",musicLength="";

    public String execute() throws Exception {
        if(this.musicName!=null && this.artistName!=null && this.albumName!=null && this.composerName!=null && this.songwriterName!=null && this.musicLength!=null
                && !musicName.equals("") && !artistName.equals("") && !albumName.equals("") && !composerName.equals("") && !songwriterName.equals("") && !musicLength.equals("")){
            String response = this.getBean().createMusic(this.musicName, this.artistName, this.albumName ,composerName, songwriterName, musicLength,session.get("username").toString());
            if(response.equals("failed")){
                return "failed";
            }
            else if(response.equals("worked")){
                return "worked";
            }
        }
        return "rip";
    }

    public Bean getBean(){
        if(!session.containsKey("Bean"))
            this.setBean(new Bean());
        return (Bean) session.get("Bean");
    }

    public void setBean(Bean bean) {this.session.put("Bean",bean);}

    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public void setComposerName(String composerName) {
        this.composerName = composerName;
    }

    public void setSongwriterName(String songwriterName) {
        this.songwriterName = songwriterName;
    }

    public void setMusicLength(String musicLength) {
        this.musicLength = musicLength;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }
}
