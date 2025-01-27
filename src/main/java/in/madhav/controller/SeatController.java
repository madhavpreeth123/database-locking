package in.madhav.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import in.madhav.service.OptimisticSeatBookingService;
import in.madhav.service.PessimisticSeatBookingService;

@RestController
public class SeatController {

	@Autowired
	private OptimisticSeatBookingService optimisticSeatBookingService;
	
	@Autowired
	private PessimisticSeatBookingService pessimisticSeatBookingService;
	
	@GetMapping("/optimistic/{seatId}")
    public String testOptimistic(@PathVariable Long seatId) throws InterruptedException {
        
		optimisticSeatBookingService.bookTicker(seatId);
        return "Optimistic locking test started! Check logs for results.";
    }
	
	
	@GetMapping("/pessimistic/{seatId}")
    public String testPessimistic(@PathVariable Long seatId) throws InterruptedException {
        
		pessimisticSeatBookingService.testPessimisticLocking(seatId);
        return "Optimistic locking test started! Check logs for results.";
    }
}
