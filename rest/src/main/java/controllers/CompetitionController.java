package controllers;

import cz.muni.fi.pa165.esports.dao.CompetitionDao;
import cz.muni.fi.pa165.esports.dto.CompetitionDTO;
import cz.muni.fi.pa165.esports.dto.PlayerDTO;
import cz.muni.fi.pa165.esports.entity.Competition;
import cz.muni.fi.pa165.esports.entity.Player;
import cz.muni.fi.pa165.esports.facade.CompetitionFacade;
import cz.muni.fi.pa165.esports.facade.PlayerFacade;
import exceptions.ResourceAlreadyExistingException;
import exceptions.ResourceNotFoundException;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.Collection;
import java.util.List;


@RestController
@RequestMapping(ControllerConstants.COMPETITIONS)
@Slf4j
public class CompetitionController {

    @Inject
    CompetitionFacade competitionFacade;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final Collection<CompetitionDTO> getPlayers() {
        log.debug("rest getPlayers()");
        return competitionFacade.getAllCompetitions();
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final CompetitionDTO createCompetition(@RequestBody CompetitionDTO competitionDTO) throws Exception {
        log.debug("rest createCompetition()");
        try {
            Long competition = competitionFacade.createCompetition(competitionDTO);
            return competitionFacade.getCompetitionById(competition);
        } catch (Exception ex) {
            throw new ResourceAlreadyExistingException();
        }
    }

    @RequestMapping(value = "/name/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final CompetitionDTO getByName(@PathVariable("name") String name) throws Exception {
        log.debug("rest get by name {}", name);

        CompetitionDTO competitionByName = competitionFacade.getCompetitionByName(name);
        if (competitionByName == null) {
            throw new ResourceNotFoundException();
        }
        return competitionByName;
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final CompetitionDTO getById(@PathVariable("id") Long id) throws Exception {
        log.debug("rest get by id {}", id);

        CompetitionDTO competitionByName = competitionFacade.getCompetitionById(id);
        if (competitionByName == null) {
            throw new ResourceNotFoundException();
        }
        return competitionByName;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final void deleteById(@PathVariable("id") Long id) throws Exception {
        log.debug("rest delete by id {}", id);
        try {
            competitionFacade.deleteCompetition(id);
        } catch (Exception e) {
            throw new ResourceNotFoundException();
        }
    }

    @RequestMapping(value = "/{id}/addTeam/{team}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final void addTeamToCompetition(@PathVariable("id") Long id, @PathVariable("team") String team) throws Exception {
        log.debug("rest delete by id {}", id);
        try {
            competitionFacade.addTeam(id, team);
        } catch (Exception e) {
            throw new ResourceNotFoundException();
        }
    }

    @RequestMapping(value = "/{id}/removeTeam/{team}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final void removeTeamFromCompetition(@PathVariable("id") Long id, @PathVariable("team") String team) throws Exception {
        log.debug("rest delete by id {}", id);
        try {
            competitionFacade.removeTeam(id, team);
        } catch (Exception e) {
            throw new ResourceNotFoundException();
        }
    }
}