package entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "airplane")
public class Airplane {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String name;

    @Column
    private String model;

    @Column
    private Integer places;

    @OneToMany(mappedBy = "airplane", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Flight> flights;

    @ManyToOne
    @JoinColumn(name = "airline_id", nullable = false)
    private Airplane airplane;


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getModel() {
        return model;
    }

    public Integer getPlaces() {
        return places;
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public Airplane getAirplane() {
        return airplane;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setPlaces(Integer places) {
        this.places = places;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

    public void setAirplane(Airplane airplane) {
        this.airplane = airplane;
    }
}

