package farmSystem.closeUp.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Platform extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "platform_id")
    private Long platformId;

    private String platformName;

    private String platformImageUrl;

    @Builder
    public Platform(Long platformId, String platformName, String platformImageUrl) {
        this.platformId = platformId;
        this.platformName = platformName;
        this.platformImageUrl = platformImageUrl;
    }

}
