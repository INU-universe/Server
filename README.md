# UNIverse Server 입니다🤎


## 🖥️ 프로젝트 소개
MEGA BOX를 참고하여 만든 영화 예매 사이트입니다.
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
        - git checkout -b feature/12-User-API

<br>

- `commit message 규칙`
    - 종류: 메시지
    - 예시
        - feat: 회원 관리 API 엔티티 구현
        - fix: 회원 조회 서비스 에러 수정 
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
- 회원 API (/api/user)
  - 로그인 POST (/login)
  - 회원 가입 POST (/join)
  - 회원 탈퇴 POST (/delete)
  - 회원 수정 POST (/update)
- 위치 API (/api/location)
  - 위치 업데이트 POST (/update)
  - 단일 위치 조회 GET (/findOne)
  - 친구 위치 전체 조회 GET (/notFavorite/findAll)
  - 친한 친구 위치 전체 조회 GET (/favorite/findAll)
- 채팅방 API (/api/chatRoom)
  - 채팅방 조회 GET (/findAll)
  - 채팅방 생성 POST (/create)
  - 채팅방 삭제 POST  (/delete/{chatRoomId})
- 친구 요청 API (/api/friendRequest)
  - 친구 요청 토글 POST (/toggle/{toUserId})
  - 친구 요청 수락 POST (/accept/{toUserId})
  - 친구 요청 거절 POST (/reject/{toUserId})
  - 친구 요청 조회 GET (/findAll)
- 친구 API (/api/friend)
  - 친구 조회 GET (/findAll)
  - 친구 삭제 POST (/delete/{toUserId})
  - 친한 친구 토글 POST (/toggle/{toUserId})

---
