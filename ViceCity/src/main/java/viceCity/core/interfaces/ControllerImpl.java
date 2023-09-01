package viceCity.core.interfaces;

import viceCity.models.guns.Gun;
import viceCity.models.guns.Pistol;
import viceCity.models.guns.Rifle;
import viceCity.models.neighbourhood.GangNeighbourhood;
import viceCity.models.players.CivilPlayer;
import viceCity.models.players.MainPlayer;
import viceCity.models.players.Player;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static viceCity.common.ConstantMessages.*;

public class ControllerImpl implements Controller {
    private Collection<Player> civilPlayers;
    private ArrayDeque<Gun> guns;
    private MainPlayer mainPlayer;

    public ControllerImpl() {
        this.civilPlayers = new ArrayList<>();
        this.guns = new ArrayDeque<>();
        this.mainPlayer = new MainPlayer();
    }


    @Override
    public String addPlayer(String name) {
        CivilPlayer civilPlayer = new CivilPlayer(name);
        civilPlayers.add(civilPlayer);
        return String.format(PLAYER_ADDED, civilPlayer.getName());
    }

    @Override
    public String addGun(String type, String name) {
        Gun gun;
        if (type.equals("Pistol")) {
            gun = new Pistol(name);
        } else if (type.equals("Rifle")) {
            gun = new Rifle(name);
        } else
            return GUN_TYPE_INVALID;

        guns.add(gun);
        return String.format(GUN_ADDED, name, type);
    }

    @Override
    public String addGunToPlayer(String name) {
        if (guns.isEmpty()) {
            return GUN_QUEUE_IS_EMPTY;
        }
        Gun currentGun = guns.pop();

        boolean playerExist = civilPlayers.stream().anyMatch(cp -> cp.getName().equals(name));
        if (name.equals("Vercetti")) {
            this.mainPlayer.getGunRepository().add(currentGun);
            return String.format(GUN_ADDED_TO_MAIN_PLAYER, currentGun.getName(), mainPlayer.getName());
        } else if (playerExist) {
            civilPlayers.stream()
                    .filter(cp -> cp.getName().equals(name))
                    .findFirst()
                    .get()
                    .getGunRepository()
                    .add(currentGun);
            return String.format(GUN_ADDED_TO_CIVIL_PLAYER, currentGun.getName(), name);
        }

        return CIVIL_PLAYER_DOES_NOT_EXIST;
    }

    @Override
    public String fight() {
        int numberOfCivilPlayersBeforeTheShooting = civilPlayers.size();

        GangNeighbourhood gangNeighbourhood = new GangNeighbourhood();
        gangNeighbourhood.action(mainPlayer, civilPlayers);

        long fullHealthCivilAfterShooting = civilPlayers.stream().filter(cp -> cp.getLifePoints() == 50).count();
        boolean civilAreFine = fullHealthCivilAfterShooting == numberOfCivilPlayersBeforeTheShooting;
        boolean mainPlayerIsFine = mainPlayer.getLifePoints() == 100;

        if (civilAreFine && mainPlayerIsFine) {
            return FIGHT_HOT_HAPPENED;
        }
        int numberOfCivilPlayerAfterShooting = civilPlayers.size();
        int deadCivilPlayers = numberOfCivilPlayersBeforeTheShooting - numberOfCivilPlayerAfterShooting;

        StringBuilder builder = new StringBuilder();
        builder.append(FIGHT_HAPPENED)
                .append(System.lineSeparator());
        builder.append(String.format(MAIN_PLAYER_LIVE_POINTS_MESSAGE, mainPlayer.getLifePoints()))
                .append(System.lineSeparator());
        builder.append(String.format(MAIN_PLAYER_KILLED_CIVIL_PLAYERS_MESSAGE, deadCivilPlayers))
                .append(System.lineSeparator());
        builder.append(String.format(CIVIL_PLAYERS_LEFT_MESSAGE, civilPlayers.size()));

        return builder.toString();
    }
}
