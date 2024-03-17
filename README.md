# UNIverse Server 입니다🤎

## 🖥️ 프로젝트 소개
UNIverse는 인천대학교 내에서 학우들끼리 위치 공유를 할 수 있는 서비스입니다. 
<br>

## 🕰️ 개발 기간
* 24.01.01일 - 진행중

## ⚙️ 개발 환경
- `Java 17`
- `JDK 1.8.0 이상`
- **IDE** : IntelliJ IDEA
- **Framework** : Springboot(3.1.7)
- **Database** : MySQL
- **ORM** : Hibernate (Spring Data JPA 사용)

## 🧑‍🤝‍🧑 맴버구성
<p>
    <a href="https://github.com/M-ung">
      <img src="https://avatars.githubusercontent.com/u/126846468?v=4" width="100">
    </a>
</p>

## 📝 규칙
- `커밋 컨벤션`
    - Feat: 새로운 기능 추가
    - Fix: 버그 수정
    - Docs: 문서 수정
    - Style: 코드 포맷팅, 세미콜론 누락, 코드 변경이 없는 경우
    - Refactor: 코드 리팩토링
    - Test: 테스트 코드, 리팩토링 테스트 코드 추가
    - Chore: 빌드 업무 수정, 패키지 매니저 수정
<br>

- `issue 규칙`
    - 템플릿
        - issue 제목
            - 예시: **feat: 이슈 정리**
        - issue 템플릿
            ```markdown
            ## 📋 이슈 내용
            
            ## ✅ 체크리스트
            
            ## 📚 레퍼런스
            
            ```
<br>

- `branch 규칙`
    - 각자의 깃 닉네임을 딴 branch 명을 사용한다.
    - 예시
        - git checkout -b feature/이슈번호-기능
        - git checkout -b feature/12-UserAPI

<br>

- `commit message 규칙`
    - 종류: 메시지
    - 예시
        - feat: 커밋 내용 - #이슈번호
        - feat: 로그인 구현 - #2 
<br>

- `PR 규칙`
    - PR 템플릿

        ```markdown
        ## 📋 이슈 번호
        
        ## 🛠 구현 사항
        
        ## 📚 기타
        
        ```
        <br>

- `DTO 규칙`
    - 엔티티명 + Response/Request + DTO
    - 예시
        - UserResponseDTO
        - PostRequestDTO

## 📌 API 명세서
https://shade-match-d08.notion.site/09776810d5cd483090f2d30614407a40?pvs=4

## 📌 참고 자료
- 도커 참고 자료
  - https://devfoxstar.github.io/java/springboot-docker-ec2-deploy/
  - https://ppaksang.tistory.com/17
  - https://velog.io/@zvyg1023/CICD-Docker-Github-Action-Spring-Boot
  - https://chan-it-note.tistory.com/121
---
