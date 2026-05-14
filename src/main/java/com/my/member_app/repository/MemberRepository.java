package com.my.member_app.repository;



import com.my.member_app.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    // 기본 CRUD 외에 다른 기능을 사용하고자 할 때 Repository에 추가할 수 있는 요소들
    // 기본 CRUD만 필요하면 그냥 비워두면 됨

    // 1. 쿼리 메서드

    // 2. JPQL

    // 3. native query
    // 이름 검색
    @Query(value = "SELECT * FROM members WHERE name LIKE %:keyword% ORDER BY name", nativeQuery = true)
    public List<Member> searchByName(@Param("keyword") String keyword);

    // 주소 검색
    @Query(value = "SELECT * FROM members WHERE address LIKE %:keyword% ORDER BY address", nativeQuery = true)
    public List<Member> searchByAddress(@Param("keyword") String keyword);
}