package viceCity.repositories.interfaces;

import viceCity.models.guns.Gun;

import java.util.ArrayList;
import java.util.Collection;


public class GunRepository implements Repository{
    private Collection<Gun> models;

    public GunRepository() {
        this.models = new ArrayList<>();
    }


    @Override
    public Collection getModels() {
        return this.models;
    }

    @Override
    public void add(Object model) {
        this.models.add((Gun) model);
    }

    @Override
    public boolean remove(Object model) {
        return this.models.remove(model);
    }

    @Override
    public Object find(String name) {
        return this.models.stream().filter(g-> g.getName().equals(name)).findFirst().get();
    }
}
