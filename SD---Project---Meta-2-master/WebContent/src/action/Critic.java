package action;

import com.opensymphony.xwork2.ActionSupport;
import model.Bean;
import org.apache.struts2.interceptor.SessionAware;

import java.rmi.RemoteException;
import java.util.Map;

public class Critic extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    public String album=null, critic=null, score=null;
    private Map<String, Object> session;


    public String execute() throws RemoteException {
        if ((this.album != null && !album.equals("")) && (this.critic != null && !critic.equals("")) && (this.score != null && !score.equals(""))) {
            String response = this.getBean().makeCritic(Double.parseDouble(score), critic, album, session.get("username").toString());
            String[] respSplit = response.split(";");
            switch(respSplit[0]){
                case "type|makeCriticFail":
                    return "failed";
                case "type|albumNotFound":
                    return "failed";
                case "type|albumDatabaseEmpty":
                    return "failed";
                case "type|criticComplete":
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

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getCritic() {
        return critic;
    }

    public void setCritic(String critic) {
        this.critic = critic;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }
}
