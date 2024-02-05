package otel.trace;

import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;


@RestController
public class GreetingController {
    private static final  String template = "Hello, %s! I'm Bar. \n";

    @Autowired
    private LooGreetingService looGreetingService;
    @RequestMapping(value = "/greeting")
    public  String greeting(@RequestParam(value = "name",defaultValue = "World") String name,
                            @RequestHeader HttpHeaders httpHeaders){
        String looGreeting = looGreetingService.greeting();
        return headers(httpHeaders)+String.format(template,name)+looGreeting;
    }
    private String headers(HttpHeaders headers){
        StringBuilder sb = new StringBuilder();
        sb.append("Bar Request headers: \n");
        Set<String> headerNames = headers.keySet();
        for (String headerName : headerNames){
            List<String> headerValues = headers.getValuesAsList(headerName);
            sb.append('\t').append(headerName).append(": ").append(StringUtils.join(headerValues,',')).append('\n');
        }
        return sb.toString();
    }

}
