package jets.spring_boot.model.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class BookDTO implements Serializable {

    private Long bookId;
    private String title;
}
