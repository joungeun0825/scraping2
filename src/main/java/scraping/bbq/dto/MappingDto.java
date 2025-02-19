package scraping.bbq.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class MappingDto {

    @JsonProperty("totalPrice")
    private Integer totalPrice;

    @JsonProperty("responseList")
    private List<OrderDto> responseList;
}
