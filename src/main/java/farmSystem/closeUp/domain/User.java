package farmSystem.closeUp.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;
    private String nickName;
    private String password;
    private String address;
    private String phoneNumber;
    private String profileImageUrl;
    private String email;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;
    @Enumerated(EnumType.STRING)
    private SocialType socialType; // KAKAO, NAVER, GOOGLE
    private String socialId; // 로그인한 소셜 타입의 식별자 값
    private String gender;
    private String birthDay;
    private Long point;

    @Builder
    public User(Long userId, String nickName, String address, String phoneNumber, String profileImageUrl, String email, UserRole userRole, String password,
                SocialType socialType, String socialId, String gender, String birthDay, Long point){
        this.userId = userId;
        this.nickName = nickName;
        this.password = password;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.profileImageUrl = profileImageUrl;
        this.email = email;
        this.userRole = userRole;
        this.socialType = socialType;
        this.socialId = socialId;
        this.gender = gender;
        this.birthDay = birthDay;
        this.point = point;
    }

    // 유저 권한 설정 메소드
    public void authorizeUser() {
        this.userRole = UserRole.USER;
    }
}
