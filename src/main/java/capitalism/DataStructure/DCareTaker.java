package capitalism.DataStructure;

import java.util.LinkedList;

import lombok.Getter;

@Getter
public class DCareTaker {
	private LinkedList<DHistory> list = new LinkedList<>();

	public void addMemento(DHistory history) {
		list.add(history);
	}
}
