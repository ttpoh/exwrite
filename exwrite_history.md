1주차(0409~0414)
**목표**

기획 수정 후 수요일까지 리사이클러뷰 구현하고 php와 통신 해서 서버에 DB 저장.

**토요일**

TO DO

- 앱 기획 하기(기획 최대한 빨리 하고 과제 진행)
- 레이아웃, 기능 먼저 설계.
- 전체적인 레이아웃 만들기.

메인 레이아웃 하단에 네비게이션 바 만들어서 회원가입, 로그인 액티비티로 인텐트 사용해서 이동.

**일요일**

TO DO

- 레이아웃 다 만들고
- 메인 페이지 상단 바 만들고 운동, 식단 목록 들어간 메뉴, 공유하기 버튼 만들기.
- 서버 연동해서 운동 목록 crud 구현.

문제

- java.lang.ClassCastException: androidx.constraintlayout.widget.ConstraintLayout$LayoutParams cannot be cast to androidx.drawerlayout.widget.DrawerLayout$LayoutParams (메인 레이아웃 구성 중 아이템 호출할 때 만난 오류.)

원인 : 네비게이션뷰가 콘스트레인트레이아웃에 속해 있어서 캐스팅할 수 없었다.

context와 this 이해 부족으로 bottom sheet dialog 불러오는데 시간을 썼다.

메인 화면 레이아웃 완성.

**월요일**

TO DO

- 레이아웃 마저 만들기
- 운동, 식단 기록 CRUD 구현하기.

-bottom sheet dialog 내 textview에 클릭 이벤트를 주고 싶은데 mainactivity가 oncreate 될 때 textview 객체를 가져오지 못한다. 

-mysql 사용자 계정에 외부 접속 권한을 주는 데 시간을 보냈다.

1. mysql에 root로 접속해서 root 계정을 만들고 사용자 계정에 권한을 준다.
2. 권한을 줄 때 grant all privileges on DB이름.* to 사용자계정@ip; 형태로 해야 하는데 '사용자계정'@'ip' 이렇게 따옴표를 붙이니 안 됐다.
3. 안드로이드와 php, mysql 연동을 해야 한다.

레이아웃 완성. 

**화요일** 

TO DO

-안드로이드와 php, mysql 연동

1. php와 mysql 연동 확인
- vscode와 mysql 연동이 안 돼서 많이 헤맸다. (mysql은 localhost와 연동해야 한다!)

1. 데이터베이스 개념 공부
- 스키마: 데이터베이스 설계도.
1. db 생성 후 안드로이드와 연동 
- 안드로이드와 연동하는 부분에서 막혔다.

problem 

Response.Listener responseListener= new Response.Listener() 이 부분이 활성화가 안 됨

[https://velog.io/@hoyaho/Android-Studio-onResponse-%EB%A5%BC-%EB%AC%B4%EC%8B%9C%ED%95%98%EB%8A%94-%EC%98%A4%EB%A5%98-%EB%B0%9C%EC%83%9D](https://velog.io/@hoyaho/Android-Studio-onResponse-%EB%A5%BC-%EB%AC%B4%EC%8B%9C%ED%95%98%EB%8A%94-%EC%98%A4%EB%A5%98-%EB%B0%9C%EC%83%9D) 

여기를 참고했는데 해결 실패. 

mysql, vscode와 연동하다가 mysql 삭제 후 재설치 했는데 

php 연결 모듈까지 같이 지워서 apm 재설치.

php, 안드로이드 연동하는 것 보류.

일단 php와 연동하는 부분은 두고 앱 기능 구현 위주로 진행해야겠다.

**수요일** 

 TO DO

- 리사이클러뷰 구현
- 회원가입 구현

- sharedpreferences로 운동 기록하는 리사이클러뷰 구현.
- 식사 기록하는 리사이클러뷰 구현.

하브루타 피드백 

- 리사이클러뷰 목록 레이아웃이 알아보기 힘들다.

**목요일**

TO DO

식사 기록 리사이클러뷰 구현.

- Caused by: java.lang.NullPointerException: Attempt to invoke virtual method 'java.lang.Object com.google.gson.Gson.fromJson(java.lang.String, java.lang.reflect.Type)' on a null object reference
(new 연산자로 Gson 객체 초기화를 해 주지 않았다.)
-java.lang.ClassCastException:  A cannot be cast to B, adapter 뷰홀더 바인딩에서 오류가 떠서 리사이클러뷰 목록 호출이 안 되었다.
(바인딩할 때 TypeToken의 ArrayList 타입 문제)

레이아웃 수정.
-리사이클러뷰 목록에서 데이터 값들의 항목별 이름을 넣어주었다.

발표 자료 만들고 연습.

- 주 초반, 기획 이후에 php 통신보다는 앱 기능 구현에 조금 더 집중했으면 개발 진행이 더 수월하게 되었을 것 같다.(우선순위!)
- 다음 주 목표: 회원 가입 및 로그인 기능 구현하고 shared에 저장한 데이터 php 통신 후 서버에 올리기!
