package store.mybooks.front.admin.book.adaptor;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import store.mybooks.front.admin.book.model.request.BookCreateRequest;
import store.mybooks.front.admin.book.model.request.BookModifyRequest;
import store.mybooks.front.admin.book.model.response.BookBriefResponse;
import store.mybooks.front.admin.book.model.response.BookCreateResponse;
import store.mybooks.front.admin.book.model.response.BookDetailResponse;
import store.mybooks.front.admin.book.model.response.BookModifyResponse;
import store.mybooks.front.admin.book.model.response.BookStatusGetResponse;
import store.mybooks.front.config.GatewayAdaptorProperties;
import store.mybooks.front.pageable.dto.response.PageResponse;
import store.mybooks.front.utils.Utils;

/**
 * packageName    : store.mybooks.front.admin.book.adaptor <br/>
 * fileName       : BookAdminAdaptor<br/>
 * author         : newjaehun <br/>
 * date           : 2/26/24<br/>
 * description    :<br/>
 * ===========================================================<br/>
 * DATE              AUTHOR             NOTE<br/>
 * -----------------------------------------------------------<br/>
 * 2/26/24        newjaehun       최초 생성<br/>
 */
@Component
@RequiredArgsConstructor
public class BookAdminAdaptor {
    private final RestTemplate restTemplate;

    private final GatewayAdaptorProperties gatewayAdaptorProperties;

    private final String url = "/api/books";

    public PageResponse<BookBriefResponse> getPagedBriefBooks(Pageable pageable) {
        HttpEntity<BookCreateRequest> requestHttpEntity = new HttpEntity<>(Utils.getHttpHeader());

        ResponseEntity<PageResponse<BookBriefResponse>> exchange = restTemplate.exchange(
                gatewayAdaptorProperties.getAddress() + url + "?page=" + pageable.getPageNumber() + "&size=" + pageable.getPageSize(),
                HttpMethod.GET,
                requestHttpEntity,
                new ParameterizedTypeReference<>() {
                }
        );
        return Utils.getResponseEntity(exchange, HttpStatus.OK);
    }

    public BookDetailResponse getDetailBook(Long bookId) {
        HttpEntity<BookCreateRequest> requestHttpEntity = new HttpEntity<>(Utils.getHttpHeader());

        ResponseEntity<BookDetailResponse> exchange = restTemplate.exchange(
                gatewayAdaptorProperties.getAddress() + url + "/" + bookId,
                HttpMethod.GET,
                requestHttpEntity,
                new ParameterizedTypeReference<>() {
                }
        );
        return Utils.getResponseEntity(exchange, HttpStatus.CREATED);
    }

    public BookCreateResponse createBook(BookCreateRequest bookCreateRequest) {
        HttpEntity<BookCreateRequest> requestHttpEntity = new HttpEntity<>(bookCreateRequest, Utils.getHttpHeader());

        ResponseEntity<BookCreateResponse> exchange = restTemplate.exchange(
                gatewayAdaptorProperties.getAddress() + url,
                HttpMethod.POST,
                requestHttpEntity,
                new ParameterizedTypeReference<>() {
                }
        );

        return Utils.getResponseEntity(exchange, HttpStatus.CREATED);
    }

    public void updateBook(Long bookId, BookModifyRequest modifyRequest) {
        HttpEntity<BookModifyRequest> requestHttpEntity = new HttpEntity<>(modifyRequest, Utils.getHttpHeader());

        ResponseEntity<BookModifyResponse> exchange = restTemplate.exchange(
                gatewayAdaptorProperties.getAddress() + url + bookId,
                HttpMethod.POST,
                requestHttpEntity,
                new ParameterizedTypeReference<>() {
                }
        );
        if (exchange.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException();
        }
    }

    public List<BookStatusGetResponse> getBookStatus() {
        HttpEntity<Void> requestHttpEntity = new HttpEntity<>(Utils.getHttpHeader());

        ResponseEntity<List<BookStatusGetResponse>> exchange = restTemplate.exchange(
                gatewayAdaptorProperties.getAddress() + "/api/books-statuses",
                HttpMethod.GET,
                requestHttpEntity,
                new ParameterizedTypeReference<>() {
                }
        );
        return Utils.getResponseEntity(exchange, HttpStatus.OK);
    }
}