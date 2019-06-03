package fdmc.domain.entities;

public class Cat {
    private String name;
    private String breed;
    private String colour;
    private Integer age;

    public Cat(String name, String breed, String colour, Integer age) {
        this.name = name;
        this.breed = breed;
        this.colour = colour;
        this.age = age;
    }

    public Cat() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
