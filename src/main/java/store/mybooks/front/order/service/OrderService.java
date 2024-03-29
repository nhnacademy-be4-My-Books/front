package store.mybooks.front.order.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import store.mybooks.front.admin.book.model.response.BookDetailResponse;
import store.mybooks.front.admin.delivery_rule.dto.DeliveryRuleResponse;
import store.mybooks.front.admin.wrap.adaptor.WrapAdaptor;
import store.mybooks.front.admin.wrap.dto.response.WrapResponse;
import store.mybooks.front.book.adaptor.BookAdaptor;
import store.mybooks.front.cart.domain.CartDetail;
import store.mybooks.front.order.adaptor.OrderAdaptor;
import store.mybooks.front.order.dto.request.*;
import store.mybooks.front.order.dto.response.*;
import store.mybooks.front.order.utils.OrderUtils;
import store.mybooks.front.pageable.dto.response.PageResponse;
import store.mybooks.front.user.adaptor.UserAdaptor;
import store.mybooks.front.user_address.adaptor.UserAddressAdaptor;
import store.mybooks.front.user_coupon.adaptor.UserCouponAdaptor;
import store.mybooks.front.user_coupon.model.response.UserCouponResponse;

/**
 * packageName    : store.mybooks.front.order.service<br>
 * fileName       : OrderService<br>
 * author         : minsu11<br>
 * date           : 3/5/24<br>
 * description    :
 * ===========================================================<br>
 * DATE              AUTHOR             NOTE<br>
 * -----------------------------------------------------------<br>
 * 3/5/24        minsu11       최초 생성<br>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final BookAdaptor bookAdaptor;
    private final OrderAdaptor orderAdapter;
    private final UserAddressAdaptor userAddressAdaptor;
    private final UserAdaptor userAdaptor;
    private final WrapAdaptor wrapAdaptor;
    private final UserCouponAdaptor userCouponAdaptor;

    /**
     * methodName : getBook<br>
     * author : minsu11<br>
     * description : 주문 결제 페이지에 보일 주문내역 도서.
     * <br> *
     *
     * @param request 요청할 도서 정보
     * @return book detail response
     */
    public BookDetailResponse getBook(BookOrderDirectRequest request) {
        return bookAdaptor.getBook(request.getId());
    }

    /**
     * methodName : calculateTotalCost<br>
     * author : minsu11<br>
     * description : 도서의 총 주문 결제.
     * <br> *
     *
     * @param cartDetailList 장바구니 상세
     * @return integer
     */
    public Integer calculateTotalCost(List<CartDetail> cartDetailList, DeliveryRuleResponse deliveryRuleResponse) {
        int totalCost = 0;
        for (CartDetail cartDetail : cartDetailList) {
            totalCost += (cartDetail.getSaleCost() * cartDetail.getCartDetailAmount());
        }
        return totalCost > deliveryRuleResponse.getRuleCost() ? totalCost : totalCost + deliveryRuleResponse.getCost();
    }

    public List<Integer> calculateBooksCost(List<CartDetail> cartDetailList) {
        return cartDetailList.stream()
                .map(cartDetail -> cartDetail.getSaleCost() * cartDetail.getCartDetailAmount())
                .collect(Collectors.toList());

    }

    public Integer calculateBookCouponCost(List<BookInfoRequest> bookInfoRequests) {
        int result = 0;
        for (BookInfoRequest bookInfoRequest : bookInfoRequests) {
            int saleCost = bookInfoRequest.getSaleCost();
            int amount = bookInfoRequest.getAmount();
            int couponCost = 0;
            Long couponId = bookInfoRequest.getSelectCouponId();
            if (Objects.nonNull(couponId)) {

                UserCouponResponse coupon = userCouponAdaptor.getUserCouponResponse(couponId);
                if (coupon.isRate()) { // true 시 할인 률
                    couponCost = (saleCost * amount) * coupon.getDiscountRate() / 100;
                } else {
                    couponCost = coupon.getDiscountCost();
                }

                if (coupon.isRate() && couponCost > coupon.getMaxDiscountCost()) {
                    couponCost = coupon.getMaxDiscountCost();
                }
            }
            result += couponCost;
        }

        return result;
    }

    /**
     * methodName : calculateBookWrapCost<br>
     * author : minsu11<br>
     * description : 도서에 적용된 총 포장지 가격 계산.
     * <br>
     *
     * @param bookInfoRequests the book info requests
     * @return the integer
     */
    public Integer calculateBookWrapCost(List<BookInfoRequest> bookInfoRequests) {
        int result = 0;
        for (int i = 0; i < bookInfoRequests.size(); i++) {
            Integer wrapId = bookInfoRequests.get(i).getSelectWrapId();
            if (Objects.nonNull(wrapId)) {

                WrapResponse wrapResponse = wrapAdaptor.getWrap(wrapId);
                result += wrapResponse.getCost();
            }
        }
        return result;
    }

    /**
     * 회원의 포인트 조회.
     *
     * @param orderInfoRequest the order info request
     * @return the point
     */
    public Integer getPoint(OrderInfoRequest orderInfoRequest) {
        return orderInfoRequest.getUsingPoint();
    }

    /**
     * 회원 주문 등록.
     *
     * @param bookInfo   the book info
     * @param orderInfo  the order info
     * @param point      the point
     * @param couponCost the coupon cost
     * @param wrapCost   the wrap cost
     * @param totalCost  the total cost
     * @return the book order create response
     */
    public BookOrderCreateResponse createOrder(OrderUserInfoRequest userInfo,
                                               List<BookInfoRequest> bookInfo,
                                               OrderInfoRequest orderInfo,
                                               Integer point,
                                               Integer couponCost,
                                               Integer wrapCost,
                                               Integer totalCost) {
        totalCost += wrapCost - point - couponCost;
        String orderNumber = "";
        do {
            orderNumber = OrderUtils.createOrderNumber();
        } while (!orderAdapter.checkBookOrderNumber(orderNumber));
        BookOrderCreateRequest request = new BookOrderCreateRequest(
                userInfo.getUserName(), userInfo.getEmail(), userInfo.getPhoneNumber(),
                bookInfo, orderInfo,
                orderNumber, point, couponCost, wrapCost, totalCost, null);
        return orderAdapter.createUserBookOrder(request);
    }

    public BookOrderCreateResponse createNonUserOrder(BookOrderRequest bookInfo,
                                                      Integer wrapCost,
                                                      Integer totalCost) {
        OrderInfoRequest orderInfo = bookInfo.getOrderInfo();
        List<BookInfoRequest> bookInfoRequests = bookInfo.getBookInfoList();
        OrderUserInfoRequest userInfo = bookInfo.getUserInfo();
        totalCost += wrapCost;
        String orderNumber = "";
        do {
            orderNumber = OrderUtils.createOrderNumber();
        } while (!(boolean) orderAdapter.checkBookOrderNumber(orderNumber));
        BookOrderCreateRequest request = new BookOrderCreateRequest(
                userInfo.getUserName(), userInfo.getEmail(), userInfo.getPhoneNumber(),
                bookInfoRequests, orderInfo,
                orderNumber, 0, 0,
                wrapCost, totalCost, userInfo.getOrderCode());
        return orderAdapter.createNonUserBookOrder(request);
    }

    public BookOrderInfoResponse getPayBookOrderInfo(String orderNumber) {
        return orderAdapter.getPayBookOrderInfo(orderNumber);

    }

    public BookOrderPayInfoResponse getBookOrderPayInfo(String orderNumber) {
        return orderAdapter.getBookOrderPayInfo(orderNumber);
    }

    public List<BookOrderDetailResponse> getBookOrderDetail(String orderNumber) {
        return orderAdapter.getBookOrderDetailList(orderNumber);
    }

    public PageResponse<BookOrderUserResponse> getAllUserBookOrder(Pageable pageable) {


        return orderAdapter.getAllUserBookOrder(pageable);
    }

}
