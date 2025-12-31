package com.ohgiraffers.mapping.section01.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity(name = "entityMember")
@Table(name = "tbl_member")
@TableGenerator( // 테이블을 이용해서 기본키(pk) 생성
    name = "member_seq_tbl_generator",
    table = "tbl_my_sequences",
    pkColumnValue = "my_seq_member_no"
)
@Access(AccessType.FIELD) // JPA Entity의 모든 필드에 접근하는 방식을 FIELD로 지정(직접 접근) -> 기본 값
public class Member {

  @Id
  @Column(name = "member_no")
//  @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto Increment
  @GeneratedValue(
      strategy = GenerationType.TABLE,
      generator = "member_seq_tbl_generator"
  )
  private int memberNo;

  @Column(
      name = "member_id",
      unique = true,
      nullable = false,
      columnDefinition = "varchar(10)"
  )
  private String memberId;

  @Column(name = "member_pwd", nullable = false)
  private String memberPwd;

  @Column(name = "member_name")
  private String memberName;

  @Transient
  @Column(name = "phone")
  private String phone;

  @Column(name = "address", length = 900)
  private String address;

  @Column(name = "enroll_date")
  private LocalDateTime enrollDate;

  @Column(name = "member_role")
  @Enumerated(EnumType.STRING)
  private MemberRole memberRole;

  @Column(name = "status", columnDefinition = "char(1) default 'Y'")
  private String status;

  // Entity 클래스는 기본 생성자 필수!
  protected Member() {
  }

  public Member(
      String memberId, String memberPwd, String memberName,
      String phone, String address, LocalDateTime enrollDate,
      MemberRole memberRole, String status
  ) {
    this.memberId = memberId;
    this.memberPwd = memberPwd;
    this.memberName = memberName;
    this.phone = phone;
    this.address = address;
    this.enrollDate = enrollDate;
    this.memberRole = memberRole;
    this.status = status;
  }


  // 해당 값(memberName)의 접근 방식만 getter로 변경
  @Access(AccessType.PROPERTY)
  public String getMemberName() {
    System.out.println("getMemberName()을 이용한 Access 확인");

    return memberName + "님";
  }

  public void setMemberName(String memberName) {
    this.memberName = memberName;
  }
}