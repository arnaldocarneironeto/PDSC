package br.edu.ifpe.acsntrs.views;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author Tássio
 */
@ManagedBean(name="switch_mb")
public class imageSwitch {
    private List<String> images;
    
    @PostConstruct
    public void init() {
        images = new ArrayList<String>();
        for (int i = 1; i <= 2; i++) {
            images.add("student" + i + ".jpg");
        }
    }
 
    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
    
}
