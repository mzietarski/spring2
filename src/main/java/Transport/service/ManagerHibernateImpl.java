package Transport.service;

import java.util.ArrayList;
import java.util.List;

import Transport.domain.Bus;
import Transport.domain.Mechanik;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class ManagerHibernateImpl implements Manager {

	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public Long addMechanik(Mechanik mechanik) {
		mechanik.setId(null);
		return (Long) sessionFactory.getCurrentSession().save(mechanik);
	}

	@Override
	public List<Mechanik> getAllMechanik() {
		return sessionFactory
				.getCurrentSession()
				.getNamedQuery("mechanik.all")
				.list();
	}

	@Override
	public Mechanik findByIdMechanik(Long id) {
		return (Mechanik)sessionFactory
				.getCurrentSession()
				.get(Mechanik.class,id);
	}

	@Override
	public Mechanik findByNazwiskoMechanik(String nazwisko) {
		return (Mechanik)sessionFactory
				.getCurrentSession()
				.getNamedQuery("mechanik.byNazwisko")
				.setString("nazwisko",nazwisko)
				.uniqueResult();
	}

	@Override
	public List <Bus> findBusByYear(int year){
		return   sessionFactory
				.getCurrentSession()
				.getNamedQuery("bus.byYear")
				.setInteger("year",year)
				.list();
	}
	@Override
	public void deleteMechanik(Mechanik mechaniks) {


		mechaniks = (Mechanik) sessionFactory
				.getCurrentSession()
				.get(Mechanik.class, mechaniks.getId());
		sessionFactory.getCurrentSession().delete(mechaniks);
	}
	@Override
	public  void editMechanik(Mechanik mechanik){

		sessionFactory.getCurrentSession().update(mechanik);
	}

	@Override
	public  void editBus(Bus bus){

		sessionFactory.getCurrentSession().update(bus);

	}
	@Override
	public Long addBus(Bus bus) {
		bus.setId(null);
		return (Long) sessionFactory.getCurrentSession().save(bus);
	}

	@Override
	public List<Bus> getAllBus() {
		return sessionFactory
				.getCurrentSession()
				.getNamedQuery("bus.all")
				.list();
	}

	@Override
	public Bus findByIdBus(Long id) {
		return (Bus) sessionFactory
				.getCurrentSession()
				.get(Bus.class,id);
	}

	@Override
	public List<Bus> findByMarkaBus(String marka) {
		 List<Bus> findBus;
		findBus =sessionFactory
				.getCurrentSession()
				.getNamedQuery("bus.byMarka")
				.setString("marka","%"+marka+"%")
				.list();
		return findBus;
	}

	@Override
	public void deleteBus(Bus bus) {
		bus = (Bus) sessionFactory.getCurrentSession()
				.get(Bus.class,bus.getId());
		if(bus.getMechaniks()!=null)
			for (Mechanik mechanik :bus.getMechaniks()){
				mechanik.setBuses(null);
				sessionFactory.getCurrentSession().update(mechanik);

			}
		sessionFactory.getCurrentSession().delete(bus);


	}

	@Override
	public List<Mechanik> getAllMechanikBus(Long idBuses) {

		List<Mechanik> allMechanikBus ;
		allMechanikBus =sessionFactory.getCurrentSession()
				.getNamedQuery("mechanik.byBus")
				.setLong("buses",idBuses)
				.list();

		return allMechanikBus;
	}

	@Override
	public void addMechanikToBus(Long idMechanik, Long idBus) {
		Bus bus = (Bus) sessionFactory.getCurrentSession().get(
				Bus.class, idBus);
		Mechanik mechanik = (Mechanik) sessionFactory.getCurrentSession()
				.get(Mechanik.class, idMechanik);
		mechanik.setBuses(bus);
	}

	@Override
	public void clearAll() {
		sessionFactory
				.getCurrentSession()
				.createQuery("delete from Mechanik")
				.executeUpdate();
		sessionFactory
				.getCurrentSession()
				.createQuery("delete from Bus")
				.executeUpdate();

	}


}
