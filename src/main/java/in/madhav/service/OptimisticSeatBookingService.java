package in.madhav.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.madhav.entity.Seat;

@Service
public class OptimisticSeatBookingService {

	@Autowired
	private MovieTicketBooking movieTicketBooking;
	
	public void bookTicker(Long seatId) throws InterruptedException {
		
		Thread th1=new Thread(() -> {
			try {
			System.out.println(Thread.currentThread().getName()+" is trying to book ticket");
			Seat seat = movieTicketBooking.bookTicket(seatId);
			System.out.println(Thread.currentThread().getName()+" successfully booked ticket with version num "+seat.getVersion());
			}
			catch(Exception ex) {
				System.out.println("failed: "+ex.getMessage());
			}
		});
		
		Thread th2=new Thread(() -> {
			try {
			System.out.println(Thread.currentThread().getName()+" is trying to book ticket");
			movieTicketBooking.bookTicket(seatId);
			System.out.println(Thread.currentThread().getName()+" successfully booked ticket with version num \"+seat.getVersion()");
			
			}
			catch(Exception ex) {
				System.out.println("failed: "+ex.getMessage());
			}
		});
		
		th1.start();
		th2.start();
		th1.join();
		th2.join();
	}
}
