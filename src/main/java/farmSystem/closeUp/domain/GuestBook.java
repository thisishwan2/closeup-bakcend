package farmSystem.closeUp.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GuestBook extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "guestbook_id")
    private Long guestBookId;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", referencedColumnName =  "user_id")
    private User creator; //크리에이터

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user; //일반 유저

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "heart_id", referencedColumnName = "heart_id")
    private Heart heart;

    @Builder
    public GuestBook(Long guestBookId, String content){
        this.guestBookId = guestBookId;
        this.content = content;
    }

    public void setUser(User user){
        this.user = user;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public void setHeart(Heart heart) { this.heart = heart; }

}