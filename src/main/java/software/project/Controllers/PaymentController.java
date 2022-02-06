package software.project.Controllers;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import software.project.mainClasses.Payment;
import software.project.mainClasses.Request;
import software.project.mainClasses.Technician;
import software.project.repository.PaymentRepository;
import software.project.repository.RequestRepo;
import software.project.repository.TechnicianRepository;
import software.project.service.LoggedUser;

@Controller
public class PaymentController {

    @Autowired
    private PaymentRepository paymentRepo;

    @Autowired
    private Request request;

    @Autowired
    private RequestRepo requestRepo;

    @Autowired
    private TechnicianRepository techRepo;

    @Autowired
    private LoggedUser loggedUser;

    @GetMapping("/payments")
    public String payment(Model model) {
        List<Payment> payments = paymentRepo.findByPayerIdOrderBySentDateDesc(loggedUser.get_User().getId());
        model.addAttribute("payment", payments);
        return "payments";
    }

    @GetMapping("/makepayment/{id}")
    public String makePayment(@PathVariable(name = "id") long id, @ModelAttribute("payment") Payment payment,
            Model model, RedirectAttributes ra) {
        Optional<Request> req = requestRepo.findById(id);
        req.ifPresent(
                request -> {
                    model.addAttribute("request", request);
                    model.addAttribute("id", id);
                });
        if (req.isEmpty()) {
            ra.addFlashAttribute("error", "please select the order to pay for");
            return "redirect:/order/seeOrderStatus?error";
        }
        paymentRepo.findRequest(id).ifPresent(
                pay -> {
                    model.addAttribute("payment", pay);
                });

        return "payment";
    }

    @PostMapping("/payment/{id}")
    public String submitFeedback(@PathVariable("id") long id, @ModelAttribute("payment") Payment payment,
            RedirectAttributes ra) {
        if (loggedUser.get_User() != null) {
            payment.setPayer(loggedUser.get_User());
        } else {
            return "redirect:/login";
        }
        try {
            Optional<software.project.mainClasses.Request> req = requestRepo.findById(id);
            req.ifPresent(
                    request -> {
                        payment.setRequest(request);
                        techRepo.findById(request.getTechinician()).ifPresent(
                                tech -> {
                                    payment.setTech(tech);
                                });
                    });
            if (req.isEmpty()) {
                ra.addFlashAttribute("error", "please select the order to pay for" + id);
                return "redirect:/order/seeOrderStatus?error";
            }
            paymentRepo.save(payment);
            ra.addFlashAttribute("success", "Payment successfully!");
            return "redirect:/payments?success";
        } catch (Exception e) {
            ra.addFlashAttribute("error", "error, could not process Payment information. please try again.");
            return "redirect:/order/seeOrderStatus?error";
        }
    }
}
