package farmSystem.closeUp.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestTest {

    private String nickname;
    private String address;
    private String phoneNumber;
    private String profileImageUrl;
    private String gender;
    private String birthday;
}
