package kapitalism.javaorigin.Classes;

public class CBelong {
	/*
	todo
	interface로 빼던지 cbeing랑 상하관계 바꾸던지
	* */
	protected CCountry myCountry;
	
	public CBelong(CCountry country)
	{
		myCountry = country;
	}

	public CCountry getMyCountry() {
		return myCountry;
	}
}
