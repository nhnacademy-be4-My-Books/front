package store.mybooks.front.global;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import store.mybooks.front.auth.exception.AccessIdForbiddenException;
import store.mybooks.front.auth.exception.AuthenticationIsNotValidException;
import store.mybooks.front.auth.exception.StatusIsNotActiveException;
import store.mybooks.front.global.exception.ManageFailedException;
import store.mybooks.front.global.exception.ValidationFailException;

/**
 * packageName    : store.mybooks.front.global
 * fileName       : GlobalControllerAdvice
 * author         : damho-lee
 * date           : 2/27/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2/27/24          damho-lee          최초 생성
 */
@ControllerAdvice
public class GlobalControllerAdvice {
    /**
     * methodName : badRequestException <br>
     * author : damho-lee <br>
     * description : resource 서버에서 BadRequest, NotFound 상태코드가 오는 경우를 처리하는 ExceptionHandler.<br>
     *
     * @param exception HttpClientErrorException
     * @param model     Model
     * @return string
     */
    @ExceptionHandler({HttpClientErrorException.BadRequest.class, HttpClientErrorException.NotFound.class,
            ValidationFailException.class})
    public String badRequestException(Exception exception, Model model) {
        if (exception.getMessage() != null) {
            model.addAttribute("message", exception.getMessage());
        }
        return "admin/view/error";
    }

    @ExceptionHandler({ManageFailedException.class})
    public ModelAndView registerFailedException(ManageFailedException exception,
                                                RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView(exception.getUrl());
        modelAndView.addObject("msg", exception.getMessage());
        return modelAndView;
    }

    @ExceptionHandler({AuthenticationIsNotValidException.class, AccessIdForbiddenException.class,
            StatusIsNotActiveException.class})
    public String handleAuthException(RuntimeException ex) {

        if (ex instanceof AuthenticationIsNotValidException) {
            return "redirect:/login"; // 다시 로그인
        } else if (ex instanceof StatusIsNotActiveException) {
            return "redirect:/휴대폰인증 url"; // todo 휴대폰인증 페이지로
        }

        // 권한없는 경우 index
        return "redirect:/";
    }


}
