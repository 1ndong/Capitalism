package capitalism.DataStructure;

import java.util.LinkedList;

import capitalism.Classes.CBeing;
import capitalism.Classes.Bank.CBAccount;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DBankMember {
	private final CBeing client;
	private final LinkedList<CBAccount> accountList = new LinkedList<>();
}
