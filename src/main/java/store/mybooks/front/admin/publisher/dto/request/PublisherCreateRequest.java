package store.mybooks.front.admin.publisher.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * packageName    : store.mybooks.front.book.publisher.dto.request<br>
 * fileName       : PublisherCreateRequest<br>
 * author         : minsu11<br>
 * date           : 2/26/24<br>
 * description    : 등록할 출판사 DTO
 * ===========================================================<br>
 * DATE              AUTHOR             NOTE<br>
 * -----------------------------------------------------------<br>
 * 2/26/24        minsu11       최초 생성<br>
 */
@Getter
@AllArgsConstructor
public class PublisherCreateRequest {
    private String name;
}
