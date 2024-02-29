package store.mybooks.front.admin.book.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import store.mybooks.front.admin.author.service.AuthorService;
import store.mybooks.front.admin.book.model.request.BookCreateRequest;
import store.mybooks.front.admin.book.model.request.BookModifyRequest;
import store.mybooks.front.admin.book.service.BookAdminService;
import store.mybooks.front.admin.category.service.CategoryService;
import store.mybooks.front.admin.publisher.service.PublisherService;
import store.mybooks.front.admin.tag.service.TagService;

/**
 * packageName    : store.mybooks.front.admin.book.controller <br/>
 * fileName       : BookAdminController<br/>
 * author         : newjaehun <br/>
 * date           : 2/26/24<br/>
 * description    :<br/>
 * ===========================================================<br/>
 * DATE              AUTHOR             NOTE<br/>
 * -----------------------------------------------------------<br/>
 * 2/26/24        newjaehun       최초 생성<br/>
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/book")
public class BookAdminController {
    private final BookAdminService bookAdminService;
    private final PublisherService publisherService;
    private final CategoryService categoryService;
    private final TagService tagService;
    private final AuthorService authorService;

    /**
     * methodName : getBookPage
     * author : newjaehun
     * description : 사이드바에서 책을 눌렀을 때 페이지.
     *
     * @param pageable Pageable
     * @param model Model
     * @return string
     */
    @GetMapping
    public String getBookPage(@PageableDefault(size = 8) Pageable pageable, Model model) {
        model.addAttribute("books", bookAdminService.getBooks(pageable));
        return "admin/view/book";
    }

    /**
     * methodName : getBookRegisterPage
     * author : newjaehun
     * description : 도서 등록 페이지
     *
     * @param model Model
     * @return string
     */
    @GetMapping("/register")
    public String getBookRegisterPage(Model model) {
        model.addAttribute("publishers", publisherService.getAllPublishers());
        model.addAttribute("categories", categoryService.getCategories());
        model.addAttribute("tags", tagService.getTags());
        model.addAttribute("authors", authorService.getAllAuthors());
        model.addAttribute("bookStatuses", bookAdminService.getBookStatus());
        return "admin/view/book-register";
    }

    /**
     * methodName : createBook
     * author : newjaehun
     * description : 도서 등록 요청 받는 메소드.
     *
     * @param bookCreateRequest BookCreateRequest
     * @return string
     */
    @PostMapping("/register")
    public String createBook(@Valid @ModelAttribute BookCreateRequest bookCreateRequest) {
        bookAdminService.createBook(bookCreateRequest);
        return "redirect:/admin/book/register";
    }

    /**
     * methodName : getBookUpdatePage
     * author : newjaehun
     * description : 도서 업데이트 페이지.
     *
     * @param bookId BookId
     * @param model Model
     * @return string
     */
    @GetMapping("/update")
    public String getBookUpdatePage(@RequestParam("id") Long bookId, Model model) {
        model.addAttribute("book", bookAdminService.getBook(bookId));
        return "admin/view/book-update";
    }

    /**
     * methodName : updateBook
     * author : newjaehun
     * description : 도서 업데이트 요청.
     *
     * @param modifyRequest ModifyRequest
     * @return string
     */
    @PostMapping("/update")
    public String updateBook(@ModelAttribute BookModifyRequest modifyRequest) {
        bookAdminService.updateBook(modifyRequest);
        return "redirect:/admin/book";
    }
}