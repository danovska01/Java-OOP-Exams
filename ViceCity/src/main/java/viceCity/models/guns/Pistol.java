package viceCity.models.guns;

public class Pistol extends BaseGun {
    private static final int BULLETS_PER_BARREL = 10;
    private static final int TOTAL_BULLETS = 100;
    private static final int BULLETS_PER_SHOT = 1;


    public Pistol(String name) {
        super(name, BULLETS_PER_BARREL, TOTAL_BULLETS);
    }

    @Override
    public boolean canFire(){
        return getTotalBullets()>BULLETS_PER_SHOT;
    }

    @Override
    public int fire() {
        boolean isBarrelEmpty = getBulletsPerBarrel() < BULLETS_PER_SHOT;
        if (canFire()){
            if (isBarrelEmpty){
                setTotalBullets(getTotalBullets()-BULLETS_PER_BARREL);
                setBulletsPerBarrel(BULLETS_PER_BARREL);
            }
            setBulletsPerBarrel(getBulletsPerBarrel()-BULLETS_PER_SHOT);
            return 1;
        }
        return 0;
    }
}
