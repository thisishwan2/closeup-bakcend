package farmSystem.closeUp.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RaffleProduct extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "raffleProduct_id")
    private Long raffleProductId;

    private String title;
    private String startDate;
    private String endDate;
    private String content;
    private Long winnerCount;
    private Long rafflePrice;
    private String address;
    private String winningDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="creator_id")
    private Creator creator;

    @Builder
    public RaffleProduct(Long raffleProductId, String title, String startDate, String endDate, String content, Long winnerCount, Long rafflePrice, String address, String winningDate) {
        this.raffleProductId = raffleProductId;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.content = content;
        this.winnerCount = winnerCount;
        this.rafflePrice = rafflePrice;
        this.address = address;
        this.winningDate = winningDate;
    }
}
