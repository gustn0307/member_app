package com.my.member_app.dto;

import com.my.member_app.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO(Data Transfer Object)
// Entity를 View에 노출하지 않고 DTO를 통해 데이터를 전달한다.
// Entity와 DTO를 분리하는 이유
// 1. Entity는 DB 테이블과 직접 연결되어 있어 외부에 노출하면 위험
// 2. 화면에 필요한 데이터만 골라서 전달 가능
//     -> Entity의 모든 필드를 가져오지 않고 필요한 필드만 가져와 감추고 싶은 필드를 감출 수 있다.
// 3. 입력값 검증을 DTO에서 처리 가능
// => Front에서 입력받은 값을 받아 가공하기 위해 필요한 이유는 아래와 같다.
//       Entity는 변경하면 DB 테이블이 변경되므로
//       DTO 클래스는 가공하는 과정에 필요한 일종의 temp 클래스라고 보면 된다.
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {
    private Long id;
    private String name;
    private int age;
    private String address;

    // DTO -> Entitiy 변환
    // 자주 사용되므로 어디서든 사용할 수 있도록 static으로 선언함
    public static Member toEntity(MemberDto dto) {
        // 빈 Member 생성 후 필드 하나씩 넣어주고 리턴
        Member member = new Member();
        member.setId(dto.getId());
        member.setName(dto.getName());
        member.setAge(dto.getAge());
        member.setAddress(dto.getAddress());
        return member;
    }

    // Entity -> DTO 변환
    // 자주 사용되므로 어디서든 사용할 수 있도록 static으로 선언함
    public static MemberDto toDto(Member member) {
        return new MemberDto(
                member.getId(),
                member.getName(),
                member.getAge(),
                member.getAddress()
                );
    }

}
