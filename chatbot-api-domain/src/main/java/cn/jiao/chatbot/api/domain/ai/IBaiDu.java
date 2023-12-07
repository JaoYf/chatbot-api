package cn.jiao.chatbot.api.domain.ai;

import java.io.IOException;

/**
 * @author 焦宇飞
 * @Description 文心一言接口https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/chat/completions_pro
 */
public interface IBaiDu {

    String doWxyy(String wxyyToken, String question) throws IOException;

}
