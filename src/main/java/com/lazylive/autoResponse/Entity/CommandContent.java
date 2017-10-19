package com.lazylive.autoResponse.Entity;

/**
 * 与指令内容表对应的实体类
 * @author Lazy-F
 */
public class CommandContent {
    /**
     * 主键
     */
    private String id;

    /**
     * 自动回复的内容
     */
    private String content;

    /**
     * 关联的指令表主键
     */
    private String commandId;
    /**
     * 内容添加时间
     */
    private String adddate;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAdddate() {
        return adddate;
    }

    public void setAdddate(String adddate) {
        this.adddate = adddate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCommandId() {
        return commandId;
    }

    public void setCommandId(String commandId) {
        this.commandId = commandId;
    }

    @Override
    public String toString() {
        return "CommandContent [id=" + id + ", content=" + content + ", commandId=" + commandId + "]";
    }

}
