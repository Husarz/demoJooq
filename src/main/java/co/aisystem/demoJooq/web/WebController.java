package co.aisystem.demoJooq.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Future;

@RestController
@RequiredArgsConstructor
@Slf4j
public class WebController {


    @GetMapping(value = "/person/export")
    @ResponseStatus(HttpStatus.OK)
    public Future<String> exportMember(
                                      @RequestParam List<String> fields
                                        //HttpServletResponse httpResponse
    ) throws IOException {

            return null
;
    }

}
