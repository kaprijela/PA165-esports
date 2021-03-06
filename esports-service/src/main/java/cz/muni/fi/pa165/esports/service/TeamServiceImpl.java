package cz.muni.fi.pa165.esports.service;

import cz.muni.fi.pa165.esports.dao.MatchRecordDao;
import cz.muni.fi.pa165.esports.dao.TeamDao;
import cz.muni.fi.pa165.esports.entity.Competition;
import cz.muni.fi.pa165.esports.entity.MatchRecord;
import cz.muni.fi.pa165.esports.entity.Player;
import cz.muni.fi.pa165.esports.entity.Team;
import cz.muni.fi.pa165.esports.enums.Game;
import cz.muni.fi.pa165.esports.exceptions.EsportsServiceException;
import lombok.NonNull;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

/**
 * An implementation of the {@link TeamService}.
 *
 * @author Gabriela Kandova
 */
@Service
@Transactional(readOnly = true)
public class TeamServiceImpl implements TeamService {

    private final TeamDao teamDao;
    private final MatchRecordDao matchRecordDao;

    @Inject
    public TeamServiceImpl(@NonNull TeamDao teamDao, @NonNull MatchRecordDao matchRecordDao) {
        this.teamDao = teamDao;
        this.matchRecordDao = matchRecordDao;
    }

    @Override
    public List<Team> findAll() {
        return teamDao.findAll();
    }

    @Override
    public Team findById(@NonNull Long id) {
        return teamDao.findById(id);
    }

    @Override
    public Team findByName(@NonNull String name) {
        return teamDao.findByName(name);
    }

    @Override
    public Team findByAbbreviation(@NonNull String abbreviation) {
        return teamDao.findByAbbreviation(abbreviation);
    }

    @Override
    @Modifying
    public void create(@NonNull Team team) {
        teamDao.create(team);
    }

    @Override
    @Modifying
    public void remove(@NonNull Team team) {
        Set<Player> teamPlayers = team.getPlayers();
        for (Player player : teamPlayers) { // remove association first
            teamDao.removePlayer(team, player);
        }
        teamDao.delete(team);
    }

    @Override
    @Modifying
    public void addPlayer(@NonNull Team team, @NonNull Player player) {
        if (team.getPlayers().contains(player)) {
            throw new EsportsServiceException(String.format(
                    "Player '%s' is already member of team '%s'", player.getName(), team.getName()
            ));
        }
        if (player.getTeam() != null) {
            throw new EsportsServiceException(String.format(
                    "Player '%s' is already member of team '%s'", player.getName(), player.getTeam().getName()
            ));
        }

        teamDao.addPlayer(team, player);
    }

    @Override
    @Modifying
    public void removePlayer(@NonNull Team team, @NonNull Player player) {
        if (!team.getPlayers().contains(player)) {
            throw new EsportsServiceException(String.format(
                    "Player '%s' is not a member of team '%s'", player.getName(), team.getName()
            ));
        }

        teamDao.removePlayer(team, player);
    }

    @Override
    public Double getAverageTeamScoreForCompetition(@NonNull Team team, @NonNull Competition competition) {
        // all match records for the given competition
        List<MatchRecord> matchRecords = matchRecordDao.findByCompetition(competition);

        // all match records for the given competition and team
        matchRecords = matchRecords.stream().filter(
                matchRecord -> team.equals(matchRecord.getTeam())
        ).collect(Collectors.toList());

        // early return to avoid division by zero
        if (matchRecords.size() == 0) {
            return (double) 0;
        }

        // team score (sum per player) per match number
        Map<Long, Long> resultsPerMatch = matchRecords.stream().collect(Collectors.groupingBy(
                MatchRecord::getMatchNumber, Collectors.summingLong(MatchRecord::getScore)));

        // average team score
        return (double) (resultsPerMatch.values().stream().mapToLong(Long::longValue).sum() / resultsPerMatch.size());
    }

    @Override
    public Double getAverageTeamScoreForGame(@NonNull Team team, @NonNull Game game) {
        // all match records for the given team
        List<MatchRecord> matchRecords = matchRecordDao.findByTeam(team);

        // all match records for the players of the given team when playing given game
        matchRecords = matchRecords.stream().filter(
                matchRecord -> game.equals(matchRecord.getCompetition().getGame())
        ).collect(Collectors.toList());

        // early return to avoid division by zero
        if (matchRecords.size() == 0) {
            return (double) 0;
        }

        // sum all player records for given match
        Map<Integer, Long> matchScoreSums = new HashMap<>();
        for (MatchRecord record : matchRecords) {
            int id = Objects.hash(record.getCompetition(), record.getMatchNumber());
            Long currentValue = matchScoreSums.get(id);
            if (currentValue == null) {
                currentValue = 0L;
            }
            matchScoreSums.put(id, currentValue + record.getScore());
        }
        // average match score
        return ((double) matchScoreSums.values().stream().mapToLong(Long::longValue).sum() / matchScoreSums.size());
    }
}
