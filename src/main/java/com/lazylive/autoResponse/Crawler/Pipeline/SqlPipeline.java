package com.lazylive.autoResponse.Crawler.Pipeline;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.lazylive.autoResponse.Entity.CommandContent;
import com.lazylive.autoResponse.Service.CommandService;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

@Component
public class SqlPipeline implements Pipeline {
    private final CommandService commandservice;

    @Autowired
    public SqlPipeline(@Qualifier("commandService") CommandService commandservice) {
        this.commandservice = commandservice;
    }

    @Override
    public void process(ResultItems resultitems, Task task) {
        CommandContent content = resultitems.get("content");
        String[] contents = { content.getContent() };
        commandservice.insertCommandContent(content.getCommandId(), contents, content.getAdddate(),content.getUrl());
    }
}
