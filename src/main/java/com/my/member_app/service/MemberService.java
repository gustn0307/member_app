package com.my.member_app.service;

import com.my.member_app.dto.MemberDto;
import com.my.member_app.entity.Member;
import com.my.member_app.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service // 생성자 주입 방식 등을 자동으로 해주어 하나의 객체로만 스프링이 관리
@RequiredArgsConstructor // 각각의 필드를 가지는 생성자를 생성
public class MemberService {
    // 의존성 주입 : 필요한 컴포넌트를 불러오는 작업
    // 1. 첫 번째 주입 방법(@Autowired)
//    @Autowired // 스프링이 자동으로 엮어준다.(생성자 주입방법), 실무에서는 잘 사용하지 않는다.
//    MemberRepository memberRepository;

    // 2. 두 번째 주입 방법(생성자 주입 방법), 스프링에서 권장하는 방식
//    private final MemberRepository memberRepository;
//
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    // 3. 세 번째 주입 방법(@RequiredArgsConstructor)
    // -> 두 번째 주입 방법의 생성자를 자동으로 생성해줌
    private final MemberRepository memberRepository;

    public List<MemberDto> findAll() {
        List<Member> memberList = memberRepository.findAll(); // Repository에서 필요한 정보를 가져온다.

        // memberList의 Member Entity들을 각각 MemberDto로 변환해 memberDtoList에 담는다.

        // 1. for-each 방식으로 수행
        List<MemberDto> memberDtoList = new ArrayList<>(); // 변환한 DTO를 담을 리스트
//        for (Member member: memberList){
//            memberDtoList.add(MemberDto.toDto(member)); // static 메서드이므로 클래스이름으로 접근
//        }
//        return memberDtoList;

        // 2. stream, 람다 표현식('::')을 이용해 수행(Java 8부터 사용 가능)
        //  '::' : 메서드 레퍼런스(Method Reference)
        // => 람다 표현식이 단 하나의 메서드만을 호출할 때, 불필요한 매개변수를 생략하고
        //       기존 메서드를 직접 참조하여 코드를 더 간결하고 가독성있게 만드는 기능
        //       람다식 (a, b) -> Obj.method(a, b)를 Obj::method 형태로 쓴다.
        return memberList
                .stream()
                .map(MemberDto::toDto)
                .toList();

    }

    public void insert(MemberDto dto) {
        // DTO -> Member Entity 변환해서 DB에 저장
        // memberRepository.save()의 인자는 Entity
        memberRepository.save(MemberDto.toEntity(dto));
    }

    public void delete(Long deleteId) {
        memberRepository.deleteById(deleteId);
    }

    public MemberDto findById(Long updateId) {
        Optional<Member> member = memberRepository.findById(updateId);

        if (member.isPresent()) {
            return MemberDto.toDto(member.get());
        } else {
            return null;
        }
    }

    // 검색
    // type : 이름 or 주소, keyword : 검색어
    public List<MemberDto> search(String type, String keyword) {
        List<Member> searchList = new ArrayList<>();
        switch (type) {
            case "name":
                searchList = memberRepository.searchByName(keyword);
                break;
            case "address":
                searchList = memberRepository.searchByAddress(keyword);
                break;
            default: // type 선택하지 않은 경우
                searchList = memberRepository.findAll();
        }
        return searchList
                .stream()
                .map(MemberDto::toDto)
                .toList();
    }
}
