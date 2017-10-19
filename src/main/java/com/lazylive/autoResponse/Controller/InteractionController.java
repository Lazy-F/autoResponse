package com.lazylive.autoResponse.Controller;

import com.lazylive.autoResponse.Entity.RequestJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.lazylive.autoResponse.Service.InteractionService;

/**
 * 用户与后台交互
 */
@Controller
public class InteractionController {
    private final InteractionService interactionService;

    @Autowired
    public InteractionController(@Qualifier("interactionService") InteractionService interactionService) {
        this.interactionService = interactionService;
    }


    /**
     * 接收用户指令
     * @return response
     */
    @ResponseBody
    @RequestMapping(value = "/autoReply", method = RequestMethod.POST)
    public String autoReply(@RequestBody RequestJson requestJson) {
        //判断id是否存在 是否创建线程
        return interactionService.selectCommand(requestJson.getInfo(),requestJson.getUserId());
    }
}
