package in.madhav.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import in.madhav.entity.Seat;
import jakarta.persistence.LockModeType;

public interface SeatRepo extends JpaRepository<Seat, Long>{

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("SELECT s FROM Seat s WHERE s.id= :seatId")
	public Seat findByIdWithLock(Long seatId);

}
