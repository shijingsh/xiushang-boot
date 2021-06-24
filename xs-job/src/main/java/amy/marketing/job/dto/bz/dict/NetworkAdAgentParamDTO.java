package amy.marketing.job.dto.bz.dict;

import amy.marketing.job.dto.AgentDTO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class NetworkAdAgentParamDTO extends AgentDTO {
    private List<String> networkId = new ArrayList<>();
    private boolean mobile =  false;
    private boolean usedTarget =  false;
}
