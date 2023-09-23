package farmSystem.closeUp.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long paymentId;

    private Long chargePoint;
    private String merchantId;
    private String impUid;
    private String status;
    private String createAt;
    private String failedAt;
    private String paidAt;
    private String failedReason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Payment(Long paymentId, Long chargePoint, String merchantId, String impUid, String status, String createAt, String failedAt, String paidAt, String failedReason) {
        this.paymentId = paymentId;
        this.chargePoint = chargePoint;
        this.merchantId = merchantId;
        this.impUid = impUid;
        this.status = status;
        this.createAt = createAt;
        this.failedAt = failedAt;
        this.paidAt = paidAt;
        this.failedReason = failedReason;
    }


}
