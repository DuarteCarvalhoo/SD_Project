package action;

import com.github.scribejava.core.oauth.OAuthService;
import rest.DropBoxRestClient;

public class DropboxAuth extends DropBoxRestClient {
    private String url;
    public String getUrl(){
        return url;
    }

    public String execute() throws Exception {
        OAuthService service = createService();
        url = service.getAuthorizationUrl(null);
        return "redirect";
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
