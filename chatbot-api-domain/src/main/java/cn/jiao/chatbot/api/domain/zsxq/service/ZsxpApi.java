package cn.jiao.chatbot.api.domain.zsxq.service;

import cn.jiao.chatbot.api.domain.zsxq.IZsxpApi;
import cn.jiao.chatbot.api.domain.zsxq.model.aggregates.UnAnsweredQuestionsAggregates;
import cn.jiao.chatbot.api.domain.zsxq.model.req.AnswerReq;
import cn.jiao.chatbot.api.domain.zsxq.model.req.ReqData;
import cn.jiao.chatbot.api.domain.zsxq.model.res.AnswerRes;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.rmi.RemoteException;

/**
 * @author 焦宇飞
 * 2023/12/4 21:47
 */
@Service
public class ZsxpApi implements IZsxpApi {

    private final Logger log = LoggerFactory.getLogger(ZsxpApi.class);

    @Override
    public UnAnsweredQuestionsAggregates queryUnAnsweredQuestionsTopicId(String groupId, String cookie) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpGet get = new HttpGet("https://api.zsxq.com/v2/groups/"+ groupId +"/topics?scope=unanswered_questions&count=20");

        get.addHeader("cookie", cookie);
        get.addHeader("Content-Type", "application/json,charset=utf8");

        CloseableHttpResponse response = httpClient.execute(get);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String jsonStr = EntityUtils.toString(response.getEntity());
            log.info("拉取提问数据 groupId：{}, jsonStr:{}", groupId, jsonStr);
            return JSON.parseObject(jsonStr, UnAnsweredQuestionsAggregates.class);
        } else {
            throw new RuntimeException("queryUnAnsweredQuestionsTopicId 出错了，状态码：" + response.getStatusLine().getStatusCode());
        }
    }

    @Override
    public boolean answer(String groupId, String cookie, String topicId, String text, boolean silenced) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpPost post = new HttpPost("https://api.zsxq.com/v2/topics/" + topicId + "/answer");

        post.addHeader("cookie", cookie);
        post.addHeader("Content-Type", "application/json,charset=utf8");
        post.addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/119.0.0.0 Safari/537.36");

        /* 测试数据
        String paramJson = "{\n" +
                "  \"req_data\": {\n" +
                "    \"text\": \"your father\\n\",\n" +
                "    \"image_ids\": [],\n" +
                "    \"silenced\": true\n" +
                "  }\n" +
                "}";
        */

        //构造回答问题的请求信息
        AnswerReq answerReq = new AnswerReq(new ReqData(text, silenced));
        String paramJson = JSONObject.toJSONString(answerReq);

        StringEntity entity = new StringEntity(paramJson, ContentType.create("text/json", "UTF-8"));
        post.setEntity(entity);

        CloseableHttpResponse response = httpClient.execute(post);

        //解析响应数据
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String jsonStr = EntityUtils.toString(response.getEntity());
            log.info("回答问题结果 groupId：{}, topicId:{}, jsonStr:{}", groupId, topicId, jsonStr);
            AnswerRes answerRes = JSON.parseObject(jsonStr, AnswerRes.class);
            return answerRes.isSucceeded();
        } else {
            throw new RemoteException("回答异常：" + response.getStatusLine().getStatusCode());
        }
    }
}
