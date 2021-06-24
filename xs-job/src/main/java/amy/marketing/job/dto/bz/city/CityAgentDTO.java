package amy.marketing.job.dto.bz.city;

import amy.marketing.job.dto.AgentDTO;
import lombok.Data;

@Data
public class CityAgentDTO extends AgentDTO {
    private String networkId =  "0";
    private String pName =  "";
}
