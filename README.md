# 우리 어디가?
안드로이드 전국 축제 정보 조회 앱 프로젝트

## 개요
- 전국의 축제정보를 지역별로 확인 & 검색할 수 있고,  
관심있는 축제에 대한 정보를 확인하고 저장할 수 있으며, 추억을 앨범에 기록할 수 있는 안드로이드 애플리케이션

### 개발일정
- 2020/08/03 ~ 2020/08/21

### 개발환경
- 운영체제 : Windows 10 64bit
- 언어 : Java - jdk1.8.0_251
- 개발툴 : Andorid Studio 3.5.1
- DB : SQLite3
- 테스트환경 : SANSUNG 갤럭시 S10 (API Level 29)

### 주요기능
1. 카카오톡 로그인
2. 전국의 축제 정보 확인
3. 좋아요한 축제의 거리와 정보 확인
4. 좋아요한 축제에 대한기록 남기기

### 프로그램 소개

#### 로딩 화면
  - 앱 구동시 첫 로딩 화면
  - 카카오톡 로그인창으로 전환. 이미 로그인된 정보가 있다면 메인화면으로 전환
  - 로그인이 성공 하면 축제정보 화면으로 전환 되며 프로필 사진과 하단에 메시지 출력  
  ![image](https://user-images.githubusercontent.com/63944004/94952346-cc52c180-0520-11eb-895e-0db202c0121f.png)  

#### 메인 화면
- 지역별로 축제정보를 관광공사의 API로 받아와 출력
- 상단 Appbar 하단 TabLayout, ViewPager . Fragment내부에 RecyclerView로 구성
- 검색 기능과 좋아요 리스트 관리, 서브 메인화면으로 전환할 수 있는 플로팅 버튼  
![image](https://user-images.githubusercontent.com/63944004/94952357-d07edf00-0520-11eb-9a79-fe6ef122c29f.png)  

1. 축제 상세 정보
- 축제 이미지를 클릭시 해당 축제의 자세한 정보를 Dialog로 출력  
![image](https://user-images.githubusercontent.com/63944004/94952373-d7a5ed00-0520-11eb-8760-ec0986b1ed92.png)  


2. 좋아요
- 좋아요 한 축제는 서브 메인 화면에서 google 맵으로 축제 위치 확인, 추억 남기기 가능  
![image](https://user-images.githubusercontent.com/63944004/94952377-da084700-0520-11eb-9fb7-dedd298adddc.png)  


3. 좋아요 리스트
- 좋아요한 축제의 리스트를 확인 가능
- 체크 후 삭제 버튼을 누르면 좋아요 리스트에서 삭제  
![image](https://user-images.githubusercontent.com/63944004/94952386-de346480-0520-11eb-82d0-62fe2a06683d.png)  


4. 검색
- 검색한 키워드의 축제 정보 리스트를 출력
- 검색 결과가 없다면 메시지 출력  
![image](https://user-images.githubusercontent.com/63944004/94952399-e2f91880-0520-11eb-9a54-c18d884cbdd1.png)  


#### 서브 메인 화면 
1.맵 
1-1 내 위치
- google map API를 이용해 휴대폰의 GPS로 현재 내 위치를 마커로 표시  
![image](https://user-images.githubusercontent.com/63944004/94952404-e68c9f80-0520-11eb-9387-78b59c9db336.png)  

1-2 축제 위치
- 좋아요한 축제의 위치를 마커로 표시해 현재 내 위치와 함께 확인 가능  
![image](https://user-images.githubusercontent.com/63944004/94952414-e9879000-0520-11eb-8fdd-16cb03949eef.png)  


2. 앨범
- 좋아요한 축제에 관해 추억을 남길 수 있는 기능
- 사진을 찍어 글과 함께 앨범에 저장해 언제든지 확인 가능  
![image](https://user-images.githubusercontent.com/63944004/94952420-edb3ad80-0520-11eb-9438-0d096d3e405d.png)  


3. 검색
- 좋아요한 축제를 google 검색을 통해 더욱 자세한 정보를 웹에서 확인 가능  
![image](https://user-images.githubusercontent.com/63944004/94952434-f1dfcb00-0520-11eb-86e0-15a574f113b7.png)



4. 로그아웃
- 카카오톡 로그인 정보를 삭제
- 로그아웃이 완료되면 첫 로그인 화면으로 전환됌  
![image](https://user-images.githubusercontent.com/63944004/94952441-f5735200-0520-11eb-9121-33eef173e405.png)
  
