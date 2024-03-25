package store.mybooks.front.order.exception;

/**
 * packageName    : store.mybooks.front.order.exception<br>
 * fileName       : AmountNegativeException<br>
 * author         : minsu11<br>
 * date           : 3/21/24<br>
 * description    :
 * ===========================================================<br>
 * DATE              AUTHOR             NOTE<br>
 * -----------------------------------------------------------<br>
 * 3/21/24        minsu11       최초 생성<br>
 */
public class AmountNegativeException extends RuntimeException {
    public AmountNegativeException() {
        super("수량에 음수가 들어왔습니다.");
    }
}
