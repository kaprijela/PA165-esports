package cz.fi.muni.pa165.esports.dao;

import cz.fi.muni.pa165.esports.PersistenceSampleApplicationContext;
import cz.fi.muni.pa165.esports.entity.Player;
import cz.fi.muni.pa165.esports.enums.Gender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.persistence.*;
import java.util.List;


/**
 * Tests for {@link PlayerDao}
 * @author Gabriela Kandova
 */

@Transactional
@ContextConfiguration(classes = {PersistenceSampleApplicationContext.class})
public class PlayerDaoTest extends AbstractTestNGSpringContextTests {
    @PersistenceUnit
    protected EntityManagerFactory emf;

    @PersistenceContext
    protected EntityManager em;

    @Autowired
    private PlayerDao playerDao;

    private Player mrWhite;  // ok
    private Player mrOrange; // ok
    private Player mrPink; // no name
    private Player mrBrown; // not persisted
    private Player mrBlonde; // ok
    private Player mrsRed; // ok; mrWhite's namesake

    @BeforeClass
    private void setUp() {
        em = emf.createEntityManager();
        em.getTransaction().begin();

        mrWhite = new Player();
        mrWhite.setName("Larry");
        mrWhite.setAge(50);
        mrWhite.setGender(Gender.MALE);

        mrOrange = new Player();
        mrOrange.setName("Freddy");
        mrOrange.setAge(30);
        mrOrange.setGender(Gender.MALE);
        em.persist(mrOrange);

        mrPink = new Player();
        mrPink.setName(null);
        mrPink.setAge(30);
        mrPink.setGender(Gender.MALE);

        mrBrown = new Player();
        mrBrown.setName("Mr. Brown");
        mrBrown.setAge(28);
        mrBrown.setGender(Gender.MALE);

        mrBlonde = new Player();
        mrBlonde.setName("Mr. Blonde");
        mrBlonde.setAge(32);
        mrBlonde.setGender(Gender.MALE);
        em.persist(mrBlonde);

        mrsRed = new Player();
        mrsRed.setName(mrWhite.getName());
        mrsRed.setAge(40);
        mrsRed.setGender(Gender.FEMALE);
        em.persist(mrsRed);

        em.getTransaction().commit();
    }

    @Test
    public void testCreate() {
        playerDao.create(mrWhite);

        Player foundPlayer = em.find(Player.class, mrWhite.getPlayer_id());

        Assert.assertNotNull(foundPlayer);
        Assert.assertEquals(mrWhite, foundPlayer);
        Assert.assertEquals(foundPlayer.getName(), mrWhite.getName());
        Assert.assertEquals(foundPlayer.getAge(), mrWhite.getAge());
        Assert.assertEquals(foundPlayer.getGender(), mrWhite.getGender());
    }

    @Test(expectedExceptions = { PersistenceException.class })
    public void testCreateNoName() {
        playerDao.create(mrPink);
    }

    @Test
    public void testFindByIdFound() {
        Player foundPlayer = playerDao.findById(mrWhite.getPlayer_id());

        Assert.assertNotNull(foundPlayer);
        Assert.assertEquals(foundPlayer, mrWhite);
        Assert.assertEquals(foundPlayer.getName(), mrWhite.getName());
        Assert.assertEquals(foundPlayer.getAge(), mrWhite.getAge());
        Assert.assertEquals(foundPlayer.getGender(), mrWhite.getGender());
    }

    @Test
    public void testDelete() {
        // using DAO as find and delete have to be in the same entity manager
        Player foundPlayer = playerDao.findById(mrBlonde.getPlayer_id());
        playerDao.delete(foundPlayer);

        foundPlayer = playerDao.findById(mrBlonde.getPlayer_id());
        Assert.assertNull(foundPlayer);
    }

    @Test
    public void testFindByIdNotFound() {
        Player foundPlayer = playerDao.findById(mrBlonde.getPlayer_id());
        Assert.assertNull(foundPlayer);
    }

    @Test
    public void testFindAll() {
        List<Player> foundPlayers = playerDao.findAll();
        Assert.assertNotNull(foundPlayers);
        Assert.assertEquals(foundPlayers.size(), 3);
        
        assertContainsPlayerWithName(foundPlayers, mrWhite.getName());
        assertContainsPlayerWithName(foundPlayers, mrOrange.getName());
        assertContainsPlayerWithName(foundPlayers, mrsRed.getName());
    }

    @Test
    public void testFindByNameFoundSingle() {
        List<Player> foundPlayers = playerDao.findByName(mrOrange.getName());
        Assert.assertNotNull(foundPlayers);
        Assert.assertEquals(foundPlayers.size(), 1);
        assertContainsPlayerWithName(foundPlayers, mrOrange.getName());
    }

    @Test
    public void testFindByNameFoundMultiple() {
        List<Player> foundPlayers = playerDao.findByName(mrWhite.getName());
        Assert.assertNotNull(foundPlayers);
        Assert.assertEquals(foundPlayers.size(), 2);
        assertContainsPlayerWithName(foundPlayers, mrWhite.getName());
    }
    
    @Test
    public void testFindByNameNotFound() {
        List<Player> foundPlayers = playerDao.findByName(mrBrown.getName());
        Assert.assertNotNull(foundPlayers);
        Assert.assertEquals(foundPlayers.size(), 0);
    }

    @Test
    public void testFindByGenderFoundSingle() {
        List<Player> foundPlayers = playerDao.findByGender(mrsRed.getGender());
        Assert.assertNotNull(foundPlayers);
        Assert.assertEquals(foundPlayers.size(), 1);
    }

    @Test
    public void testFindByGenderFoundMultiple() {
        List<Player> foundPlayers = playerDao.findByGender(Gender.MALE);
        Assert.assertNotNull(foundPlayers);
        Assert.assertEquals(foundPlayers.size(), 2);
    }
    
    @Test
    public void testFindByGenderNotFound() {
        List<Player> foundPlayers = playerDao.findByGender(Gender.OTHER);
        Assert.assertNotNull(foundPlayers);
        Assert.assertEquals(foundPlayers.size(), 0);
    }

    private void assertContainsPlayerWithName(List<Player> players, String expectedPlayerName) {
        for (Player player: players) {
            if (player.getName().equals(expectedPlayerName)) {
                return;
            }
        }
        Assert.fail("Couldn't find player " + expectedPlayerName + " in collection " + players);
    }

    @AfterClass
    private void cleanUp() {
        if (em != null) {
            em.close();
        }
    }
}