package catHouse.entities.houses;

import catHouse.common.ConstantMessages;
import catHouse.common.ExceptionMessages;
import catHouse.entities.cat.Cat;
import catHouse.entities.toys.Toy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

public class BaseHouse implements House {

    private String name;
    private int capacity;
    private Collection<Toy> toys;

    private Collection<Cat> cats;


    public BaseHouse(String name, int capacity) {
        this.setName(name);
        this.capacity = capacity;
        this.toys = new ArrayList<>();
        this.cats = new ArrayList<>();
    }

    public int sumSoftness() {
        return toys.stream()
                .mapToInt(Toy::getSoftness)
                .sum();
    }

    @Override
    public void addCat(Cat cat) {
        if (this.capacity <= this.cats.size()) {
            throw new IllegalStateException(ConstantMessages.NOT_ENOUGH_CAPACITY_FOR_CAT);
        }
        cats.add(cat);
    }

    @Override
    public void removeCat(Cat cat) {
        this.cats.remove(cat);

    }

    @Override
    public void buyToy(Toy toy) {

        toys.add(toy);
    }

    @Override
    public void feeding() {
        cats.forEach(Cat::eating);
    }

    @Override
    public String getStatistics() {


        return String.format("%s %s",
                this.name,
                this.getClass().getSimpleName()
                        +String.format("%s",":")
                        + String.format("%nCats: %s%n", cats.isEmpty()
                        ? "none"
                        : this.cats.stream().map(Cat::getName).collect(Collectors.joining(" ")).trim()
                )
                        + String.format("Toys: %d Softness: %d%n", this.toys.size(), this.sumSoftness())
        );



    }

    @Override
    public String getName() {
        return this.name;
    }


    @Override
    public void setName(String name) {
        if (name.trim().isEmpty()) {
            throw new NullPointerException(ExceptionMessages.HOUSE_NAME_CANNOT_BE_NULL_OR_EMPTY);
        }
        this.name = name;
    }

    @Override
    public Collection<Cat> getCats() {
        return this.cats;
    }

    @Override
    public Collection<Toy> getToys() {
        return this.toys;
    }
}
