package s.m.booksapi.dto;

import lombok.Getter;

import java.util.UUID;

@Getter
public class ResponseDTO<T> {
    private final String correlationId;
    private final T body;

    public ResponseDTO(T body){
        this.body = body;
        this.correlationId = UUID.randomUUID().toString();
    }
}
