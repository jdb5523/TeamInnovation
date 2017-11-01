package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ImageList {
    
    ObservableList<Image> imageList = FXCollections.observableArrayList();
    
    public ImageList() {
    }
    
    public ObservableList<Image> getImageList() {
        return imageList;
    }
    
    public void addImage(Image image) {
        imageList.add(image);
    }
}
