package cn.jiao.chatbot.api.domain.ai.service;

import cn.jiao.chatbot.api.domain.ai.IBaiDu;
import cn.jiao.chatbot.api.domain.ai.model.aggregates.AIAnswer;
import com.alibaba.fastjson.JSON;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;


/**
 * @author 焦宇飞
 * 2023/12/5 17:27
 */
@Service
public class BaiDu implements IBaiDu {

    private Logger log = LoggerFactory.getLogger(BaiDu.class);

    @Value("${baidu.access-token}")
    private String wxyyAccessToken;

    @Override
    public String doWxyy(String question) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpPost post = new HttpPost("https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/chat/completions_pro?access_token=" + wxyyAccessToken);

        post.addHeader("Content-Type", "application/json");

        String paramJson = "{\n" +
                "\t\"messages\": [{\n" +
                "\t\t\"role\": \"user\",\n" +
                "\t\t\"content\": \"" + question + "\"\n" +
                "\t}]\n" +
                "}";

        StringEntity entity = new StringEntity(paramJson, ContentType.create("text/json", "UTF-8"));
        post.setEntity(entity);

        CloseableHttpResponse response = httpClient.execute(post);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String jsonStr = EntityUtils.toString(response.getEntity());
            // log.info("测试数据：{}", jsonStr);
            AIAnswer aiAnswer = JSON.parseObject(jsonStr, AIAnswer.class);
            return aiAnswer.getResult();
        } else {
            throw new RuntimeException("无法调用接口, " + response.getStatusLine().getStatusCode());
        }
    }
}
