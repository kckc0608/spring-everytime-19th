# spring-everytime-19th
CEOS 19th BE study - everytime clone coding

## 에브리타임 서비스 설명 & ERD 의도 설명
![img.png](readme_img/img_whole.png)
에브리타임은 대학생 익명 커뮤니티 사이트이다.

### 회원
먼저 서비스를 이용할 회원 엔티티를 정의하였다.   

<img src="readme_img/img.png" width="400"/>


에브리타임 마이페이지를 참고하여 회원 ID, PW, 닉네임, 이름, 이메일, 가입일과 같은 기본 유저 정보에 학과, 학번, 재학생 인증 여부 정보를 추가적으로 입력받도록 설계하였다.

### 게시판 / 게시글
<img src="readme_img/img2.png" width="600"/>
다음은 유저가 작성하는 게시글과 게시글이 올라가는 게시판에 대한 ERD이다.   

#### 게시판 - 유저
게시판은 관리하는 유저가 한명만 존재할 수 있으므로 유저와 1:1 관계이다.   
관리자는 변경될 수 있기 때문에 one and only one 대신 one and one 으로 설정하였다.   

#### 게시글 - 유저
게시글은 한명의 유저가 여러 게시글을 작성할 수 있지만, 하나의 게시글에는 한명의 작성자만 존재한다.
따라서 유저와 게시글의 관계는 1:N 관계이다.

에브리타임은 익명 커뮤니티이므로, 작성자의 익명 설정 여부를 저장하는 필드도 추가하였다.   
에브리타임에는 질문글 옵션이 있으나, 해당 옵션은 클론하지 않았다.

#### 게시판 - 게시글
하나의 게시판에는 게시글이 없을 수도 있고, 여러개 있을 수도 있다.   
하나의 게시글은 오직 하나의 게시판에만 속하므로 1:N 관계이다.

#### 게시글 - 첨부 사진
<img src="readme_img/img3.png" width="600"/>
게시글을 작성할 때는 사진을 첨부할 수 있다.   
이때 하나의 게시글에는 사진이 없을 수도 있고, 여러개 있을 수도 있는 1:N 관계이다.   
사진을 직접 저장하는 것은 데이터베이스의 용량을 많이 차지하므로 서버에 있는 사진의 경로를 대신 저장하도록 하였다.   

#### 게시글 - 좋아요
<img src="readme_img/img4.png" width="600"/>
게시글 하나에는 여러개의 좋아요가 달릴 수 있지만, 하나의 좋아요는 하나의 게시글에 대한 좋아요를 나타낸다.   
따라서 게시글과 좋아요의 관계는 1:N 관계이다.

#### 유저 - 좋아요
한명의 유저는 하나의 게시글에 좋아요를 한번만 누를 수 있다.     
이를 파악하려면 좋아요를 누른 유저의 정보도 같이 저장해야 한다.     
한명의 유저는 여러 게시글에 좋아요를 누를 수 있지만, 좋아요 하나는 한명의 유저가 생성한 좋아요이므로 1:N 관계이다.   

### 댓글
<img src="readme_img/img5.png" width="600"/>
댓글 역시 익명으로 작성이 가능하므로, 댓글의 익명성 여부를 같이 저장하도록 설계하였다.

#### 게시글 - 댓글
게시글 하나에는 댓글이 없거나 여러개 달릴 수 있다.   
하나의 댓글은 하나의 게시글에 대한 댓글이다.   
따라서 게시글과 댓글 사이의 관계는 1:N 관계이다.

#### 유저 - 댓글
한명의 유저는 여러개의 댓글을 작성할 수 있다.   
하나의 댓글은 한명의 유저가 작성하였다.   
따라서 유저와 댓글 사이의 관계는 1:N 관계이다.

#### 댓글 - 대댓글
하나의 댓글에는 대댓글이 없거나 여러개의 대댓글이 달릴 수 있다.
하나의 대댓글은 대상으로 하는 댓글이 하나이다.   
따라서 댓글과 대댓글 사이의 관계는 1:N 이다.   

게시글에 작성한 댓글은 대상으로 하는 댓글이 없으므로 부모댓글 ID는 NULL 값을 허용하였다.

#### 댓글 - 좋아요
<img src="readme_img/img6.png" width="600"/>
하나의 댓글에는 어러개의 좋아요가 달릴 수 있다.   
하나의 좋아요는 하나의 댓글에 대한 좋아요이다.   
따라서 댓글과 좋아요 사이의 관계는 1:N 이다.

#### 유저 - 좋아요
한명의 유저는 여러 댓글에 대해 좋아요를 누를 수 있다.   
좋아요 하나는 한명의 유저에 의해서 생성된다.   
따라서 유저와 좋아요 사이의 관계는 1:N 이다.

#### 좋아요 모델링 특이사항
좋아요는 댓글 좋아요와 게시글 좋아요가 좋아요의 대상만 다르고 나머지 기능이 동일하다.    
<img src="readme_img/img8.png" width="400"/>   
따라서 Like 라는 추상클래스를 상속받아서 PostLike, CommentLike 를 구현하도록 하였다.   
<img src="readme_img/img9.png" width="400"/>   
실제 DB에는 likes 라는 테이블 하나만 존재하고, 이 테이블에 댓글 좋아요와 게시글 좋아요 데이터가 모두 저장된다. 

### 쪽지
<img src="readme_img/img7.png" width="600"/>
쪽지는 유저와 유저 사이 1:1로 진행되며, 쪽지를 주고받는 공간을 '쪽지함'으로 부른다.

#### 유저 - 쪽지함
에브리타임의 쪽지는 항상 1:1로 진행되는 점을 고려하여 설계하였다.   
쪽지함에는 해당 쪽지함에서 어떤 유저와 쪽지를 주고 받는지 참여한 2명의 유저를 기록하도록 하였다.   
이때 회원1은 처음으로 쪽지를 보내는 유저, 회원2는 처음으로 쪽지를 받는 유저로 정의하였고, 각각 1:1 관계로 설정하였다.

#### 쪽지함 - 쪽지
하나의 쪽지함에서는 여러개의 쪽지를 주고받을 수 있다.   
하나의 쪽지는 반드시 하나의 쪽지함에 속해있다.   
따라서 쪽지함과 쪽지의 관계는 1:N 관계이다.

#### 유저 - 쪽지
한명의 유저는 여러개의 쪽지를 보낼 수 있지만, 하나의 쪽지는 한명의 유저가 보낸 것이므로, 1:N 관계이다.

## 3주차 - JPA 심화
### 필요한 비즈니스 로직 리스트
#### 유저
- [ ] 유저 회원 가입
- [ ] 유저 회원 탈퇴
- [ ] 유저 대학교 인증
- [ ] ~~유저 로그인~~ (TODO)
- [ ] ~~유저 로그아웃~~ (TODO)
#### 게시판
- [ ] 게시판 생성
- [ ] 게시판 관리자 변경
- [ ] 게시판 삭제 (소속 게시글, 댓글 모두 삭제)
#### 게시글
- [ ] 게시글 조회
- [ ] 게시글 작성 (사진 여러장 첨부)
- [ ] 게시글 삭제
- [ ] 게시글 수정
- [ ] 게시글 좋아요 누르기
- [ ] 게시글 좋아요 취소
#### 댓글
- [ ] 게시글 댓글 작성
- [ ] 게시글 댓글 삭제
- [ ] 대댓글 작성
- [ ] 대댓글 삭제
- [ ] 댓글 좋아요 누르기
- [ ] 댓글 좋아요 취소
#### 쪽지
- [ ] 최초 쪽지 전송 (쪽지방 생성 + 쪽지 전송)
- [ ] 쪽지 전송
- [ ] 쪽지방 삭제 (전체 쪽지 삭제)