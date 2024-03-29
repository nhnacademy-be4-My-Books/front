package store.mybooks.front.admin.author.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * packageName    : store.mybooks.front.book.author.dto.request<br>
 * fileName       : AuthorDeleteRequest<br>
 * author         : minsu11<br>
 * date           : 2/25/24<br>
 * description    : 삭제할 저자의 DTO
 * ===========================================================<br>
 * DATE              AUTHOR             NOTE<br>
 * -----------------------------------------------------------<br>
 * 2/25/24        minsu11       최초 생성<br>
 */
@Getter
@AllArgsConstructor
public class AuthorDeleteRequest {
    private Integer id;
}
