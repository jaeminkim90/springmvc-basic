package hello.springmvc.basic.request;

import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

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


    /**
     * @RequestParam 사용
     * - 파라미터 이름으로 바인딩
     * @ResponseBody 추가
     * - View 조회를 무시하고, HTTP message body에 직접 해당 내용 입력 */
    @ResponseBody // 리턴 String을 응답 메시지로 바로 전달한다
    @RequestMapping("/request-param-v2")
    public String requestParamV2(
            @RequestParam("username") String memberName,
            @RequestParam("age") int memberAge) {
        log.info("username={}, age={}", memberName, memberAge);
        return "OK!";
    }

    /**
     * @RequestParam 사용
     * HTTP 파라미터 이름이 변수 이름과 같으면 @RequestParam(name="xx") 생략 가능 */
    @ResponseBody // 리턴 String을 응답 메시지로 바로 전달한다
    @RequestMapping("/request-param-v3")
    public String requestParamV3(
            //  @RequestParam의 이름을 생략하려면 요청 파라미터로 넘어오는 이름과 @RequestParam 파라미터 변수명이 같아야 한다.
            @RequestParam String username,
            @RequestParam int age) {
        log.info("username={}, age={}", username, age);
        return "OK!";
    }

    /**
     * @RequestParam 사용
     * String, int 등의 단순 타입이면 @RequestParam 도 생략 가능 */
    @ResponseBody // 리턴 String을 응답 메시지로 바로 전달한다
    @RequestMapping("/request-param-v4")
    public String requestParamV4(String username, int age) {
        // 파라미터 정보가 단순 타입(String, int, Integer)이면 @RequestParam를 아예 생략할 수 있다.
        // 단, 요청 파라미터로 넘어오는 이름과 파라미터의 변수명이 같아야 한다.

        log.info("username={}, age={}", username, age);
        return "OK!";
    }

    /**
     * @RequestParam.required
     * /request-param -> username이 없으므로 예외 *
     * 주의!
     * /request-param?username= -> 빈문자로 통과 *
     * 주의!
     * /request-param
     * int age -> null을 int에 입력하는 것은 불가능, 따라서 Integer 변경해야 함(또는 다음에 나오는
    defaultValue 사용) */
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

    /**
     * @RequestParam
     * - defaultValue 사용 *
     * 참고: defaultValue는 빈 문자의 경우에도 적용 * /request-param?username=
     */
    // 파라미터 값이 없을 때, 기본값을 지정하는 defaultValue
    @ResponseBody // 리턴 String을 응답 메시지로 바로 전달한다
    @RequestMapping("/request-param-default")
    public String requestParamDefault(
            // defaultValue가 있으면, 항상 기본값이 존재하기 때문에 required 옵션은 무의미해진다.
            @RequestParam(required = true, defaultValue = "guest") String username,
            @RequestParam(required = false, defaultValue = "-1") int age) {

        log.info("username={}, age={}", username, age);
        return "OK!";
    }

    /**
     * @RequestParam Map, MultiValueMap
     * Map(key=value)
     * MultiValueMap(key=[value1, value2, ...] ex) (key=userIds, value=[id1, id2])
     */
    // 요청 파라미터 정보를 Map으로 받을 수 있다.
    @ResponseBody // 리턴 String을 응답 메시지로 바로 전달한다
    @RequestMapping("/request-param-map")
    public String requestParamMap(@RequestParam Map<String, Object> paramMap) {
        log.info("username={}, age={}", paramMap.get("username"), paramMap.get("age"));
        return "OK!";
    }

    @ResponseBody
    @RequestMapping("/model-attribute-v1")
    public String modelAttributeV1(@ModelAttribute HelloData helloData) {

        log.info(helloData.toString());
        log.info("helloData={}",helloData);
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());

        return "OK!";
    }
}
