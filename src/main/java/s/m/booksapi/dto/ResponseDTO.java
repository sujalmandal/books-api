package s.m.booksapi.dto;

import lombok.Getter;

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

    @Override
    public String toString() {
        return "ResponseDTO{" +
                "correlationId='" + correlationId + '\'' +
                ", body=" + body +
                '}';
    }
}
