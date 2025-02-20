package scraping.bbq.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CsrfDto {
    private String csrfToken;
}
