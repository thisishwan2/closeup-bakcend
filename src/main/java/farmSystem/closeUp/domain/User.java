package farmSystem.closeUp.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    private String nickName;
    private String address;
    private String phoneNumber;
    private String profileImageUrl;
    // 권한
    // private String authority;
    private String email;
    private String gender;
    private String birthDay;
    private Long point;

    @Builder
    public User(Long userId, String nickName, String address, String phoneNumber, String profileImageUrl, String email, String gender, String birthDay, Long point){
        this.userId = userId;
        this.nickName = nickName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.profileImageUrl = profileImageUrl;
        this.email = email;
        this.gender = gender;
        this.birthDay = birthDay;
        this.point = point;
    }
}
