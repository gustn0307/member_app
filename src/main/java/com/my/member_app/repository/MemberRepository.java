package com.my.member_app.repository;



import com.my.member_app.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Repository는 항상 반드시 intetface로 만든다.
// JpaRepository<엔티티, PK타입>을 상속하면 기본 CRUD가 자동으로 제공된다.
// JpaRepository 기본 제공 메서드 일부
// save(엔티티)     :  저장/수정
// findAll()            : 전체 조회
// findById(id)      : ID로 한 건 조회, Optional<T> 반환
// deleteById(id)  : ID로 한 건 삭제
// count()           : 전체 개수 반환
// existsById(id)  : 존재 여부 확인
@Repository // 적어도 되고, 안 적어도 된다.
public interface MemberRepository extends JpaRepository<Member, Long> {
}
