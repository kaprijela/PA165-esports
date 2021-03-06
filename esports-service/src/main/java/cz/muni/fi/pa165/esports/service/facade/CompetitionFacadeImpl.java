package cz.muni.fi.pa165.esports.service.facade;

import cz.muni.fi.pa165.esports.dto.CompetitionDTO;
import cz.muni.fi.pa165.esports.entity.Competition;
import cz.muni.fi.pa165.esports.facade.CompetitionFacade;
import cz.muni.fi.pa165.esports.service.BeanMappingService;
import cz.muni.fi.pa165.esports.service.CompetitionService;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * @author gavlijan
 */
@Service
@Transactional
@NoArgsConstructor
public class CompetitionFacadeImpl implements CompetitionFacade {

    @Inject
    CompetitionService competitionService;

    @Inject
    BeanMappingService beanMappingService;

    public CompetitionFacadeImpl(CompetitionService competitionService, BeanMappingService beanMappingService) {
        this.competitionService = competitionService;
        this.beanMappingService = beanMappingService;
    }

    @Override
    public void addTeam(Long competition, String team) {
        competitionService.addTeam(competition, team);
    }

    @Override
    public void removeTeam(Long competition, String team) {
        competitionService.removeTeam(competition, team);
    }

    @Override
    public List<CompetitionDTO> getAllCompetitions() {
        return beanMappingService.mapTo(competitionService.getAll(), CompetitionDTO.class);
    }

    @Override
    public Long createCompetition(CompetitionDTO competitionDTO) {
        Competition competition = beanMappingService.mapTo(competitionDTO, Competition.class);
        return competitionService.createCompetition(competition);
    }

    @Override
    public CompetitionDTO findCompetitionByName(String name) {
        return beanMappingService.mapTo(competitionService.findByName(name), CompetitionDTO.class);
    }

    @Override
    public CompetitionDTO findCompetitionById(Long id) {
        return beanMappingService.mapTo(competitionService.findById(id), CompetitionDTO.class);
    }

    @Override
    public void deleteCompetition(Long id) {
        competitionService.removeCompetition(competitionService.findById(id));
    }
}
