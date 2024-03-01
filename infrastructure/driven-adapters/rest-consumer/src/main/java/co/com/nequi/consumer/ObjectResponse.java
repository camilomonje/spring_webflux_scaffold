package co.com.nequi.consumer;

import co.com.nequi.consumer.dto.DataResponse;
import co.com.nequi.consumer.dto.SupportResponse;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ObjectResponse {

    private DataResponse data;
    private SupportResponse supportResponse;

}