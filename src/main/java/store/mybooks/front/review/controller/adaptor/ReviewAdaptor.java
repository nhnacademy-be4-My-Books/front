package store.mybooks.front.review.controller.adaptor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import store.mybooks.front.auth.Annotation.RequiredAuthorization;
import store.mybooks.front.config.GatewayAdaptorProperties;
import store.mybooks.front.pageable.dto.response.PageResponse;
import store.mybooks.front.review.controller.dto.request.ReviewCreateRequest;
import store.mybooks.front.review.controller.dto.request.ReviewModifyRequest;
import store.mybooks.front.review.controller.dto.response.ReviewCreateResponse;
import store.mybooks.front.review.controller.dto.response.ReviewDetailGetResponse;
import store.mybooks.front.review.controller.dto.response.ReviewGetResponse;
import store.mybooks.front.review.controller.dto.response.ReviewModifyResponse;
import store.mybooks.front.utils.Utils;

/**
 * packageName    : store.mybooks.front.review.controller.adaptor<br>
 * fileName       : ReviewAdaptor<br>
 * author         : masiljangajji<br>
 * date           : 3/17/24<br>
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 3/17/24        masiljangajji       최초 생성
 */

@Component
@RequiredArgsConstructor
public class ReviewAdaptor {

    private final RestTemplate restTemplate;

    private final GatewayAdaptorProperties gatewayAdaptorProperties;

    private static final String URL = "/api/reviews";

    private static final String URL_MEMBER = "/api/member/reviews";

    private static final String URL_MEMBER_ID = "/api/member/reviews/{id}";


    @RequiredAuthorization
    public ReviewCreateResponse createReview(ReviewCreateRequest request, MultipartFile file) throws IOException {


        HttpHeaders headers = Utils.getAuthHeader();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> parts = new LinkedMultiValueMap<>();
        parts.add("request", request);

        if (!file.getOriginalFilename().isEmpty()) {
            parts.add("contentImage", new FileSystemResource(convert(file)));
        }
        HttpEntity<MultiValueMap<String, Object>> requestHttpEntity = new HttpEntity<>(parts, headers);

        ResponseEntity<ReviewCreateResponse> responseEntity =
                restTemplate.postForEntity(gatewayAdaptorProperties.getAddress() + URL_MEMBER,
                        requestHttpEntity,
                        ReviewCreateResponse.class);

        Utils.getResponseEntity(responseEntity, HttpStatus.CREATED);

        return responseEntity.getBody();
    }

    @RequiredAuthorization
    public PageResponse<ReviewGetResponse> getAllUserReview(Pageable pageable) {

        ResponseEntity<PageResponse<ReviewGetResponse>> exchange = restTemplate.exchange(
                gatewayAdaptorProperties.getAddress() + URL_MEMBER + "?page=" + pageable.getPageNumber()
                        + "&size=" + 8,
                HttpMethod.GET,
                new HttpEntity<>(Utils.getAuthHeader()),
                new ParameterizedTypeReference<>() {
                });

        return Utils.getResponseEntity(exchange, HttpStatus.OK);
    }

    // 책에서 보여줄 리뷰
    @RequiredAuthorization
    public PageResponse<ReviewDetailGetResponse> getBookReview(Pageable pageable, Long bookId) {

        ResponseEntity<PageResponse<ReviewDetailGetResponse>> exchange = restTemplate.exchange(
                gatewayAdaptorProperties.getAddress() + URL + "/book/{bookId}" + "?page=" + pageable.getPageNumber()
                        + "&size=" + pageable.getPageSize(),
                HttpMethod.GET,
                new HttpEntity<>(Utils.getAuthHeader()),
                new ParameterizedTypeReference<>() {
                }, bookId);

        return Utils.getResponseEntity(exchange, HttpStatus.OK);
    }

    @RequiredAuthorization
    public ReviewGetResponse getUserReview(Long reviewId) {

        ResponseEntity<ReviewGetResponse> exchange = restTemplate.exchange(
                gatewayAdaptorProperties.getAddress() + URL_MEMBER_ID,
                HttpMethod.GET,
                new HttpEntity<>(Utils.getAuthHeader()),
                new ParameterizedTypeReference<>() {
                }, reviewId);

        return Utils.getResponseEntity(exchange, HttpStatus.OK);
    }

    @RequiredAuthorization
    public ReviewModifyResponse modifyUserReview(Long reviewId, ReviewModifyRequest request) {

        ResponseEntity<ReviewModifyResponse> responseEntity =
                restTemplate.exchange(gatewayAdaptorProperties.getAddress() + URL_MEMBER_ID,
                        HttpMethod.PUT,
                        new HttpEntity<>(request, Utils.getAuthHeader()),
                        new ParameterizedTypeReference<>() {
                        }, reviewId);

        Utils.getResponseEntity(responseEntity, HttpStatus.OK);

        return responseEntity.getBody();
    }

    private File convert(MultipartFile file) throws IOException {
        File convFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        try (FileOutputStream fileOutputStream = new FileOutputStream(convFile)) {
            fileOutputStream.write(file.getBytes());
        }
        return convFile;
    }


}