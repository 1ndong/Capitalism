package capitalism.Property;

import capitalism.DataStructure.DTime;
import lombok.Getter;
import lombok.Setter;

@Getter
public class PBasicData {
	protected DTime birth = new DTime(0, 0, 0);
	protected String name = "";
	@Setter
	protected int ID = 0;//사업자 등록번호 or 주민번호
	protected PAsset propertyAsset = new PAsset();

	public PBasicData(DTime birth, String name) {
		this.birth = birth;
		this.name = name;
	}
}
