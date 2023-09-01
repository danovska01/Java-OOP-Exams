package viceCity.models.neighbourhood;

import viceCity.models.guns.Gun;
import viceCity.models.players.CivilPlayer;
import viceCity.models.players.Player;

import java.util.Collection;

public class GangNeighbourhood implements Neighbourhood {

    public GangNeighbourhood() {
    }

    @Override
    public void action(Player mainPlayer, Collection<Player> civilPlayers) {
        while (mainPlayer.isAlive()
                && civilPlayers.stream().anyMatch(Player::isAlive)
                && (mainPlayer.getGunRepository().getModels().size()>0
                || civilPlayers.stream().anyMatch(cp->cp.getGunRepository().getModels().size()>0))) {
            CivilPlayer currentCivil = (CivilPlayer) civilPlayers.stream().findFirst().get();
            while (mainPlayer.getGunRepository().getModels().size() > 0) {
                Gun currentGun = mainPlayer.getGunRepository().getModels().stream().findFirst().get();
                currentCivil.takeLifePoints(currentGun.fire());
                if (!currentGun.canFire()) {
                    mainPlayer.getGunRepository().remove(currentGun);
                }
                if (!currentCivil.isAlive()) {
                    civilPlayers.remove(currentCivil);
                    if (civilPlayers.size()>0){
                        currentCivil = (CivilPlayer) civilPlayers.stream().findFirst().get();
                    }
                }

            }

            while (mainPlayer.isAlive()
                    && !civilPlayers.isEmpty()
                    && civilPlayers.stream().findFirst().get().getGunRepository().getModels().size() > 0) {
                Gun currentGun = currentCivil.getGunRepository().getModels().stream().findFirst().get();
                mainPlayer.takeLifePoints(currentGun.fire());
                if (!currentGun.canFire()) {
                    mainPlayer.getGunRepository().remove(currentGun);
                }
            }
        }

    }
}
