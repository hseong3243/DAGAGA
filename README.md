# DAGAGA
+ 교내 중고 도서 거래 활성화를 위한 중고 거래 웹 어플리케이션
![title](https://user-images.githubusercontent.com/48748265/230789662-d72f9129-b954-47b3-8fed-a5b5782f8aaa.png)

## 주요 기능
+ 중고 물품 판매 게시판, 등록된 물품 리스트 검색을 제공
![3번](https://user-images.githubusercontent.com/48748265/230789776-7bc97066-e9be-4855-b142-3012c96df8c9.png)

## 팀 구성
+ Backend 1명
+ Web publisher 2명

## 개발 스택
<h3>Backend</h3>
+ Spring Boot
+ Spring Data JPA
+ Thymeleaf

## 발생문제 및 해결방법
1. 프론트엔드 담당 팀원의 이탈
+ 기존 프론트엔드 1명, 웹 퍼블리셔 2명, 백엔드 1명으로 구성된 팀에서 프론트엔드 팀원의 갑작스런 이탈로 인해 개발 계획의 변경이 필요하였습니다.
+ 학교의 자율 프로젝트 강의의 일부로 진행된 프로젝트로써 이미 팀 빌딩이 끝나고 3주 가량이 지난 상태였기에 새로운 팀원을 영입할 수 없는 상태였습니다.
웹 퍼블리셔를 담당하는 인원은 Javascript에 대한 지식이 없고 새롭게 배우는 것도 여의치 않은 상황이었습니다.
이로인해 템플릿 엔진인 Thymeleaf를 사용하여 서버 사이드 렌더링으로 구현하는 것으로 방향을 전환하였습니다.
