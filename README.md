<h1 align = "center">일상 활동을 기록하다 활기록</h1>
<p align = "center">
<img src = "https://github.com/wngusv/Team1---healthcare/assets/154950245/2f65216c-c9e2-4a50-a7dc-07a754925232">
</p>

<h1>개요</h1>
📌 목적 과 대상

- 대상 : 건강한 라이프스타일을 추구하는 사용자들
- 목적 : 회원 개인의 신체상태를 기반으로 한 맞춤형 건강 관리를 제공하여, 운동, 식단, 물 섭취 등을 기록하고 분석하여 개인의 건강을 총괄적으로 지원 나눌 수 있도록 돕고자 함

📝 학습목표
- RDBMS의 이해 및 활용
- CRUD 기능 구현 및 SQL 문법 이해
- 사용자 입력값 유효성 검사

💻 개발환경
- 프로젝트 도구 : Eclipse, Github
- 사용 언어 : Java, SQL

📝 협업 프로세스
- 기존 주제였던 일기장에서 유저가 건강관련 기록을 할 수 있도록 주제 구체화
- 구현할 화면을 구성하고 화면에 따라 클래스를 나누고 분업
- 각자 맡은 클래스를 명확히 분배해서 충돌을 적게 겪을 수 있었음
- 검색을 통해서 사용해보지 못했던 라이브러리나 필요한 정보 수집

<h1>프로그램 소개</h1>

+ 프로그램 소개 사용자들에게 개인 맞춤형 건강 관리를 제공
회원가입 후 성별, 나이, 키, 몸무게 등을 입력하면 BMI를 계산해주며, 운동, 물 섭취, 식단 기록을 통해 건강한 라이프스타일을 갖출 수 있도록 도와줌
커뮤니티 게시판을 통해 사용자들 간의 소통과 정보 교환이 가능

<h1>상세 내용</h1>

### [ERD]
![image](https://github.com/wngusv/Team1---healthcare/assets/154950245/502731c3-4da6-46dc-8dc9-e0d94eb1d300)

![erd2](https://github.com/wngusv/Team1---healthcare/assets/154950245/cdaf29e2-4aa8-477d-b732-32759eb38a21)

### [로그인]

![로그인화면](https://github.com/wngusv/Team1---healthcare/assets/154950245/e3315f92-44dd-42d1-84ca-7f439767cf80)

### [회원가입]

![회원가입](https://github.com/wngusv/Team1---healthcare/assets/154950245/31005d89-44a0-48e1-a9f7-ade6df597a96)

### [온보딩]

- 회원가입시 온보딩 화면을 볼수있게 하여 사용자 경험을 개선하고 새로운 사용자들이 서비스를 더 쉽게 이해하고 활용할 수 있도록 지원합니다. 이를 통해 사용자 유입 및 유지율을 향상시키고 서비스의 가치를 최대화할 수 있습니다.


|                                                                                                                                          |                                                                                                                                          |                                                                                                                                          |                                                                                                                                          |                                                                                                                                          |
| :--------------------------------------------------------------------------------------------------------------------------------------: | :--------------------------------------------------------------------------------------------------------------------------------------: | :--------------------------------------------------------------------------------------------------------------------------------------: | :--------------------------------------------------------------------------------------------------------------------------------------: | :--------------------------------------------------------------------------------------------------------------------------------------: |
| <img src = "https://github.com/wngusv/Team1---healthcare/assets/154950245/5f437697-dd6d-415a-bd65-79d7eb2d555d" weight=200 height=313>   |  <img src = "https://github.com/wngusv/Team1---healthcare/assets/154950245/be8895f5-f9af-4d45-83e9-5be82cdf7406" weight=200 height=313>  | <img src = "https://github.com/wngusv/Team1---healthcare/assets/154950245/552f513f-3a51-44d7-93ba-5499b7874687" weight=200 height=313>   | <img src = "https://github.com/wngusv/Team1---healthcare/assets/154950245/6878562c-193d-4ae9-b8e5-a333eb9d20c2" weight=200 height=313>   |  <img src = "https://github.com/wngusv/Team1---healthcare/assets/154950245/9a16ca2a-5fd2-4554-a3fe-99feb28fffbd" weight=200 height=313>  |

### [프로필 수정]

- 이 메뉴를 통해 사용자는 자신의 프로필 정보를 언제든지 업데이트할 수 있습니다. 현재 몸무게를 변경할 경우, 이 정보는 데이터베이스의 몸무게 기록 테이블에 저장되어 몸무게 변화 메뉴의 차트에 자동으로 반영됩니다.

![프로필수정](https://github.com/wngusv/Team1---healthcare/assets/154950245/0a4a7b93-52dd-4ea4-9a00-010cd016a709)


### [메인]

![메인](https://github.com/wngusv/Team1---healthcare/assets/154950245/2ca79360-c9fe-4cb7-aeda-2d7059f5df9f)

### [식단 기록]
- 이 기능은 사용자가 식단 기록 메뉴를 통해 자신의 식단 상태를 한눈에 파악할 수 있도록 하였고 메인화면 반원그래프에 사용자의 하루 섭취 칼로리를 직관적으로 시각화 했습니다 .
- 권장 칼로리는 회원의 몸무게 필드값 과 활동량 필드값 으로 계산합니다. (`weight` * `activity_index`)
- 최근에 자주 먹은 음식 보기 메뉴는 사용자가 2번 이상 등록한 음식을 기록하여 편리하게 찾고 등록할 수 있도록 합니다. 이 기능은 사용자가 자주 먹는 음식을 빠르게 찾아서 기록할 수 있도록 돕습니다.
  
![식단기록](https://github.com/wngusv/Team1---healthcare/assets/154950245/a92591a5-667a-4fa5-8076-91e1ef8cd297)

![음식섭취창](https://github.com/wngusv/Team1---healthcare/assets/154950245/9ed81f23-8adc-4bb6-9a8c-1625a8b0ccf0) 

![최근에 자주 먹은 음식 보기](https://github.com/wngusv/Team1---healthcare/assets/154950245/f9d6accb-bf8d-48d5-9b25-372627488d01)

![섭취칼로리그래프](https://github.com/wngusv/Team1---healthcare/assets/154950245/825b232c-e1df-450c-959c-00f0456ead2c)

### [운동 기록]

- 운동 기록은 운동 종목에 따라 초당 소모 칼로리를 테이블에 등록하여 계산하며, 이를 통해 소모 칼로리를 추적합니다. 그러나 timediff 함수는 Expression으로 계산할 수 없기 때문에 트리거를 사용하여 계산합니다.

<img src = "https://github.com/wngusv/Team1---healthcare/assets/154950245/11b9ac36-f73c-4f7b-bced-08da6d3ca402" weight =300 height=400>

### [물 기록]

- 물 기록은 버튼을 통해 이미지 상태를 저장하여, 한 컵 기준으로 250ml의 물을 마신 기록을 할 수 있는 메뉴입니다.

![물기록](https://github.com/wngusv/Team1---healthcare/assets/154950245/e90ef988-4384-4658-b893-9e9d86b80349)

### [해빗 트래커]

- 해빗 트래커 메뉴에서는 운동 기록을 하게 되면 해당 날짜에 섭취한 칼로리 양을 시각적으로 표시합니다. 권장 칼로리 이하에서 섭취한 경우에는 파란색 폰트로 표시되어 사용자에게 긍정적인 피드백을 제공합니다. 그러나 권장 칼로리를 초과한 경우에는 빨간색 폰트로 표시되어 사용자에게 경고를 줌으로써 건강한 식단 관리에 도움을 줍니다.

![캘린더](https://github.com/wngusv/Team1---healthcare/assets/154950245/a1b940ca-bdab-41d2-824a-79a445553104)

![캘린더설명](https://github.com/wngusv/Team1---healthcare/assets/154950245/578df18c-7fd9-4553-aaab-c0f17cf71b36)


### [몸무게 변화]

- 이 기능은 사용자가 몸무게를 수정할 때마다 해당 변경 사항이 자동으로 차트에 반영됩니다. 사용자는 자신의 몸무게 변화를 시각적으로 추적하고 추세를 확인할 수 있습니다.

![차트](https://github.com/wngusv/Team1---healthcare/assets/154950245/cd4d53b4-2988-4a85-8780-a22457eb13cb)

### [게시판]

![게시판](https://github.com/wngusv/Team1---healthcare/assets/154950245/74f21e1a-93cc-4cb8-91f3-4874aa51e6fd)

### [몸무게 활동량 BMI 계산]

![계산](https://github.com/wngusv/Team1---healthcare/assets/154950245/c35c80c8-a198-4b8c-a893-db53bf951adf)

### [트리거]

![트리거1](https://github.com/wngusv/Team1---healthcare/assets/154950245/d662b3e2-df9c-43ab-87fe-5c5a21a473f7)

![트리거2](https://github.com/wngusv/Team1---healthcare/assets/154950245/f7c74406-452a-462e-ad3a-c3e9d5c453ba)

![트리거3](https://github.com/wngusv/Team1---healthcare/assets/154950245/b8c97dac-5046-4254-b7e0-1490b5b3f2af)


                 
