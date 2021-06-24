package amy.marketing.job.dto.bz.dict;

import amy.marketing.job.dto.AgentDTO;
import lombok.Data;

@Data
public class NetworkAdAgentDTO extends AgentDTO {
    private String networkId =  "1";
    private boolean mobile =  false;
    private boolean usedTarget =  false;
}
