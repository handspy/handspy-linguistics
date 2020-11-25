package pt.up.hs.linguistics.client.project;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pt.up.hs.linguistics.client.AuthorizedUserFeignClient;
import pt.up.hs.linguistics.client.project.dto.Participant;
import pt.up.hs.linguistics.client.project.dto.Task;
import pt.up.hs.linguistics.client.sampling.dto.Text;

import javax.validation.constraints.NotNull;

@AuthorizedUserFeignClient(name = "project")
public interface ProjectFeignClient {

    @RequestMapping(value = "/api/projects/{projectId}/tasks/{taskId}", method = RequestMethod.GET)
    Task getTask(
        @PathVariable("projectId") @NotNull Long projectId,
        @PathVariable("taskId") @NotNull Long taskId
    );

    @RequestMapping(value = "/api/projects/{projectId}/participants/{participantId}", method = RequestMethod.GET)
    Participant getParticipant(
        @PathVariable("projectId") @NotNull Long projectId,
        @PathVariable("participantId") @NotNull Long participantId
    );
}
