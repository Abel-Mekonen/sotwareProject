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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.AllArgsConstructor;
import lombok.Data;
import software.project.mainClasses.RequestHelper;
import software.project.mainClasses.SearchTechnician;
import software.project.mainClasses.Payment;
import software.project.mainClasses.Request;
// import software.project.mainClasses.SearchTutor;
import software.project.mainClasses.User;
import software.project.repository.CustomerRepository;
import software.project.repository.PaymentRepository;
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

    @Autowired
    private final PaymentRepository paymentrepo;

    @GetMapping("order/orderdevice")
    public ModelAndView makeOrder(@AuthenticationPrincipal User user,
            @ModelAttribute SearchTechnician searchTechnician) {

        ModelAndView model = new ModelAndView("order");
        model.addObject("ord", true);
        model.addObject("Query", new SearchTechnician());
        return model;
    }

    @PostMapping(path = "/order/ListOfTechnician")
    public ModelAndView searchTechnician(@AuthenticationPrincipal User user,
            @ModelAttribute SearchTechnician searchTechnician) {
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
        List<Request> requestLists = requestRepo.findByCustomer(sendId);

        List<RequestHelper> doneHelpers = new ArrayList<RequestHelper>();
        List<RequestHelper> pendingHelpers = new ArrayList<RequestHelper>();
        List<RequestHelper> declinedHelpers = new ArrayList<RequestHelper>();
        List<RequestHelper> acceptedHelpers = new ArrayList<RequestHelper>();
        List<RequestHelper> rHelpers = new ArrayList<RequestHelper>();

        for (Request requests : requestLists) {
            RequestHelper ReqHelp = new RequestHelper();
            technicianRepository.findById(requests.getTechinician()).ifPresent(tech -> {
                ReqHelp.setTech(tech);
            });
            paymentrepo.findRequest(requests.getId()).ifPresent(
                    payment -> {
                        ReqHelp.setPayment(payment);
                    });
            ;// .ifPresent(payment -> {
             // ReqHelp.setPayment(payment);
             // });

            ReqHelp.setReq(requests);
            ReqHelp.setStatus(requests.getStatus());
            ReqHelp.setUser(user);
            rHelpers.add(ReqHelp);
            if (requests.getStatus().name() == "DONE")
                doneHelpers.add(ReqHelp);
            else if (requests.getStatus().name() == "PENDING")
                pendingHelpers.add(ReqHelp);
            else if (requests.getStatus().name() == "DECLINE")
                declinedHelpers.add(ReqHelp);
            else if (requests.getStatus().name() == "ACCEPTED")
                acceptedHelpers.add(ReqHelp);
        }
        mView.addObject("helpList", rHelpers);
        mView.addObject("done", doneHelpers);
        mView.addObject("pending", pendingHelpers);
        mView.addObject("declined", declinedHelpers);
        mView.addObject("accepted", acceptedHelpers);

        return mView;
    }

    @PostMapping("/delete-request/{id}")
    public String deleteReq(@AuthenticationPrincipal User user, @PathVariable Long id, RedirectAttributes ra) {
        try {
            requestRepo.findById(id).ifPresent(
                    req -> {
                        requestRepo.deleteById(id);
                        ra.addFlashAttribute("success", "order deleted successFully");
                    });
            return "redirect:/order/seeOrderStatus?success";
        } catch (Exception e) {
            ra.addFlashAttribute("error", "internal Error happened during deleting!");
            return "redirect:/order/seeOrderStatus?error";
        }
    }
}
