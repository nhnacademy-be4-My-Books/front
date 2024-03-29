package store.mybooks.front.admin.book_order.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * packageName    : store.mybooks.front.admin.book_order.dto.request<br>
 * fileName       : BookOrderRegisterInvoiceNumberRequest<br>
 * author         : minsu11<br>
 * date           : 3/4/24<br>
 * description    :
 * ===========================================================<br>
 * DATE              AUTHOR             NOTE<br>
 * -----------------------------------------------------------<br>
 * 3/4/24        minsu11       최초 생성<br>
 */
@Getter
@AllArgsConstructor
public class BookOrderRegisterInvoiceNumberRequest {

    @Positive
    private Long id;

    @NotBlank
    @Size(min = 10, max = 20)
    private String invoiceNumber;
}
