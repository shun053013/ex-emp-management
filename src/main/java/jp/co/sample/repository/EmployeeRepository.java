package jp.co.sample.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.sample.domain.Employee;

/**
 * @author shun053012
 *データベースアクセス処理を行う
 */
@Repository
public class EmployeeRepository {

	/**
	 * データベースから検索されたResultSetオブジェクトを１つのオブジェクトに変換する
	 */
	@Autowired
	private NamedParameterJdbcTemplate template;
	private static final RowMapper<Employee> EMPLOYEE_ROW_MAPPER = (rs, i) -> {
		Employee employee = new Employee();
		employee.setId(rs.getInt("id"));
		employee.setName(rs.getString("name"));
		employee.setImage(rs.getString("image"));
		employee.setGender(rs.getString("gender"));
		employee.setHireDate(rs.getDate("hire_date"));
		employee.setMailAddress(rs.getString("mail_address"));
		employee.setZipCode(rs.getString("zip_code"));
		employee.setTelephone(rs.getString("telephone"));
		employee.setSalary(rs.getInt("salary"));
		employee.setCharacteristics(rs.getString("characteristics"));
		employee.setDependentsCount(rs.getInt("dependents_count"));

		return employee;
	};

	/** 
	 * 従業員一覧情報を入社日順で取得する.
	 * 
	 * @return 従業員一覧情報（従業員が存在しない場合はサイズ０件の従業員一覧を返す）
	 * 
	 */
	public List<Employee> findAll() {
		String sql = "select id,name,image,gender,hire_date,mail_address,zip_code,telephone,salary,characteristics,dependents_count from employees order by hire_date desc";
		List<Employee> employeeList = template.query(sql, EMPLOYEE_ROW_MAPPER);

		if (employeeList.size() == 0) {
			return null;
		} else {
			return employeeList;
		}
	}

	/** 主キーから従業員情報を取得する（従業員情報が存在しない場合はSpringが自動的に例外を発生する。）
	 * @param id　ID
	 * @return 従業員情報
	 */
	public Employee load(Integer id) {
		String sql = "select id,name,image,gender,hire_date,mail_address,zip_code,telephone,salary,characteristics,dependents_count from employees where id=:id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		
		Employee employee = template.queryForObject(sql, param, EMPLOYEE_ROW_MAPPER);
		return employee;

	}

	/**従業員情報を変更する（今回は従業員情報の扶養人数だけを更新できるようなSQLを発行する。）
	 * @param employee 従業員情報
	 */
	public void update(Employee employee) {
		String sql = "update employees set dependents_count=:dependentsCount where id=:id";
		SqlParameterSource param = new BeanPropertySqlParameterSource(employee);
		template.update(sql, param);

	}

}
