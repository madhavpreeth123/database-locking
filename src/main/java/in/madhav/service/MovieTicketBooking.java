package in.madhav.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.madhav.entity.Seat;
import in.madhav.repo.SeatRepo;

@Service
public class MovieTicketBooking {

	@Autowired
	private SeatRepo seatRepo;

	@Transactional
	public Seat bookTicket(Long seatId) {

		Seat seat = seatRepo.findById(seatId)
				.orElseThrow(() -> new RuntimeException("Seat not found with id: " + seatId));

		System.out.println(Thread.currentThread().getName() + " fetched Seat version number: " + seat.getVersion());
		if (seat.isBooked()) {

			throw new RuntimeException("Seat already booked");
		}

		seat.setBooked(true);

		// version check will happens

		return seatRepo.save(seat);
	}

	@Transactional
	public void bookTicketWithPessimistic(Long seatId) {

		System.out.println(Thread.currentThread().getName() + " is attempting to fetch the seat");

		// pessimistic lock
		Seat seat = seatRepo.findByIdWithLock(seatId);

		System.out.println(Thread.currentThread().getName() + " acquired the lock for seat id " + seatId);

		if (seat.isBooked()) {

			throw new RuntimeException("Seat already booked");
		}

		// booking seat
		System.out.println(Thread.currentThread().getName() + " booking the seat " + seatId);

		seat.setBooked(true);

		seatRepo.save(seat);

		System.out.println(Thread.currentThread().getName() + " successfully book the seat with ID " + seatId);

	}

}
