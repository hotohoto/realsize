package kr.jollybus.realsize;

public enum Status {
	INIT,
	BASIC, // �׸��� �غ� ��
	DRAWING, // �׸��� ��.
	DRAWING_DONE, // �̹� �浹 ó������ �ߴµ� ����ڰ� �������� ���� ���.
	ADJUSTING_READY, // �� �������.
	ADJUSTING, // �� 1�� �����.
	ADJUSTING_DONE, //�� 2�� �����.
}
