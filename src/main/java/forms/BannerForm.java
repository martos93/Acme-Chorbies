package forms;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public class BannerForm {
    private String url;

    public BannerForm(){
        super();
    }

    @URL
    @NotBlank
    public String getUrl(){
        return url;
    }
    public void setUrl(String url){
        this.url = url;
    }
}
