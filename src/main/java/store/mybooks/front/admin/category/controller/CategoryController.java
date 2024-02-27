package store.mybooks.front.admin.category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import store.mybooks.front.admin.category.model.request.CategoryCreateRequest;
import store.mybooks.front.admin.category.model.request.CategoryModifyRequest;
import store.mybooks.front.admin.category.model.response.CategoryGetResponseForUpdate;
import store.mybooks.front.admin.category.service.CategoryService;

/**
 * packageName    : store.mybooks.front.admin.controller
 * fileName       : CategoryController
 * author         : damho-lee
 * date           : 2/23/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2/23/24          damho-lee          최초 생성
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/category")
public class CategoryController {
    private final CategoryService categoryService;

    /**
     * methodName : getCategoryPage <br>
     * author : damho-lee <br>
     * description : 사이드바에서 카테고리를 눌렀을 때 페이지.<br>
     *
     * @param page Integer
     * @param size Integer
     * @param model Model
     * @return string
     */
    @GetMapping
    public String getCategoryPage(@RequestParam(value = "page", required = false) Integer page,
                                  @RequestParam(value = "size", required = false) Integer size,
                                  Model model) {
        if (page == null) {
            page = 0;
        }

        if (size == null) {
            size = 10;
        }

        model.addAttribute("categories", categoryService.getCategories(page, size).getContent());

        return "/admin/view/category";
    }

    /**
     * methodName : getCategoryRegisterPage <br>
     * author : damho-lee <br>
     * description : 카테고리 등록 페이지.<br>
     *
     * @param model Model
     * @return string
     */
    @GetMapping("/register")
    public String getCategoryRegisterPage(Model model) {
        model.addAttribute(categoryService.getHighestCategories());
        return "/admin/view/category-register";
    }

    /**
     * methodName : categoryRegister <br>
     * author : damho-lee <br>
     * description : 카테고리 등록 요청을 받음. 성공적으로 처리되면 카테고리 등록 페이지로 리다이텍트함.<br>
     *
     * @param categoryCreateRequest CategoryCreateRequest
     * @return string
     */
    @PostMapping("/register")
    public String categoryRegister(@ModelAttribute CategoryCreateRequest categoryCreateRequest) {
        categoryService.createCategory(categoryCreateRequest);
        return "redirect:/admin/category/register";
    }

    /**
     * methodName : deleteCategory <br>
     * author : damho-lee <br>
     * description : 카테고리 삭제 요청을 처리. 성공적으로 처리되면 카테고리 페이지로 리다이렉트<br>
     *
     * @param id Integer
     * @return string
     */
    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable("id") Integer id) {
        categoryService.deleteCategory(id);
        return "redirect:/admin/category";
    }

    /**
     * methodName : getUpdateForm <br>
     * author : damho-lee <br>
     * description : 카테고리 수정 페이지. resource 서버에서 id 를 통해 데이터를 받아와서 model 에 넣어준다.<br>
     *
     * @param id Integer
     * @param model Model
     * @return string
     */
    @GetMapping("/update")
    public String getUpdateForm(@RequestParam("id") Integer id, Model model) {
        CategoryGetResponseForUpdate categoryGetResponseForUpdate = categoryService.getCategory(id);
        model.addAttribute("category", categoryGetResponseForUpdate.getTargetCategory());
        model.addAttribute("levelOneCategory", categoryGetResponseForUpdate.getLevelOneCategory());
        model.addAttribute("levelTwoCategory", categoryGetResponseForUpdate.getLevelTwoCategory());

        return "/admin/view/category-update";
    }

    /**
     * methodName : updateCategory <br>
     * author : damho-lee <br>
     * description : 카테고리 수정 요청 처리. 성공적으로 처리되면 카테고리 페이지로 리다이렉트.<br>
     *
     * @param categoryModifyRequest CategoryModifyRequest
     * @return string
     */
    @PostMapping("/update")
    public String updateCategory(@ModelAttribute CategoryModifyRequest categoryModifyRequest) {
        categoryService.updateCategory(categoryModifyRequest);
        return "redirect:/admin/category";
    }

    /**
     * methodName : badRequestException <br>
     * author : damho-lee <br>
     * description : resource 서버에서 BadRequest, NotFound 상태코드가 오는 경우를 처리하는 ExceptionHandler.<br>
     *
     * @param exception HttpClientErrorException
     * @param model Model
     * @return string
     */
    @ExceptionHandler({HttpClientErrorException.BadRequest.class, HttpClientErrorException.NotFound.class})
    public String badRequestException(HttpClientErrorException exception, Model model) {
        if (exception.getMessage() != null) {
            model.addAttribute("message", exception.getMessage());
        }
        return "/admin/view/error";
    }
}