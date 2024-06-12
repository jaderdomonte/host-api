package com.hostfully.bookingapi.db;

import com.hostfully.bookingapi.db.entity.*;
import com.hostfully.bookingapi.db.enumeration.BookingStatusEnum;
import com.hostfully.bookingapi.db.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Component
@AllArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final PropertyRepository propertyRepository;

    private final GuestRepository guestRepository;

    private final BookingStatusRepository bookingStatusRepository;

    private final BookingRepository bookingRepository;

    private final BlockingRepository blockingRepository;

    @Override
    public void run(String... args) throws Exception {
        createProperties();
        createGuests();
        createBookingStatus();
        createBookings();
        createBlockings();
    }

    private void createBookingStatus() {
        Arrays.stream(BookingStatusEnum.values())
                .forEach(statusEnum ->
                        bookingStatusRepository.save(BookingStatusEntity.builder().id(statusEnum.getId()).description(statusEnum.getDescription()).build()));
    }

    private void createGuests() {
        GuestEntity aaronRodgers = GuestEntity.builder().fullName(GuestName.builder().firstName("Aaron").lastName("Rodgers").build()).build();
        GuestEntity brockPurdy = GuestEntity.builder().fullName(GuestName.builder().firstName("Brock").lastName("Purdy").build()).build();
        GuestEntity patrickMahomes = GuestEntity.builder().fullName(GuestName.builder().firstName("Patrick").lastName("Mahomes").build()).build();
        GuestEntity jordanLove = GuestEntity.builder().fullName(GuestName.builder().firstName("Jordan").lastName("Love").build()).build();
        GuestEntity ericStokes = GuestEntity.builder().fullName(GuestName.builder().firstName("Eric").lastName("Stokes").build()).build();

        List<GuestEntity> guestEntities = Arrays.asList(aaronRodgers, brockPurdy, patrickMahomes, jordanLove, ericStokes);

        guestEntities.forEach(guestEntity -> guestRepository.save(guestEntity));
    }

    private void createProperties() {
        PropertyEntity lakefrontEscape = PropertyEntity.builder().name("Lakefront Escape").build();
        PropertyEntity beachsideHome = PropertyEntity.builder().name("Beachside Home").build();
        PropertyEntity villageCabin = PropertyEntity.builder().name("Village Cabin").build();
        PropertyEntity laPremiunSuite = PropertyEntity.builder().name("LA Premiun Suite").build();
        PropertyEntity snowLodge = PropertyEntity.builder().name("Snow Lodge").build();

        List<PropertyEntity> propertyEntities = Arrays.asList(lakefrontEscape, beachsideHome, villageCabin, laPremiunSuite, snowLodge);

        propertyEntities.forEach(propertyEntity -> propertyRepository.save(propertyEntity));

        System.out.println(propertyRepository.findAll().size());
    }

    private void createBookings() {
        GuestEntity guest = GuestEntity.builder().id(1L).build();
        Period period = Period.builder().checkIn(LocalDate.now().plusDays(10)).checkOut(LocalDate.now().plusDays(20)).build();
        BookingStatusEntity bookingStatusEntity = new BookingStatusEntity(BookingStatusEnum.CONFIRMED.getId());
        PropertyEntity propertyEntity = PropertyEntity.builder().id(1L).name("Lakefront Escape").build();

        bookingRepository.save(BookingEntity.builder().guest(guest).property(propertyEntity).period(period).status(bookingStatusEntity).build());

        Period period2 = Period.builder().checkIn(LocalDate.now().plusDays(10)).checkOut(LocalDate.now().plusDays(20)).build();
        PropertyEntity propertyEntity2 = PropertyEntity.builder().id(2L).name("Beachside Home").build();

        bookingRepository.save(BookingEntity.builder().guest(guest).property(propertyEntity2).period(period2).status(bookingStatusEntity).build());
    }

    private void createBlockings() {
        Period period = Period.builder().checkIn(LocalDate.now().plusDays(10)).checkOut(LocalDate.now().plusDays(20)).build();
        PropertyEntity villageCabin = PropertyEntity.builder().id(3L).name("Village Cabin").build();

        blockingRepository.save(BlockingEntity.builder().property(villageCabin).period(period).build());

        PropertyEntity laPremiunSuite = PropertyEntity.builder().id(4L).name("LA Premiun Suite").build();

        blockingRepository.save(BlockingEntity.builder().property(laPremiunSuite).period(period).build());
    }
}
