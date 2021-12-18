package com.vesmer.web.timontey.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vesmer.web.timontey.domain.QuotaMoney;
import com.vesmer.web.timontey.domain.QuotaTime;
import com.vesmer.web.timontey.repository.OrderRepository;
import com.vesmer.web.timontey.repository.QuotaTimeRepository;
import com.vesmer.web.timontey.repository.StaffRepository;
import com.vesmer.web.timontey.repository.WorkTypeRepository;
import com.vesmer.web.timontey.service.QuotaMoneyService;
import com.vesmer.web.timontey.service.QuotaTimeService;

@Service
@Transactional
public class QuotaTimeServiceImp implements QuotaTimeService {
	@Autowired
	private QuotaTimeRepository quotaTimeRepository;
	
	@Autowired
	private StaffRepository staffRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private WorkTypeRepository workTypeRepository;
	

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

}
