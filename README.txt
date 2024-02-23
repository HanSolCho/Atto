호스트들의 Alive 상태 체크 및 모니터링 API 서버 개발

회원가입 및 로그인 API

 회원가입
 
 POST /signUp
 
 요청 :
 parameter		type		example
 id		        String        	testId
 password		String		testPassword
 roleType		String		“admin” or “user”
 
 응답 :
	200 ok
	
	예외 :
	//중복 ID
	{ 
		"timestamp": "2024-02-23T10:08:01.3883948",
		"status": 409,
		"error": "CONFLICT",
		"code": "DUPLICATE_USERID",
		"message": "이미 존재하는 ID 입니다."
	}

 로그인 
 
 POST /signIn 
 
 요청 : 
 parameter		type		example
 id		        String        	testId
 password		String		testPassword

 
 응답 :
	{
		"id": "test100",
		"password": null,
		"accountRoleType": "ROLE_USER",
		"token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0MTAwOlJPTEVfVVNFUiIsImlzcyI6ImNvbGFiZWFyNzU0IiwiaWF0IjoxNzA4NjUxMTQzLCJleHAiOjE3MDg2NjE5NDN9.CtwJ-GPcVMv8DJLNjLfxAvl-r7NNGCPHTGFsDqoNX8nq3thQV5ejkXciLgh7afmcipLIwBCNB5MweHPsgGY9uA"
	}
	예외 :
	//로그인 정보 불일치
	{
		"timestamp": "2024-02-23T10:18:33.2712042",
		"status": 401,
		"error": "UNAUTHORIZED",
		"code": "UNAUTHORIZED_ACCOUNT",
		"message": "해당 계정 정보가 존재하지 않습니다"
	}

 호스트 관리 API
 
 등록
 
 POST /hostManager/addHost 
  
 요청 : 
 parameter		type		example
 id		        String        	testId
 password		String		testPassword
 ip 			String		127.0.0.1
 
 응답 :
 	200 ok
 예외 :
	//중복 데이터 입력시
	{ 
		"timestamp": "2024-02-23T11:19:41.8154708",
		"status": 409,
		"error": "CONFLICT",
		"code": "DUPLICATE_RESOURCE",
		"message": "데이터가 이미 존재합니다"
	}
	//100개 초과시
	{ 
		"timestamp": "2024-02-23T11:21:44.4481174",
		"status": 400,
		"error": "BAD_REQUEST",
		"code": "LIMIT_HOST_COUNT",
		"message": "등록된 호스트 명단이 제한된 값을 초과했습니다."
	}
	
 조회
 
 GET /hostManager/hostsInfo 

 요청 : 
 parameter		type		example
 id		        String        	testId

 
 응답:
	[
		{
			"name": "test",
			"ip": "127.0.0.1",
			"createDate": "2024-02-20T00:00:00.000+00:00",
			"updateDate": "2024-02-20T00:00:00.000+00:00",
			"reachable": false,
			"lastAliveDate": null,
			"num": 1
		},
		{
			"name": "test2",
			"ip": "127.0.0.1",
			"createDate": "2024-02-20T00:00:00.000+00:00",
			"updateDate": "2024-02-20T00:00:00.000+00:00",
			"reachable": false,
			"lastAliveDate": null,
			"num": 2
		}
	]
 
 수정
 
 PUT /hostManager/updateHost 
 
 요청 : 
 parameter		type		example
 name			String		testName
 ip			String		127.0.0.1
 id			String		testId
 num 			int		1
 
 응답:
  	 200 ok

 삭제
 
 DELETE /hostManager/deleteHost
 
 요청 : 
 parameter		type		example
 id			String		testId
 num 			int		1
 
 
 응답:
   	200 ok
   
호스트 현재 상태 조회 API

 현재 상태 조회
 
 GET /hostMonitoring/currentHostInfo 
 
 요청 : 
 parameter		type		example
 id			String		testId
 ip			String		127.0.0.1
 
 응답 : 
	{
		"name": "test100",
		"ip": "127.0.0.1",
		"createDate": "2024-02-23T11:21:14.000+00:00",
		"updateDate": "2024-02-23T11:21:14.000+00:00",
		"reachable": true,
		"lastAliveDate": "2024-02-23T11:47:30.000+00:00",
		"num": 106
	}
	
호스트 상태 모니터링 API
 
 GET /hostMonitoring/monitoringHost 
 
 요청 : 
 parameter		type		example
 id			String		testId
 ip			String		all or 127.0.0.1
- 전체 상태 조회 시 ip=all 그외의 경우 해당 ip 입력 

응답 : 
 // ip : all
	[
		{
			"name": "test",
			"ip": "127.0.0.1",
			"createDate": null,
			"updateDate": null,
			"reachable": true,
			"lastAliveDate": "2024-02-23T11:49:41.000+00:00",
			"num": 1
		},
		{
			"name": "test2",
			"ip": "127.0.0.2",
			"createDate": null,
			"updateDate": null,
			"reachable": true,
			"lastAliveDate": "2024-02-23T11:49:41.000+00:00",
			"num": 2
		}
	]
 // ip : 단일 ip
	{
		"name": "test100",
		"ip": "127.0.0.1",
		"createDate": "2024-02-23T11:21:14.000+00:00",
		"updateDate": "2024-02-23T11:21:14.000+00:00",
		"reachable": true,
		"lastAliveDate": "2024-02-23T11:47:30.000+00:00",
		"num": 106
	}

사용자 활동에 대한 기록 API

 GET /logMonitoring
 
 요청:
 parameter		type		example
 id 			String		testId
 
 응답 :
	[
		{
			"id": "test6",
			"content": "회원가입",
			"date": "2024-02-22T14:53:57.000+00:00",
			"result": "SUCESS"
		},
		{
			"id": "test6",
			"content": "중복된 ID",
			"date": "2024-02-22T15:21:39.000+00:00",
			"result": "FAIL"
		}
	]


*구현상 제약 사항 및 전달 사항

- 사용자 활동에 대한 감사 기능 제공
 -> 기능 자체는 구현하였으나 실제 사용량이 많아질 경우에는 해당 방식에는 부하가 클것으로 생각됩니다.
 -> 향후 개선한다면 각 기록마다 로그를 통해 파일로 기록하고 일별로 저장하여 관리할 수 있을것이라고 생각됩니다.

* 개인 사정상 주말 간 개발이 불가하여 추가적인 수정은 못하게 되었습니다.