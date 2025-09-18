package hello.thymeleaf.basic;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @GetMapping("/variable")
    public String variable(Model model){
        User userA = new User("userA", 10); //View 로 Model 넘겨준후, Model은 이것으로 화면 렌더링
        User userB = new User("userB", 20);

        List<User> list=new ArrayList<>();
        list.add(userA);
        list.add(userB);

        Map<String, User> map = new HashMap<>();
        map.put("userA", userA);
        map.put("userB", userB);

        model.addAttribute("user", userA);
        model.addAttribute("users", list);  //Model 에 key - value 의 형태로 들어간다
        model.addAttribute("userMap", map);

        return "basic/variable";
    }

    /**
     *  스프링 부트 3.0 부터는 ${#request} , ${#response} , ${#session} , ${#servletContext} 를 지원하
     * 지 않는다. 그래서 Model 에 넣은후 view 에 전달해야한다
     */
    @GetMapping("/basic-objects")
    public String basicObjects(Model model, HttpServletRequest request,
                               HttpServletResponse response, HttpSession session) {
        session.setAttribute("sessionData", "Hello Session");
        model.addAttribute("request", request);
        model.addAttribute("response", response);
        model.addAttribute("servletContext", request.getServletContext());
        return "basic/basic-objects";
    }
    @Component("helloBean")
    static class HelloBean {
        public String hello(String data) {
            return "Hello " + data;
        }
    }
    @Data
    static class User{
        private String username;
        private int age;

        public User(String username, int age) {
            this.username = username;
            this.age = age;
        }
    }
}
