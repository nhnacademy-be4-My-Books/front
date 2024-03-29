package store.mybooks.front.order.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * packageName    : store.mybooks.front.order.dto.request<br>
 * fileName       : OrderRequest<br>
 * author         : minsu11<br>
 * date           : 3/16/24<br>
 * description    :
 * ===========================================================<br>
 * DATE              AUTHOR             NOTE<br>
 * -----------------------------------------------------------<br>
 * 3/16/24        minsu11       최초 생성<br>
 */
@Getter
@Setter
@NoArgsConstructor
public class OrderInfoRequest {
    private String recipientName;
    private String recipientPhoneNumber;
    private String recipientAddress;
    private Integer deliveryId;
    private String deliveryDate;
    private String receiverMessage;
    private Integer usingPoint;
    private Integer wrapCost;
    private Integer couponApplicationAmount;


}
