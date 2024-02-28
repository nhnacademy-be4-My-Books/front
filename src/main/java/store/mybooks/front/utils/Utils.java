package store.mybooks.front.utils;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

/**
 * packageName    : store.mybooks.front.util<br>
 * fileName       : Util<br>
 * author         : minsu11<br>
 * date           : 2/27/24<br>
 * description    :
 * ===========================================================<br>
 * DATE              AUTHOR             NOTE<br>
 * -----------------------------------------------------------<br>
 * 2/27/24        minsu11       최초 생성<br>
 */
@Slf4j
public class Utils {

    private Utils() {
    }

    /**
     * methodName : getHttpHeader<br>
     * author : minsu11<br>
     * description : 중복된 {@code Http Hearder} 생성 부분을 메서드로 분리.
     * 매개변수에 아무것도 넣지 않으면 기본적으로 {@code Json}으로 설정.
     * <br> *
     *
     * @return http headers
     */
    public static HttpHeaders getHttpHeader() {
        return getHttpHeader(MediaType.APPLICATION_JSON, List.of(MediaType.APPLICATION_JSON));
    }

    /**
     * methodName : getHttpHeader<br>
     * author : minsu11<br>
     * description : 중복된 {@code Http Hearder} 생성 부분을 메서드로 분리.
     * {@code mediaType}으로 설정.
     * <br> *
     *
     * @param mediaType 요청과 응답을 받을 타입
     * @return http headers
     */
    public static HttpHeaders getHttpHeader(MediaType mediaType) {
        return getHttpHeader(mediaType, List.of(mediaType));
    }


    /**
     * methodName : getHttpHeader<br>
     * author : minsu11<br>
     * description : 중복된 {@code Http Hearder} 생성 부분을 메서드로 분리.
     * <br> *
     *
     * @param mediaType     요청 받을 타입
     * @param mediaTypeList 응답 받을 타입
     * @return http headers
     */
    public static HttpHeaders getHttpHeader(MediaType mediaType, List<MediaType> mediaTypeList) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaType);
        headers.setAccept(mediaTypeList);
        return headers;
    }


    /**
     * methodName : getResponseEntity<br>
     * author : minsu11<br>
     * description : 중복 코드
     * <br> *
     *
     * @param <T>           the type parameter
     * @param restTemplate  restTemplate
     * @param url           api
     * @param method        요청 방식
     * @param requestEntity 요청할 데이터
     * @param status        응답 받을 {@code Http status code}
     * @param targetType    반환 받을 객체 타입
     * @param id            api에 보낼 id
     * @return 반환 받을 타입 데이터
     */
    public static <T> T getResponseEntity(ResponseEntity<T> exchange, HttpStatus status) {


        if (exchange.getStatusCode() != status) {
            throw new RuntimeException();
        }

        return exchange.getBody();
    }


}