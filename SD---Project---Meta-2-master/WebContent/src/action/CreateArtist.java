package action;

import com.opensymphony.xwork2.ActionSupport;
import model.Bean;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

public class CreateArtist extends ActionSupport implements SessionAware {
    private Map<String, Object> session;
    private static final long serialVersionUID = 4L;
    private String nameArtist="", descriptionArtist="", songwriterArtist="", composerArtist="", bandArtist="" ;

    public String execute() throws Exception{
        boolean isSongwriter=false, isComposer=false, isBand=false;
        if(this.nameArtist!=null && this.descriptionArtist!=null && this.songwriterArtist!=null && this.composerArtist!=null && this.bandArtist!=null
        && !nameArtist.equals("") && !descriptionArtist.equals("") && !songwriterArtist.equals("") && !composerArtist.equals("") && !bandArtist.equals("")){
            if(this.songwriterArtist.equals("0")){
                isSongwriter = true;
            }
            if(this.composerArtist.equals("0")){
                isComposer = true;
            }
            if(this.bandArtist.equals("0")){
                isBand = true;
                isSongwriter = false;
                isComposer = false;
            }
            String response = this.getBean().createArtist(this.nameArtist, this.descriptionArtist, isSongwriter, isComposer, isBand);
            if(response.equals("type|musicianExists")){
                return "failed";
            }
            else if(response.equals("type|createMusicianComplete")){
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

    public void setNameArtist(String nameArtist) {
        this.nameArtist = nameArtist;
    }

    public void setDescriptionArtist(String descriptionArtist) {
        this.descriptionArtist = descriptionArtist;
    }

    public void setSongwriterArtist(String songwriterArtist) {
        this.songwriterArtist = songwriterArtist;
    }

    public void setComposerArtist(String composerArtist) {
        this.composerArtist = composerArtist;
    }

    public void setBandArtist(String bandArtist) {
        this.bandArtist = bandArtist;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }
}
