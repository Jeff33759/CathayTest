package cathaybk.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import cathaybk.model.po.MyCurrency;

public interface MyCurrencyRepo extends JpaRepository<MyCurrency, Integer>{
	
	/**
	 * 冠上@Modifying，聲明這個語句是增刪改的語句。
	 * 而@Modifying是什麼事務等級?由@Transactional決定。
	 * 
	 * 一般的@Query都是Read Only，冠上@Transactional，
	 * 本質上是聲明了@Transactional(readOnly=false)，讓此方法的SQL不是Read Only。
	 * 
	 * 只要自訂義的SQL是增刪改，都要這麼做。
	 * */
	@Transactional
	@Modifying
	@Query(value = "TRUNCATE TABLE currency RESTART IDENTITY",
			nativeQuery = true)
	public void truncateTable();

}
