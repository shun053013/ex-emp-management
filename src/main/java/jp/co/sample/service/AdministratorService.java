package jp.co.sample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.sample.domain.Administrator;
import jp.co.sample.repository.AdministratorRepository;

/**
 * 管理者情報を操作するサービスクラス.
 * 
 * @author shun053012
 *
 */
@Service
@Transactional
public class AdministratorService {
	
	@Autowired
	private AdministratorRepository administratorRepository;
	
	/**管理者情報を挿入
	 * @param administrator 管理者情報
	 */
	public void insert(Administrator administrator) {
		System.out.println(administratorRepository);
		administratorRepository.insert(administrator);
		
	}
	
	/**管理者のメールアドレスとパスワードを検索
	 * @param mailAddress 管理者のメールアドレス
	 * @param password　　管理者のパスワード
	 * @return　　管理者情報
	 */
	public Administrator login(String mailAddress,String password) {
		Administrator administrator=administratorRepository.findByMailAddressAndPassword(mailAddress, password);
		return administrator;
		
	}
	
	
	
	

}
