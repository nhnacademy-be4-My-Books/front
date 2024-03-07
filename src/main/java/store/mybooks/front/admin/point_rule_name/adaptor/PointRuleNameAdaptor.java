package store.mybooks.front.admin.point_rule_name.adaptor;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import store.mybooks.front.admin.point_rule_name.dto.request.PointRuleNameCreateRequest;
import store.mybooks.front.admin.point_rule_name.dto.response.PointRuleNameCreateResponse;
import store.mybooks.front.admin.point_rule_name.dto.response.PointRuleNameResponse;
import store.mybooks.front.config.GatewayAdaptorProperties;
import store.mybooks.front.utils.Utils;

/**
 * packageName    : store.mybooks.front.admin.point_rule_name.adaptor<br>
 * fileName       : PointRuleNameAdaptor<br>
 * author         : minsu11<br>
 * date           : 3/7/24<br>
 * description    :
 * ===========================================================<br>
 * DATE              AUTHOR             NOTE<br>
 * -----------------------------------------------------------<br>
 * 3/7/24        minsu11       최초 생성<br>
 */
@Component
@RequiredArgsConstructor
public class PointRuleNameAdaptor {
    private final GatewayAdaptorProperties gatewayAdaptorProperties;
    private final RestTemplate restTemplate;
    private static final String URL = "/api/point-rule-names";


    public List<PointRuleNameResponse> getPointRuleNameList() {
        ResponseEntity<List<PointRuleNameResponse>> exchange = restTemplate.exchange(
                gatewayAdaptorProperties.getAddress() + URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<PointRuleNameResponse>>() {
                }
        );
        return Utils.getResponseEntity(exchange, HttpStatus.OK);
    }

    public PointRuleNameCreateResponse createPointRuleName(PointRuleNameCreateRequest request) {
        HttpHeaders headers = Utils.getAuthHeader();
        HttpEntity<PointRuleNameCreateRequest> httpEntity = new HttpEntity<>(request, headers);
        ResponseEntity<PointRuleNameCreateResponse> exchange = restTemplate.exchange(
                gatewayAdaptorProperties.getAddress() + URL,
                HttpMethod.POST,
                httpEntity,
                new ParameterizedTypeReference<PointRuleNameCreateResponse>() {
                }
        );
        return Utils.getResponseEntity(exchange, HttpStatus.CREATED);
    }
}
