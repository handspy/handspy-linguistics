package pt.up.hs.linguistics.client.sampling;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pt.up.hs.linguistics.client.AuthorizedUserFeignClient;
import pt.up.hs.linguistics.client.sampling.dto.Text;

import javax.validation.constraints.NotNull;

@AuthorizedUserFeignClient(name = "sampling")
public interface SamplingFeignClient {

    @RequestMapping(value = "/api/projects/{projectId}/texts/{textId}", method = RequestMethod.GET)
    Text getText(
        @PathVariable("projectId") @NotNull Long projectId,
        @PathVariable("textId") @NotNull Long textId
    );
}
