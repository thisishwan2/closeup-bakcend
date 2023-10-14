package farmSystem.closeUp.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long imageId;
    private String uploadImageName;
    private String originalImageName;
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "raffleProduct_id")
    private RaffleProduct raffleProduct;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notification_id")
    private Notification notification;

    @Builder
    public Image(Long imageId, String originalImageName, String uploadImageName, String imageUrl){
        this.imageId = imageId;
        this.originalImageName = originalImageName;
        this.uploadImageName = uploadImageName;
        this.imageUrl = imageUrl;
    }
}
