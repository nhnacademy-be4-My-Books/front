package store.mybooks.front.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * packageName    : store.mybooks.resource.user.dto.request<br>
 * fileName       : UserLoginRequest<br>
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
public class UserLoginRequest {

    private String email;
    private String password;


    public void setPassword(String password) {
        this.password = password;
    }
}
