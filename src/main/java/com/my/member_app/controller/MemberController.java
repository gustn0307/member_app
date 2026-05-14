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
        // '/'로 시작하면 절대 경로(Context Root), 그렇지 않으면 상대 경로(현재 RequestMapping 기준)
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

    @GetMapping("update")
    public String updateForm(@RequestParam("updateId") Long updateId,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        // 1. 선택한 ID를 가져오는지 확인
        log.info("@@@@@@@@@@@@@ updateId = " + updateId);

        // 2. 해당 ID를 검색해서 dto 받아온다.
        MemberDto updateDto = memberService.findById(updateId);
        log.info("updateDto : " + updateDto);

        // 3. updateDto 비어있는지 확인 후 비어있으면 member/view로 보낸다
        if (updateDto==null){
            redirectAttributes.addFlashAttribute("message", "선택한 회원을 찾을 수 없습니다.");
            return "redirect:view";
        }else {
            // 4. 모델에 담아서 updateMember의 Form에 보낸다
            model.addAttribute("dto", updateDto);
            return "updateMember";
        }
    }

    @PostMapping("update")
    public String updateMember(@ModelAttribute("dto") MemberDto dto,
                               RedirectAttributes redirectAttributes){
        // 1. 선택한 DTO를 가져오는지 확인
        log.info("updateMember() updateDto : " + dto);

        // 2. 제대로 가져왔는지 확인 후 수정된 DTO를 DB에 반영(UPDATE)
        memberService.insert(dto);

        // 3. 메시지 보내기
        redirectAttributes.addFlashAttribute("message", "정상적으로 수정되었습니다.");
        return "redirect:view";
    }
}
