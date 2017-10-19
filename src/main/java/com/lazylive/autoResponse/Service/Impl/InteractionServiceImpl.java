package com.lazylive.autoResponse.Service.Impl;

import java.util.*;

import com.alibaba.fastjson.JSON;
import com.lazylive.autoResponse.Entity.RequestJson;
import com.lazylive.autoResponse.Entity.ResponseJson;
import com.lazylive.autoResponse.Enum.StatusEnum;
import com.lazylive.autoResponse.Service.CommandService;
import com.lazylive.autoResponse.Service.InteractionService;
import com.lazylive.autoResponse.Utils.HTTPUtil;
import com.lazylive.autoResponse.Utils.PropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lazylive.autoResponse.Entity.Command;
import com.lazylive.autoResponse.Entity.CommandContent;

@Service("interactionService")
public class InteractionServiceImpl implements InteractionService {
    private final CommandService commandService;
    private static String flag = "";
    protected static int size = 0;

    @Autowired
    public InteractionServiceImpl(CommandService commandService) {
        this.commandService = commandService;
    }


    @Override
    public String selectCommand(String strCommand, String userid) {
        List<Command> commands;
        ResponseJson responseData;
        // 1.异常处理
        if (strCommand == null || strCommand.trim().isEmpty()) {
            responseData = new ResponseJson();
            responseData.setCode(StatusEnum.ERROR.statusCode());
            responseData.setStatus(StatusEnum.ERROR.toString());
            responseData.setText("数据格式不正确");

            return JSON.toJSONString(responseData);
        }

        //判断是否为全英文字符
        if (strCommand.matches("^[a-zA-Z]*")) {
            //在properties文件中查找
            strCommand = PropertiesUtil.getValue("/pinyinConversion.properties", strCommand);
        }

        //判断是否为数字
        if (strCommand.matches("\\d+")) {
            responseData = new ResponseJson();
            responseData.setCode(StatusEnum.SUCCESS.statusCode());
            responseData.setStatus(StatusEnum.SUCCESS.toString());
            responseData.setText((Integer.parseInt(strCommand)+1)+"");
            return JSON.toJSONString(responseData);
        }
        // 判断'我教你'命令格式  '#((\w|[\u4e00-\u9fa5])+[,]?)+' 只匹配 符合 '#aaaa,bbbb,...'格式的指令
        if (strCommand.trim().matches("#((\\w|[\\u4e00-\\u9fa5])+[,]?)+")) {
            //将指令暂存到 flag 字段
            flag = strCommand;
            strCommand = "用户定义";
        }
        // 多指令判断
        switch (strCommand.trim()) {
            // 后台地址
            case "!9527":
                responseData = new ResponseJson();
                responseData.setCode(StatusEnum.SUCCESS.statusCode());
                responseData.setStatus(StatusEnum.SUCCESS.toString());
                responseData.setText("后台地址");
                responseData.setUrl("www.bing.com");
                return JSON.toJSONString(responseData);
            // 学习指令格式
            case "我教你":
                responseData = new ResponseJson();
                responseData.setCode(StatusEnum.SUCCESS.statusCode());
                responseData.setStatus(StatusEnum.SUCCESS.toString());
                responseData.setUrl(null);
                responseData.setText("^_^ 请以：\n '#问题,回复1,回复2,...' \n 的格式告诉我，我就能学会了哦~");
                return JSON.toJSONString(responseData);
            // 学习用户指令
            case "用户定义":
                //切割字符串
                String[] usrCommand = flag.substring(1).split(",");
                //过滤 ' #aaa, ' 格式的内容
                if (usrCommand.length <= 1) {
                    responseData = new ResponseJson();
                    responseData.setCode(StatusEnum.SUCCESS.statusCode());
                    responseData.setStatus(StatusEnum.SUCCESS.toString());
                    responseData.setUrl(null);
                    responseData.setText("w(ﾟДﾟ)w 请不要输入空回复~");
                    return JSON.toJSONString(responseData);
                }
                //插入指令
                //判断是否有重复指令
                commands = commandService.selectCommand(usrCommand[0],"用户定义");
                String[] copy = new String[usrCommand.length-1];
                int b;
                // 去除指令名 数组复制
                System.arraycopy(usrCommand,1,copy,0,usrCommand.length-1);
                if (commands.size() > 0) {
                    b = commandService.insertCommandContent(commands.get(0).getId(),copy,null);
                } else {
                    b = commandService.insertCommandAndContent(usrCommand[0],"用户定义",copy,null); //不存在指令 返回新插入指令ID
                }
                // 判断是否插入成功
                if (b > 0) {
                    responseData = new ResponseJson();
                    responseData.setCode(StatusEnum.SUCCESS.statusCode());
                    responseData.setStatus(StatusEnum.SUCCESS.toString());
                    responseData.setUrl(null);
                    responseData.setText("(￣_,￣ ) 我学会了哦~问我'" + usrCommand[0]+ "'看看~");
                    return JSON.toJSONString(responseData);
                } else {
                    responseData = new ResponseJson();
                    responseData.setCode(StatusEnum.SUCCESS.statusCode());
                    responseData.setStatus(StatusEnum.SUCCESS.toString());
                    responseData.setUrl(null);
                    responseData.setText("( ĭ ^ ĭ ) 诶..不好意思，我没学会呀..请稍后再试试..");
                    return JSON.toJSONString(responseData);
                }
            default:
                // 数据库常规指令查询输出
                commands = commandService.selectCommand(strCommand,null);
                // 判断是否有指令
                if (flag.equals(strCommand) && size > 0) {
                    List<CommandContent> contents = commands.get(0).getContentList();
                    responseData = new ResponseJson();
                    responseData.setCode(StatusEnum.SUCCESS.statusCode());
                    responseData.setStatus(StatusEnum.SUCCESS.toString());
                    responseData.setUrl(contents.get(--size).getUrl());
                    responseData.setText(contents.get(--size).getContent());
                    return JSON.toJSONString(responseData);
                }
                if (commands.size() > 0) {
                    flag = strCommand;
                    size = commands.get(0).getContentList().size();
                    if (size > 0) {
                        List<CommandContent> contents = commands.get(0).getContentList();
                        responseData = new ResponseJson();
                        responseData.setCode(StatusEnum.SUCCESS.statusCode());
                        responseData.setStatus(StatusEnum.SUCCESS.toString());
                        responseData.setUrl(contents.get(size - 1).getUrl());
                        responseData.setText(contents.get(size - 1).getContent());
                        return JSON.toJSONString(responseData);
                    } else {
                        responseData = new ResponseJson();
                        responseData.setCode(StatusEnum.SUCCESS.statusCode());
                        responseData.setStatus(StatusEnum.SUCCESS.toString());
                        responseData.setUrl(null);
                        responseData.setText("Σ( ° △ °|||)︴ 没有更多'" + strCommand + "'了~");
                        return JSON.toJSONString(responseData);
                    }
                } else {    // 调用图灵接口
                    RequestJson requestJson = new RequestJson();
                    requestJson.setInfo(strCommand);
                    requestJson.setUserId(userid);
                    requestJson.setKey("8fd88d4f6cb144598c7393890d0584dd");

                    String responseJson = HTTPUtil.sendPost("http://www.tuling123.com/openapi/api", requestJson);
                    // 当次数超出1000次时切换key
                    if (responseJson.contains("40004")) {
                        List<String> listKey = new LinkedList<>();
                        listKey.add("5e492765a6734344a303608049e58fd8");
                        listKey.add("f5140ec34e624a37bc985aacd6192ebd");
                        listKey.add("dcdef080c9ef4fd8b176ebe9d5773d8f");
                        listKey.add("f0b4781da12c47448ca298ec1ccf7d7a");
                        for (String aListKey : listKey) {
                            //设置新key
                            requestJson.setKey(aListKey);
                            //重新请求
                            responseJson = HTTPUtil.sendPost("http://www.tuling123.com/openapi/api", requestJson);
                            if (!responseJson.contains("40004")) {
                                responseData = JSON.parseObject(responseJson, ResponseJson.class);
                                responseData.setCode(StatusEnum.SUCCESS.statusCode());
                                responseData.setStatus(StatusEnum.SUCCESS.toString());
                                return JSON.toJSONString(responseData);
                            }
                        }
                        //当数据库无内容，且图灵机器人 请求次数使用完毕才会回复此信息
                        responseJson = responseJson.replaceAll("\"text\":\"[\\u4e00-\\u9fa5]+\"", "回复'我教你'让我学到更多知识!");
                        responseData = JSON.parseObject(responseJson, ResponseJson.class);
                        responseData.setCode(StatusEnum.SUCCESS.statusCode());
                        responseData.setStatus(StatusEnum.SUCCESS.toString());
                        return JSON.toJSONString(responseData);
                    } else if (responseJson.contains("\"" + strCommand + "\"")) {  //当图灵没有相关指令时
                        responseData = new ResponseJson();
                        responseData.setCode(StatusEnum.SUCCESS.statusCode());
                        responseData.setStatus(StatusEnum.SUCCESS.toString());
                        responseData.setText("(๑•̀ㅂ•́)و✧ 咦,你说的'" + strCommand + "'是什么呀~\n回复'我教你'让我学到更多知识!");
                        return JSON.toJSONString(responseData);
                    } else if (responseJson.contains("图灵工程师")) { // 替换 图灵工程师 标志
                        responseJson = responseJson.replaceAll("图灵工程师", "cici");
                        responseData = JSON.parseObject(responseJson, ResponseJson.class);
                        responseData.setCode(StatusEnum.SUCCESS.statusCode());
                        responseData.setStatus(StatusEnum.SUCCESS.toString());
                        return JSON.toJSONString(responseData);
                    }

                    // 插入内容
                    String[] contens= {responseJson.substring(responseJson.indexOf("t\":\"") + 4, responseJson.lastIndexOf("\""))};
                    commandService.insertCommandAndContent(strCommand,"指令学习",contens,null);
                    // 图灵正常返回
                    responseData = JSON.parseObject(responseJson, ResponseJson.class);
                    responseData.setCode(StatusEnum.SUCCESS.statusCode());
                    responseData.setStatus(StatusEnum.SUCCESS.toString());
                    return JSON.toJSONString(responseData);
                }
        }
    }
}
