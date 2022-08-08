package cathaybk.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import cathaybk.model.po.MyCurrency;

public interface MyCurrencyRepo extends JpaRepository<MyCurrency, Integer>{
	
	@Transactional
	@Modifying
	@Query(value = "TRUNCATE TABLE currency RESTART IDENTITY",
			nativeQuery = true)
	public void truncateTable();

}
