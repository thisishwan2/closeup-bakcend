package farmSystem.closeUp.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserInfoRequest {
    @NotBlank(message = "회원 타입 설정은 필수입니다.")
    private String userRole;
    @NotBlank(message = "닉네임 입력은 필수입니다.")
    private String nickname;
    @NotBlank(message = "주소 입력은 필수입니다.")
    private String address;
    @NotBlank(message = "휴대전화번호 입력은 필수입니다.")
    private String phoneNumber;
    @NotBlank(message = "프로필 사진은 필수입니다.")
    private String profileImageUrl;
    @NotBlank(message = "성별 입력은 필수입니다.")
    private String gender;
    @NotBlank(message = "생일 입력은 필수입니다.")
    private String birthday;
}
