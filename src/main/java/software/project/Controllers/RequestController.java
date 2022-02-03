package software.project.Controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.AllArgsConstructor;
import lombok.Data;
import software.project.mainClasses.RequestHelper;
import software.project.mainClasses.SearchTechnician;
import software.project.mainClasses.Request;
// import software.project.mainClasses.SearchTutor;
import software.project.mainClasses.User;
import software.project.repository.CustomerRepository;
import software.project.repository.RequestRepo;
import software.project.repository.TechnicianRepository;
import software.project.service.OrderService;
@Data
@AllArgsConstructor
@Controller
public class RequestController {
    @Autowired
    private final OrderService orderService;
    @Autowired
    private final CustomerRepository customerRepository;
    @Autowired
    private final Request request;
    @Autowired
    private final RequestRepo requestRepo;
    @Autowired
    private final TechnicianRepository technicianRepository;


    @GetMapping("order/orderdevice")
    public ModelAndView makeOrder(@AuthenticationPrincipal User user, @ModelAttribute SearchTechnician searchTechnician ){

        ModelAndView model = new ModelAndView("order");
        model.addObject("Query", new SearchTechnician());
        return model;
    }
    @PostMapping(path = "/order/ListOfTechnician")
    public ModelAndView searchTechnician(@AuthenticationPrincipal User user, @ModelAttribute SearchTechnician searchTechnician){
        return orderService.search(user, searchTechnician);
    }

    
    @PostMapping("/send-request/{id}")
    public String sendRequest(@PathVariable String id, @AuthenticationPrincipal User user) {
        return orderService.sendRequest(id, user);
    }
    
    @GetMapping("/order/requestSuccess")
    public String requestSuccess() {
        return "successTeller";
    }

    @GetMapping("/order/seeOrderStatus")
    public ModelAndView seeOrder(@AuthenticationPrincipal User user) {
        ModelAndView mView = new ModelAndView("seeOrder");

        long sendId = user.getCustomerProfile().getId();
        List<Request>  requestLists = requestRepo.findByCustomer(sendId);
        
        List<RequestHelper> rHelpers = new ArrayList<RequestHelper>();
        

        for (Request requests : requestLists) { 
            RequestHelper ReqHelp = new RequestHelper();
            technicianRepository.findById(requests.getTechinician()).ifPresent(tech -> {
                ReqHelp.setTech(tech);        
            });
            
            ReqHelp.setReq(requests);
            ReqHelp.setStatus(requests.getStatus());
            ReqHelp.setUser(user);
            rHelpers.add(ReqHelp);
        }
        mView.addObject("helpList", rHelpers);

        return mView;
    }


    
}
