package services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PromptService {

	public String interpret(String command) {
		String res = "";

		if (command.equals("corchuelo")) {
			res = "huye";
		} else if (command.equals("hello")) {
			res = "bye";
		} else if (command.equals("sad")) {
			res = "send nudes";
		} else {
			res = "Command not understood";
		}
		return res;
	}
}
