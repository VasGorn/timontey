package com.vesmer.web.timontey.service.imp;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vesmer.web.timontey.domain.Employee;
import com.vesmer.web.timontey.domain.Order;
import com.vesmer.web.timontey.domain.QuotaMoney;
import com.vesmer.web.timontey.domain.QuotaTime;
import com.vesmer.web.timontey.domain.WorkType;
import com.vesmer.web.timontey.domain.WorkTypeHours;
import com.vesmer.web.timontey.repository.OrderRepository;
import com.vesmer.web.timontey.repository.QuotaTimeRepository;
import com.vesmer.web.timontey.repository.StaffRepository;
import com.vesmer.web.timontey.repository.WorkTypeRepository;
import com.vesmer.web.timontey.service.QuotaMoneyService;
import com.vesmer.web.timontey.service.QuotaTimeService;

@Service
@Transactional
public class QuotaTimeServiceImp implements QuotaTimeService {
	private final QuotaTimeRepository quotaTimeRepository;
	private final StaffRepository staffRepository;
	private final OrderRepository orderRepository;
	private final WorkTypeRepository workTypeRepository;
	
	@Autowired
	public QuotaTimeServiceImp(QuotaTimeRepository quotaTimeRepository, StaffRepository staffRepository,
			OrderRepository orderRepository, WorkTypeRepository workTypeRepository) {
		this.quotaTimeRepository = quotaTimeRepository;
		this.staffRepository = staffRepository;
		this.orderRepository = orderRepository;
		this.workTypeRepository = workTypeRepository;
	}

	@Override
	public List<QuotaTime> getQuotaTimeListForOrder(long orderId, short numMonth, 
			short year) {
		List<QuotaTime> quotaTimeList = 
				quotaTimeRepository.getQuotaTimeListForOrder(orderId, numMonth, year);
		
		for(QuotaTime quotaTime: quotaTimeList) {
			patchQuotaTime(quotaTime);
		}
		
		return quotaTimeList;
	}
		
	@Override
	public List<QuotaTime> getQuotaTimeListForEmployee(long employeeId, short numMonth, short year) {
		List<QuotaTime> quotaTimeList = 
				quotaTimeRepository.getQuotaTimeListForEmployee(employeeId, numMonth, year);
		
		for(QuotaTime quotaTime: quotaTimeList) {
			patchQuotaTime(quotaTime);
		}
		
		Iterator<QuotaTime> iterator = quotaTimeList.iterator();
		while(iterator.hasNext()) {
			QuotaTime quotaTime = iterator.next();
			if(quotaTime.getWorkTypeHours().size() < 1) {
				iterator.remove();
			}
		}
		
		return quotaTimeList;
	}
		
	@Override
	public List<QuotaTime> getQuotaTimeListForManager(long managerId, short numMonth, short year) {
		List<QuotaTime> quotaTimeList = 
				quotaTimeRepository.getQuotaTimeListForManager(managerId, numMonth, year);
		
		for(QuotaTime quotaTime: quotaTimeList) {
			patchQuotaTime(quotaTime);
		}
		
		return quotaTimeList;
	}
	
	@Override
	public QuotaTime save(QuotaTime quotaTime) {
		quotaTimeRepository.save(quotaTime);
		patchQuotaTime(quotaTime);
		return quotaTime;
	}
	
	@Override
	public QuotaTime update(QuotaTime quotaTime) {
		quotaTimeRepository.update(quotaTime);
		patchQuotaTime(quotaTime);
		return quotaTime;
	}
	
	@Override
	public void delete(long workHoursId) {
		try {
			quotaTimeRepository.delete(workHoursId);
		} catch (EmptyResultDataAccessException e) {
			System.out.println("Data not found" + e.toString());
		}
	}

	private void patchQuotaTime(QuotaTime quotaTime) {
		Employee employee = 
				staffRepository.findById(quotaTime.getEmployee().getId()).get();
		quotaTime.setEmployee(employee);
			
		Order order = orderRepository.findById(quotaTime.getOrder().getId()).get();
		Employee manager = 
				staffRepository.findById(order.getManager().getId()).get();
		order.setManager(manager);
		quotaTime.setOrder(order);

		List<WorkTypeHours> workHoursList = quotaTime.getWorkTypeHours();
		for(WorkTypeHours workHours: workHoursList) {
			WorkType workType = workTypeRepository.findById(
					workHours.getWorkType().getId()).get();
			workHours.setWorkType(workType);
		}
	}
}
