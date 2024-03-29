package store.mybooks.front.admin.author.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : store.mybooks.front.book.author.dto.response<br>
 * fileName       : AuthorResponse<br>
 * author         : minsu11<br>
 * date           : 2/24/24<br>
 * description    : 저자의 응답 DTO
 * ===========================================================<br>
 * DATE              AUTHOR             NOTE<br>
 * -----------------------------------------------------------<br>
 * 2/24/24        minsu11       최초 생성<br>
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorResponse {
    private Integer id;
    private String name;
    private String content;
}
