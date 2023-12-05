package cn.jiao.chatbot.api.domain.zsxq;

import cn.jiao.chatbot.api.domain.zsxq.model.aggregates.UnAnsweredQuestionsAggregates;
import java.io.IOException;

/**
 * @author 焦宇飞
 * 2023/12/4 20:58
 */
public interface IZsxpApi {

    UnAnsweredQuestionsAggregates queryUnAnsweredQuestionsTopicId(String groupId, String cookie) throws IOException;

    boolean answer(String groupId, String cookie, String topicId, String text, boolean silenced) throws IOException;
}
