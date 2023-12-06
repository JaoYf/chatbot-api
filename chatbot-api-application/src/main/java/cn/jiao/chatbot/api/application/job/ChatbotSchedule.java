package cn.jiao.chatbot.api.application.job;

import cn.jiao.chatbot.api.domain.ai.IBaiDu;
import cn.jiao.chatbot.api.domain.zsxq.IZsxpApi;
import cn.jiao.chatbot.api.domain.zsxq.model.aggregates.UnAnsweredQuestionsAggregates;
import cn.jiao.chatbot.api.domain.zsxq.model.vo.Topics;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

/**
 * @author 焦宇飞
 * @Description 问答任务
 */
@EnableScheduling
@Configuration
public class ChatbotSchedule {

    private Logger log = LoggerFactory.getLogger(ChatbotSchedule.class);

    @Value("${chatbot-api.groupId}")
    private String groupId;
    @Value("${chatbot-api.cookie}")
    private String cookie;

    @Resource
    private IZsxpApi zsxpApi;
    @Resource
    private IBaiDu baiDu;

    // 表达式：
    @Scheduled(cron = "0 0/1 * * * ?")
    public void run() {
        try {
            if (new Random().nextBoolean()) {
                log.info("随机打烊...");
                return;
            }

            GregorianCalendar calendar = new GregorianCalendar();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            if (hour > 22 || hour < 7) {
                log.info("打烊时间不工作，AI下班了！");
                return;
            }

            // 1.检索问题，调用知识星球接口，获取待回答问题
            UnAnsweredQuestionsAggregates unAnsweredQuestionsAggregates = zsxpApi.queryUnAnsweredQuestionsTopicId(groupId, cookie);
            log.info("检索结果：{}", JSON.toJSONString(unAnsweredQuestionsAggregates));

            List<Topics> topics = unAnsweredQuestionsAggregates.getResp_data().getTopics();
            if (topics == null || topics.isEmpty()) {
                log.info("本次检索未查询到待回答问题");
                return;
            }
            Topics topic = topics.get(0);

            // 2.AI回答，调用文心一言接口
            String answer = baiDu.doWxyy(topic.getQuestion().getText().trim());

            // 3.问题回复，调用知识星球接口
            boolean status = zsxpApi.answer(groupId, cookie, topic.getTopic_id(), answer, false);
            log.info("编号：{} 问题：{} 回答：{} 状态：{}",topic.getTopic_id(), topic.getQuestion().getText(), answer, status);
        } catch (IOException e) {
            log.error("自动回答问题异常");
        }
    }
}
