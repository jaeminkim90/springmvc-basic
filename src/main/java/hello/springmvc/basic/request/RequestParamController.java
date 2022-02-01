package hello.springmvc.basic.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Controller
public class RequestParamController {

    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));
        log.info("username={}, age={}", username, age);

        response.getWriter().write("OK!");
    }

    @ResponseBody // 리턴 String을 응답 메시지로 바로 전달한다
    @RequestMapping("/request-param-v2")
    public String requestParamV2(
            @RequestParam("username") String memberName,
            @RequestParam("age") int memberAge) {
        log.info("username={}, age={}", memberName, memberAge);
        return "OK!";
    }

    @ResponseBody // 리턴 String을 응답 메시지로 바로 전달한다
    @RequestMapping("/request-param-v3")
    public String requestParamV3(
            //  @RequestParam의 이름을 생략하려면 요청 파라미터로 넘어오는 이름과 @RequestParam 파라미터 변수명이 같아야 한다.
            @RequestParam String username,
            @RequestParam int age) {
        log.info("username={}, age={}", username, age);
        return "OK!";
    }

    @ResponseBody // 리턴 String을 응답 메시지로 바로 전달한다
    @RequestMapping("/request-param-v4")
    public String requestParamV4(String username, int age) {
        // 파라미터 정보가 단순 타입(String, int, Integer)이면 @RequestParam를 아예 생략할 수 있다.
        // 단, 요청 파라미터로 넘어오는 이름과 파라미터의 변수명이 같아야 한다.

        log.info("username={}, age={}", username, age);
        return "OK!";
    }

    // 필수 파라미터를 지정하는 required 사용 예제
    @ResponseBody // 리턴 String을 응답 메시지로 바로 전달한다
    @RequestMapping("/request-param-required")
    public String requestParamRequired(
            // required ture일 경우, 해당 데이터가 요청 파라미터에 필수로 포함되어야 한다.
            // required는 기본값이 true다.
            @RequestParam(required = true) String username,
            @RequestParam(required = false) Integer age) { // 기본형인 int는 null 불가

        log.info("username={}, age={}", username, age);
        return "OK!";
    }
}
