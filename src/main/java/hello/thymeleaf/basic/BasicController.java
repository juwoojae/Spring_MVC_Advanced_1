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

    /**
     * User 에 @Data 에노테이션 꼭 넣기!!
     * 문자열로 개체의 프로퍼티나 메서드에 접근할수있는 도구이다
     * User.username==User['username']==user.getUsername()
     */
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
    /// 유틸리티 객체와 날짜 아래 링크
    //https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html#expression-utility-objects <=타임리프 유틸리티 객체

    @GetMapping("/link")
    public String link(Model model){
        model.addAttribute("param1", "data1");
        model.addAttribute("param2", "data2");
        return "basic/link";
    }

    @GetMapping("/literal")
    public String literal(Model model){
        model.addAttribute("data", "Spring!");
        return "basic/literal";
    }

    @GetMapping("/operation")
    public String operation(Model model){
        model.addAttribute("nullData",null);
        model.addAttribute("data", "Spring");
        return "basic/operation";
    }

    @GetMapping("/attribute")
    public String attribute(){
        return "basic/attribute";
    }

    @GetMapping("/each")
    public String each(Model model){
        addUsers(model);
        return "basic/each";
    }
    @GetMapping ("/condition")
    public String condition(Model model){
        addUsers(model);
        return "basic/condition";
    }
    @GetMapping("/comments")
    public String comments(Model model){
        model.addAttribute("data", "Spring!");
        return "basic/comments";
    }
    @GetMapping("/block")
    public String block(Model model){
        addUsers(model);
        return "basic/block";
    }

    /**
     * 타임 리프는 자바스크립트에서 타임리프를 편리하게 사용할 수 있는 자바스크립트 인라인 기능을 제공한다
     */
    @GetMapping("/javascript")
    public String javascript(Model model){

        model.addAttribute("user", new User("UserA",10));
        addUsers(model);//"users",list 모델에 추가
        return "basic/javascript";
    }

    // (users, list) 를 model 에 넣는 Test init
    private void addUsers(Model model){
        List<User> list = new ArrayList<>();
        list.add(new User("UserA", 10));
        list.add(new User("UserB", 20));
        list.add(new User("UserC", 30));

        model.addAttribute("users", list);
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
