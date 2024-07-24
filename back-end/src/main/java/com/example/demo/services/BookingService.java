package com.example.demo.services;

import com.example.demo.entities.BookingEntity;
import com.example.demo.helpers.TimeProvider;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.ConferenceRoomRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class BookingService {
    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    ConferenceRoomRepository conferenceRoomRepository;

    @Autowired
    TimeProvider timeProvider;


    public BookingEntity checkBooking(final BookingEntity booking, final long room_id) {
        try {
            conferenceRoomRepository.findById(room_id);
        } catch (final Exception e) {
            throw new IllegalArgumentException("Room doesn't exist");
        }

        final List<BookingEntity> bookings = bookingRepository.findAll();
        bookings.removeIf(b -> b.getConferenceRoomEntity().getId() != room_id);
        for (final BookingEntity b : bookings) {
            if (b.getConferenceRoomEntity().getId() == room_id) {
                if (booking.getStartTime().toLocalDateTime().isAfter(b.getStartTime().toLocalDateTime()) && booking.getStartTime().toLocalDateTime().isBefore(b.getEndTime().toLocalDateTime())) {
                    throw new IllegalArgumentException("Booking start time is in another booking");
                } else if (booking.getEndTime().toLocalDateTime().isAfter(b.getStartTime().toLocalDateTime()) && booking.getEndTime().toLocalDateTime().isBefore(b.getEndTime().toLocalDateTime())) {
                    throw new IllegalArgumentException("Booking end time is in another booking");
                } else if (booking.getStartTime().toLocalDateTime().isBefore(b.getStartTime().toLocalDateTime()) && booking.getEndTime().toLocalDateTime().isAfter(b.getEndTime().toLocalDateTime())) {
                    throw new IllegalArgumentException("Booking is in another booking");
                } else if (booking.getStartTime().toLocalDateTime().isEqual(b.getStartTime().toLocalDateTime()) || booking.getEndTime().toLocalDateTime().isEqual(b.getEndTime().toLocalDateTime())) {
                    throw new IllegalArgumentException("Booking is in another booking");
                }
            }
        }
        if (booking.getStartTime().toLocalDateTime().isBefore(this.timeProvider.now())) {
            throw new IllegalArgumentException("Booking start time is in the past");
        }

        if (booking.getStartTime().toLocalDateTime().isAfter(booking.getEndTime().toLocalDateTime())) {
            throw new IllegalArgumentException("Start time must be before end time");
        }
        return booking;
    }


    public BookingEntity addBooking(BookingEntity booking, final long room_id) {
        booking = checkBooking(booking, room_id);
        final int length = 10;
        final boolean useLetters = true;
        final boolean useNumbers = true;
        String generatedString;
        final boolean idIsUnique = false;

        final List<BookingEntity> bookingEntities = bookingRepository.findAll();
        generatedString = RandomStringUtils.random(length, useLetters, useNumbers);
        for (final BookingEntity bookingEntity : bookingEntities) {
            if (bookingEntity.getUniqueId().equals(generatedString)) {
                generatedString = RandomStringUtils.random(length, useLetters, useNumbers);
            }
        }
        booking.setUniqueId(generatedString);
        booking.setConferenceRoomEntity(conferenceRoomRepository.findById(room_id).get());


        return bookingRepository.save(booking);
    }


    public List<BookingEntity> getAllBookings() {
        return bookingRepository.findAll();
    }

    public List<BookingEntity> getBookingsByRoom(final long roomId) {
        if (conferenceRoomRepository.existsById(roomId)) {
            final List<BookingEntity> bookings = bookingRepository.findAll();
            bookings.removeIf(booking -> booking.getConferenceRoomEntity().getId() != roomId);
            return bookings;
        } else {
            throw new IllegalArgumentException("Room doesn't exist");
        }
    }

    public List<BookingEntity> getAvailableToday(final long roomId, final Timestamp timestamp) {
        if (conferenceRoomRepository.existsById(roomId)) {
            final List<BookingEntity> bookings = getBookingsByRoom(roomId);
            bookings.removeIf(booking -> !booking.getStartTime().toLocalDateTime().toLocalDate().isEqual(timestamp.toLocalDateTime().toLocalDate()));
            return bookings;
        } else {
            throw new IllegalArgumentException("Room doesn't exist");
        }
    }


    public void deleteBooking(final String uniqueId) {
        final List<BookingEntity> bookings = bookingRepository.findAll();
        boolean found = false;
        for (final BookingEntity booking : bookings) {
            if (booking.getUniqueId().equals(uniqueId)) {
                bookingRepository.delete(booking);
                found = true;
            }
        }
        if (!found) {
            throw new IllegalArgumentException("Booking not found");
        }

    }

    public List<BookingEntity> getBookingsByDateAndRoom(final String date, final long roomId) {
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Date dateAfter = null;
        try {
            dateAfter = format.parse(date);
        } catch (final ParseException e) {
            e.printStackTrace();
        }
        final List<BookingEntity> bookings = bookingRepository.findAll();
        final Date finalDateAfter = dateAfter;
        assert finalDateAfter != null;
        final LocalDate dateStripped = new Date(finalDateAfter.getTime()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        bookings.removeIf(booking -> !booking.getStartTime().toLocalDateTime().toLocalDate().isEqual(dateStripped));
        if (bookings.isEmpty()) {
            return null;
        } else {
            return bookings;
        }

    }

    public BookingEntity updateBooking(final String uniqueId, final BookingEntity bookingEntity) {
        final BookingEntity bookingToUpdate = getBookingByUniqueID(uniqueId);
        final BookingEntity checkedBooking = checkBooking(bookingEntity, bookingToUpdate.getId());
        bookingToUpdate.setStartTime(checkedBooking.getStartTime());
        bookingToUpdate.setEndTime(checkedBooking.getEndTime());
        bookingRepository.save(bookingToUpdate);
        return bookingRepository.save(bookingToUpdate);
    }


    public BookingEntity getBookingByUniqueID(final String uniqueId) {
        final List<BookingEntity> bookings = bookingRepository.findAll();
        for (final BookingEntity booking : bookings) {
            if (booking.getUniqueId().equals(uniqueId)) {
                return booking;
            }
        }
        throw new IllegalArgumentException("Booking not found");
    }

    public long getRoomIDByUniqueID(final String uniqueId) {
        final List<BookingEntity> bookings = bookingRepository.findAll();
        for (final BookingEntity booking : bookings) {
            if (booking.getUniqueId().equals(uniqueId)) {
                return booking.getConferenceRoomEntity().getId();
            }
        }
        throw new IllegalArgumentException("Booking not found");
    }

    public void deleteBookingById(final long id) {
        if (!bookingRepository.existsById(id)) {
            throw new IllegalArgumentException("Booking not found");
        }
        bookingRepository.deleteById(id);
    }
}
