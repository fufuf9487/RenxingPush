package top.imzdx.qqpush.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.imzdx.qqpush.model.dto.Result;
import top.imzdx.qqpush.service.SystemService;

/**
 * @author Renxing
 */
@RestController
@CrossOrigin
@RequestMapping("/sys")
public class SystemController {
    @Autowired
    SystemService systemService;

    @GetMapping("qqbotlist")
    public Result getQQBotPublicList() {
        return new Result("ok", systemService.getPublicQqBot());
    }
}
