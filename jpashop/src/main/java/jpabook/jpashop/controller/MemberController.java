package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("new")
    public String createForm(Model model){
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("new")
    public String create(@Valid MemberForm form, BindingResult result){
        if(result.hasErrors()) return "members/createMemberForm";
        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());
        memberService.join(Member.JOIN(form.getName(), address));
        return "redirect:/";
    }

    @GetMapping("")
    public String list(Model model){
        List<Member> members = memberService.fineMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }


}
