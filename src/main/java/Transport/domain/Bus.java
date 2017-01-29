package Transport.domain;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.util.Collection;


@Entity
@NamedQueries({
        @NamedQuery(name="bus.all", query = "select b from Bus b"),
        @NamedQuery(name = "bus.byMarka",query = "select b from Bus b where b.marka like :marka "),
        @NamedQuery(name = "bus.byYear",query = "select a from Bus a where a.year>:year")
})
public class Bus {

    private Long idBus; //idCar
    private String marka; //name
    private String model; //warnings
    private double nrlini; //course
    private int year;
    private Collection<Mechanik> mechaniks; //Run runs


    @Id
    @Column(name = "idBus")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return idBus;
    }

    public void setId(Long idBus) {
        this.idBus = idBus;
    }
    
    public String getMarka() {
        return marka;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }

    public String getModel() {
        return model;
    }

    public void setNrlini(double nrlini) {
        this.nrlini = nrlini;
    }


    public double getNrlini() {
        return nrlini;
    }

    public void setModel(String model) {
        this.model = model;
    }


    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }


    @OneToMany(mappedBy = "buses",cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    public Collection<Mechanik> getMechaniks() {
        return mechaniks;
    }

    public void setMechaniks(Collection<Mechanik> mechaniks) {
        this.mechaniks = mechaniks;
    }

}
