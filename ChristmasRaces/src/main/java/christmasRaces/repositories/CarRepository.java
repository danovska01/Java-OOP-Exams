package christmasRaces.repositories;

import christmasRaces.entities.cars.Car;
import christmasRaces.repositories.interfaces.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CarRepository implements Repository<Car> {
    private List<Car> models;

    public CarRepository() {
        this.models = new ArrayList<>();
    }

    @Override
    public Car getByName(String name) {
        return this.models.stream().filter(p -> p.getModel().equals(name)).findFirst().orElse(null);

    }

    @Override
    public Collection<Car> getAll() {
        return Collections.unmodifiableList(this.models);

    }

    @Override
    public void add(Car model) {
        this.models.add(model);
    }

    @Override
    public boolean remove(Car model) {
        return this.models.remove(model);
    }
}
