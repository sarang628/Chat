# Chat 모듈

토랑 앱에서 채팅 기능 적용을 위해 만든 모듈.
STOMP 프로토콜을 사용 하여 소켓 통신 구현.

# 주요 기능

- 채팅방 리스트 화면
- 1:1 or 1:n 채팅 화면
- 소켓통신 usecase
  - 소켓 접속, 채팅방 구독 and 구독 해지
- Repository usecase
  - 채팅방 불러오기
  - 채팅 내용 불러오기
  - 채팅방 생성하기
  - 채팅 메세지 전송하기
  - 로그인 상태 확인하기
- 채팅방에서 이미지 업로드 하기

# 채팅방에서 이미지 업로드 하기

채팅방 이미지 업로드 전 이미지를 선택해야한다.
BottomSheetScaffold를 활용해 이미지 선택 기능을 구현하기로 했다. 

task: TorangBottomSheetModule에서 채팅방 이미지 업로드용 BottomSheet 만들기.


기기 내 이미지 선택은 InstagramGalleryModule를 사용하고 있다.

채팅방 에서 사용할 이미지 업로드 용 화면이 새로 필요 하였다.

task: InstagraModule에서 채팅방 용 이미지 업로드 화면 만들기

이미지 선택까지 완료하였다면

이미지 업로드 logic를 구현 해야한다.

