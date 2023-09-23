package farmSystem.closeUp.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Raffle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "raffle_id")
    private Long raffleId;

    private String raffleWhether;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "raffleProduct_id")
    private RaffleProduct raffleProduct;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Raffle(Long raffleId, String raffleWhether) {
        this.raffleId = raffleId;
        this.raffleWhether = raffleWhether;
    }
}
