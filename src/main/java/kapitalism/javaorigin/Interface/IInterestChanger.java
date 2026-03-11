package kapitalism.javaorigin.Interface;

public interface IInterestChanger {
	void addAccount(IInterestRate account);
	void interestChange();
}

/*
 * central 기준금리 Ichanger
 * bank * n Irate 받아서 각각의 계정으로 쏴주는 Ichanger
 * account Irate
 */
