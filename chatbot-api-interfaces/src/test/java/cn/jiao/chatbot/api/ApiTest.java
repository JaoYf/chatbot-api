package cn.jiao.chatbot.api;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

/**
 * @author 焦宇飞
 * @Description 单元测试
 * 2023/12/4 17:41
 */
@SpringBootTest
public class ApiTest {

    @Test
    public void query_unanswered_questions() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpGet get = new HttpGet("https://api.zsxq.com/v2/groups/15552815521442/topics?scope=unanswered_questions&count=20");

        get.addHeader("cookie", "zsxq_access_token=150A11DB-1E6D-EA56-18E2-9D3356B417DF_F1DCE9811C723657; abtest_env=product; zsxqsessionid=8331bca7db521d0b8ea3c13a0ed6e517; sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%22184542585485812%22%2C%22first_id%22%3A%2218c05846b54277-09128aa3ec9d9a8-26031051-1693734-18c05846b554d0%22%2C%22props%22%3A%7B%22%24latest_traffic_source_type%22%3A%22%E7%9B%B4%E6%8E%A5%E6%B5%81%E9%87%8F%22%2C%22%24latest_search_keyword%22%3A%22%E6%9C%AA%E5%8F%96%E5%88%B0%E5%80%BC_%E7%9B%B4%E6%8E%A5%E6%89%93%E5%BC%80%22%2C%22%24latest_referrer%22%3A%22%22%7D%2C%22identities%22%3A%22eyIkaWRlbnRpdHlfY29va2llX2lkIjoiMThjMDU4NDZiNTQyNzctMDkxMjhhYTNlYzlkOWE4LTI2MDMxMDUxLTE2OTM3MzQtMThjMDU4NDZiNTU0ZDAiLCIkaWRlbnRpdHlfbG9naW5faWQiOiIxODQ1NDI1ODU0ODU4MTIifQ%3D%3D%22%2C%22history_login_id%22%3A%7B%22name%22%3A%22%24identity_login_id%22%2C%22value%22%3A%22184542585485812%22%7D%2C%22%24device_id%22%3A%2218c05846b54277-09128aa3ec9d9a8-26031051-1693734-18c05846b554d0%22%7D");
        get.addHeader("Content-Type", "application/json,charset=utf8");

        CloseableHttpResponse response = httpClient.execute(get);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String res = EntityUtils.toString(response.getEntity());
            System.out.println("res = " + res);
        } else {
            System.out.println(response.getStatusLine().getStatusCode());
        }
    }

    @Test
    void answer() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpPost post = new HttpPost("https://api.zsxq.com/v2/topics/211458158224441/answer");

        post.addHeader("cookie", "zsxq_access_token=150A11DB-1E6D-EA56-18E2-9D3356B417DF_F1DCE9811C723657; abtest_env=product; zsxqsessionid=8331bca7db521d0b8ea3c13a0ed6e517; sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%22184542585485812%22%2C%22first_id%22%3A%2218c05846b54277-09128aa3ec9d9a8-26031051-1693734-18c05846b554d0%22%2C%22props%22%3A%7B%22%24latest_traffic_source_type%22%3A%22%E7%9B%B4%E6%8E%A5%E6%B5%81%E9%87%8F%22%2C%22%24latest_search_keyword%22%3A%22%E6%9C%AA%E5%8F%96%E5%88%B0%E5%80%BC_%E7%9B%B4%E6%8E%A5%E6%89%93%E5%BC%80%22%2C%22%24latest_referrer%22%3A%22%22%7D%2C%22identities%22%3A%22eyIkaWRlbnRpdHlfY29va2llX2lkIjoiMThjMDU4NDZiNTQyNzctMDkxMjhhYTNlYzlkOWE4LTI2MDMxMDUxLTE2OTM3MzQtMThjMDU4NDZiNTU0ZDAiLCIkaWRlbnRpdHlfbG9naW5faWQiOiIxODQ1NDI1ODU0ODU4MTIifQ%3D%3D%22%2C%22history_login_id%22%3A%7B%22name%22%3A%22%24identity_login_id%22%2C%22value%22%3A%22184542585485812%22%7D%2C%22%24device_id%22%3A%2218c05846b54277-09128aa3ec9d9a8-26031051-1693734-18c05846b554d0%22%7D");
        post.addHeader("Content-Type", "application/json,charset=utf8");

        String jsonParam = "{\n" +
                "  \"req_data\": {\n" +
                "    \"text\": \"your father\\n\",\n" +
                "    \"image_ids\": [],\n" +
                "    \"silenced\": true\n" +
                "  }\n" +
                "}";

        StringEntity entity = new StringEntity(jsonParam, ContentType.create("text/json", "UTF-8"));
        post.setEntity(entity);

        CloseableHttpResponse response = httpClient.execute(post);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String res = EntityUtils.toString(response.getEntity());
            System.out.println("res = " + res);
        } else {
            System.out.println(response.getStatusLine().getStatusCode());
        }
    }
}