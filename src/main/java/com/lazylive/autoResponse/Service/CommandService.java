package com.lazylive.autoResponse.Service;

import java.util.List;

import com.lazylive.autoResponse.Entity.Command;
import com.lazylive.autoResponse.Entity.CommandContent;

public interface CommandService {

    /**
     * 根据commandId查询指令
     * 
     * @param commandId
     * @return
     */
    Command selectCommandById(String commandId);

    /**
     * 根据Command.name Command.description 字段查询指令集
     * 
     * @param command
     * @param description
     * @return List<Command>
     */
    List<Command> selectCommand(String command, String description);

    /**
     * 根据commandId 字段查询指令相应的内容集
     * 
     * @param commandId
     * @return List<CommandContent>
     */
    List<CommandContent> selectCommandContentByCmdId(String commandId);

    /**
     * 根据指令ID (Command.ID)，删除一或多条指令，并删除相应内容 (可以用事务，当删除指令或内容出现错误，回滚事务)
     * 
     * @param commandIdArray
     */
    int deleteCommandByIDList(String[] commandIdArray);

    /**
     * 根据内容ID (CommandContent.ID)，删除指令的一条或多条内容 (可以用事务，当删除指令或内容出现错误，回滚事务)
     * 
     * @param contentIdArray
     */
    int deleteCommandContentByIdList(String[] contentIdArray);

    /**
     * 在数据表中，插入一条指令，并返回其ID
     * 
     * @param command
     * @return
     */
    int insertCommand(String command, String description);

    /**
     * 根据指令ID (Command.ID)，插入一条或多条指令内容
     * 
     * @param commandId
     * @param contents
     */
    int insertCommandContent(String commandId, String[] contents,String url);

    int insertCommandAndContent(String command, String description, String[] contents,String url);

    int deleteCommandContentByAddDate(String date);

}
