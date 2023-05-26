# 🐶 댕댕잇 🐶
팀프로젝트로 진행한  반려간식 쇼핑몰 사이트입니다.
## DEMO : 👉<a href="http://54.180.83.114:8080/DangDangEat/">댕댕잇</a>👈 
배포 서버 및 DB: AWS EC2 / RDS <br>
(**관리자 계정
*ID:* admin |
Pass: 1234 <br>
일반 계정
ID: lzd87271 |
Pass: 1234



![그림3](https://user-images.githubusercontent.com/112055211/230164741-e8a8ea39-f02f-44a8-a098-7fb45d255c56.png)

#️⃣목차
---
[1. 개요](#1️⃣개요)<br>
[2. 개발 환경 및 기술 스택](#2️⃣개발-환경-및-기술-스택)<br>
[3. 담당파트 작업파일 경로](#3️⃣담당파트-작업파일-경로)<br>
[4. ERD 및 구현페이지 설명](#4️⃣erd-및-구현페이지-설명)<br>
  - [4-1.ERD](#erd)<br>
  - [4-2.관리자페이지](#관리자페이지)<br>
  - [4-3.회원-결제 중 쿠폰 적용](#회원-결제-중-쿠폰사용)<br>
  - [4-4.마이페이지](#마이페이지)<br>

<br><br><br>
# 1️⃣개요
----
- 설명: <br>: MVC모델2을 기반으로 스프링 프레임워크를 이용하여 만든 반려간식 쇼핑몰 사이트입니다.<br>
              교육 수료 후 2022.12.12 - 2023.01.13 기간동안 진행한 JSP기반의 프로젝트를 기능 추가 및 스프링 프레임워크 기반으로 전환했습니다.
               
- 개발기간: 2023.02.27 ~ 
- 총 개발 인원: 7명

- 담당파트: 
  - 쿠폰 테이블 설계 
  - 관리자 페이지 쿠폰 기능
  - 마이 페이지 쿠폰
  - 결제 시 쿠폰 적용
  - 메인 페이지 신상품 상품 노출
  - 마이 페이지 - 쿠폰 내역 기능
  - 마이 페이지 - 마이리뷰 보기 기능
  - 사이트 디자인 총괄
  - 로고 제작

 
 
<br><br><br>
# 2️⃣개발 환경 및 기술 스택
----
개발환경
- IDE: STS4
- Server: Apache Tomcat 8.0
- DB Tool: dbeaver, workbench
- Build tool: maven
기술 스택
- BACK-END
  - JAVA(jdk 1.8.0)
  - SPRING FRAMEWORK / MVC 모델2 아키텍처 패턴
  - jdbc
  - MySQL
  - Mybatis
  - jsp
  - AWS EC2, RDS
  
- FRONT-END
  - Bootstrap
  - javascript
  - jQuery
  - Ajax
  - HTML/CSS

<br><br><br>
# 3️⃣담당파트 작업파일 경로
---
<b>Controller</b>
쿠폰
https://github.com/trumpetflor/DangDangEat_Spring/blob/34d5ce36ce95fe530892776c40714e7e219777ae/DangDangEat/src/main/java/com/thisteam/dangdangeat/controller/OrderController.java#L43

https://github.com/trumpetflor/DangDangEat_Spring/blob/34d5ce36ce95fe530892776c40714e7e219777ae/DangDangEat/src/main/java/com/thisteam/dangdangeat/controller/MemberController.java#L144

<b>Service</b>
https://github.com/trumpetflor/DangDangEat_Spring/blob/34d5ce36ce95fe530892776c40714e7e219777ae/DangDangEat/src/main/java/com/thisteam/dangdangeat/service/OrderService.java#L32

<b>Mapper</b>
https://github.com/trumpetflor/DangDangEat_Spring/blob/34d5ce36ce95fe530892776c40714e7e219777ae/DangDangEat/src/main/java/com/thisteam/dangdangeat/mapper/CouponMapper.java#L1

<b>Mapper.xml</b>
https://github.com/trumpetflor/DangDangEat_Spring/blob/main/DangDangEat/src/main/resources/com/thisteam/dangdangeat/mapper/CouponMapper.xml


<br><br><br>
# 4️⃣ERD 및 구현페이지 설명
---
- ### ERD
![그림1](https://github.com/trumpetflor/DangDangEat_Spring/assets/112055211/bdc52fdb-b24c-4ae2-86be-ba34a9999971)


- ### 관리자페이지
<br>
  1-1. 쿠폰 수정
  
![admin_coupon_update](https://user-images.githubusercontent.com/112055211/232452189-89792ec1-120c-4870-b4c1-6605a5525bfa.gif)

  1-2. 쿠폰 삭제
![admin_coupon_delete](https://user-images.githubusercontent.com/112055211/232452793-00c2e278-b5e8-4209-8caa-29640a659e52.gif)

- ### 회원 결제 중 쿠폰사용
 <br>이미지수정중
- ### 마이페이지
이미지수정중
<b> 마이페이지 _쿠폰 </b>
- 현재 사용 가능한 쿠폰 및 쿠폰 사용 내역 확인 기능
- 새로운 쿠폰 등록 기능
![mypage-coupon-register](https://user-images.githubusercontent.com/112055211/232450466-dfe3bae4-0c1f-4ae0-8133-d49d20181587.gif)
