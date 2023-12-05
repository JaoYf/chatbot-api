package cn.jiao.chatbot.api.domain.ai.model.vo;

/**
 * @author 焦宇飞
 * 2023/12/5 17:35
 */
public class Usage {
    private int prompt_tokens;

    private int completion_tokens;

    private int total_tokens;

    public void setPrompt_tokens(int prompt_tokens){
        this.prompt_tokens = prompt_tokens;
    }
    public int getPrompt_tokens(){
        return this.prompt_tokens;
    }
    public void setCompletion_tokens(int completion_tokens){
        this.completion_tokens = completion_tokens;
    }
    public int getCompletion_tokens(){
        return this.completion_tokens;
    }
    public void setTotal_tokens(int total_tokens){
        this.total_tokens = total_tokens;
    }
    public int getTotal_tokens(){
        return this.total_tokens;
    }

}
