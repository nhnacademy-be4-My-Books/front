package store.mybooks.front.oauth.controller;

import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import store.mybooks.front.auth.adaptor.TokenAdaptor;
import store.mybooks.front.auth.dto.request.TokenCreateRequest;
import store.mybooks.front.auth.dto.response.TokenCreateResponse;
import store.mybooks.front.oauth.service.OauthService;
import store.mybooks.front.user.dto.response.UserLoginResponse;
import store.mybooks.front.utils.CookieUtils;
import store.mybooks.front.utils.Utils;

/**
 * packageName    : store.mybooks.front.oauth2<br>
 * fileName       : OauthRestController<br>
 * author         : masiljangajji<br>
 * date           : 3/3/24<br>
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 3/3/24        masiljangajji       최초 생성
 */
@Controller
@RequiredArgsConstructor
public class OauthController {

    private final OauthService oauthService;

    private final TokenAdaptor tokenAdaptor;

    /**
     * methodName : oauthLogin
     * author : masiljangajji
     * description : 페이코 로그인을 처리함
     *
     * @param provider provider , Payco
     * @param code     code
     * @param request  request
     * @param response response
     * @return string
     */
    @GetMapping("/login/oauth2/code/{provider}")
    public String oauthLogin(@PathVariable String provider, @RequestParam String code, HttpServletRequest request,
                             HttpServletResponse response) {

        UserLoginResponse loginResponse = oauthService.oauthLogin(provider, code);

        if (loginResponse.getUserId().equals(0L)) {
            return "redirect:/verification/social/" + loginResponse.getStatus();
        }

        if (loginResponse.getIsValidUser()) { // 기존에 계정이 있거나 , 회원가입에 문제가 없는 경우 로그인

            TokenCreateResponse tokenCreateResponse =
                    tokenAdaptor.createToken(
                            new TokenCreateRequest(loginResponse.getIsAdmin(), loginResponse.getUserId(),
                                    loginResponse.getStatus(), String.valueOf(UUID.randomUUID()),
                                    Utils.getUserIp(request), Utils.getUserAgent(request)));


            CookieUtils.addJwtCookie(response, tokenCreateResponse.getAccessToken());
            return "redirect:/";
        }

        return "redirect:/login";
    }


}