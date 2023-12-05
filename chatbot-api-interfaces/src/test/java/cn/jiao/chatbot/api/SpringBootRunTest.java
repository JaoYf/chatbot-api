package cn.jiao.chatbot.api;

import cn.jiao.chatbot.api.domain.zsxq.IZsxpApi;
import cn.jiao.chatbot.api.domain.zsxq.model.aggregates.UnAnsweredQuestionsAggregates;
import cn.jiao.chatbot.api.domain.zsxq.model.vo.Topics;
import com.alibaba.fastjson.JSON;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

/**
 * @author 焦宇飞
 * 2023/12/4 22:23
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootRunTest {

    private Logger log = LoggerFactory.getLogger(SpringRunner.class);

    @Value("${chatbot-api.groupId}")
    private String groupId;

    @Value("${chatbot-api.cookie}")
    private String cookie;

    @Autowired
    private IZsxpApi zsxpApi;

    @Test
    public void test_zsxqApi() throws IOException {
        UnAnsweredQuestionsAggregates unAnsweredQuestionsAggregates = zsxpApi.queryUnAnsweredQuestionsTopicId(groupId, cookie);
        log.info("测试结果：{}", JSON.toJSONString(unAnsweredQuestionsAggregates));
        List<Topics> topics = unAnsweredQuestionsAggregates.getResp_data().getTopics();

        for (Topics topic : topics) {
            String topicId = topic.getTopic_id();
            String text = topic.getQuestion().getText();

            log.info("topicId: {}, text:{}", topicId, text);

            //回答问题
            //TODO 问题text发送给ChatGPT，回显给变量text作为回答内容
            zsxpApi.answer(groupId, cookie, topicId, text, false);
        }


    }
}
