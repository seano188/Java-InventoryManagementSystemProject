 package sg.edu.iss.team8ca.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import sg.edu.iss.team8ca.model.Inventory;
import sg.edu.iss.team8ca.model.TransHistory;
import sg.edu.iss.team8ca.model.TransType;
import sg.edu.iss.team8ca.service.TransHistoryImpl;

@Controller
@RequestMapping("/report")
public class UsageTransReportController {
	@Autowired
	private TransHistoryImpl thservice; 
	
	@Autowired
	public void setUsageReport(TransHistoryImpl usageReport) {
		this.thservice = usageReport;
	}
	
	@RequestMapping(value ="/form", method = RequestMethod.GET)
	public ModelAndView reportform(){
        ModelAndView modelAndView = new ModelAndView();
        
        ArrayList<String>typeList = new ArrayList<String>();
        typeList.add("All transactions");
        for (TransType type : TransType.values()) { 
            typeList.add(type.name()); 
        }

        modelAndView.addObject("transType", "All transactions");
        modelAndView.addObject("typeList", typeList);
        modelAndView.setViewName("usage-trans-form");
        return modelAndView;
    }
	
	
	@RequestMapping(value ="/generate", method=RequestMethod.POST)
	public ModelAndView generateReport(WebRequest request){
		ModelAndView modelAndView = new ModelAndView();
		String id= request.getParameter("productId");
		long productId = Long.parseLong(id);
		Inventory product = thservice.findInvById(productId);
		
		ArrayList<String>typeList = new ArrayList<String>();
	        typeList.add("All transactions");
	        for (TransType type : TransType.values()) { 
	            typeList.add(type.name()); 
	        }
	
	    modelAndView.addObject("typeList", typeList);
	    modelAndView.addObject("transType", request.getParameter("transType"));
		//if product not found
		if(product == null) 
		{
			modelAndView.addObject("error", "invalid");
			modelAndView.setViewName("usage-trans-form");
	        return modelAndView;
		}	
		//if no date range provided
		if(request.getParameter("startDate").isBlank()) 
		{
			if(request.getParameter("transType").equals("All transactions")) 
			{
				List<TransHistory> alltransHistory = thservice.listTransHisForId(productId);
				
				if(alltransHistory.size()== 0) 
				{
					modelAndView.addObject("error", "invalid-date");
					modelAndView.setViewName("usage-trans-form");
			        return modelAndView;
				}
				modelAndView.addObject("transHistory", alltransHistory);
				modelAndView.setViewName("usage-trans-form");
				modelAndView.addObject("product", product);
		        return modelAndView;		
			}else 
			{
				List<TransHistory> transHistoryByType = thservice.listTransHisForIdType(productId,request.getParameter("transType"));
				if(transHistoryByType.size()== 0) 
				{
					modelAndView.addObject("error", "invalid-type");
					modelAndView.setViewName("usage-trans-form");
			        return modelAndView;
				}
				modelAndView.addObject("transHistory", transHistoryByType);
				modelAndView.setViewName("usage-trans-form");
				modelAndView.addObject("product", product);
		        return modelAndView;
			}
				
		}
		//if date range provided
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		LocalDate start = LocalDate.parse(startDate); 
		LocalDate end = LocalDate.parse(endDate); 
		
		if(request.getParameter("transType").equals("All transactions")) 
		{
		
			List<TransHistory> transHistory = thservice.listTransHisForDate(productId, start, end);
			//if no inventory found for selected dates
			if(transHistory.size()== 0) 
			{
				modelAndView.addObject("error", "invalid-date");
				modelAndView.setViewName("usage-trans-form");
		        return modelAndView;
			}
			
			modelAndView.addObject("transHistory", transHistory);
			modelAndView.addObject("product", product);
			modelAndView.addObject("start", start);
			modelAndView.addObject("end", end);	
			modelAndView.setViewName("usage-trans-form");
	        return modelAndView;
		}else{
			List<TransHistory> transHistoryByType = thservice.listTransHisForDateType(productId, start, end,request.getParameter("transType"));
			//if no inventory found for selected dates and type
			if(transHistoryByType.size()== 0) 
			{
				modelAndView.addObject("error", "invalid-type");
				modelAndView.setViewName("usage-trans-form");
		        return modelAndView;
			}
			
			modelAndView.addObject("transHistory", transHistoryByType);
			modelAndView.addObject("product", product);
			modelAndView.addObject("start", start);
			modelAndView.addObject("end", end);	
			modelAndView.setViewName("usage-trans-form");
	        return modelAndView;
			
		}
    }
	
	
		

}
