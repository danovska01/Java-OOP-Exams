package catHouse.core;

import catHouse.common.ConstantMessages;
import catHouse.common.ExceptionMessages;
import catHouse.entities.cat.Cat;
import catHouse.entities.cat.LonghairCat;
import catHouse.entities.cat.ShorthairCat;
import catHouse.entities.houses.House;
import catHouse.entities.houses.LongHouse;
import catHouse.entities.houses.ShortHouse;
import catHouse.entities.toys.Ball;
import catHouse.entities.toys.Mouse;
import catHouse.entities.toys.Toy;
import catHouse.repositories.ToyRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class ControllerImpl implements Controller {

    private ToyRepository toys;
    private Collection<House> houses;

    public ControllerImpl() {
        this.toys = new ToyRepository();
        this.houses = new ArrayList<>();
    }

    @Override
    public String addHouse(String type, String name) {

        if ("ShortHouse".equals(type)) {
            House house = new ShortHouse(name);
            houses.add(house);
        } else if ("LongHouse".equals(type)) {
            houses.add(new LongHouse(name));
        } else {
            throw new NullPointerException(ExceptionMessages.INVALID_HOUSE_TYPE);
        }

        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_HOUSE_TYPE, type);
    }

    @Override
    public String buyToy(String type) {

        if ("Ball".equals(type)) {
            toys.buyToy(new Ball());
        } else if (("Mouse".equals(type))) {
            toys.buyToy(new Mouse());
        } else {
            throw new IllegalArgumentException(ExceptionMessages.INVALID_TOY_TYPE);
        }
        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_TOY_TYPE, type);
    }

    @Override
    public String toyForHouse(String houseName, String toyType) {

        if (toys.findFirst(toyType) == null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.NO_TOY_FOUND, toyType));
        } else {
            houses.stream()
                    .filter(h -> h.getName().equals(houseName))
                    .findFirst()
                    .ifPresent(h -> h.buyToy(toys.findFirst(toyType)));
        }

        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_TOY_IN_HOUSE, toyType, houseName);
    }

    @Override
    public String addCat(String houseName, String catType, String catName, String catBreed, double price) {

        Cat cat;

        if ("ShorthairCat".equals(catType)) {
            cat = new ShorthairCat(catName, catBreed, price);

        } else if ("LonghairCat".equals(catType)) {
            cat = new LonghairCat(catName, catBreed, price);
        } else {
            throw new IllegalArgumentException(ExceptionMessages.INVALID_CAT_TYPE);
        }

        houses.stream()
                .filter(h -> h.getName().equals(houseName))
                .findFirst()
                .ifPresent(h -> h.addCat(cat));


        String returnValue;

        if (!houses.stream().anyMatch(h -> h.getName().equals(houseName))) {
            returnValue = ConstantMessages.UNSUITABLE_HOUSE;
        } else {
            returnValue = String.format(ConstantMessages.SUCCESSFULLY_ADDED_CAT_IN_HOUSE, catType, houseName);
        }
        return returnValue;
    }

    @Override
    public String feedingCat(String houseName) {
        int catCount = houses.stream()
                .filter(h -> h.getName().equals(houseName))
                .findFirst()
                .map(h -> h.getCats().size())
                .orElse(0);

        return String.format(ConstantMessages.FEEDING_CAT, catCount);
    }

    @Override
    public String sumOfAll(String houseName) {

        double catsCost = houses.stream()
                .mapToDouble(h -> h.getCats().stream().mapToDouble(Cat::getPrice).sum())
                .sum();

        double toysCost = houses.stream()
                .mapToDouble(h -> h.getToys().stream().mapToDouble(Toy::getPrice).sum())
                .sum();

        double totalValueHouse = catsCost + toysCost;


        return String.format(ConstantMessages.VALUE_HOUSE, houseName, totalValueHouse);
    }

    @Override
    public String getStatistics() {

        return houses.stream().
                map(House::getStatistics).
                collect(Collectors.joining("")).
                trim();
    }
}
