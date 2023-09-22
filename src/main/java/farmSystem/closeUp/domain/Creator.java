package farmSystem.closeUp.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Creator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "creator_id")
    private Long creatorId;

    private String nickName;
    private String address;
    private String phoneNumber;
    private String profileImageUrl;
    private String email;
    private String kakaoId;
    private String gender;
    private String birthDay;
    private String profileComment;
    private String verificationImageUrl;
    // private Long point;

    @Builder
    public Creator(Long creatorId, String nickName, String address, String phoneNumber, String profileImageUrl, String email, String kakaoId, String gender, String birthDay, String profileComment, String verificationImageUrl){
        this.creatorId = creatorId;
        this.nickName = nickName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.profileImageUrl = profileImageUrl;
        this.email = email;
        this.kakaoId = kakaoId;
        this.gender = gender;
        this.birthDay = birthDay;
        this.profileComment = profileComment;
        this.verificationImageUrl = verificationImageUrl;
    }
}
