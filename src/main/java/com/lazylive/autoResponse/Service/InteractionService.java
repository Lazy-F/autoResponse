package com.lazylive.autoResponse.Service;

public interface InteractionService {
    /**
     * 跟据用户指令返回数据库交互数据
     * @param strCommand
     * @return
     */
    String selectCommand(String strCommand,String userid);
}
