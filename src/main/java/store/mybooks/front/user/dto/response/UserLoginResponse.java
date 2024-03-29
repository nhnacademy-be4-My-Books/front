package store.mybooks.front.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : store.mybooks.resource.user.dto.response<br>
 * fileName       : UserLoginResponse<br>
 * author         : masiljangajji<br>
 * date           : 2/23/24<br>
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2/23/24        masiljangajji       최초 생성
 */

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginResponse {

    private Boolean isValidUser;

    private Boolean isAdmin;

    private Long userId;

    private String status;

}
