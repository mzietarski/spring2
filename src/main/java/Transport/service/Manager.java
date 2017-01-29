package Transport.service;

import java.util.List;

import Transport.domain.Bus;
import Transport.domain.Mechanik;

public interface Manager {

	Long addMechanik(Mechanik mechanik);
	List<Mechanik> getAllMechanik();
	Mechanik findByIdMechanik(Long id);
	Mechanik findByNazwiskoMechanik(String nazwisko);
	void deleteMechanik(Mechanik mechaniks);
	void editMechanik(Mechanik mechanik);

	Long addBus(Bus bus);
	List<Bus> getAllBus();
	Bus findByIdBus(Long id);
	List<Bus> findByMarkaBus(String marka);
	List<Bus> findBusByYear(int year);
	void deleteBus(Bus bus);
	void editBus(Bus bus);

	List<Mechanik> getAllMechanikBus(Long idBus);
	void addMechanikToBus(Long idType,Long idBook);

	void clearAll();

}
