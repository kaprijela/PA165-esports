package cz.muni.fi.pa165.esports.service.facade;

import cz.muni.fi.pa.esports.dto.*;
import cz.muni.fi.pa.esports.facade.CompetitionFacade;

import java.util.List;

public class CompetitionFacadeImpl implements CompetitionFacade {
    @Override
    public boolean addTeam(TeamDTO teamDTO) {
        return false;
    }

    @Override
    public List<CompetitionDTO> getAllCompetitions() {
        return null;
    }

    @Override
    public void removeTeam(TeamDTO teamDTO) {

    }

    @Override
    public void getPlayerStatisticForCompetion(PlayerDTO playerDTO) {
    }

    @Override
    public LadderDTO getLadder() {
        return null;
    }

    @Override
    public  void createCompetition(CompetitionDTO competitionDTO) {

    }

    @Override
    public  void updateCompetition(CompetitionDTO competitionDTO) {

    }

    @Override
    public CompetitionDTO getCompetitionByName(String name) {
        return null;
    }
}
