package store.mybooks.front.order.dto.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * packageName    : store.mybooks.front.order.dto.request<br>
 * fileName       : OrderCreateRequest<br>
 * author         : minsu11<br>
 * date           : 3/16/24<br>
 * description    :
 * ===========================================================<br>
 * DATE              AUTHOR             NOTE<br>
 * -----------------------------------------------------------<br>
 * 3/16/24        minsu11       최초 생성<br>
 */
@Getter
@AllArgsConstructor
public class BookOrderCreateRequest {
    private List<BookInfoRequest> bookInfoList;
    private OrderInfoRequest orderInfo;
    private String orderNumber;
    private Integer pointCost;
    private Integer couponCost;
    private Integer wrapCost;
    private Integer totalCost;

}
