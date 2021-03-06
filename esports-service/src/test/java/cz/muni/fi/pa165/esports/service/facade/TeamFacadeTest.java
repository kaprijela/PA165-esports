package cz.muni.fi.pa165.esports.service.facade;

import cz.muni.fi.pa165.esports.service.BeanMappingService;
import cz.muni.fi.pa165.esports.service.TeamService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author Radovan Tomasik
 */

@RunWith(MockitoJUnitRunner.class)
public class TeamFacadeTest {

    @Mock
    TeamService teamService;

    @Mock
    BeanMappingService beanMappingService;

    @InjectMocks
    TeamFacadeImpl teamFacade;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllTeams() {
        teamFacade.getAllTeams();
        verify(teamService, times(1)).findAll();
    }


    @Test
    public void testGetTeamById() {
        teamFacade.findTeamById(1L);
        verify(teamService, times(1)).findById(1L);
    }


    @Test
    public void testGetTeamByName() {
        teamFacade.findTeamByName("name");
        verify(teamService, times(1)).findByName("name");
    }


    @Test
    public void testGetTeamByAbbreviation() {
        teamFacade.findTeamByAbbreviation("abb");
        verify(teamService, times(1)).findByAbbreviation("abb");
    }
}
//
//    @Test
//    public void testCreateTeam() {
//        TeamDTO teamDTO = new TeamDTO();
//        teamDTO.setId(1L);
//        teamDTO.setName("Men of Gondor");
//        Team menE = new Team();
//        menE.setId(1L);
//        menE.setName("Men of Gondor");
//        when(beanMappingService.mapTo(teamDTO, Team.class)).thenReturn(menE);
//        teamFacade.registerNewTeam(teamDTO);
//        verify(beanMappingService, times(1)).mapTo(teamDTO, Team.class);
//        verify(teamService, times(1)).create(menE);
//
//    }
//
//    @Test
//    public void testDeleteTeam() {
//        TeamDTO teamDTO = new TeamDTO();
//        teamDTO.setId(1L);
//        teamDTO.setName("Men of Gondor");
//        Team menE = new Team();
//        menE.setId(1L);
//        menE.setName("Men of Gondor");
//        when(beanMappingService.mapTo(teamDTO, Team.class)).thenReturn(menE);
//        teamFacade.registerNewTeam(teamDTO);
//        verify(beanMappingService, times(1)).mapTo(teamDTO, Team.class);
//        verify(teamService, times(1)).create(menE);
//        teamFacade.removeTeam(teamDTO);
//        verify(teamService, times(1)).remove(menE);
//    }
//}