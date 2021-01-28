package diplom.response;

import lombok.Data;

@Data
public class ErrorMessage extends AbstractResponse {
    private String message;

    public boolean hasMessage() {
        return message != null;
    }
}
