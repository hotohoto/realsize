package kr.jollybus.realsize;

public enum Status {
	INIT,
	BASIC, // 그리기 준비 끝
	DRAWING, // 그리는 중.
	DRAWING_DONE, // 이미 충돌 처리까지 했는데 사용자가 손을떼지 않은 경우.
	ADJUSTING_READY, // 점 안찍었음.
	ADJUSTING, // 점 1개 찍었음.
	ADJUSTING_DONE, //점 2개 찍었음.
}
