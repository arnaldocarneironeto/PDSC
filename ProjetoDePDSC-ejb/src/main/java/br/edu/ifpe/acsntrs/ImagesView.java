package br.edu.ifpe.acsntrs;

/**
 *
 * @author Tássio
 */
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Named;

@Stateless
@Named("imagesView_mb")
public class ImagesView{
    
    
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
}
