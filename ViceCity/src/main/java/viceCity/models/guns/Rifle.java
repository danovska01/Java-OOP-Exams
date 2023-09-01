package viceCity.models.guns;

import viceCity.models.guns.BaseGun;

public class Rifle extends BaseGun {
    private static final int BULLETS_PER_BARREL = 50;
    private static final int TOTAL_BULLETS = 500;
    private static final int BULLETS_PER_SHOT = 5;


    public Rifle(String name) {
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
            return 5;
        }
        return 0;
    }
}
