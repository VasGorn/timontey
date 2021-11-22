package com.vesmer.web.timontey.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	@Autowired
	private QuotaMoneyRepository quotaMoneyRepository;
	
	@Autowired
	private StaffRepository staffRepository;
	
	@Autowired
	private OrderRepository orderRepository;

	@Override
	public List<QuotaMoney> getQuotaMoneysForManager(long managerId) {
		List<QuotaMoney> quotaMoneyList = quotaMoneyRepository.getQuotaMoneysForManager(managerId);
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

}
