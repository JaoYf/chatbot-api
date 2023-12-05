package cn.jiao.chatbot.api.domain.ai.model.aggregates;

import cn.jiao.chatbot.api.domain.ai.model.vo.Usage;

/**
 * @author 焦宇飞
 * 2023/12/5 17:34
 */
public class AIAnswer {
    private String id;

    private String object;

    private int created;

    private String result;

    private boolean is_truncated;

    private boolean need_clear_history;

    private String finish_reason;

    private Usage usage;

    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return this.id;
    }
    public void setObject(String object){
        this.object = object;
    }
    public String getObject(){
        return this.object;
    }
    public void setCreated(int created){
        this.created = created;
    }
    public int getCreated(){
        return this.created;
    }
    public void setResult(String result){
        this.result = result;
    }
    public String getResult(){
        return this.result;
    }
    public void setIs_truncated(boolean is_truncated){
        this.is_truncated = is_truncated;
    }
    public boolean getIs_truncated(){
        return this.is_truncated;
    }
    public void setNeed_clear_history(boolean need_clear_history){
        this.need_clear_history = need_clear_history;
    }
    public boolean getNeed_clear_history(){
        return this.need_clear_history;
    }
    public void setFinish_reason(String finish_reason){
        this.finish_reason = finish_reason;
    }
    public String getFinish_reason(){
        return this.finish_reason;
    }
    public void setUsage(Usage usage){
        this.usage = usage;
    }
    public Usage getUsage(){
        return this.usage;
    }

}
