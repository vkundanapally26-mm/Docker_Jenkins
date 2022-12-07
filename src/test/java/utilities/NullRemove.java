package utilities;

import java.util.function.Predicate;

public class NullRemove implements Predicate<Object> {

	@Override
	public boolean test(Object obj) {
		return obj == null;
	}

}