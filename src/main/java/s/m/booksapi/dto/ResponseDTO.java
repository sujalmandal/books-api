package s.m.booksapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.UUID;

@Getter
public class ResponseDTO<T> {
    private String correlationId;
    private T body;

    public ResponseDTO(){ }

    public ResponseDTO(T body){
        this.body = body;
        this.correlationId = UUID.randomUUID().toString();
    }

    @JsonIgnore
    public LinkedHashMap<String, Object> getBodyAsMap(){
        return new ObjectMapper().convertValue(this.body, LinkedHashMap.class);
    }

    @JsonIgnore
    public String getBodyAsString(){
        return (String) body;
    }

    @Override
    public String toString() {
        return "ResponseDTO{" +
                "correlationId='" + correlationId + '\'' +
                ", body=" + body +
                '}';
    }
}
