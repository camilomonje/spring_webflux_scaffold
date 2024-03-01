package co.com.nequi.consumer.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class SupportResponse {

    private String url;
    private String text;
}
