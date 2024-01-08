package farmSystem.closeUp.dto.raffleProduct.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class GetRaffleProductsResponse {

    private Long raffleProductId;
    private String raffleProductTitle;
    private String creatorName;
    private String raffleProductThumbnail;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long raffleProductPrice;

    public static GetRaffleProductsResponse of(Long raffleProductId, String raffleProductTitle, String creatorName, String raffleProductThumbnail, LocalDate startDate, LocalDate endDate, Long raffleProductPrice) {
        return GetRaffleProductsResponse.builder()
                .raffleProductId(raffleProductId)
                .raffleProductTitle(raffleProductTitle)
                .creatorName(creatorName)
                .raffleProductThumbnail(raffleProductThumbnail)
                .startDate(startDate)
                .endDate(endDate)
                .raffleProductPrice(raffleProductPrice)
                .build();
    }
}
