package farmSystem.closeUp.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Heart extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "heart_id")
    private Long heartId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "guestbook_id")
    private GuestBook guestBook;

    @Builder
    public Heart(Long heartId, GuestBook guestBook) {
        this.heartId = heartId;
        this.guestBook = guestBook;
    }

    public void setGuestBook(GuestBook guestBook) {
        this.guestBook = guestBook;
    }


}
