package com.vesmer.web.timontey.service.imp;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vesmer.web.timontey.domain.Employee;
import com.vesmer.web.timontey.domain.Order;
import com.vesmer.web.timontey.domain.QuotaMoney;
import com.vesmer.web.timontey.repository.OrderRepository;
import com.vesmer.web.timontey.repository.QuotaMoneyRepository;
import com.vesmer.web.timontey.repository.StaffRepository;
import com.vesmer.web.timontey.service.QuotaMoneyService;

@Service
@Transactional
public class QuotaMoneyServiceImp implements QuotaMoneyService{
	private final QuotaMoneyRepository quotaMoneyRepository;
	private final StaffRepository staffRepository;
	private final OrderRepository orderRepository;
	
	@Autowired
	public QuotaMoneyServiceImp(QuotaMoneyRepository quotaMoneyRepository, StaffRepository staffRepository,
			OrderRepository orderRepository) {
		this.quotaMoneyRepository = quotaMoneyRepository;
		this.staffRepository = staffRepository;
		this.orderRepository = orderRepository;
	}

	@Override
	public List<QuotaMoney> getQuotaMoneysForManager(long managerId) {
		List<QuotaMoney> quotaMoneyList = quotaMoneyRepository.getQuotaMoneysForManager(managerId);
		for(QuotaMoney qMoney: quotaMoneyList) {
			patch(qMoney);
		}
		return quotaMoneyList;
	}
		
	@Override
	public List<QuotaMoney> getQuotaMoneysForOrder(long orderId) {
		List<QuotaMoney> quotaMoneyList = quotaMoneyRepository.getQuotaMoneysForOrder(orderId);
		for(QuotaMoney qMoney: quotaMoneyList) {
			patch(qMoney);
		}
		return quotaMoneyList;
	}
	
	@Override
	public QuotaMoney save(QuotaMoney quotaMoney) {
		long quotaMoneyId = quotaMoneyRepository.save(quotaMoney);
		quotaMoney.setId(quotaMoneyId);
		patch(quotaMoney);
		return quotaMoney;
	}
	
	@Override
	public QuotaMoney update(QuotaMoney quotaMoney) {
		quotaMoneyRepository.update(quotaMoney);
		patch(quotaMoney);
		return quotaMoney;
	}
	
	public void patch(QuotaMoney qMoney) {
		Employee employee = staffRepository.findById(qMoney.getEmployee().getId()).get();
		qMoney.setEmployee(employee);
			
		Order order = orderRepository.findById(qMoney.getOrder().getId()).get();
		Employee manager = staffRepository.findById(order.getManager().getId()).get();
		order.setManager(manager);
		qMoney.setOrder(order);
	}

	@Override
	public void delete(long quotaMoneyId) {
		try {
			quotaMoneyRepository.delete(quotaMoneyId);
		} catch (EmptyResultDataAccessException e) {
			System.err.println("Data not found: " + e.getMessage());
		}
	}
		
	@Override
	public Optional<QuotaMoney> getQuotaMoneyById(long id) {
		Optional<QuotaMoney> opt = quotaMoneyRepository.findQuotaMoneyById(id);

		if(opt.isPresent()) {
			patch(opt.get());
		}
		return opt;
	}
}
