package store.mybooks.front.user.dto.request;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * packageName    : store.mybooks.resource.user.dto.request<br>
 * fileName       : UserCreateRequest<br>
 * author         : masiljangajji<br>
 * date           : 2/13/24<br>
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2/13/24        masiljangajji       최초 생성
 */

@Getter
@AllArgsConstructor
public class UserCreateRequest {

    private String name;

    private String password;

    private String phoneNumber;

    private String email;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birth;



    public void setPassword(String password) {
        this.password = password;
    }
}
