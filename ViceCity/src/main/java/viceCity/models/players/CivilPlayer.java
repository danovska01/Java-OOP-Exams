package viceCity.models.players;

public class CivilPlayer extends BasePlayer{
    private static final int LIVE_POINTS = 50;

    public CivilPlayer(String name) {
        super(name, LIVE_POINTS);
    }
}
