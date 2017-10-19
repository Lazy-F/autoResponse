package com.lazylive.autoResponse.Dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lazylive.autoResponse.Entity.Command;
import com.lazylive.autoResponse.Entity.CommandContent;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface CommandDao {
    /**
     * 根据Command对象的 name 和 description 字段查询指令列表。
     * 当 name 和 description 为空时查询全表
     * 其中，name字段不能使用模糊查询，而description字段为模糊查询。
     * @param command
     * @return
     */
    List<Command> selectCommand(Command command);
    /**
     * 根据command.id 查询相应内容，并返回内容列表
     * @param command
     * @return
     */
    List<CommandContent> selectCommandContentByCmdId(Command command);
    /**
     * 向数据库添加一条指令
     * @param command
     * @return 受影响行数
     */
    int insertCommand(Command command);
    /**
     * 添加一条或多条Content
     * @param commandContents
     * @return 受影响行数
     */
    int insertCommandContent(List<CommandContent> commandContents);
    
    /**
     * 根据ID删除指令
     * @param strid
     * @return 受影响行数
     */
    int deleteCommandById(String[] strid);
    /**
     * 根据ID删除指令内容
     * @param strid
     * @return 受影响行数
     */
    int deleteCommandContentById(String[] strid);
    /**
     * 根据一个或多个CommandID删除Content
     * @param strid
     * @return 受影响行数
     */
    int deleteCommandContentByCommandId(String[] strid);
   
    Command selectCommandById(Command command);

    int deleteCommandContentByAddDate(String date);
}
