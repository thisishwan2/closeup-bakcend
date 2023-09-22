package farmSystem.closeUp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;

public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long imageId;

    private String imageUrl;

    @Builder
    public Image(Long imageId, String imageUrl){
        this.imageId = imageId;
        this.imageUrl = imageUrl;
    }
}
