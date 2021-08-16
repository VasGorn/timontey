package controllers.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import domain.Expenses;

@RestController
@RequestMapping(path = "/rest/expenses", produces = "application/json")
public class ExpensesRest {
	@Autowired
	private ExpensesService expensesService;
	
	@GetMapping("/all")
	public List<Expenses> expensesGetAll(){
		return expensesService.getAll();
	}

}
