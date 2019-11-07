package jp.co.sample.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.sample.domain.Administrator;

/**
 * administratorsテーブルを操作するリポジトリ.
 * 
 * @author shun053012
 *
 */
@Repository
public class AdministratorRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	/**
	 * データベースから検索されたResultSetオブジェクトを１つのオブジェクトに変換する.
	 */
	private static final RowMapper<Administrator> ADMINISTRATOR_ROW_MAPPER = (rs, i) -> {
		Administrator administrator = new Administrator();
		administrator.setId(rs.getInt("id"));
		administrator.setName(rs.getString("name"));
		administrator.setMailAddress(rs.getString("mail_address"));
		administrator.setPassword(rs.getString("password"));

		return administrator;

	};

	/**
	 * 管理者情報を挿入する.
	 * 
	 * @param administrator 管理者情報
	 */
	public void insert(Administrator administrator) {
		String insertsql = "insert into administrators (name,mail_address,password) values(:name,:mailAddress,:password)";
		SqlParameterSource param = new BeanPropertySqlParameterSource(administrator);

		template.update(insertsql, param);

	}

	/**
	 * メールアドレスとパスワードから管理者情報を取得する.
	 * 
	 * @param mailAddress メールアドレス
	 * @param password    パスワード
	 * @return 管理者情報（存在しない場合はnullを返す）
	 */
	public Administrator findByMailAddressAndPassword(String mailAddress, String password) {
		String sql = "select id,name,mail_address,password from administrators where mail_address =:mailAddress and password =:password ";
		SqlParameterSource param = new MapSqlParameterSource().addValue("mailAddress", mailAddress).addValue("password", password);
		try {
			Administrator administrator = template.queryForObject(sql, param, ADMINISTRATOR_ROW_MAPPER);

			return administrator;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
