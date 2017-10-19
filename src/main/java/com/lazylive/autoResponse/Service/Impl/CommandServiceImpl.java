package com.lazylive.autoResponse.Service.Impl;

import java.util.ArrayList;
import java.util.List;

import com.lazylive.autoResponse.Utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lazylive.autoResponse.Entity.Command;
import com.lazylive.autoResponse.Entity.CommandContent;
import com.lazylive.autoResponse.Dao.CommandDao;
import com.lazylive.autoResponse.Service.CommandService;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("commandService")
public class CommandServiceImpl implements CommandService {
    private final CommandDao commandDao;

    @Autowired
    public CommandServiceImpl(CommandDao commandDao) {
        this.commandDao = commandDao;
    }

    @Override
    @Transactional(readOnly = true,propagation= Propagation.REQUIRED,isolation= Isolation.READ_COMMITTED,rollbackForClassName="Exception")
    public List<Command> selectCommand(String strCommand, String description) {
        Command command = new Command();
        command.setName(strCommand);
        command.setDescription(description);
        return commandDao.selectCommand(command);
    }

    @Override
    @Transactional(readOnly = true,propagation= Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,rollbackForClassName="Exception")
    public List<CommandContent> selectCommandContentByCmdId(String commandId) {
        Command command = new Command();
        command.setId(commandId);
        return commandDao.selectCommandContentByCmdId(command);
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,isolation= Isolation.READ_COMMITTED,rollbackForClassName="Exception")
    public int deleteCommandByIDList(String[] commandIdArray) {
        if (commandIdArray.length == 0) {return -1;}

        int status = commandDao.deleteCommandById(commandIdArray);
        if (status > 0) {
            status = commandDao.deleteCommandContentByCommandId(commandIdArray);
        }
        return status;
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,isolation= Isolation.READ_COMMITTED,rollbackForClassName="Exception")
    public int deleteCommandContentByIdList(String[] contentIdArray) {
        if (contentIdArray.length == 0)
            return -1;
        return commandDao.deleteCommandContentById(contentIdArray);
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,isolation= Isolation.READ_COMMITTED,rollbackForClassName="Exception")
    public int insertCommand(String strCommand, String description) {
        if (strCommand == null || description == null) {
            return -1;
        }
        Command command = new Command();
        command.setName(strCommand);
        command.setDescription(description);
        return commandDao.insertCommand(command);
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,isolation= Isolation.READ_COMMITTED,rollbackForClassName="Exception")
    public int insertCommandContent(String commandId, String[] contents,String url) {
        if (commandId == null || contents.length == 0) {
            return -1;
        }
        List<CommandContent> commandContentList = new ArrayList<>();
        for (String content : contents) {
            if("".equals(content) || content.trim().isEmpty() ) break;
            CommandContent commandcontent = new CommandContent();
            commandcontent.setContent(content);
            commandcontent.setCommandId(commandId);
            commandcontent.setAdddate(TimeUtil.ISO_LOCAL_DATE());
            commandcontent.setUrl(url);
            commandContentList.add(commandcontent);
        }
        return commandDao.insertCommandContent(commandContentList);
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,isolation= Isolation.READ_COMMITTED,rollbackForClassName="Exception")
    public int insertCommandAndContent(String command, String description, String[] contents, String url) {
        int id = insertCommand(command,description);
        return insertCommandContent(String.valueOf(id),contents,url);
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,isolation= Isolation.READ_COMMITTED,rollbackForClassName="Exception")
    public Command selectCommandById(String commandId) {
        Command command = new Command();
        command.setId(commandId);
        command = commandDao.selectCommandById(command);
        command.setId(commandId);
        return command;
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,isolation= Isolation.READ_COMMITTED,rollbackForClassName="Exception")
    public int deleteCommandContentByAddDate(String date) {
        return commandDao.deleteCommandContentByAddDate(date);
    }
}
