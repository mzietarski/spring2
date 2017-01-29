package Transport.domain;

import java.util.*;

import javax.persistence.*;

@Entity
@NamedQueries({
		@NamedQuery(name = "mechanik.all", query = "Select t from Mechanik t"),
		@NamedQuery(name = "mechanik.byNazwisko",query = "select t from Mechanik t where t.nazwisko=:nazwisko"),
		@NamedQuery(name = "mechanik.byBus",query = "select a from Mechanik a where a.buses=:buses")
})
public class Mechanik {

	private Long idMechanik; //idRun
	private String imie; //name
	private String nazwisko; //descript
	private double wiek; //price
	private double payment; //distance
	private Bus buses;


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name ="idMechanik")
	public Long getId() {
		return idMechanik;
	}
	public void setId(Long idMechanik) {
		this.idMechanik = idMechanik;
	}

	public String getNazwisko() {
		return nazwisko;
	}

	public void setNazwisko(String nazwisko) {
		this.nazwisko = nazwisko;
	}

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }
	public Double getWiek() {
		return wiek;
	}

	public void setWiek(Double wiek) {
		this.wiek = wiek;
	}

	public Double getPayment() {
		return payment;
	}

	public void setPayment(Double payment) {
		this.payment = payment;
	}


	@ManyToOne
	@JoinColumn(name = "bus_IdBus")
	public Bus getBuses() {
		return buses;
	}

	public void setBuses(Bus buses) {
		this.buses = buses;
	}
}
