package jp.co.sample.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.domain.Administrator;
import jp.co.sample.form.InsertAdministratorForm;
import jp.co.sample.form.LoginForm;
import jp.co.sample.service.AdministratorService;

/**
 * 管理者情報を操作するコントローラ.
 * @author shun053012
 */
@Controller
@RequestMapping("/")
public class AdministratorController {
	@Autowired
	private HttpSession session;
	
	
	@Autowired
	private AdministratorService administratorService;
	
	@ModelAttribute
	public InsertAdministratorForm setUpInsertAdministratorForm() {
		InsertAdministratorForm insertAdministratorForm =  new InsertAdministratorForm();
		return insertAdministratorForm;
		
	}
	/**
	 * 管理者情報登録画面を表示する
	 * @return　管理者情報登録画面
	 */
	@RequestMapping("toInsert")
	public String toInsert() {
		return "administrator/insert.html";
		
	}
	/**
	 * 管理者情報を登録する.
	 * @param form リクエストパラメータ情報
	 * @return ログイン画面へリダイレクト
	 */
	@RequestMapping("/insert")
	public String insert(InsertAdministratorForm form) {
		Administrator administrator = new Administrator();
		BeanUtils.copyProperties(form, administrator);
		
//		AdministratorService administratorService= new AdministratorService();
		administratorService.insert(administrator);
		return "redirect:/";
	}
	@ModelAttribute
	public LoginForm setUpLoginForm() {
		LoginForm loginForm = new LoginForm();
		return  loginForm;
	}
	/**
	 * ログイン画面へフォワード
	 * @return　ログイン画面
	 */
	@RequestMapping("/")
	public  String toLogin() {
		return "administrator/login.html";
	}
	
	/**
	 * メールアドレスとパスワードが一致した登録者情報を検索する
	 * @param form　登録者情報
	 * @param model
	 * @return　ログインに失敗したらログイン画面を表示
	 * 　　　　　ログインに成功したら従業員リストを表示
	 */
	@RequestMapping("/login")
	public String login (LoginForm form,Model model) {
		Administrator administrator=administratorService.login(form.getMailAddress(),form.getPassword());
		if(administrator==null) {
			model.addAttribute("fail","メールアドレスまたはパスワードが不正です");
			return "administrator/login";
		}
		session.setAttribute("administratorName",administrator.getName());
		return "forward:/employee/showList";
	}
	/**
	 * sessionスコープの情報を消去
	 * @return ログイン画面にリダイレクト
	 */
	@RequestMapping("/logout")
	public String logout() {
		session.invalidate();
		return "redirect:/";
	}
}
