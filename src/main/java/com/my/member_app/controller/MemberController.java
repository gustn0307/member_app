package com.my.member_app.controller;

import com.my.member_app.dto.MemberDto;
import com.my.member_app.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("member")
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final MemberService memberService;

    @GetMapping("view")
    public String showAllMember(Model model) {
        List<MemberDto> memberDtoList = memberService.findAll();

        model.addAttribute("title", "회원정보");
        model.addAttribute("lists", memberDtoList);

        return "showMember";
    }

    @GetMapping("insertForm")
    public String insertForm(Model model){
        // 모델에 빈 DTO를 만들어서 보낸다.
        model.addAttribute("dto", new MemberDto());

        return "insertMember";
    }


    @PostMapping("insert")
    public String insertMember(@ModelAttribute("dto") MemberDto dto,
                               RedirectAttributes redirectAttributes) {
        // RedirectAttributes : 리다이렉트용 1회성 모델에 담아 보내는 기능
        log.info("result : " + dto);

        memberService.insert(dto);

        // 성공 메시지를 RedirectAttributes에 담아 보낸다.
        redirectAttributes.addFlashAttribute("message", "등록이 완료되었습니다.");

        // "redirect:view" : "view"를 GET 방식으로 다시 호출
        // RequestMapping("member") 로 인해 "redirect:view" 또는 "redirect:/member/view"로 적는다
        // RequestMapping()의 인자를 뺀 값만 적든지 아예 전체 경로를 적어야 함
        return "redirect:view";
    }

    @PostMapping("delete")
    public String delete(@RequestParam("deleteId") Long deleteId,
                         RedirectAttributes redirectAttributes) {

        log.info("@@@@@@@@@@@@@ deleteId = " + deleteId);

        memberService.delete(deleteId);

        redirectAttributes.addFlashAttribute("message", "정상적으로 삭제되었습니다.");
        return "redirect:view";
    }
}
