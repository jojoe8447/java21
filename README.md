# Employee Management

Java 21 과 Spring Boot 로 만든 극단적으로 작은 직원 관리 예제입니다. 저장소는 데이터베이스 대신 `ConcurrentHashMap` 을 사용합니다.

## Run

```bash
./gradlew bootRun
```

## API

- `GET /api/employees`
- `GET /api/employees/{id}`
- `GET /api/employees?keyword=engineer`
- `POST /api/employees`
- `PUT /api/employees/{id}`
- `DELETE /api/employees/{id}`

## Java 8 -> 21 Features Used

- `record`: 직원, 요청, 응답 모델
- `sealed interface`: 생성/수정 명령 모델링
- pattern matching `switch`: 명령 분기 처리
- `switch` expression: 부서/상태 표시명 생성
- text blocks + `formatted()`: 응답 summary 문자열
- `List.of`, `Set.of`, `Set.copyOf`, `Objects.requireNonNullElse*`
- `Stream.toList()`
- virtual threads: `spring.threads.virtual.enabled=true`
