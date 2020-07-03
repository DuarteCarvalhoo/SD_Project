package action;

import com.opensymphony.xwork2.ActionSupport;
import model.Bean;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

public class CreateAlbum extends ActionSupport implements SessionAware {
    private Map<String, Object> session;
    private static final long serialVersionUID = 4L;
    private String albumName=null,albumDescription=null,albumGenre=null,albumArtist=null,albumPublisher=null;

    public String execute() throws Exception{
        if(this.albumName!=null && this.albumDescription!=null && this.albumGenre!=null && this.albumArtist!=null && this.albumPublisher!=null
                && !albumName.equals("") && !albumDescription.equals("") && !albumGenre.equals("") && !albumArtist.equals("") && !albumPublisher.equals("")){
            String response = this.getBean().createAlbum(this.albumName, this.albumDescription,this.albumGenre,this.albumArtist,this.albumPublisher);
            if(response.equals("type|albumExists")){
                return "failed";
            }
            else if(response.equals("type|createAlbumComplete")){
                return "worked";
            }
            else if (response.equals("type|createAlbumFailed")){
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

    public void setBean(Bean bean) {this.session.put("Bean",bean);}

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public void setAlbumDescription(String albumDescription) {
        this.albumDescription = albumDescription;
    }

    public void setAlbumGenre(String albumGenre) {
        this.albumGenre = albumGenre;
    }

    public void setAlbumArtist(String albumArtist) {
        this.albumArtist = albumArtist;
    }

    public void setAlbumPublisher(String albumPublisher) {
        this.albumPublisher = albumPublisher;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }
}
