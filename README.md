# 자판기 관리 프로그램 / Vending Machine Management Program

대학교 Java Programming final project용 Swing 기반 자판기 관리 프로그램입니다. 3학년 요구 구현 범위까지만 의도적으로 구현했으며, 4학년/추가 요구인 cloud, Bluetooth, IrDA, embedded, web admin, backup-server/failover 구조는 구현하지 않았습니다.

## 실행 방법

```bash
javac -encoding UTF-8 -d out $(find src -name "*.java")
java -cp out Main
```

서버 소켓 테스트는 별도 터미널에서 다음처럼 실행합니다.

```bash
java -cp out network.VendingServer
```

로컬 테스트는 `localhost:5000`을 사용합니다. 여러 PC로 테스트할 때는 `network/VendingClient.java`의 `localhost`를 서버 PC의 IP 주소로 바꾸면 됩니다.

## 구현 체크리스트

| 구분 | 요구사항 | 구현 상태 |
|---|---|---|
| GUI | Swing GUI, 고객/관리자 화면 분리, 제목 `자판기 관리 프로그램` | O |
| 음료 | 기본 8종, 재고 10, 품절 표시, 보충 후 판매 재개 | O |
| 돈 투입 | 10/50/100/500/1000원, 지폐 5000원, 총 7000원 제한 | O |
| 동적 객체 | `MoneyInput`을 `new`로 생성하고 구매/환불 후 `null` 처리 | O |
| 거스름돈 | 생성자 기본 동전, 투입/환불/구매/수금 시 동전 증감 | O |
| 관리자 | 로그인, 비밀번호 변경, 매출 조회, 재고 보충, 돈 상태/수금, 이름/가격 변경 | O |
| 파일 | drinks/money/sales/admin/stock_history 파일 생성·저장·읽기 | O |
| 예외 | 사용자 정의 예외와 try-catch로 오류 표시 | O |
| 자료구조 | 직접 구현 Linked List, Stack, Queue, Tree 사용 | O |
| 정렬/검색 | 가격/재고/판매수 정렬, 이름/가격 검색, 트리 검색 | O |
| 스레드 | StockMonitorThread, AutoSaveThread, ServerSendThread | O |
| DB 계층 | SQLite 외부 의존성 없이 실행 가능한 LocalDatabaseManager 분리 | O |
| 소켓 | ServerSocket 5000, ClientHandler 다중 클라이언트, 서버 로그/요약 저장 | O |
| 제외 | cloud/Bluetooth/IrDA/web/backup-server 등 4학년·추가 기능 | O |
