package Transport.service;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import Transport.domain.Bus;
import Transport.domain.Mechanik;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/beans.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@Transactional
public class ManagerTest {

    String randomString="";
    Mechanik mechanik1=new Mechanik();
    Mechanik mechanik2 = new Mechanik();
    Mechanik mechanik3 = new Mechanik();
    Bus bus1 =new Bus();

    Bus bus2 =new Bus();
    @Autowired
    Manager Manager;

    @Before
    public void setUp() throws Exception {
        char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        randomString= sb.toString();

        mechanik1.setNazwisko(randomString);
        mechanik1.setImie("Jan");
        mechanik1.setWiek(40.00);
        mechanik1.setPayment(4000.00);

        mechanik2.setNazwisko(randomString);
        mechanik2.setImie("Jakub");
        mechanik2.setWiek(30.00);
        mechanik2.setPayment(3000.00);

        mechanik3.setNazwisko(randomString);
        mechanik3.setImie("Bogdan");
        mechanik3.setWiek(20.00);
        mechanik3.setPayment(2000.00);

        bus1.setMarka("MAN");
        bus1.setModel(randomString);
        bus1.setYear(2015);
        bus1.setNrlini(200);


        bus2.setMarka("DAF");
        bus2.setModel(randomString);
        bus2.setYear(2010);
        bus2.setNrlini(120);
    }

    @After
    @Rollback
    public void tearDown() throws Exception {

        
    }

    @Test
    public void addMechanikCheck() {

   
        Manager.addMechanik(mechanik1);
        assertEquals(randomString,
                Manager
                        .findByNazwiskoMechanik(mechanik1.getNazwisko())
                        .getNazwisko()
        );

   }
//
    @Test
    public void findByMarkaBus() {

    long      id=  Manager.addBus(bus1);

        Manager.addBus(bus2);



        assertNotNull(bus2.getMarka(),Manager.findByMarkaBus(bus2.getMarka()));
        assertEquals(bus1.getMarka(),Manager.findByMarkaBus("MAN").get(0).getMarka());

        assertEquals(bus1.getMarka(),Manager.findByMarkaBus("TGA").get(0).getMarka());
    }



    @Test
    public void addBusCheck() {

        Manager.addBus(bus2);

        assertEquals(bus2.getId(),
                Manager
                        .findByIdBus(bus2.getId())
                        .getId()

        );


    }

    @Test
    public void getAllMechanikCheck() throws FileNotFoundException {


        Manager.addMechanik(mechanik1);

        Manager.addMechanik(mechanik2);
        assertTrue(2<=Manager.getAllMechanik().size());


    }

    @Test
    public void getAllBusCheck() {

        Manager.addBus(bus1);
        Manager.addBus(bus2);
        assertTrue(2<=Manager.getAllBus().size());

    }
    @Test
    public void findByIdMechanik() throws Exception {

        Long id= Manager.addMechanik(mechanik1);

         Manager.addMechanik(mechanik2);
        assertEquals(mechanik1.getImie(), Manager.findByIdMechanik(id).getImie());

    }

    @Test
    public void deleteMechanik() throws Exception {

        Long idMechanik1= Manager.addMechanik(mechanik1);

        Long idMechanik2= Manager.addMechanik(mechanik2);
        Manager.deleteMechanik(mechanik1);
        try {
            Manager.findByIdMechanik(idMechanik1);
        }catch (NullPointerException e){
            assertTrue(true);
        }
        assertEquals(idMechanik2, Manager.findByIdMechanik(idMechanik2).getId());


    }

    @Test
    public void findByIdBus() throws Exception {
        Long id= Manager.addBus(bus1);
        assertEquals(bus1.getMarka(), Manager.findByIdBus(id).getMarka());

    }

    @Test
    public void deleteBus() throws Exception {

        Long idBus= Manager.addBus(bus1);

        Long idBus2= Manager.addBus(bus2);
        Manager.deleteBus(bus2);
        try {
            Manager.findByIdBus(idBus2);
        }catch (NullPointerException e){
            assertTrue(true);
        }
        assertEquals(idBus, Manager.findByIdBus(idBus).getId());

    }
    @Test
    public void addMechanikToBus() throws Exception {

        Long id= Manager.addMechanik(mechanik1);
        Long id2 = Manager.addMechanik(mechanik2);
        Long idBus2= Manager.addBus(bus2);

        Long idBus1= Manager.addBus(bus1);
        Manager.addMechanikToBus(id,idBus1);
        Manager.addMechanikToBus(id2,idBus1);

       assertEquals(id, Manager.getAllMechanikBus(idBus1).get(0).getId());

        assertEquals(id2, Manager.getAllMechanikBus(idBus1).get(1).getId());
        assertEquals(0,Manager.getAllMechanikBus(idBus2).size());

    }

    @Test
    public void findBusByYear() throws Exception{
        Manager.addBus(bus1);
        Manager.addBus(bus2);
       assertTrue( 1 <=Manager.findBusByYear(2013).size());

    }

    @Test
    public void editBus() throws  Exception{
        Manager.addBus(bus1);
        Manager.addBus(bus2);

      Bus  editBus = Manager.findByIdBus(bus1.getId());

        editBus.setMarka("Iveco");
        Manager.editBus(editBus);
        assertEquals(editBus.getMarka(),Manager.findByIdBus(editBus.getId()).getMarka());

    }
    public void editMechanik() throws  Exception{
        Manager.addMechanik(mechanik1);
        Manager.addMechanik(mechanik2);

        Mechanik editMechanik = Manager.findByIdMechanik(mechanik1.getId());

        editMechanik.setImie("Zbigniew");
        Manager.editMechanik(editMechanik);
        assertEquals(editMechanik.getImie(),Manager.findByIdMechanik(editMechanik.getId()).getImie());
        assertEquals(mechanik2.getImie(),Manager.findByIdMechanik(mechanik1.getId()).getImie());

    }
}
