package jp.co.sample.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.domain.Employee;
import jp.co.sample.form.UpdateEmployeeForm;
import jp.co.sample.service.EmployeeService;

/**
 * 従業員情報を操作するコントローラ.
 * 
 * @author shun053012
 *
 */
@Controller
@RequestMapping("/employee")
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;
	
	@ModelAttribute
	public UpdateEmployeeForm setupdateEmoloyeeForm() {
		return new UpdateEmployeeForm();
	}
	
	/**
	 * 従業員情報を全件取得する.
	 * 
	 * @param id 従業員ID
	 * @param model　リクエストスコープ
	 * @return　授業員の詳細情報画面
	 */
	@RequestMapping("/showList")
	public String showList(Model model) {
		List<Employee> employeeList= employeeService.showList();
		model.addAttribute("employeeList",employeeList);
		
		return "employee/list.html";
		
	}
	

	/**　従業員情報を取得する
	 * @param id　従業員ID
	 * @param model　リクエストスコープ
	 * @return　従業員の詳細情報画面にフォワード
	 */
	@RequestMapping("/showDetail")
	public String showDetail(String id,Model model) {
		Employee employee=employeeService.showDetail(Integer.parseInt(id));
		model.addAttribute("employee",employee);
		return "employee/detail.html";
		
		
		
	}
	/**従業員情報の更新
	 * @param form　従業員のIDと扶養人数
	 * @return 従業員一覧画面へフォワード
	 */
	@RequestMapping("/update")
	public String update(UpdateEmployeeForm form) {
		Employee employee = new Employee();
		employee.setId(Integer.parseInt(form.getId()));
		employee.setDependentsCount(Integer.parseInt(form.getDependentsCount()));
		employeeService.update(employee);
		return "redirect:/employee/showList";
	}

}
