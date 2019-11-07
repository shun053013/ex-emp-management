package jp.co.sample.domain;

/**
 * 管理者情報を表すドメイン.
 * 
 * @author shun053012
 *
 */
public class Administrator {
	

	/** ID */
	private Integer Id;

	/** 名前 */
	private String name;
	
	/**
	 * メールアドレス
	 */
	private String mailAddress;
	
	/**
	 * パスワード
	 */
	private String password;
	
	public Administrator(Integer id, String name, String mailAddress, String password) {
		super();
		Id = id;
		this.name = name;
		this.mailAddress = mailAddress;
		this.password = password;
	}
	
	public Administrator() {
	}

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMailAddress() {
		return mailAddress;
	}
	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Administrator [Id=" + Id + ", name=" + name + ", mailAddress=" + mailAddress + ", password=" + password
				+ "]";
	}
}
