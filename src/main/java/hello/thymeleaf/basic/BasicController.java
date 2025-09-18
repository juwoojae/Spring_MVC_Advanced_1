package hello.thymeleaf.basic;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Controller 클래스 안의 메서드에서 return 이 String 이라면
 * 그 문자열이 논리 view 이름이 되고, viewResolver 가 논리 view 를 물리view경로 로 전환한후
 * 탬플릿을 렌더링해준다
 */
@Controller
@RequestMapping("/basic")
public class BasicController {

    //default 는 escape
    @GetMapping("/text-basic")
    public String textBasic(Model model){
        model.addAttribute("data", "<b>Hello Spring!</b>");
        return "basic/text-basic";
    }
    //unescape 하기 -? th:utext , [()] 사용하기
    @GetMapping("/text-unescaped")
    public String textUnescaped(Model model){
        model.addAttribute("data", "<b>Hello Spring!</b>");
        return "basic/text-unescaped";
    }
}
